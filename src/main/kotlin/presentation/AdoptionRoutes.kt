package com.patitas_web.presentation

import com.patitas_web.application.AdoptionService
import com.patitas_web.domain.AdoptionRequest
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureAdoptionRoutes() {
    val service = AdoptionService()

    routing {
        route("/adoptions") {
            get {
                call.respond(service.findAll())
            }

            post {
                try {
                    val request = call.receive<AdoptionRequest>()
                    val adoption = service.create(request)
                    call.respond(HttpStatusCode.Created, adoption)
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.BadRequest, "Error creating adoption request: ${e.message}")
                }
            }

            delete("/{id}") {
                val id = call.parameters["id"]?.toIntOrNull()
                if (id == null) {
                    call.respond(HttpStatusCode.BadRequest, "Invalid ID")
                    return@delete
                }
                if (service.delete(id)) {
                    call.respond(HttpStatusCode.OK, "Adoption request deleted")
                } else {
                    call.respond(HttpStatusCode.NotFound, "Adoption request not found")
                }
            }
        }
    }
}
