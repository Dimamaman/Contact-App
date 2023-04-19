package com.example.contactapptoken.data.source.remote

import com.example.contactapptoken.data.source.remote.api.AuthApi
import com.example.contactapptoken.data.source.remote.api.ContactApi
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object MyClient {
    private val BASE_URL = "https://660a-195-158-16-140.ngrok-free.app"

    private val okHttpClient = OkHttpClient
        .Builder()
//        .addInterceptor()
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val authApi: AuthApi = retrofit.create(AuthApi::class.java)
    val contactApi: ContactApi = retrofit.create(ContactApi::class.java)

}