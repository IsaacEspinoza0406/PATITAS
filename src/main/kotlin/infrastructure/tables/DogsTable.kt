package com.patitas_web.infrastructure.tables

import org.jetbrains.exposed.sql.Table

object DogsTable : Table("dogs") {
        val id = integer("id").autoIncrement()
        val name = varchar("name", 100)
        val age = integer("age").nullable()
        val breed = varchar("breed", 100).default("Mestizo")
        val history = text("history").nullable()
        val sterilized = text("sterilized").nullable()
        val adopted = text("adopted").nullable()
                val createdBy = integer("created_by")

        override val primaryKey = PrimaryKey(id)



    
}