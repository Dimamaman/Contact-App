package com.example.contactapptoken.domain.contact

import com.example.contactapptoken.data.source.remote.request.AddContactRequest
import com.example.contactapptoken.data.source.remote.request.EditContactRequest
import com.example.contactapptoken.data.source.remote.response.AddContactResponse
import com.example.contactapptoken.data.source.remote.response.AllContacts
import com.example.contactapptoken.data.source.remote.response.EditContactResponse
import retrofit2.Callback

interface ContactRepository {
    fun getAllContacts(callBack: Callback<List<AllContacts>>)
    fun deleteNote(id: Int,callBack: Callback<Unit>)
    fun editContact(editContact: EditContactRequest, callback: Callback<EditContactResponse>)
    fun addContact(contact: AddContactRequest, callback: Callback<AddContactResponse>)
}