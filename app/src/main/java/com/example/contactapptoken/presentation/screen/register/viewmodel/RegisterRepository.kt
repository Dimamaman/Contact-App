package com.example.contactapptoken.presentation.screen.register.viewmodel

import androidx.lifecycle.LiveData
import com.example.contactapptoken.data.source.remote.request.RegisterRequest

interface RegisterRepository {
    val progressLoadingLivedata: LiveData<Boolean>
    val networkUnAvailableLiveData: LiveData<Unit>
    val sendMessage: LiveData<String>
    val openVerifyScreenLiveData: LiveData<Unit>
    val errorLiveData: LiveData<String>
    val openLoginScreenLiveData: LiveData<Unit>

    fun register(register: RegisterRequest)
    fun openLoginScreen()
}