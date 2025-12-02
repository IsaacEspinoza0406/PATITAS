package com.patitas_web.presentation

import com.patitas_web.application.AdoptanteService
import com.patitas_web.di.Dependencies
import com.patitas_web.domain.entities.Adoptante
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureAdoptanteRoutes() {
    val service = Dependencies.adoptanteService
    
    routing {
        route("/adoptantes") {
            post {
                val adoptante = call.receive<Adoptante>()
                val created = service.create(adoptante)
                call.respond(HttpStatusCode.Created, created)
            }
        }
    }
}
