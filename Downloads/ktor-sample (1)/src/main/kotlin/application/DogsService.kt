package com.patitas_web.application

import com.patitas_web.domain.DogsFullResponse
import com.patitas_web.domain.DogsRequest
import com.patitas_web.infrastructure.DatabaseFactory.dbQuery
import com.patitas_web.infrastructure.tables.DogsTable
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import kotlin.collections.firstOrNull


class DogsService {
    private fun toDogsFullResponse(row: ResultRow): DogsFullResponse = DogsFullResponse(
        id = row[DogsTable.id],
        name = row[DogsTable.name],
        age = row[DogsTable.age],
        breed = row[DogsTable.breed],
        history = row[DogsTable.history],
        sterilized = row[DogsTable.sterilized],
        adopted = row[DogsTable.adopted],
        created_by = row[DogsTable.created_by]
    )

    suspend fun findAll(): List<DogsFullResponse> = dbQuery {
        DogsTable.selectAll().map(::toDogsFullResponse)
    }

    suspend fun create(request: DogsRequest): DogsFullResponse {
        val result = dbQuery {
            val insertStatement = DogsTable.insert { table ->
                table[DogsTable.id] = request.id
                table[DogsTable.name] = request.name
                table[DogsTable.age] = request.age
                table[DogsTable.breed] = request.breed
                table[DogsTable.history] = request.history
                table[DogsTable.sterilized] = request.sterilized
                table[DogsTable.adopted] = request.adopted
                table[DogsTable.created_by] = request.created_by
            }
            insertStatement.resultedValues?.singleOrNull()?.let(::toDogsFullResponse)
        }
        return result ?: throw IllegalStateException("Error al guardar al perro en la base de datos.")
    }

    suspend fun delete(request: DogsRequest) {
        val result = dbQuery {
            val insertStatement = DogsTable.deleteWhere { DogsTable.id eq request.id }
        }
        return result ?: throw IllegalStateException("Error al eliminar el perro de la base de datos")
    }

    suspend fun getById(id: Int): DogsFullResponse? {
        return dbQuery {
            DogsTable.select { DogsTable.id eq id }
                .map { toDogsFullResponse(it) }
                .firstOrNull()
        }
    }

//    suspend fun update(request: DogsRequest){
//        val result = dbQuery {
//            val insertStatement = DogsTable.update({ DogsTable.id eq request.id }) {
//
//            }
//        }
//    }

}