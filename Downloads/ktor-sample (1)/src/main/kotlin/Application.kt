package com.patitas_web

import com.patitas_web.infrastructure.DatabaseFactory
import com.patitas_web.presentation.configureAdoptanteRoutes
import com.patitas_web.presentation.configurePaymentRoutes
import io.ktor.server.application.*
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import com.patitas_web.infrastructure.tables.AdoptantesTable
import com.patitas_web.infrastructure.tables.PaymentsTable
import com.patitas_web.presentation.configureUsersRoutes


fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    DatabaseFactory.init()

    transaction {
        SchemaUtils.create(AdoptantesTable)
        SchemaUtils.create(PaymentsTable)
    }

    configureSerialization()

    configureAdoptanteRoutes()
    configurePaymentRoutes()
    configureUsersRoutes()
}