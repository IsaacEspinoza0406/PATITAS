package com.patitas_web.infrastructure

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction
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
                    it[id] = 1
                    it[name] = "Admin"
                }
                RolesTable.insert {
                    it[id] = 2
                    it[name] = "Adoptante"
                }
            }

            // 2. Seed Admin User
            val adminEmail = "admin@patitas.com"
            if (UsersTable.select { UsersTable.email eq adminEmail }.count() == 0L) {
                val hashedPassword = org.mindrot.jbcrypt.BCrypt.hashpw("admin123", org.mindrot.jbcrypt.BCrypt.gensalt())
                UsersTable.insert {
                    it[name] = "Admin"
                    it[email] = adminEmail
                    it[password] = hashedPassword
                    it[roleId] = 1
                }
            }
        }
    }
}
