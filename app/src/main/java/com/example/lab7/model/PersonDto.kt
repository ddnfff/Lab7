// Замените текущий файл на этот
package com.example.lab7.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PersonDto(
    @Json(name = "id") val id: Int,
    @Json(name = "name") val name: String,
    @Json(name = "company") val company: CompanyDto? = null
) {
    fun toEntity(): PersonEntity = PersonEntity(
        id = id,
        name = name,
        age = 0, // По умолчанию, так как API не отдает возраст
        profession = company?.name ?: "Unknown"
    )
}

@JsonClass(generateAdapter = true)
data class CompanyDto(
    @Json(name = "name") val name: String
)