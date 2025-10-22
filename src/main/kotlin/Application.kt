package com.patitas_web

import com.patitas_web.infrastructure.DatabaseFactory
import com.patitas_web.presentation.configureAdoptanteRoutes
import io.ktor.server.application.*
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import com.patitas_web.infrastructure.tables.AdoptantesTable


fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    DatabaseFactory.init()

    transaction {
        SchemaUtils.create(AdoptantesTable)
    }

    configureSerialization()

    configureAdoptanteRoutes()
}