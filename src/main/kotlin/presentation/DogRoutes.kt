package com.patitas_web.presentation

import com.patitas_web.application.DogService
import com.patitas_web.domain.DogRequest
import io.ktor.http.HttpStatusCode
import io.ktor.server.request.receive
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.put
import io.ktor.server.routing.route
import io.ktor.server.routing.routing
import io.ktor.server.application.*
import io.ktor.server.response.*

fun Application.configureDogRoutes() {
    val service = DogService()

    routing{
        route("/dogs"){
            get{
                call.respond(service.findAll())
            }
            post{
                val request = call.receive<DogRequest>()
                val dog = service.create(request)
                call.respond(HttpStatusCode.Created, dog)
            }
            put("/{id}") {
                val id = call.parameters["id"]?.toIntOrNull()
                if (id == null) {
                    call.respond(HttpStatusCode.BadRequest,"ID de perro invalido.")
                    return@put
                }
                val request = call.receive<DogRequest>()
                val updateDog = service.update(id,request)
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
            }
        }

    }
}
