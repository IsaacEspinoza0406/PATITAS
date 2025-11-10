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
    val photos: List<DogPhotoResponse> = emptyList()
)

@Serializable
data class DogPhotoRequest(
    val photoUrl: String,
    val description: String?
)

@Serializable  
data class DogPhotoResponse(
    val id: Int,
    val dogId: Int,
    val photoUrl: String,
    val description: String?
)