package com.example.contactapptoken.presentation.screen.verify.viewmodel.impl

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.contactapptoken.data.model.ErrorData
import com.example.contactapptoken.data.source.local.MySharedPref
import com.example.contactapptoken.data.source.remote.request.VerifyCodeRequest
import com.example.contactapptoken.data.source.remote.response.VerifyCodeResponse
import com.example.contactapptoken.domain.auth.AuthRepository
import com.example.contactapptoken.domain.auth.AuthRepositoryImpl
import com.example.contactapptoken.presentation.screen.verify.viewmodel.VerifyRepository
import com.example.contactapptoken.utils.isConnected
import com.google.gson.Gson
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class VerifyRepositoryImpl : ViewModel(), VerifyRepository {

    private val repository: AuthRepository = AuthRepositoryImpl.getInstance()

    override val progressLoadingLivedata = MutableLiveData<Boolean>()
    override val networkUnAvailableLiveData = MutableLiveData<Unit>()
    override val openHomeScreenLiveData = MutableLiveData<Unit>()
    override val errorLiveData = MutableLiveData<String>()
    override val openRegisterScreenLiveData = MutableSharedFlow<Unit>()


    override fun clickSubmit(verify: VerifyCodeRequest) {
        if (!isConnected()) {
            networkUnAvailableLiveData.value = Unit
            return
        }
        progressLoadingLivedata.value = true
        Log.d("DDD","VERIFY -> $verify")
        repository.verifyCode(verify, object : Callback<VerifyCodeResponse> {
            override fun onResponse(
                call: Call<VerifyCodeResponse>,
                response: Response<VerifyCodeResponse>
            ) {
                progressLoadingLivedata.value = false
                if (response.isSuccessful) {
                    response.body().let {
                        MySharedPref.getInstance().token = it!!.token
                        MySharedPref.getInstance().hasToken = true
                        MySharedPref.getInstance().phone = it.phone
                    }
                    openHomeScreenLiveData.value = Unit
                } else {
                    MySharedPref.getInstance().hasToken = false
                    val gson = Gson()
                    response.errorBody()?.let {
                        val error = gson.fromJson(it.string(), ErrorData::class.java)
                        errorLiveData.value = error.message
                    }
                }
            }

            override fun onFailure(call: Call<VerifyCodeResponse>, t: Throwable) {
                progressLoadingLivedata.value = false
                errorLiveData.value = t.message
            }
        })
    }

    override fun openRegisterScreen() {
        viewModelScope.launch {
            openRegisterScreenLiveData.emit(Unit)
        }
    }
}