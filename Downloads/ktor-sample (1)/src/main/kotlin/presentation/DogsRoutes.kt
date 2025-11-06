package com.patitas_web.presentation

import com.patitas_web.application.DogsService
import com.patitas_web.domain.DogsRequest
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.request.receive
import io.ktor.server.response.*
import io.ktor.server.routing.*


fun Application.configureDogsRoutes() {
    val service = DogsService()

    routing {
        route("/dogs"){
            get {
                val dogs = service.findAll()
                call.respond(dogs)
            }

             post {
                try{
                    val request = call.receive<DogsRequest>()
                    val newDog = service.create(request)
                    call.respond(HttpStatusCode.Created,newDog)
                }catch(e:Exception){
                    call.respond(HttpStatusCode.BadRequest, "error al añadir un perro ${e.message}")
                }
            }

            delete {
                try{
                    val request = call.receive<DogsRequest>()
                    val deleteDog = service.delete(request)
                    call.respond(HttpStatusCode.OK,deleteDog)
                }catch(e:Exception){
                    call.respond(HttpStatusCode.BadRequest, "Error al eliminar un perro ${e.message}")
                }
            }

        }

        route("/dogs/{id}") {
            get {
                call.parameters["id"]?.toIntOrNull()?.let { id ->
                    try {
                        val dog = service.getById(id)
                        if (dog != null) {
                            call.respond(HttpStatusCode.OK, dog)
                        } else {
                            call.respond(HttpStatusCode.NotFound, "El ID del perro no existe")
                        }
                    } catch (e: Exception) {
                        call.respond(HttpStatusCode.InternalServerError, "Error al procesar la solicitud: ${e.message}")
                    }
                } ?: run { call.respond(HttpStatusCode.NotFound, "El ID del perro no existe") }
            }
        }

    }
}