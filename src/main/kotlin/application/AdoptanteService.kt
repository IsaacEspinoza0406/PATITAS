package com.patitas_web.application

import com.patitas_web.domain.AdoptanteRequest
import com.patitas_web.domain.AdoptanteFullResponse
import com.patitas_web.infrastructure.DatabaseFactory.dbQuery
import com.patitas_web.infrastructure.tables.AdoptantesTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class AdoptanteService {

    private fun toAdoptanteFullResponse(row: ResultRow): AdoptanteFullResponse = AdoptanteFullResponse(
        id = row[AdoptantesTable.id],
        nombreCompleto = row[AdoptantesTable.nombreCompleto],
        telefono = row[AdoptantesTable.telefono],
        edad = row[AdoptantesTable.edad],
        ocupacion = row[AdoptantesTable.ocupacion],
        ingresoMensual = row[AdoptantesTable.ingresoMensual],
        horasDeTrabajo = row[AdoptantesTable.horasDeTrabajo],
        tienePatio = row[AdoptantesTable.tienePatio],
        ninosEnCasa = row[AdoptantesTable.ninosEnCasa],
        tipoVivienda = row[AdoptantesTable.tipoVivienda],
        convivientes = row[AdoptantesTable.convivientes],
        mascotasAnteriores = row[AdoptantesTable.mascotasAnteriores],
        aunConservaMascotas = row[AdoptantesTable.aunConservaMascotas],
        responsabilidadesMascota = row[AdoptantesTable.responsabilidadesMascota],
        opinionEsterilizacion = row[AdoptantesTable.opinionEsterilizacion]
    )

    suspend fun findAll(): List<AdoptanteFullResponse> = dbQuery {
        AdoptantesTable.selectAll().map(::toAdoptanteFullResponse)
    }

    suspend fun create(request: AdoptanteRequest): AdoptanteFullResponse {
        val result = dbQuery {
            val insertStatement = AdoptantesTable.insert { table ->
                table[nombreCompleto] = request.nombreCompleto
                table[telefono] = request.telefono
                table[edad] = request.edad
                table[ocupacion] = request.ocupacion
                table[ingresoMensual] = request.ingresoMensual
                table[horasDeTrabajo] = request.horasDeTrabajo
                table[tienePatio] = request.tienePatio
                table[ninosEnCasa] = request.ninosEnCasa
                table[tipoVivienda] = request.tipoVivienda
                table[convivientes] = request.convivientes
                table[mascotasAnteriores] = request.mascotasAnteriores
                table[aunConservaMascotas] = request.aunConservaMascotas
                table[responsabilidadesMascota] = request.responsabilidadesMascota
                table[opinionEsterilizacion] = request.opinionEsterilizacion
            }
            insertStatement.resultedValues?.singleOrNull()?.let(::toAdoptanteFullResponse)
        }
        return result ?: throw IllegalStateException("Error al guardar el adoptante en la base de datos.")
    }


    suspend fun findById(id: Int): AdoptanteFullResponse? {
        return dbQuery {
            AdoptantesTable.select { AdoptantesTable.id eq id }
                .map(::toAdoptanteFullResponse)
                .singleOrNull()
        }
    }


    suspend fun delete(id: Int): Boolean {
        return dbQuery {
            AdoptantesTable.deleteWhere { AdoptantesTable.id eq id } > 0
        }
    }
}