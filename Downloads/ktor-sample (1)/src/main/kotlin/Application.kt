package com.patitas_web

import com.patitas_web.infrastructure.DatabaseFactory
import com.patitas_web.infrastructure.tables.AdoptantesTable
import com.patitas_web.infrastructure.tables.DogsTable
import com.patitas_web.infrastructure.tables.DogPhotosTable
import com.patitas_web.infrastructure.tables.DonationTable
import com.patitas_web.infrastructure.tables.UsersTable
import com.patitas_web.presentation.configureAdoptanteRoutes
import com.patitas_web.presentation.configureDogsRoutes
import com.patitas_web.presentation.configureDogPhotoRoutes
import com.patitas_web.presentation.configureDonationRoutes
import com.patitas_web.presentation.configureUsersRoutes
import io.ktor.server.plugins.cors.routing.*
import io.ktor.http.*

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}
//hh

fun Application.module() {
    DatabaseFactory.init()

    install(CORS) {
        allowMethod(HttpMethod.Options)
        allowMethod(HttpMethod.Get)
        allowMethod(HttpMethod.Post)
        allowMethod(HttpMethod.Put)
        allowMethod(HttpMethod.Delete)

        allowHeader(HttpHeaders.ContentType)
        allowHeader(HttpHeaders.Authorization)

        allowHost("localhost:4200", schemes = listOf("http"))
    }

    transaction {
        SchemaUtils.create(UsersTable, AdoptantesTable, DogsTable, DogPhotosTable, DonationTable, UsersTable)
    }

    configureSerialization()
    routing {
        get("/") {
            call.respondText("OK")
        }
    }
    configureAdoptanteRoutes()
    configureDogsRoutes()
    configureDogPhotoRoutes()
    configureDonationRoutes()
    configureUsersRoutes()
}
