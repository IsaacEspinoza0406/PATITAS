package com.patitas_web.infrastructure

import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

object DatabaseFactory {
    fun init() {
        val driverClassName = "org.postgresql.Driver"

        // Prioritize AWS/Env vars, fallback to local defaults
        val dbHost = System.getenv("DB_HOST") ?: "localhost"
        val dbPort = System.getenv("DB_PORT") ?: "5432"
        val dbName = System.getenv("DB_NAME") ?: "perritos_web"
        val user = System.getenv("DB_USER") ?: "postgres"
        val password = System.getenv("DB_PASSWORD") ?: "emico3110"

        // Construct JDBC URL
        val jdbcUrl = "jdbc:postgresql://$dbHost:$dbPort/$dbName"

        println("Intentando conectar a la base de datos...")
        println("URL: jdbc:postgresql://$dbHost:$dbPort/****")
        println("Usuario: $user")

        try {
            // Check for full DATABASE_URL (common in some PaaS)
            if (!System.getenv("DATABASE_URL").isNullOrBlank()) {
                val dbUrlFromEnv = System.getenv("DATABASE_URL")
                val jdbcUrlFromEnv = dbUrlFromEnv.replaceFirst("postgres://", "jdbc:postgresql://")
                    .replaceFirst("postgresql://", "jdbc:postgresql://")
                println("Usando DATABASE_URL de entorno.")
                Database.connect(jdbcUrlFromEnv, driver = driverClassName)
            } else {
                println("Usando configuración estándar (Host/Port).")
                Database.connect(jdbcUrl, driverClassName, user, password)
            }
            println("✅ Conexión a BD establecida exitosamente.")
        } catch (e: Exception) {
            println("❌ CRITICAL ERROR: Falló la conexión a la base de datos.")
            println("Detalles: ${e.message}")
            e.printStackTrace()
            // Optional: Rethrow if you want the app to crash on start failure (recommended for production)
            // throw e 
        }
    }

    suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO) { block() }
}
