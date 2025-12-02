package com.patitas_web.domain

data class DogPhotoResponse(
    val id: Int,
    val dogId: Int,
    val photoUrl: String,
    val description: String?
)
