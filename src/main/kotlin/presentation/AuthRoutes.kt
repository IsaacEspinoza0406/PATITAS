package com.patitas_web.presentation

import com.patitas_web.application.AuthService
import com.patitas_web.domain.LoginRequest
import com.patitas_web.domain.RegisterRequest
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureAuthRoutes() {
    routing {
        route("/api/auth") {
            
            // POST /api/auth/register
            post("/register") {
                try {
                    val request = call.receive<RegisterRequest>()
                    val user = AuthService.register(request)
                    call.respond(HttpStatusCode.Created, user)
                } catch (e: IllegalArgumentException) {
                    call.respond(HttpStatusCode.BadRequest, mapOf("error" to e.message))
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.InternalServerError, mapOf("error" to "Error al registrar usuario"))
                }
            }

            // POST /api/auth/login
            post("/login") {
                try {
                    val request = call.receive<LoginRequest>()
                    val authResponse = AuthService.login(request)
                    call.respond(HttpStatusCode.OK, authResponse)
                } catch (e: IllegalArgumentException) {
                    call.respond(HttpStatusCode.Unauthorized, mapOf("error" to e.message))
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.InternalServerError, mapOf("error" to "Error al iniciar sesión"))
                }
            }
            
            // GET /api/auth/user/{id}
            get("/user/{id}") {
                try {
                    val userId = call.parameters["id"]?.toIntOrNull()
                    if (userId == null) {
                        call.respond(HttpStatusCode.BadRequest, mapOf("error" to "ID inválido"))
                        return@get
                    }
                    
                    val user = AuthService.findById(userId)
                    if (user != null) {
                        call.respond(HttpStatusCode.OK, user)
                    } else {
                        call.respond(HttpStatusCode.NotFound, mapOf("error" to "Usuario no encontrado"))
                    }
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.InternalServerError, mapOf("error" to "Error al buscar usuario"))
                }
            }
        }
    }
}
