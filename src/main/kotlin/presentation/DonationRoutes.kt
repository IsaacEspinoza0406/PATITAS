package com.patitas_web.presentation

import com.patitas_web.application.DonationService
import com.patitas_web.domain.DonationRequest
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import io.ktor.server.routing.routing

fun Application.configureDonationRoutes() {
    val service = DonationService()

    routing {
        route("/donaciones"){
            post {
                try{
                    val request = call.receive<DonationRequest>()
                    val nuevaDonacion = service.create(request)
                    call.respond(HttpStatusCode.Created, nuevaDonacion)
                } catch (e: Exception){
                    call.respond(HttpStatusCode.BadRequest, "Error al recibir datos: ${e.message}")
                }
            }
        }
    }
}