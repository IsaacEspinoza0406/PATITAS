package com.patitas_web.presentation

import com.patitas_web.application.DonationService
import com.patitas_web.domain.DonationRequest
import io.ktor.http.*
import io.ktor.server.routing.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.respond


fun Application.configureDonationRoutes() {
    val service = DonationService()

    routing {
        route("/donation") {

            get {
                val donations = service.findAll()
                call.respond(HttpStatusCode.OK,donations)
            }

            post {
                try {
                    val request = call.receive<DonationRequest>()
                    val newDonation = service.create(request)
                    call.respond(HttpStatusCode.Created, newDonation)
                }catch ( e: Exception){
                    call.respond(HttpStatusCode.BadRequest, "Error al hacer una donación ${e.message}")
                }
            }
        }
    }
}