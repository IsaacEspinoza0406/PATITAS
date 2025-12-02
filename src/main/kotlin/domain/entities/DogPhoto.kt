package com.patitas_web.domain.entities

import kotlinx.serialization.Serializable

@Serializable
data class DogPhoto(
    val id: Int = 0,
    val dogId: Int,
    val photoUrl: String,
    val description: String?
)
