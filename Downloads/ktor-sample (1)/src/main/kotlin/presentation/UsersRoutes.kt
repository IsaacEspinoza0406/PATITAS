package com.patitas_web.presentation

import com.patitas_web.application.PaymentService
import com.patitas_web.application.UsersService
import com.patitas_web.domain.PaymentRequest
import com.patitas_web.domain.UserRequest
import com.patitas_web.domain.UserFullResponse
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.response.respond
import io.ktor.server.routing.*

fun Application.configureUsersRoutes() {
    val service = UsersService()

    routing {
        route("/users") {
            get{
                val users = service.findAll()
                call.respond(HttpStatusCode.OK, users)
            }

            post {
                try {
                    val request = call.receive<UserRequest>()
                    val nuevoUsuario = service.create(request)
                    call.respond(HttpStatusCode.Created, nuevoUsuario)
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.BadRequest, "Error al crear el usuario: ${e.message}")
                }
            }

            delete {
                try{
                    val request = call.receive<UserRequest>()
                    val eliminarUsuario = service.delete(request)
                    call.respond(HttpStatusCode.OK, eliminarUsuario)
                }catch (e: Exception){
                    call.respond(HttpStatusCode.BadRequest, "Error al eliminar usuario: ${e.message}")
                }
            }
        }
    }
}
