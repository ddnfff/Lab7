package com.example.lab7.data

import com.example.lab7.model.PersonDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface PersonApi {
    @GET("users/{id}")  // Используем эндпоинты jsonplaceholder
    suspend fun getPersonById(@Path("id") id: Int): Response<PersonDto>

    @GET("users")
    suspend fun getAllPersons(): Response<List<PersonDto>>
}