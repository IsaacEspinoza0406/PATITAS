package com.patitas_web.domain.ports

import com.patitas_web.domain.entities.User

interface UserRepository {
    suspend fun findByEmail(email: String): User?
    suspend fun findById(id: Int): User?
    suspend fun create(user: User): User
    suspend fun roleExists(roleId: Int): Boolean
    suspend fun getRoleName(roleId: Int): String?
}
