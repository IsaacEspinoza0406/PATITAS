package com.patitas_web

import com.patitas_web.infrastructure.DatabaseFactory
import com.patitas_web.presentation.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.cors.routing.*
import io.ktor.server.netty.*
import io.ktor.http.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import com.patitas_web.infrastructure.security.JwtConfig

fun main(args: Array<String>): Unit = EngineMain.main(args)

fun Application.module() {
    install(CORS) {
        anyHost()
        allowHeader(HttpHeaders.ContentType)
        allowHeader(HttpHeaders.Authorization)
        allowMethod(HttpMethod.Options)
        allowMethod(HttpMethod.Put)
        allowMethod(HttpMethod.Delete)
        allowMethod(HttpMethod.Patch)
    }
    install(ContentNegotiation) {
        json()
    }
    install(Authentication) {
        jwt("auth-jwt") {
            verifier(JwtConfig.verifier)
            validate { credential ->
                if (credential.payload.getClaim("id").asInt() != null) {
                    JWTPrincipal(credential.payload)
                } else {
                    null
                }
            }
        }
    }
    
    DatabaseFactory.init()
    
    configureDogRoutes()
    configureAdoptionRoutes()
    configureAdoptanteRoutes()
    configureAuthRoutes()
    configureDonationRoutes()
    
    routing {
        staticFiles("/uploads", java.io.File("uploads"))
    }
}
