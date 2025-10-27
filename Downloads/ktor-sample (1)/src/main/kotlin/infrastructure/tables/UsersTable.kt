package com.patitas_web.infrastructure.tables

import org.jetbrains.exposed.sql.Table

object UsersTable: Table("users") {
    val id = integer("id").autoIncrement()
    val role_id = integer("role_id")
    val name = varchar("name", 255)
    val email = varchar("email", 255)
    val password = varchar("password", 255)
    val telefono = varchar("telefono", 255)

    override val primaryKey = PrimaryKey(UsersTable.id)

}