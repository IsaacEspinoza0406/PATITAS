package com.patitas_web.application

import com.patitas_web.domain.entities.User
import com.patitas_web.domain.ports.UserRepository
import com.patitas_web.infrastructure.security.JwtConfig
import org.mindrot.jbcrypt.BCrypt

class UserService(private val repository: UserRepository) {

    suspend fun register(user: User): User {
        if (repository.findByEmail(user.email) != null) {
            throw IllegalArgumentException("El email ya está registrado")
        }
        if (!repository.roleExists(user.roleId)) {
            throw IllegalArgumentException("El rol no existe")
        }
        
        val hashedPassword = BCrypt.hashpw(user.password, BCrypt.gensalt())
        val userWithHashedPassword = user.copy(password = hashedPassword)
        
        return repository.create(userWithHashedPassword)
    }

    suspend fun login(email: String, password: String):Pair<String, User> {
        val user = repository.findByEmail(email)
            ?: throw IllegalArgumentException("Credenciales inválidas")

        if (!BCrypt.checkpw(password, user.password)) {
            throw IllegalArgumentException("Credenciales inválidas")
        }

        val token = JwtConfig.generateToken(user)
        return Pair(token, user)
    }
}
