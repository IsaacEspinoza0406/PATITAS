package com.patitas_web.infrastructure

import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

object DatabaseFactory {
    fun init() {
        val driverClassName = "org.postgresql.Driver"

        val dbHost = System.getenv("DB_HOST") ?: "localhost"
        val dbPort = System.getenv("DB_PORT") ?: "5432"
        val dbName = System.getenv("DB_NAME") ?: "perritos_web"
        val jdbcUrl = "jdbc:postgresql://$dbHost:$dbPort/$dbName"
        val user = System.getenv("DB_USER") ?: "postgres"
        val password = System.getenv("DB_PASSWORD") ?: "emico3110"

        println("Conectando a: $jdbcUrl con usuario: $user")

        try {
            if (System.getenv("DATABASE_URL") != null) {
                val dbUrlFromEnv = System.getenv("DATABASE_URL")
                val jdbcUrlFromEnv = dbUrlFromEnv.replaceFirst("postgres://", "jdbc:postgresql://")
                    .replaceFirst("postgresql://", "jdbc:postgresql://")
                println("Conectando usando DATABASE_URL...")
                Database.connect(jdbcUrlFromEnv)
            } else {
                println("Conectando usando variables separadas...")
                Database.connect(jdbcUrl, driverClassName, user, password)
            }
            println("Conexión a BD configurada.")
        } catch (e: Exception) {
            println("ERROR AL CONECTAR A LA BD: ${e.message}")
            e.printStackTrace()
            throw e
        }
    }

    suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO) { block() }
}
