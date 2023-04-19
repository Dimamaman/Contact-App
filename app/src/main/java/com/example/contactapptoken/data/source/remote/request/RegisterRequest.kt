package com.example.contactapptoken.data.source.remote.request

data class RegisterRequest(
    val firstName: String,
    val lastName: String,
    val password: String,
    val phone: String
)