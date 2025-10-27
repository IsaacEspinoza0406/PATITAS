package com.patitas_web.application

import com.patitas_web.domain.PaymentFullResponse
import com.patitas_web.domain.PaymentRequest
import com.patitas_web.infrastructure.tables.PaymentsTable
import com.patitas_web.infrastructure.DatabaseFactory.dbQuery
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import java.time.format.DateTimeFormatter

class PaymentService {
    private fun toPaymentFullResponse(row: ResultRow): PaymentFullResponse = PaymentFullResponse(
        id = row[PaymentsTable.id],
        user_id = row[PaymentsTable.user_id],
        amount = row[PaymentsTable.amount],
        status = row[PaymentsTable.status],
        method = row[PaymentsTable.method],
        created_at = row[PaymentsTable.created_at].format(DateTimeFormatter.ISO_DATE_TIME)
    )

    suspend fun findAll(): List<PaymentFullResponse> = dbQuery {
        PaymentsTable.selectAll().map(::toPaymentFullResponse)
    }

    suspend fun create(request: PaymentRequest): PaymentFullResponse {
        val result = dbQuery {
            val insertStatement = PaymentsTable.insert { table ->
                table[PaymentsTable.id] = request.id
                table[PaymentsTable.user_id] = request.user_id
                table[PaymentsTable.amount] = request.amount
                table[PaymentsTable.status] = request.status
                table[PaymentsTable.method] = request.method
            }
            insertStatement.resultedValues?.singleOrNull()?.let(::toPaymentFullResponse)
        }
        return result ?: throw IllegalStateException("Error al guardar la donación en la base de datos.")
    }



}