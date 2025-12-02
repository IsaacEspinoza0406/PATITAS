package com.patitas_web.infrastructure.repositories

import com.patitas_web.domain.entities.Adoptante
import com.patitas_web.domain.ports.AdoptanteRepository
import com.patitas_web.infrastructure.DatabaseFactory.dbQuery
import com.patitas_web.infrastructure.tables.AdoptantesTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class SqlAdoptanteRepository : AdoptanteRepository {

    private fun toAdoptante(row: ResultRow): Adoptante = Adoptante(
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

    override suspend fun findAll(): List<Adoptante> = dbQuery {
        AdoptantesTable.selectAll().map(::toAdoptante)
    }

    override suspend fun findById(id: Int): Adoptante? = dbQuery {
        AdoptantesTable.select { AdoptantesTable.id eq id }
            .map(::toAdoptante)
            .singleOrNull()
    }

    override suspend fun create(adoptante: Adoptante): Adoptante {
        val result = dbQuery {
            val insertStatement = AdoptantesTable.insert { table ->
                table[nombreCompleto] = adoptante.nombreCompleto
                table[telefono] = adoptante.telefono
                table[edad] = adoptante.edad
                table[ocupacion] = adoptante.ocupacion
                table[ingresoMensual] = adoptante.ingresoMensual
                table[horasDeTrabajo] = adoptante.horasDeTrabajo
                table[tienePatio] = adoptante.tienePatio
                table[ninosEnCasa] = adoptante.ninosEnCasa
                table[tipoVivienda] = adoptante.tipoVivienda
                table[convivientes] = adoptante.convivientes
                table[mascotasAnteriores] = adoptante.mascotasAnteriores
                table[aunConservaMascotas] = adoptante.aunConservaMascotas
                table[responsabilidadesMascota] = adoptante.responsabilidadesMascota
                table[opinionEsterilizacion] = adoptante.opinionEsterilizacion
            }
            insertStatement.resultedValues?.singleOrNull()?.let(::toAdoptante)
        }
        return result ?: throw IllegalStateException("Error al guardar el adoptante en la base de datos.")
    }

    override suspend fun update(id: Int, adoptante: Adoptante): Adoptante? {
        val updated = dbQuery {
            AdoptantesTable.update({ AdoptantesTable.id eq id }) { table ->
                table[nombreCompleto] = adoptante.nombreCompleto
                table[telefono] = adoptante.telefono
                table[edad] = adoptante.edad
                table[ocupacion] = adoptante.ocupacion
                table[ingresoMensual] = adoptante.ingresoMensual
                table[horasDeTrabajo] = adoptante.horasDeTrabajo
                table[tienePatio] = adoptante.tienePatio
                table[ninosEnCasa] = adoptante.ninosEnCasa
                table[tipoVivienda] = adoptante.tipoVivienda
                table[convivientes] = adoptante.convivientes
                table[mascotasAnteriores] = adoptante.mascotasAnteriores
                table[aunConservaMascotas] = adoptante.aunConservaMascotas
                table[responsabilidadesMascota] = adoptante.responsabilidadesMascota
                table[opinionEsterilizacion] = adoptante.opinionEsterilizacion
            }
        }
        return if (updated > 0) findById(id) else null
    }

    override suspend fun delete(id: Int): Boolean {
        return dbQuery {
            AdoptantesTable.deleteWhere { AdoptantesTable.id eq id } > 0
        }
    }
}
