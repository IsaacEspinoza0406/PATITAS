package com.patitas_web.infrastructure.repositories

import com.patitas_web.domain.entities.Dog
import com.patitas_web.domain.ports.DogRepository
import com.patitas_web.domain.ports.DogPhotoRepository
import com.patitas_web.infrastructure.DatabaseFactory.dbQuery
import com.patitas_web.infrastructure.tables.DogsTable
import com.patitas_web.infrastructure.tables.DogPhotosTable
import com.patitas_web.infrastructure.tables.AdoptionQuestionnairesTable
import com.patitas_web.infrastructure.tables.AdoptionRequestsTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.statements.UpdateBuilder
import com.patitas_web.domain.DogPhotoResponse

class SqlDogRepository(private val photoRepository: DogPhotoRepository) : DogRepository {

    private suspend fun toDog(row: ResultRow): Dog {
        val dogId = row[DogsTable.id]
        
        val photos = photoRepository.findByDogId(dogId).map { 
             DogPhotoResponse(it.id, it.dogId, it.photoUrl, it.description)
        }
        
        return Dog(
            id = row[DogsTable.id],
            name = row[DogsTable.name],
            age = row[DogsTable.age],
            breed = row[DogsTable.breed],
            history = row[DogsTable.history],
            sterilized = row[DogsTable.sterilized],
            adopted = row[DogsTable.adopted],
            vaccines = row[DogsTable.vaccines],
            photos = photos
        )
    }

    private fun statementToDog(statement: UpdateBuilder<Int>, dog: Dog) {
        statement[DogsTable.name] = dog.name
        dog.age?.let { statement[DogsTable.age] = it }
        dog.breed?.let { statement[DogsTable.breed] = it }
        dog.history?.let { statement[DogsTable.history] = it }
        dog.sterilized?.let { statement[DogsTable.sterilized] = it }
        dog.adopted?.let { statement[DogsTable.adopted] = it }
        dog.vaccines?.let { statement[DogsTable.vaccines] = it }
    }

    override suspend fun findAll(): List<Dog> {
        return dbQuery {
            DogsTable.selectAll().map { it }
        }.map { toDog(it) }
    }

    override suspend fun findById(id: Int): Dog? {
        val row = dbQuery {
            DogsTable.select { DogsTable.id eq id }
                .singleOrNull()
        }
        return row?.let { toDog(it) }
    }

    override suspend fun create(dog: Dog): Dog {
        val insertedRow = dbQuery {
            val insertStatement = DogsTable.insert {
                statementToDog(it, dog)
                it[DogsTable.createdBy] = 1 // TODO: Assign actual user ID from context
            }
            insertStatement.resultedValues?.singleOrNull()
        }
        return insertedRow?.let { toDog(it) } ?: throw IllegalStateException("Error al crear el perro.")
    }

    override suspend fun update(id: Int, dog: Dog): Dog? {
        val updated = dbQuery {
            DogsTable.update({ DogsTable.id eq id }) { statementToDog(it, dog) }
        }
        return if (updated > 0) {
            findById(id)
        } else null
    }

    override suspend fun delete(id: Int): Boolean {
        return dbQuery {
            // Manual Cascade Delete
            DogPhotosTable.deleteWhere { DogPhotosTable.dogId eq id }
            AdoptionQuestionnairesTable.deleteWhere { AdoptionQuestionnairesTable.dogId eq id }
            AdoptionRequestsTable.deleteWhere { AdoptionRequestsTable.dogId eq id }
            
            DogsTable.deleteWhere { DogsTable.id eq id } > 0
        }
    }
}
