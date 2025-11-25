package com.patitas_web.application

import com.patitas_web.domain.AdoptionRequest
import com.patitas_web.domain.AdoptionResponse
import com.patitas_web.infrastructure.DatabaseFactory.dbQuery
import com.patitas_web.infrastructure.tables.AdoptionQuestionnairesTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class AdoptionService {

    private fun toAdoptionResponse(row: ResultRow): AdoptionResponse = AdoptionResponse(
        id = row[AdoptionQuestionnairesTable.id],
        adoptanteId = row[AdoptionQuestionnairesTable.adoptanteId],
        dogId = row[AdoptionQuestionnairesTable.dogId]
    )

    suspend fun findAll(): List<AdoptionResponse> = dbQuery {
        AdoptionQuestionnairesTable.selectAll().map(::toAdoptionResponse)
    }

    suspend fun create(request: AdoptionRequest): AdoptionResponse {
        val result = dbQuery {
            val insertStatement = AdoptionQuestionnairesTable.insert {
                it[adoptanteId] = request.adoptanteId
                it[dogId] = request.dogId
            }
            insertStatement.resultedValues?.singleOrNull()?.let(::toAdoptionResponse)
        }
        return result ?: throw IllegalStateException("Error creating adoption request")
    }

    suspend fun delete(id: Int): Boolean {
        return dbQuery {
            AdoptionQuestionnairesTable.deleteWhere { AdoptionQuestionnairesTable.id eq id } > 0
        }
    }
}
