package com.patitas_web.infrastructure.tables

import org.jetbrains.exposed.sql.Table

object DogsTable : Table("dogs") {
    val id = integer(name = "id").autoIncrement()
    val name = varchar("name", 255)
    val age = integer("age").nullable()
    val breed = varchar("breed", 255)
    val history = text("history").nullable()
    val sterilized = text("sterilized").nullable()
    val adopted = text("adopted").nullable()
    //val created_by = foreignKey()

    override val primaryKey = PrimaryKey(DogsTable.id)
}
