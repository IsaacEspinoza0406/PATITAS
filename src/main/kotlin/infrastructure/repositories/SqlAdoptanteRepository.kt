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
                table[AdoptantesTable.nombreCompleto] = adoptante.nombreCompleto
                table[AdoptantesTable.telefono] = adoptante.telefono
                table[AdoptantesTable.edad] = adoptante.edad
                table[AdoptantesTable.ocupacion] = adoptante.ocupacion
                table[AdoptantesTable.ingresoMensual] = adoptante.ingresoMensual
                table[AdoptantesTable.horasDeTrabajo] = adoptante.horasDeTrabajo
                table[AdoptantesTable.tienePatio] = adoptante.tienePatio
                table[AdoptantesTable.ninosEnCasa] = adoptante.ninosEnCasa
                table[AdoptantesTable.tipoVivienda] = adoptante.tipoVivienda
                table[AdoptantesTable.convivientes] = adoptante.convivientes
                table[AdoptantesTable.mascotasAnteriores] = adoptante.mascotasAnteriores
                table[AdoptantesTable.aunConservaMascotas] = adoptante.aunConservaMascotas
                table[AdoptantesTable.responsabilidadesMascota] = adoptante.responsabilidadesMascota
                table[AdoptantesTable.opinionEsterilizacion] = adoptante.opinionEsterilizacion
            }
            insertStatement.resultedValues?.singleOrNull()?.let(::toAdoptante)
        }
        return result ?: throw IllegalStateException("Error al guardar el adoptante en la base de datos.")
    }

    override suspend fun update(id: Int, adoptante: Adoptante): Adoptante? {
        val updated = dbQuery {
            AdoptantesTable.update({ AdoptantesTable.id eq id }) { table ->
                table[AdoptantesTable.nombreCompleto] = adoptante.nombreCompleto
                table[AdoptantesTable.telefono] = adoptante.telefono
                table[AdoptantesTable.edad] = adoptante.edad
                table[AdoptantesTable.ocupacion] = adoptante.ocupacion
                table[AdoptantesTable.ingresoMensual] = adoptante.ingresoMensual
                table[AdoptantesTable.horasDeTrabajo] = adoptante.horasDeTrabajo
                table[AdoptantesTable.tienePatio] = adoptante.tienePatio
                table[AdoptantesTable.ninosEnCasa] = adoptante.ninosEnCasa
                table[AdoptantesTable.tipoVivienda] = adoptante.tipoVivienda
                table[AdoptantesTable.convivientes] = adoptante.convivientes
                table[AdoptantesTable.mascotasAnteriores] = adoptante.mascotasAnteriores
                table[AdoptantesTable.aunConservaMascotas] = adoptante.aunConservaMascotas
                table[AdoptantesTable.responsabilidadesMascota] = adoptante.responsabilidadesMascota
                table[AdoptantesTable.opinionEsterilizacion] = adoptante.opinionEsterilizacion
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
