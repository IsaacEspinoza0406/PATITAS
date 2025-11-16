package com.patitas_web.application

import com.patitas_web.domain.DonationRequest
import com.patitas_web.domain.DonationResponse
import com.patitas_web.infrastructure.DatabaseFactory.dbQuery
import com.patitas_web.infrastructure.tables.DonationTable
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll

class DonationService {
    private fun toDonationFullResponse(row: ResultRow): DonationResponse = DonationResponse(
        id = row[DonationTable.id],
        card_number = row[DonationTable.card_number],
        cardholder_name = row[DonationTable.cardholder_name],
        cvv = row[DonationTable.cvv],
        exp_year = row[DonationTable.exp_year],
        email = row[DonationTable.email],
        payment_method = row[DonationTable.payment_method]
    )

    suspend fun findAll(): List<DonationResponse> = dbQuery {
        DonationTable.selectAll().map(::toDonationFullResponse)
    }

    suspend fun create (Donationrequest: DonationRequest): DonationResponse {
        val result = dbQuery {
            val insertStatement = DonationTable.insert { table ->
                table[DonationTable.id] = Donationrequest.id
                table[DonationTable.card_number] = Donationrequest.card_number
                table[DonationTable.cardholder_name] = Donationrequest.cardholder_name
                table[DonationTable.cvv] = Donationrequest.cvv
                table[DonationTable.exp_year] = Donationrequest.exp_year
                table[DonationTable.email] = Donationrequest.email
                table[DonationTable.payment_method] = Donationrequest.payment_method
            }
            insertStatement.resultedValues?.singleOrNull()?.let(::toDonationFullResponse)
        }
        return result ?: throw IllegalStateException("Error al guardar la donación en la base de datos.")
    }
}