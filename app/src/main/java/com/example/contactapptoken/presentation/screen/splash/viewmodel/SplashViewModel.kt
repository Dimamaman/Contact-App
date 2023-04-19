package com.example.contactapptoken.presentation.screen.splash.viewmodel

import androidx.lifecycle.LiveData

interface SplashViewModel {
    fun findScreen()

    val openNextScreenLiveData: LiveData<Unit>
    val openHomeScreenLiveData: LiveData<Unit>
    val openLoginScreenLiveData: LiveData<Unit>
}