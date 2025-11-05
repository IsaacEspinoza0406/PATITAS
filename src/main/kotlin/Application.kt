package com.patitas_web

import com.patitas_web.infrastructure.DatabaseFactory
import com.patitas_web.infrastructure.tables.AdoptantesTable
import com.patitas_web.infrastructure.tables.DogsTable
import com.patitas_web.presentation.configureAdoptanteRoutes
import com.patitas_web.presentation.configureDogRoutes
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

    transaction {
        SchemaUtils.create(AdoptantesTable, DogsTable)
    }

    configureSerialization()
    // Ruta raíz simple para pruebas y verificación
    routing {
        get("/") {
            call.respondText("OK")
        }
    }
    configureAdoptanteRoutes()
    configureDogRoutes()
}
