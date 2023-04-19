package com.example.contactapptoken.data.source.remote.request

data class VerifyCodeRequest(
    val phone: String,
    val code: String
)