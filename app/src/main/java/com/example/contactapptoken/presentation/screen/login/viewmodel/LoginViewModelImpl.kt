package com.example.contactapptoken.presentation.screen.login.viewmodel

import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.contactapptoken.app.App
import com.example.contactapptoken.data.model.ErrorData
import com.example.contactapptoken.data.source.local.MySharedPref
import com.example.contactapptoken.data.source.remote.request.LoginRequest
import com.example.contactapptoken.data.source.remote.response.LoginResponse
import com.example.contactapptoken.domain.auth.AuthRepository
import com.example.contactapptoken.domain.auth.AuthRepositoryImpl
import com.example.contactapptoken.utils.isConnected
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModelImpl : ViewModel(), LoginViewModel {
    private val repository: AuthRepository = AuthRepositoryImpl.getInstance()

    override val openRegisterScreenLiveData = MutableLiveData<Unit>()
    override val openHomeScreenLiveData = MutableLiveData<Unit>()
    override val networkUnAvailableLiveData = MutableLiveData<Unit>()
    override val progressLoadingLivedata = MutableLiveData<Boolean>()
    override val errorLiveData = MutableLiveData<String>()

    override fun loginSubmit(request: LoginRequest) {
        if (!isConnected()) {
            // internetga ulanmagan liveda
            networkUnAvailableLiveData.value = Unit
            return
        }
        progressLoadingLivedata.value = true

        repository.login(request, object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {

                progressLoadingLivedata.value = false

                if (response.isSuccessful) {
                    response.body()?.let {
                        val token = it.token
                        MySharedPref.getInstance().token = token
                        MySharedPref.getInstance().hasToken = true
                        MySharedPref.getInstance().phone = it.phone
                    }
                    openHomeScreenLiveData.value = Unit
                } else {
                    MySharedPref.getInstance().hasToken = false
                    val gson = Gson()
                    response.errorBody()?.let {
                        val error : ErrorData
                        try {
                            error = gson.fromJson(it.toString(), ErrorData::class.java)
                            Toast.makeText(App.instance, error.message, Toast.LENGTH_SHORT).show()
                            errorLiveData.postValue(error.message)
                        } catch (e: Exception){
                            Toast.makeText(App.instance, e.message, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                /*progressLoadingLivedata.value = false
                errorLiveData.value = t.message*/
                Toast.makeText(App.instance, t.message, Toast.LENGTH_SHORT).show()
                errorLiveData.postValue(t.message)
            }
        })
    }

    override fun openRegisterScreen() {
        openRegisterScreenLiveData.value = Unit
    }
}