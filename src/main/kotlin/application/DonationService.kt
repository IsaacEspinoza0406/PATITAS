package com.patitas_web.application

import com.patitas_web.domain.DonationRequest
import com.patitas_web.domain.DonationResponse
import com.patitas_web.infrastructure.DatabaseFactory.dbQuery
import com.patitas_web.infrastructure.tables.DonationsTable
import com.patitas_web.infrastructure.tables.PaymentMethodsTable
import org.jetbrains.exposed.sql.*

class DonationService {

    private fun toDonationResponse(row: ResultRow): DonationResponse {
        val methodName = row[PaymentMethodsTable.methodName]
        return DonationResponse(
            id = row[DonationsTable.id],
            userId = row[DonationsTable.userId],
            amount = row[DonationsTable.amount].toDouble(),
            methodName = methodName,
            transactionId = row[DonationsTable.transactionId],
            payerEmail = row[DonationsTable.payerEmail],
            status = row[DonationsTable.status]
        )
    }

    suspend fun create(request: DonationRequest): DonationResponse {
        return dbQuery {
            // Ensure payment method exists or get its ID
            val methodId = PaymentMethodsTable.select { PaymentMethodsTable.methodName eq request.methodName }
                .singleOrNull()?.get(PaymentMethodsTable.id)
                ?: PaymentMethodsTable.insert {
                    it[methodName] = request.methodName
                } get PaymentMethodsTable.id

            val insertStatement = DonationsTable.insert {
                it[userId] = request.userId
                it[amount] = request.amount.toBigDecimal()
                it[DonationsTable.methodId] = methodId
                it[transactionId] = request.transactionId
                it[payerEmail] = request.payerEmail
                it[status] = "Completed" // Default status
            }
            
            val insertedRow = insertStatement.resultedValues?.singleOrNull()
                ?: throw IllegalStateException("Error creating donation")

            // Fetch full details including method name
            (DonationsTable innerJoin PaymentMethodsTable)
                .select { DonationsTable.id eq insertedRow[DonationsTable.id] }
                .single()
                .let(::toDonationResponse)
        }
    }
}
