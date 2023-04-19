package com.example.contactapptoken.data.source.remote.response

data class VerifyCodeResponse(
    val token: String,
    val phone: String
)