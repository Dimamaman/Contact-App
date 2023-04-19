package com.example.contactapptoken.presentation.screen.home.viewmodel

import androidx.lifecycle.LiveData
import com.example.contactapptoken.data.source.remote.request.AddContactRequest
import com.example.contactapptoken.data.source.remote.request.EditContactRequest
import com.example.contactapptoken.data.source.remote.response.AllContacts
import com.example.contactapptoken.data.source.remote.response.GetAllContactsResponse

interface HomeScreenRepository {
    val progressLoadingLivedata: LiveData<Boolean>
    val getContacts: LiveData<List<AllContacts>>
    val errorLiveData: LiveData<String>
    val openLoginScreenLiveData: LiveData<Unit>

    fun getContacts()
    fun deleteNote(id: Int)
    fun editContact(editContact: EditContactRequest)
    fun addContact(contact: AddContactRequest)
    fun logOut()
}