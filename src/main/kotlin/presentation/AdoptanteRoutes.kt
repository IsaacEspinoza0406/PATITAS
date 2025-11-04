package com.patitas_web.presentation

import com.patitas_web.application.AdoptanteService
import com.patitas_web.domain.AdoptanteRequest
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureAdoptanteRoutes() {
    val service = AdoptanteService()

    routing {
        route("/adoptantes") {

                    get {
                val adoptantes = service.findAll()
                call.respond(HttpStatusCode.OK, adoptantes)
            }

            post {
                try {
                    val request = call.receive<AdoptanteRequest>()
                    val nuevoAdoptante = service.create(request)
                    call.respond(HttpStatusCode.Created, nuevoAdoptante)
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.BadRequest, "Error en los datos recibidos: ${e.message}")
                }
            }


            get("/{id}") {
                val id = call.parameters["id"]?.toIntOrNull()
                if (id == null) {
                    call.respond(HttpStatusCode.BadRequest, "ID de adoptante inválido.")
                    return@get
                }

                val adoptante = service.findById(id)
                if (adoptante != null) {
                    call.respond(HttpStatusCode.OK, adoptante)
                } else {
                    call.respond(HttpStatusCode.NotFound, "Adoptante no encontrado.")
                }
            }


            delete("/{id}") {
                val id = call.parameters["id"]?.toIntOrNull()
                if (id == null) {
                    call.respond(HttpStatusCode.BadRequest, "ID de adoptante inválido.")
                    return@delete
                }

                val deleted = service.delete(id)
                if (deleted) {
                    call.respond(HttpStatusCode.OK, "Adoptante eliminado exitosamente.")
                } else {
                    call.respond(HttpStatusCode.NotFound, "Adoptante no encontrado.")
                }
            }
        }
    }
}