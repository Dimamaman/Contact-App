package com.example.contactapptoken.domain.auth

import com.example.contactapptoken.data.source.remote.MyClient
import com.example.contactapptoken.data.source.remote.request.LoginRequest
import com.example.contactapptoken.data.source.remote.request.RegisterRequest
import com.example.contactapptoken.data.source.remote.request.VerifyCodeRequest
import com.example.contactapptoken.data.source.remote.response.LoginResponse
import com.example.contactapptoken.data.source.remote.response.RegisterResponse
import com.example.contactapptoken.data.source.remote.response.VerifyCodeResponse
import retrofit2.Callback

class AuthRepositoryImpl private constructor(): AuthRepository {
    companion object {
        private lateinit var instance: AuthRepository

        fun getInstance(): AuthRepository {
            if(!(Companion::instance.isInitialized)) {
                instance = AuthRepositoryImpl()
            }
            return instance
        }
    }

    override fun login(request: LoginRequest, callback: Callback<LoginResponse>) {
        MyClient.authApi.login(request).enqueue(callback)
    }

    override fun register(request: RegisterRequest, callback: Callback<RegisterResponse>) {
        MyClient.authApi.register(request).enqueue(callback)
    }

    override fun verifyCode(request: VerifyCodeRequest, callback: Callback<VerifyCodeResponse>) {
        MyClient.authApi.verifyCode(request).enqueue(callback)
    }

}