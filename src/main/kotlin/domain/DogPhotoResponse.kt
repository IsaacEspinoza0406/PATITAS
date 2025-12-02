package com.patitas_web.domain

import kotlinx.serialization.Serializable

@Serializable
data class DogPhotoResponse(
    val id: Int,
    val dogId: Int,
    val photoUrl: String,
    val description: String?
)
