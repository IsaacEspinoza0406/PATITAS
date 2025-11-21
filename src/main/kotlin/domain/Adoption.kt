package com.patitas_web.domain

import kotlinx.serialization.Serializable

@Serializable
data class AdoptionRequest(
    val userId: Int,
    val dogId: Int
)

@Serializable
data class AdoptionResponse(
    val id: Int,
    val userId: Int,
    val dogId: Int
)
