package com.patitas_web.presentation

import com.patitas_web.application.DonationService
import com.patitas_web.di.Dependencies
import com.patitas_web.domain.entities.Donation
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureDonationRoutes() {
    val service = Dependencies.donationService
    
    routing {
        route("/donations") {
            get {
                call.respond(service.findAll())
            }
            
            post {
                val donation = call.receive<Donation>()
                val created = service.create(donation)
                call.respond(HttpStatusCode.Created, created)
            }
        }
    }
}
