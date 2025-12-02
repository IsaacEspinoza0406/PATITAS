package com.patitas_web.presentation

import com.patitas_web.application.AdoptionService
import com.patitas_web.di.Dependencies
import com.patitas_web.domain.AdoptionRequest
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureAdoptionRoutes() {
    val service = Dependencies.adoptionService
    
    routing {
        route("/adoptions") {
            get {
                call.respond(service.findAll())
            }
            
            post {
                val request = call.receive<AdoptionRequest>()
                val response = service.create(request)
                call.respond(HttpStatusCode.Created, response)
            }
            
            delete("/{id}") {
                val id = call.parameters["id"]?.toIntOrNull()
                if (id == null) {
                    call.respond(HttpStatusCode.BadRequest, "ID inválido")
                    return@delete
                }
                if (service.delete(id)) {
                    call.respond(HttpStatusCode.OK, "Solicitud eliminada")
                } else {
                    call.respond(HttpStatusCode.NotFound, "Solicitud no encontrada")
                }
            }
            
            put("/{id}/accept") {
                val id = call.parameters["id"]?.toIntOrNull()
                if (id == null) {
                    call.respond(HttpStatusCode.BadRequest, "ID inválido")
                    return@put
                }
                if (service.accept(id)) {
                    call.respond(HttpStatusCode.OK, "Solicitud aceptada")
                } else {
                    call.respond(HttpStatusCode.NotFound, "Solicitud no encontrada")
                }
            }
        }
    }
}
