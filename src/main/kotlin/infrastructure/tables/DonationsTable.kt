package com.patitas_web.infrastructure.tables

import org.jetbrains.exposed.sql.Table

object PaymentMethodsTable : Table("payment_methods") {
    val id = integer("id").autoIncrement()
    val methodName = varchar("method_name", 50).uniqueIndex()

    override val primaryKey = PrimaryKey(id)
}

object DonationsTable : Table("donations") {
    val id = integer("id").autoIncrement()
    val userId = integer("user_id").references(UsersTable.id)
    val amount = decimal("amount", 10, 2)
    val methodId = integer("method_id").references(PaymentMethodsTable.id)
    val transactionId = varchar("transaction_id", 100).nullable()
    val payerEmail = varchar("payer_email", 150).nullable()
    val status = varchar("status", 50).nullable()

    override val primaryKey = PrimaryKey(id)
}
