package com.example.contactapptoken.presentation.screen.verify.viewmodel

import androidx.lifecycle.LiveData
import com.example.contactapptoken.data.source.remote.request.VerifyCodeRequest
import kotlinx.coroutines.flow.MutableSharedFlow

interface VerifyRepository {
    val progressLoadingLivedata: LiveData<Boolean>
    val networkUnAvailableLiveData: LiveData<Unit>
    val openHomeScreenLiveData: LiveData<Unit>
    val errorLiveData: LiveData<String>
    val openRegisterScreenLiveData: MutableSharedFlow<Unit>

    fun clickSubmit(verify: VerifyCodeRequest)
    fun openRegisterScreen()
}