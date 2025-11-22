package com.patitas_web.infrastructure.tables

import com.patitas_web.infrastructure.tables.DonationTable.primaryKey
import org.jetbrains.exposed.sql.Table

object DonationTable: Table("donations") {
    val id = integer("id").autoIncrement()
    val paymentMethodToken = varchar("payment_method_token", 255)
    val amount = long("amount")
    val currency = varchar("currency", 255)
    val status = varchar("status", 255)
    val transactionId = varchar("transaction_id", 255)
    val message = varchar("message", 255)

    override val primaryKey = PrimaryKey(id)
}