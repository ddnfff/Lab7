package com.example.lab7.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PersonDto(
    @Json(name = "id") val id: Int,
    @Json(name = "name") val name: String,
    @Json(name = "age") val age: Int? = null,  // Делаем nullable
    @Json(name = "profession") val profession: String? = null
) {
    fun toEntity(): PersonEntity = PersonEntity(
        id = id,
        name = name,
        age = age ?: 0,  // Значение по умолчанию
        profession = profession ?: ""
    )
}