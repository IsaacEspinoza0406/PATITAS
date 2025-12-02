package com.patitas_web.infrastructure.repositories

import com.patitas_web.domain.entities.Adoption
import com.patitas_web.domain.ports.AdoptionRepository
import com.patitas_web.infrastructure.DatabaseFactory.dbQuery
import com.patitas_web.infrastructure.tables.AdoptionQuestionnairesTable
import com.patitas_web.infrastructure.tables.AdoptantesTable
import com.patitas_web.infrastructure.tables.DogsTable
import com.patitas_web.infrastructure.tables.DogPhotosTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class SqlAdoptionRepository : AdoptionRepository {

    private fun toAdoption(row: ResultRow): Adoption = Adoption(
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

    override suspend fun findAll(): List<Adoption> = dbQuery {
        val rows = AdoptionQuestionnairesTable
            .leftJoin(AdoptantesTable, { adoptanteId }, { AdoptantesTable.id })
            .leftJoin(DogsTable, { AdoptionQuestionnairesTable.dogId }, { DogsTable.id })
            .slice(
                AdoptionQuestionnairesTable.id,
                AdoptionQuestionnairesTable.adoptanteId,
                AdoptionQuestionnairesTable.dogId,
                DogsTable.name,
                AdoptantesTable.nombreCompleto,
                AdoptantesTable.telefono,
                AdoptantesTable.edad,
                AdoptantesTable.ocupacion,
                AdoptantesTable.ingresoMensual,
                AdoptantesTable.horasDeTrabajo,
                AdoptantesTable.tienePatio,
                AdoptantesTable.ninosEnCasa,
                AdoptantesTable.tipoVivienda,
                AdoptantesTable.convivientes,
                AdoptantesTable.mascotasAnteriores,
                AdoptantesTable.aunConservaMascotas,
                AdoptantesTable.responsabilidadesMascota,
                AdoptantesTable.opinionEsterilizacion
            )
            .selectAll()
            .toList()

        val adoptions = rows.map(::toAdoption)

        val dogIds = adoptions.map { it.dogId }.distinct()

        val photos = if (dogIds.isNotEmpty()) {
            DogPhotosTable.select { DogPhotosTable.dogId inList dogIds }
                .orderBy(DogPhotosTable.id)
                .map { it[DogPhotosTable.dogId] to it[DogPhotosTable.photoUrl] }
                .distinctBy { it.first }
                .toMap()
        } else {
            emptyMap()
        }

        adoptions.map { it.copy(dogImageUrl = photos[it.dogId]) }
    }

    override suspend fun create(adoption: Adoption): Adoption {
        val result = dbQuery {
            val insertStatement = AdoptionQuestionnairesTable.insert {
                it[adoptanteId] = adoption.adoptanteId
                it[dogId] = adoption.dogId
            }
            insertStatement.resultedValues?.singleOrNull()?.let { row ->
                Adoption(
                    id = row[AdoptionQuestionnairesTable.id],
                    adoptanteId = row[AdoptionQuestionnairesTable.adoptanteId],
                    dogId = row[AdoptionQuestionnairesTable.dogId]
                )
            }
        }
        return result ?: throw IllegalStateException("Error creating adoption request")
    }

    override suspend fun delete(id: Int): Boolean {
        return dbQuery {
            AdoptionQuestionnairesTable.deleteWhere { AdoptionQuestionnairesTable.id eq id } > 0
        }
    }

    override suspend fun accept(id: Int): Boolean = dbQuery {
        val request = AdoptionQuestionnairesTable.select { AdoptionQuestionnairesTable.id eq id }.singleOrNull()
        if (request != null) {
            val dogId = request[AdoptionQuestionnairesTable.dogId]
            // Update dog status to Adopted
            DogsTable.update({ DogsTable.id eq dogId }) {
                it[adopted] = "Si"
            }
            // Delete the request
            AdoptionQuestionnairesTable.deleteWhere { AdoptionQuestionnairesTable.id eq id } > 0
        } else {
            false
        }
    }
}
