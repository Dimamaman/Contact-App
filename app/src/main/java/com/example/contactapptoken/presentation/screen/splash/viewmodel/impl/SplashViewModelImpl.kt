package com.example.contactapptoken.presentation.screen.splash.viewmodel.impl

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.contactapptoken.data.source.local.MySharedPref
import com.example.contactapptoken.data.source.remote.request.LoginRequest
import com.example.contactapptoken.data.source.remote.response.LoginResponse
import com.example.contactapptoken.domain.auth.AuthRepository
import com.example.contactapptoken.domain.auth.AuthRepositoryImpl
import com.example.contactapptoken.presentation.screen.splash.viewmodel.SplashViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

class SplashViewModelImpl : ViewModel(), SplashViewModel {

    private val repository: AuthRepository = AuthRepositoryImpl.getInstance()

    override val openNextScreenLiveData = MutableLiveData<Unit>()
    override val openHomeScreenLiveData = MutableLiveData<Unit>()
    override val openLoginScreenLiveData = MutableLiveData<Unit>()

    init {
        val thread = Executors.newSingleThreadExecutor() // Yagona boshqa oqimni shakllantiradi
        thread.execute { // ishga tushiradi
            thread.awaitTermination(2, TimeUnit.SECONDS)
            openNextScreenLiveData.postValue(Unit) // Qimatini o'zgaritradi.
            thread.shutdown()
        }
    }

    override fun findScreen() {
        if (MySharedPref.getInstance().hasToken) {
            val phone = MySharedPref.getInstance().phone
            val password = MySharedPref.getInstance().parol
            repository.login(LoginRequest(phone, password), object : Callback<LoginResponse> {
                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: Response<LoginResponse>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            MySharedPref.getInstance().token = it.token
                        }
                        openHomeScreenLiveData.value = Unit
                    } else {
                        openLoginScreenLiveData.value = Unit
                    }
                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    MySharedPref.getInstance().hasToken = false
                    openLoginScreenLiveData.value = Unit
                }
            })
        } else {
            openLoginScreenLiveData.value = Unit
        }
    }
}
