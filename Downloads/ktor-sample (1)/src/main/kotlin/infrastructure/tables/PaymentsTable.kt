package com.patitas_web.infrastructure.tables

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.datetime
import java.time.LocalDateTime

object PaymentsTable: Table("payments") {

    val id = integer("id").autoIncrement()
    val user_id = integer("user_id")
    val amount = double("amount")
    val method = varchar("method", 200)
    val status = varchar("status", 200)
    val created_at = datetime("created_at").default(LocalDateTime.now())

    override val primaryKey = PrimaryKey(PaymentsTable.id)
}