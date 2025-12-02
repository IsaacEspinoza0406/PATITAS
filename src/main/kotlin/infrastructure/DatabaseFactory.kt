package com.patitas_web.infrastructure

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import com.patitas_web.infrastructure.tables.*

object DatabaseFactory {
    fun init() {
        val driverClassName = "org.postgresql.Driver"
        val jdbcUrl = System.getenv("JDBC_URL") ?: "jdbc:postgresql://localhost:5432/perritos_web"
        val username = System.getenv("DB_USER") ?: "postgres"
        val password = System.getenv("DB_PASSWORD") ?: "emico3110"

        val config = HikariConfig().apply {
            this.driverClassName = driverClassName
            this.jdbcUrl = jdbcUrl
            this.username = username
            this.password = password
            maximumPoolSize = 3
            isAutoCommit = false
            transactionIsolation = "TRANSACTION_REPEATABLE_READ"
            validate()
        }
        val dataSource = HikariDataSource(config)
        Database.connect(dataSource)
        
        transaction {
            SchemaUtils.create(
                DogsTable,
                AdoptantesTable,
                DonationsTable,
                PaymentMethodsTable,
                UsersTable,
                RolesTable,
                AdoptionQuestionnairesTable,
                DogPhotosTable
            )
            
            // Seed initial data
            seed()
        }
    }

    suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO) { block() }

    private fun seed() {
        transaction {
            // 1. Seed Roles
            if (RolesTable.selectAll().count() == 0L) {
                RolesTable.insert {
                    it[RolesTable.id] = 1
                    it[RolesTable.name] = "Admin"
                }
                RolesTable.insert {
                    it[RolesTable.id] = 2
                    it[RolesTable.name] = "Adoptante"
                }
            }

            // 2. Seed Admin User
            val adminEmail = "admin@patitas.com"
            if (UsersTable.select { UsersTable.email eq adminEmail }.count() == 0L) {
                val hashedPassword = org.mindrot.jbcrypt.BCrypt.hashpw("admin123", org.mindrot.jbcrypt.BCrypt.gensalt())
                UsersTable.insert {
                    it[UsersTable.name] = "Admin"
                    it[UsersTable.email] = adminEmail
                    it[UsersTable.password] = hashedPassword
                    it[UsersTable.roleId] = 1
                }
            }
            
            // 3. Fix Schema Constraints (Migration)
            try {
                exec("ALTER TABLE adoptantes ALTER COLUMN convivientes TYPE varchar(255)") {}
                exec("ALTER TABLE adoptantes ALTER COLUMN ninos_en_casa TYPE varchar(255)") {}
                exec("ALTER TABLE adoptantes ALTER COLUMN mascotas_anteriores TYPE text") {}
                exec("ALTER TABLE adoptantes ALTER COLUMN aun_conserva_mascotas TYPE text") {}
                exec("ALTER TABLE adoptantes ALTER COLUMN tiene_patio TYPE varchar(50)") {}
                exec("ALTER TABLE adoptantes ALTER COLUMN tipo_vivienda TYPE varchar(50)") {}
            } catch (e: Exception) {
                // Ignore if already altered or fails, to prevent startup crash
                println("Schema migration warning: ${e.message}")
            }
        }
    }
}
