package com.example.contactapptoken.presentation.screen.home.viewmodel.impl

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.contactapptoken.app.App
import com.example.contactapptoken.data.model.ErrorData
import com.example.contactapptoken.data.source.local.MySharedPref
import com.example.contactapptoken.data.source.remote.request.AddContactRequest
import com.example.contactapptoken.data.source.remote.request.EditContactRequest
import com.example.contactapptoken.data.source.remote.response.AddContactResponse
import com.example.contactapptoken.data.source.remote.response.AllContacts
import com.example.contactapptoken.data.source.remote.response.EditContactResponse
import com.example.contactapptoken.data.source.remote.response.GetAllContactsResponse
import com.example.contactapptoken.domain.contact.ContactRepository
import com.example.contactapptoken.domain.contact.ContactRepositoryImpl
import com.example.contactapptoken.presentation.screen.home.viewmodel.HomeScreenRepository
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeScreenRepositoryImpl : ViewModel(), HomeScreenRepository {
    private val contactRepository: ContactRepository = ContactRepositoryImpl.getInstance()

    override val progressLoadingLivedata = MutableLiveData<Boolean>()
    override val getContacts = MutableLiveData<List<AllContacts>>()
    override val errorLiveData = MutableLiveData<String>()
    override val openLoginScreenLiveData = MutableLiveData<Unit>()

    override fun getContacts() {
        progressLoadingLivedata.value = true

        contactRepository.getAllContacts(object : Callback<List<AllContacts>> {
            override fun onResponse(
                call: Call<List<AllContacts>>,
                response: Response<List<AllContacts>>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        Log.d("DDD","${it[0]}")
                        getContacts.value = it
                        progressLoadingLivedata.value = false
                    }
                } else {
                    val gson = Gson()
                    response.errorBody()?.let {
                        val error = gson.fromJson(it.string(), ErrorData::class.java)
                        errorLiveData.value = error.message
                    }
                }
            }

            override fun onFailure(call: Call<List<AllContacts>>, t: Throwable) {
                errorLiveData.value = t.message
            }
        })
    }

    override fun deleteNote(id: Int) {
        contactRepository.deleteNote(id,object : Callback<Unit> {
            override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                if (response.isSuccessful) {
                    getContacts()
                } else {
                    val gson = Gson()
                    response.errorBody()?.let {
                        val error = gson.fromJson(it.string(),ErrorData::class.java)
                        Toast.makeText(App.instance, error.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }

            override fun onFailure(call: Call<Unit>, t: Throwable) {
                Toast.makeText(App.instance, t.message, Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun editContact(editContact: EditContactRequest) {
        contactRepository.editContact(editContact,object : Callback<EditContactResponse> {
            override fun onResponse(
                call: Call<EditContactResponse>,
                response: Response<EditContactResponse>
            ) {
                if (response.isSuccessful) {
                    getContacts()
                } else {
                    val gson = Gson()
                    response.errorBody()?.let {
                        val error = gson.fromJson(it.string(),ErrorData::class.java)
                        Toast.makeText(App.instance, error.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }

            override fun onFailure(call: Call<EditContactResponse>, t: Throwable) {
                Toast.makeText(App.instance, t.message, Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun addContact(contact: AddContactRequest) {
        contactRepository.addContact(contact,object: Callback<AddContactResponse> {
            override fun onResponse(
                call: Call<AddContactResponse>,
                response: Response<AddContactResponse>
            ) {
                if(response.isSuccessful){
                    getContacts()
                } else {
                    val gson = Gson()
                    response.errorBody()?.let {
                        val error = gson.fromJson(it.string(),ErrorData::class.java)
                        Toast.makeText(App.instance, error.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }

            override fun onFailure(call: Call<AddContactResponse>, t: Throwable) {
                Toast.makeText(App.instance, t.message, Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun logOut() {
        MySharedPref.getInstance().hasToken = false
        MySharedPref.getInstance().token = ""
        openLoginScreenLiveData.value = Unit
    }
}