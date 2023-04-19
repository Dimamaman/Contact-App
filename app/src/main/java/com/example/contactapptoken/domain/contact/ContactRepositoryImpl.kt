package com.example.contactapptoken.domain.contact

import com.example.contactapptoken.data.source.local.MySharedPref
import com.example.contactapptoken.data.source.remote.MyClient
import com.example.contactapptoken.data.source.remote.request.AddContactRequest
import com.example.contactapptoken.data.source.remote.request.EditContactRequest
import com.example.contactapptoken.data.source.remote.response.AddContactResponse
import com.example.contactapptoken.data.source.remote.response.AllContacts
import com.example.contactapptoken.data.source.remote.response.EditContactResponse
import retrofit2.Callback

class ContactRepositoryImpl: ContactRepository {
    private val contactApi = MyClient.contactApi
    private val token = MySharedPref.getInstance().token

    companion object {
        private lateinit var instance: ContactRepository

        fun getInstance(): ContactRepository {
            if (!(::instance.isInitialized)) {
                instance = ContactRepositoryImpl()
            }
            return instance
        }
    }


    override fun getAllContacts(callBack: Callback<List<AllContacts>>) {
        contactApi.getAllContacts(token).enqueue(callBack)
    }

    override fun deleteNote(id: Int, callBack: Callback<Unit>) {
        contactApi.deleteNote(token,id).enqueue(callBack)
    }

    override fun editContact(editContact: EditContactRequest, callback: Callback<EditContactResponse>) {
        contactApi.editContact(token,editContact).enqueue(callback)
    }

    override fun addContact(contact: AddContactRequest, callback: Callback<AddContactResponse>) {
        contactApi.addContact(token,contact).enqueue(callback)
    }
}