package com.patitas_web.domain

import kotlinx.serialization.Serializable

@Serializable
data class UserRequest(
    val id: Int,
    val role_id: Int,
    val name: String,
    val email: String,
    val password: String,
    val telefono: String
)

@Serializable
data class UserFullResponse(
    val id: Int,
    val role_id: Int,
    val name: String,
    val email: String,
    val password: String,
    val telefono: String
)