package com.patitas_web.presentation

import com.patitas_web.application.DonationService
import com.patitas_web.domain.DonationRequest
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureDonationRoutes() {
    val service = DonationService()

    routing {
        route("/donations") {
            post {
                try {
                    val request = call.receive<DonationRequest>()
                    val donation = service.create(request)
                    call.respond(HttpStatusCode.Created, donation)
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.BadRequest, "Error processing donation: ${e.message}")
                }
            }
        }
    }
}
