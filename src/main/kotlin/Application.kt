package com.patitas_web

import com.patitas_web.infrastructure.DatabaseFactory
import com.patitas_web.infrastructure.tables.AdoptantesTable
import com.patitas_web.infrastructure.tables.DogsTable
import com.patitas_web.infrastructure.tables.DogPhotosTable
import com.patitas_web.infrastructure.tables.UsersTable
import com.patitas_web.infrastructure.tables.RolesTable
import com.patitas_web.presentation.configureAdoptanteRoutes
import com.patitas_web.presentation.configureDogRoutes
import com.patitas_web.presentation.configureDogPhotoRoutes
import com.patitas_web.presentation.configureAuthRoutes

// --- IMPORTACIONES AÑADIDAS ---
import io.ktor.server.plugins.cors.routing.* // <-- AÑADIDO
import io.ktor.http.* // <-- AÑADIDO
// ------------------------------

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.plugins.contentnegotiation.*
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    DatabaseFactory.init()

    // --- BLOQUE CORS AÑADIDO ---
    install(CORS) { // <-- AÑADIDO
        allowMethod(HttpMethod.Options)
        allowMethod(HttpMethod.Get)
        allowMethod(HttpMethod.Post)
        allowMethod(HttpMethod.Put)
        allowMethod(HttpMethod.Delete)

        allowHeader(HttpHeaders.ContentType)
        allowHeader(HttpHeaders.Authorization)

        // Esta es la línea clave que soluciona tu error:
        allowHost("localhost:4200", schemes = listOf("http")) // <-- AÑADIDO
    }
    // ---------------------------

    transaction {
        SchemaUtils.create(UsersTable, RolesTable, AdoptantesTable, DogsTable, DogPhotosTable)
    }

    configureSerialization()
    routing {
        get("/") {
            call.respondText("OK")
        }
    }
    configureAuthRoutes()
    configureAdoptanteRoutes()
    configureDogRoutes()
    configureDogPhotoRoutes()
}