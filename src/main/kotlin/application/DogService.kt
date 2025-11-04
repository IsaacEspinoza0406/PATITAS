package com.patitas_web.application

import com.patitas_web.domain.DogRequest
import com.patitas_web.domain.DogResponse
import com.patitas_web.infrastructure.DatabaseFactory.dbQuery // Importamos nuestra función de ayuda
import com.patitas_web.infrastructure.tables.DogsTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.statements.UpdateBuilder

class DogService {


    private fun toDogResponse(row: ResultRow) = DogResponse(
        id = row[DogsTable.id],
        name = row[DogsTable.name],
        age = row[DogsTable.age],
        breed = row[DogsTable.breed],
        history = row[DogsTable.history],
        sterilized = row[DogsTable.sterilized],
        adopted = row[DogsTable.adopted]
    )


    private fun statementToDog(statement: UpdateBuilder<Int>, dog: DogRequest) {
        statement[DogsTable.name] = dog.name
        dog.age?.let { statement[DogsTable.age] = it }
        dog.breed?.let { statement[DogsTable.breed] = it }
        dog.history?.let { statement[DogsTable.history] = it }
        dog.sterilized?.let { statement[DogsTable.sterilized] = it }
        dog.adopted?.let { statement[DogsTable.adopted] = it }
    }


    suspend fun findAll(): List<DogResponse> = dbQuery {
        DogsTable.selectAll().map(::toDogResponse)
    }


    suspend fun create(request: DogRequest): DogResponse {
        val result = dbQuery {
            val insertStatement = DogsTable.insert { statementToDog(it, request) }
            insertStatement.resultedValues?.singleOrNull()?.let(::toDogResponse)
        }
        return result ?: throw IllegalStateException("Error al crear el perro.")
    }


    suspend fun update(id: Int, request: DogRequest): DogResponse? {
        val updated = dbQuery {
            DogsTable.update({ DogsTable.id eq id }) { statementToDog(it, request) }
        }
        return if (updated > 0) {
            dbQuery {
                DogsTable.select { DogsTable.id eq id }
                    .map(::toDogResponse)
                    .singleOrNull()
            }
        } else null
    }


    suspend fun delete(id: Int): Boolean {
        return dbQuery {
            DogsTable.deleteWhere { DogsTable.id eq id } > 0
        }
    }
}