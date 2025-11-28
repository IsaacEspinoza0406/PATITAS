package com.patitas_web.application

import com.patitas_web.domain.AdoptionRequest
import com.patitas_web.domain.AdoptionResponse
import com.patitas_web.infrastructure.DatabaseFactory.dbQuery
import com.patitas_web.infrastructure.tables.AdoptionQuestionnairesTable
import com.patitas_web.infrastructure.tables.AdoptantesTable
import com.patitas_web.infrastructure.tables.DogsTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class AdoptionService {

    private fun toAdoptionResponse(row: ResultRow): AdoptionResponse = AdoptionResponse(
        id = row[AdoptionQuestionnairesTable.id],
        adoptanteId = row[AdoptionQuestionnairesTable.adoptanteId],
        dogId = row[AdoptionQuestionnairesTable.dogId],
        dogName = row.getOrNull(DogsTable.name),
        nombreCompleto = row.getOrNull(AdoptantesTable.nombreCompleto),
        telefono = row.getOrNull(AdoptantesTable.telefono),
        edad = row.getOrNull(AdoptantesTable.edad),
        ocupacion = row.getOrNull(AdoptantesTable.ocupacion),
        ingresoMensual = row.getOrNull(AdoptantesTable.ingresoMensual),
        horasDeTrabajo = row.getOrNull(AdoptantesTable.horasDeTrabajo),
        tienePatio = row.getOrNull(AdoptantesTable.tienePatio),
        ninosEnCasa = row.getOrNull(AdoptantesTable.ninosEnCasa),
        tipoVivienda = row.getOrNull(AdoptantesTable.tipoVivienda),
        convivientes = row.getOrNull(AdoptantesTable.convivientes),
        mascotasAnteriores = row.getOrNull(AdoptantesTable.mascotasAnteriores),
        aunConservaMascotas = row.getOrNull(AdoptantesTable.aunConservaMascotas),
        responsabilidadesMascota = row.getOrNull(AdoptantesTable.responsabilidadesMascota),
        opinionEsterilizacion = row.getOrNull(AdoptantesTable.opinionEsterilizacion)
    )

    suspend fun findAll(): List<AdoptionResponse> = dbQuery {
        (AdoptionQuestionnairesTable innerJoin AdoptantesTable innerJoin DogsTable)
            .selectAll()
            .map(::toAdoptionResponse)
    }

    suspend fun create(request: AdoptionRequest): AdoptionResponse {
        val result = dbQuery {
            val insertStatement = AdoptionQuestionnairesTable.insert {
                it[adoptanteId] = request.adoptanteId
                it[dogId] = request.dogId
            }
            // For create, we might not have the joined data immediately unless we fetch it.
            // For simplicity, return basic response with nulls for details, or fetch it.
            // Returning nulls is fine as the frontend usually re-fetches or doesn't need details immediately.
            insertStatement.resultedValues?.singleOrNull()?.let { row ->
                 AdoptionResponse(
                    id = row[AdoptionQuestionnairesTable.id],
                    adoptanteId = row[AdoptionQuestionnairesTable.adoptanteId],
                    dogId = row[AdoptionQuestionnairesTable.dogId],
                    dogName = null, nombreCompleto = null, telefono = null, edad = null,
                    ocupacion = null, ingresoMensual = null, horasDeTrabajo = null,
                    tienePatio = null, ninosEnCasa = null, tipoVivienda = null,
                    convivientes = null, mascotasAnteriores = null, aunConservaMascotas = null,
                    responsabilidadesMascota = null, opinionEsterilizacion = null
                )
            }
        }
        return result ?: throw IllegalStateException("Error creating adoption request")
    }

    suspend fun delete(id: Int): Boolean {
        return dbQuery {
            AdoptionQuestionnairesTable.deleteWhere { AdoptionQuestionnairesTable.id eq id } > 0
        }
    }
}
