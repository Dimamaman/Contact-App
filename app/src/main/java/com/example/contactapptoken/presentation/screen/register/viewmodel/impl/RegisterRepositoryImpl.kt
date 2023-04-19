package com.example.contactapptoken.presentation.screen.register.viewmodel.impl

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.contactapptoken.app.App
import com.example.contactapptoken.data.model.ErrorData
import com.example.contactapptoken.data.source.remote.request.RegisterRequest
import com.example.contactapptoken.data.source.remote.response.RegisterResponse
import com.example.contactapptoken.domain.auth.AuthRepository
import com.example.contactapptoken.domain.auth.AuthRepositoryImpl
import com.example.contactapptoken.presentation.screen.register.viewmodel.RegisterRepository
import com.example.contactapptoken.utils.isConnected
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterRepositoryImpl : ViewModel(), RegisterRepository {

    private val repository: AuthRepository = AuthRepositoryImpl.getInstance()

    override val progressLoadingLivedata = MutableLiveData<Boolean>()
    override val networkUnAvailableLiveData = MutableLiveData<Unit>()
    override val sendMessage = MutableLiveData<String>()
    override val openVerifyScreenLiveData = MutableLiveData<Unit>()
    override val errorLiveData = MutableLiveData<String>()
    override val openLoginScreenLiveData = MutableLiveData<Unit>()


    override fun register(register: RegisterRequest) {
        if (!isConnected()) {
            networkUnAvailableLiveData.value = Unit
            return
        }
        progressLoadingLivedata.value = true

        repository.register(register, object : Callback<RegisterResponse> {

            override fun onResponse(
                call: Call<RegisterResponse>,
                response: Response<RegisterResponse>
            ) {
                progressLoadingLivedata.value = false

                if (response.isSuccessful) {
                    response.body().let {
                        sendMessage.value = it?.message
                    }
                    openVerifyScreenLiveData.value = Unit
                } else {
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

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                /*progressLoadingLivedata.value = false
                errorLiveData.value = t.message*/
                Toast.makeText(App.instance, t.message, Toast.LENGTH_SHORT).show()
                errorLiveData.postValue(t.message)
            }
        })
    }

    override fun openLoginScreen() {
        openLoginScreenLiveData.value = Unit
    }
}