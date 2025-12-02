package com.patitas_web.presentation

import com.patitas_web.application.UserService
import com.patitas_web.di.Dependencies
import com.patitas_web.domain.entities.User
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureAuthRoutes() {
    val service = Dependencies.userService
    
    routing {
        route("/api/auth") {
            post("/register") {
                try {
                    val user = call.receive<User>()
                    val createdUser = service.register(user)
                    call.respond(HttpStatusCode.Created, createdUser)
                } catch (e: IllegalArgumentException) {
                    call.respond(HttpStatusCode.BadRequest, e.message ?: "Error")
                }
            }
            
            post("/login") {
                try {
                    val credentials = call.receive<LoginRequest>()
                    val (token, user) = service.login(credentials.email, credentials.password)
                    call.respond(AuthResponse(token, user))
                } catch (e: IllegalArgumentException) {
                    call.respond(HttpStatusCode.Unauthorized, e.message ?: "Error")
                }
            }
        }
    }
}

@kotlinx.serialization.Serializable
data class LoginRequest(val email: String, val password: String)

@kotlinx.serialization.Serializable
data class AuthResponse(val token: String, val user: User)
