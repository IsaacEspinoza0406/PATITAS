package com.patitas_web.infrastructure.tables

import org.jetbrains.exposed.sql.Table

object RolesTable : Table("roles") {
    val id = integer("id").autoIncrement()
    val name = varchar("name", 50).uniqueIndex()
    val description = varchar("description", 255).nullable()
    
    override val primaryKey = PrimaryKey(id)
}