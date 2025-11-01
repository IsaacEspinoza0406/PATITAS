package com.patitas_web.domain
import kotlinx.serialization.Serializable

@Serializable
data class DogRequest(
    val name: String,
    val age: Int?,
    val breed: String?,
    val history: String?,
    val sterilized: String?,
    val adopted: String?,

)

@Serializable
data class DogResponse(
    val id: Int,
    val name: String,
    val age: Int?,
    val breed: String?,
    val history: String?,
    val sterilized: String?,
    val adopted: String?,
)