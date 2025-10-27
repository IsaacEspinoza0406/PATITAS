package com.patitas_web.presentation

import com.patitas_web.application.PaymentService
import com.patitas_web.domain.PaymentRequest
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configurePaymentRoutes() {
    val service = PaymentService()

    routing {
        route("/donaciones"){
            get {
                val payments = service.findAll()
                call.respond(HttpStatusCode.OK,payments)
            }

            post{
                try {
                    val request = call.receive<PaymentRequest>()
                    val nuevoPago = service.create(request)
                    call.respond(HttpStatusCode.Created,nuevoPago)
                    } catch (e:Exception){
                    call.respond(HttpStatusCode.BadRequest,"Error al recibir los datos: ${e.message}")
                }
            }
        }
    }
}
