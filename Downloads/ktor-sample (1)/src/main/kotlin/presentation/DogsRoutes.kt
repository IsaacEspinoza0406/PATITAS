package com.patitas_web.presentation

import com.patitas_web.application.DogsService
import com.patitas_web.domain.DogsRequest
import io.ktor.http.HttpStatusCode
import io.ktor.server.request.receive
import io.ktor.server.routing.*
import io.ktor.server.application.*
import io.ktor.server.response.*

fun Application.configureDogsRoutes() {
    val service = DogsService()

    routing {
        route("/dogs") {
            get {
                call.respond(service.findAll())
            }

            post {
                val request = call.receive<DogsRequest>()
                val dog = service.create(request)
                call.respond(HttpStatusCode.Created, dog)
            }

            put("/{id}") {
                val id = call.parameters["id"]?.toIntOrNull()
                if (id == null) {
                    call.respond(HttpStatusCode.BadRequest, "ID de perro invalido.")
                    return@put
                }
                val request = call.receive<DogsRequest>()
                val updateDog = service.update(id, request)
                if (updateDog != null) {
                    call.respond(HttpStatusCode.OK, updateDog)
                } else {
                    call.respond(HttpStatusCode.NotFound, "Perro no encontrado")
                }
            }

            delete("/{id}") {
                val id = call.parameters["id"]?.toIntOrNull()
                if (id == null) {
                    call.respond(HttpStatusCode.BadRequest, "ID de perro invalido")
                    return@delete
                }
                val deleted = service.delete(id)
                if (deleted) {
                    call.respond(HttpStatusCode.OK, "Perro eliminado exitosamente")
                } else {
                    call.respond(HttpStatusCode.NotFound, "Perro no encontrado")
                }
            }

            get("/{id}") {
                val id = call.parameters["id"]?.toIntOrNull()
                if (id == null) {
                    call.respond(HttpStatusCode.BadRequest, "ID inválido.")
                    return@get
                }

                val dog = service.getById(id)
                if (dog != null) {
                    call.respond(HttpStatusCode.OK, dog)
                } else {
                    call.respond(HttpStatusCode.NotFound, "Perro no encontrado.")
                }
            }
        }
    }
}