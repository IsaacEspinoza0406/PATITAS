package com.patitas_web.infrastructure.repositories

import com.patitas_web.domain.entities.Donation
import com.patitas_web.domain.ports.DonationRepository
import com.patitas_web.infrastructure.DatabaseFactory.dbQuery
import com.patitas_web.infrastructure.tables.DonationsTable
import com.patitas_web.infrastructure.tables.PaymentMethodsTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class SqlDonationRepository : DonationRepository {

    private fun toDonation(row: ResultRow): Donation {
        val methodName = row[PaymentMethodsTable.methodName]
        return Donation(
            id = row[DonationsTable.id],
            userId = row[DonationsTable.userId],
            amount = row[DonationsTable.amount].toDouble(),
            methodName = methodName,
            transactionId = row[DonationsTable.transactionId],
            payerEmail = row[DonationsTable.payerEmail],
            status = row[DonationsTable.status]
        )
    }

    override suspend fun create(donation: Donation): Donation {
        return dbQuery {
            // Ensure payment method exists or get its ID
            val methodId = PaymentMethodsTable.select { PaymentMethodsTable.methodName eq donation.methodName }
                .singleOrNull()?.get(PaymentMethodsTable.id)
                ?: PaymentMethodsTable.insert {
                    it[PaymentMethodsTable.methodName] = donation.methodName
                } get PaymentMethodsTable.id

            val insertStatement = DonationsTable.insert {
                it[DonationsTable.userId] = donation.userId
                it[DonationsTable.amount] = donation.amount.toBigDecimal()
                it[DonationsTable.methodId] = methodId
                it[DonationsTable.transactionId] = donation.transactionId
                it[DonationsTable.payerEmail] = donation.payerEmail
                it[DonationsTable.status] = donation.status ?: "Completed"
            }
            
            val insertedRow = insertStatement.resultedValues?.singleOrNull()
                ?: throw IllegalStateException("Error creating donation")

            // Fetch full details including method name
            (DonationsTable innerJoin PaymentMethodsTable)
                .select { DonationsTable.id eq insertedRow[DonationsTable.id] }
                .single()
                .let(::toDonation)
        }
    }

    override suspend fun findAll(): List<Donation> = dbQuery {
        (DonationsTable innerJoin PaymentMethodsTable)
            .selectAll()
            .map(::toDonation)
    }
}
