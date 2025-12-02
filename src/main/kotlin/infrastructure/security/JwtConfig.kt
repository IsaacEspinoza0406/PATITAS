package com.patitas_web.infrastructure.security

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.patitas_web.domain.entities.User
import java.util.*

object JwtConfig {
    private const val secret = "patitas-secret-key" // TODO: Use environment variable in production
    private const val issuer = "patitas-web"
    private const val validityInMs = 36_000_00 * 24 // 24 hours
    private val algorithm = Algorithm.HMAC256(secret)

    val verifier: JWTVerifier = JWT.require(algorithm)
        .withIssuer(issuer)
        .build()

    fun generateToken(user: User): String {
        return JWT.create()
            .withSubject("Authentication")
            .withIssuer(issuer)
            .withClaim("id", user.id)
            .withClaim("email", user.email)
            .withClaim("roleId", user.roleId)
            .withExpiresAt(getExpiration())
            .sign(algorithm)
    }

    private fun getExpiration() = Date(System.currentTimeMillis() + validityInMs)
}
