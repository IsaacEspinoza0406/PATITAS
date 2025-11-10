package com.patitas_web.application

import com.patitas_web.domain.DogRequest
import com.patitas_web.domain.DogResponse
import com.patitas_web.infrastructure.DatabaseFactory.dbQuery
import com.patitas_web.infrastructure.tables.DogsTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.statements.UpdateBuilder

class DogService {

    private val photoService = DogPhotoService()

    private suspend fun toDogResponse(row: ResultRow): DogResponse {
        val dogId = row[DogsTable.id]
        val photos = photoService.findByDogId(dogId)
        
        return DogResponse(
            id = row[DogsTable.id],
            name = row[DogsTable.name],
            age = row[DogsTable.age],
            breed = row[DogsTable.breed],
            history = row[DogsTable.history],
            sterilized = row[DogsTable.sterilized],
            adopted = row[DogsTable.adopted],
            photos = photos
        )
    }


    private fun statementToDog(statement: UpdateBuilder<Int>, dog: DogRequest) {
        statement[DogsTable.name] = dog.name
        dog.age?.let { statement[DogsTable.age] = it }
        dog.breed?.let { statement[DogsTable.breed] = it }
        dog.history?.let { statement[DogsTable.history] = it }
        dog.sterilized?.let { statement[DogsTable.sterilized] = it }
        dog.adopted?.let { statement[DogsTable.adopted] = it }
    }


    suspend fun findAll(): List<DogResponse> {
        val dogs = dbQuery {
            DogsTable.selectAll().toList()
        }
        return dogs.map { row -> toDogResponse(row) }
    }


    suspend fun create(request: DogRequest): DogResponse {
        val insertedRow = dbQuery {
            val insertStatement = DogsTable.insert {
                statementToDog(it, request)
                it[DogsTable.createdBy] = 1
            }
            insertStatement.resultedValues?.singleOrNull()
        }
        return insertedRow?.let { toDogResponse(it) } ?: throw IllegalStateException("Error al crear el perro.")
    }


    suspend fun update(id: Int, request: DogRequest): DogResponse? {
        val updated = dbQuery {
            DogsTable.update({ DogsTable.id eq id }) { statementToDog(it, request) }
        }
        return if (updated > 0) {
            val updatedRow = dbQuery {
                DogsTable.select { DogsTable.id eq id }.singleOrNull()
            }
            updatedRow?.let { toDogResponse(it) }
        } else null
    }


    suspend fun delete(id: Int): Boolean {
        return dbQuery {
            DogsTable.deleteWhere { DogsTable.id eq id } > 0
        }
    }
}