package com.patitas_web.domain.entities

data class Role(
    val id: Int,
    val name: String
)

data class User(
    val id: Int = 0,
    val name: String,
    val email: String,
    val password: String,
    val roleId: Int,
    val roleName: String? = null // Joined field
)
