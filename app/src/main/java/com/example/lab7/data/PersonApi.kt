package com.example.lab7.data

import com.example.lab7.model.PersonDto
import com.example.lab7.model.TodoDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PersonApi {
    @GET("users/{id}")
    suspend fun getPersonById(@Path("id") id: Int): Response<PersonDto>

    @GET("users")
    suspend fun getAllPersons(): Response<List<PersonDto>>

    @GET("todos")
    suspend fun getTodosByUserId(@Query("userId") userId: Int): Response<List<TodoDto>>
}