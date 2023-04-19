package com.example.contactapptoken.data.source.remote.api

import com.example.contactapptoken.data.source.remote.request.LoginRequest
import com.example.contactapptoken.data.source.remote.request.RegisterRequest
import com.example.contactapptoken.data.source.remote.request.VerifyCodeRequest
import com.example.contactapptoken.data.source.remote.response.LoginResponse
import com.example.contactapptoken.data.source.remote.response.RegisterResponse
import com.example.contactapptoken.data.source.remote.response.VerifyCodeResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface AuthApi {

    @POST("/api/v1/login")
    fun login(@Body request: LoginRequest): Call<LoginResponse>

    @POST("/api/v1/register")
    fun register(@Body request: RegisterRequest): Call<RegisterResponse>

    @POST("/api/v1/register/verify")
    fun verifyCode(@Body request: VerifyCodeRequest): Call<VerifyCodeResponse>

}