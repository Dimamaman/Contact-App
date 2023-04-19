package com.example.contactapptoken.domain.auth

import com.example.contactapptoken.data.source.remote.request.LoginRequest
import com.example.contactapptoken.data.source.remote.request.RegisterRequest
import com.example.contactapptoken.data.source.remote.request.VerifyCodeRequest
import com.example.contactapptoken.data.source.remote.response.LoginResponse
import com.example.contactapptoken.data.source.remote.response.RegisterResponse
import com.example.contactapptoken.data.source.remote.response.VerifyCodeResponse
import retrofit2.Call
import retrofit2.Callback

interface AuthRepository {

    fun login(request: LoginRequest, callback: Callback<LoginResponse>)

    fun register(request: RegisterRequest, callback: Callback<RegisterResponse>)

    fun verifyCode(request: VerifyCodeRequest, callback: Callback<VerifyCodeResponse>)

}