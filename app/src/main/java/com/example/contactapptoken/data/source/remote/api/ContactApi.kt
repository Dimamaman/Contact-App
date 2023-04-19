package com.example.contactapptoken.data.source.remote.api

import com.example.contactapptoken.data.source.remote.request.AddContactRequest
import com.example.contactapptoken.data.source.remote.request.EditContactRequest
import com.example.contactapptoken.data.source.remote.response.AddContactResponse
import com.example.contactapptoken.data.source.remote.response.AllContacts
import com.example.contactapptoken.data.source.remote.response.EditContactResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface ContactApi {

    @POST("/api/v1/contact")
    fun addContact(@Header("token") token: String, @Body request: AddContactRequest): Call<AddContactResponse>

    @GET("/api/v1/contact")
    fun getAllContacts(@Header("token") token: String): Call<List<AllContacts>>

    @DELETE("api/v1/contact")
    fun deleteNote(@Header("token") token: String, @Query("id") id: Int): Call<Unit>

    @PUT("api/v1/contact")
    fun editContact(@Header("token") token: String,@Body request: EditContactRequest): Call<EditContactResponse>

}