package com.patitas_web.domain
import kotlinx.serialization.Serializable

@Serializable
data class DogsRequest(
    val id: Int,
    val name: String,
    val age: Int?,
    val breed: String?,
    val history: String?,
    val sterilized: String?,
    val adopted: String?,
    val created_by: Int
)

@Serializable
data class DogsFullResponse(
    val id: Int,
    val name: String,
    val age: Int?,
    val breed: String?,
    val history: String?,
    val sterilized: String?,
    val adopted: String?,
    val created_by: Int
)