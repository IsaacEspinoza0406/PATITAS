package com.patitas_web.domain

import kotlinx.serialization.Serializable

@Serializable
data class AdoptionRequest(
    val adoptanteId: Int,
    val dogId: Int
)

@Serializable
data class AdoptionResponse(
    val id: Int,
    val adoptanteId: Int,
    val dogId: Int
)
