package com.patitas_web.domain

import kotlinx.serialization.Serializable

@Serializable
data class RegisterRequest(
    val name: String,
    val email: String,
    val password: String,
    val roleId: Int,
)

@Serializable
data class LoginRequest(
    val email: String,
    val password: String
)

@Serializable
data class AuthResponse(
    val token: String,
    val user: UserResponse
)

@Serializable
data class UserResponse(
    val id: Int,
    val name: String,
    val email: String,
    val roleId: Int,
    val roleName: String,
)