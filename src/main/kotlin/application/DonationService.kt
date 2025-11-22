package com.patitas_web.application

import com.patitas_web.domain.DonationResponse
import com.patitas_web.infrastructure.tables.DonationTable
import com.patitas_web.domain.DonationRequest
import com.patitas_web.infrastructure.DatabaseFactory.dbQuery
import org.jetbrains.exposed.sql.insert

class DonationService{
    suspend fun create(request: DonationRequest): DonationResponse {
        val result = dbQuery {
            val insertStatement = DonationTable.insert {
                it[paymentMethodToken] = request.paymentMethodToken
                it[amount] = request.amount
                it[currency] = request.currency
            }
            insertStatement.resultedValues?.singleOrNull() } ?: throw IllegalStateException("Error al guardar la donación en la base de datos.")
        return DonationResponse(
            status = "SUCCESS",
            transactionId = result[DonationTable.id].toString(),
            message = "Se aplicó la donación exitosamente."
        )
    }

}
