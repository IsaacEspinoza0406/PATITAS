package com.patitas_web

import com.patitas_web.infrastructure.DatabaseFactory
import com.patitas_web.infrastructure.tables.AdoptantesTable
import com.patitas_web.infrastructure.tables.DogsTable
import com.patitas_web.infrastructure.tables.DogPhotosTable
import com.patitas_web.infrastructure.tables.UsersTable
import com.patitas_web.infrastructure.tables.RolesTable
import com.patitas_web.infrastructure.tables.DonationsTable
import com.patitas_web.infrastructure.tables.PaymentMethodsTable
import com.patitas_web.infrastructure.tables.AdoptionQuestionnairesTable
import com.patitas_web.presentation.configureAdoptanteRoutes
import com.patitas_web.presentation.configureDogRoutes
import com.patitas_web.presentation.configureDogPhotoRoutes
import com.patitas_web.presentation.configureAuthRoutes
import com.patitas_web.presentation.configureDonationRoutes
import com.patitas_web.presentation.configureAdoptionRoutes
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.cors.routing.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.http.content.*
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    DatabaseFactory.init()

    install(CORS) {
        allowMethod(HttpMethod.Options)
        allowMethod(HttpMethod.Get)
        allowMethod(HttpMethod.Post)
        allowMethod(HttpMethod.Put)
        allowMethod(HttpMethod.Delete)
        allowMethod(HttpMethod.Patch)

        allowHeader(HttpHeaders.ContentType)
        allowHeader(HttpHeaders.Authorization)
        allowHeader(HttpHeaders.AccessControlAllowOrigin)
        
        // Allow localhost for Angular Dev Server
        allowHost("localhost:4200", schemes = listOf("http", "https"))
        
        // Allow production domain (PLACEHOLDER - UPDATE BEFORE DEPLOYMENT)
        // allowHost("patitas-frontend-production.com", schemes = listOf("https"))
        
        allowNonSimpleContentTypes = true
        allowCredentials = true
    }

    transaction {
        // --- FACTORY RESET: DROP LEGACY/GHOST TABLES ---
        // These tables might exist from previous versions and block dropping UsersTable/DogsTable
        // We use CASCADE to ensure all dependencies are removed.
        exec("DROP TABLE IF EXISTS dog_vaccines CASCADE;") { }
        exec("DROP TABLE IF EXISTS vaccines CASCADE;") { }
        exec("DROP TABLE IF EXISTS questionnaire_answers CASCADE;") { }
        exec("DROP TABLE IF EXISTS questionnaire_questions CASCADE;") { }
        exec("DROP TABLE IF EXISTS messages CASCADE;") { }
        exec("DROP TABLE IF EXISTS questionnaires CASCADE;") { }
        exec("DROP TABLE IF EXISTS payments CASCADE;") { }
        exec("DROP TABLE IF EXISTS adoption_questionnaires CASCADE;") { }
        exec("DROP TABLE IF EXISTS dog_photos CASCADE;") { }
        exec("DROP TABLE IF EXISTS donations CASCADE;") { }

        // --- FACTORY RESET: DROP CURRENT TABLES ---
        SchemaUtils.drop(
            DonationsTable,
            PaymentMethodsTable,
            DogPhotosTable,
            DogsTable,
            AdoptantesTable,
            UsersTable, 
            RolesTable,
            AdoptionQuestionnairesTable // This now points to 'adoption_requests' table
        )

        // --- CREATE TABLES ---
        SchemaUtils.create(
            RolesTable, 
            UsersTable, 
            AdoptantesTable, 
            DogsTable, 
            DogPhotosTable,
            PaymentMethodsTable,
            DonationsTable,
            AdoptionQuestionnairesTable
        )

        // --- SEED DATA ---
        // 1. Roles
        if (RolesTable.selectAll().count() == 0L) {
            RolesTable.insert {
                it[id] = 1
                it[name] = "Admin"
            }
            RolesTable.insert {
                it[id] = 2
                it[name] = "User"
            }
        }

        // 2. Default Admin User
        if (UsersTable.select { UsersTable.email eq "admin@patitas.com" }.count() == 0L) {
            UsersTable.insert {
                it[name] = "Administrador"
                it[email] = "admin@patitas.com"
                it[password] = "admin123" // Plain text as per AuthService
                it[roleId] = 1
            }
        }
    }

    install(ContentNegotiation) {
        json()
    }

    routing {
        static("/uploads") {
            files("uploads")
        }
        get("/") {
            call.respondText("OK")
        }
    }
    configureAuthRoutes()
    configureAdoptanteRoutes()
    configureDogRoutes()
    configureDogPhotoRoutes()
    configureDonationRoutes()
    configureAdoptionRoutes()
}