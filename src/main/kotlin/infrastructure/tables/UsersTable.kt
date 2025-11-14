package com.patitas_web.infrastructure.tables

import org.jetbrains.exposed.sql.Table

object UsersTable : Table("users") {
    val id = integer("id").autoIncrement()
    val roleId = integer("role_id").references(RolesTable.id)
    val name = varchar("name", 100)
    val email = varchar("email", 150).uniqueIndex()
    val password = varchar("password", 255)
    
    override val primaryKey = PrimaryKey(id)
}