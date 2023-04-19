package com.example.contactapptoken.presentation.screen.login.viewmodel

import androidx.lifecycle.LiveData
import com.example.contactapptoken.data.source.remote.request.LoginRequest

interface LoginViewModel {
    val openRegisterScreenLiveData: LiveData<Unit>
    val openHomeScreenLiveData: LiveData<Unit>
    val networkUnAvailableLiveData: LiveData<Unit>
    val progressLoadingLivedata: LiveData<Boolean>
    val errorLiveData: LiveData<String>

    fun loginSubmit(request: LoginRequest)
    fun openRegisterScreen()

}