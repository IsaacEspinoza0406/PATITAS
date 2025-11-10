package com.patitas_web.application

import com.patitas_web.domain.DogPhotoRequest
import com.patitas_web.domain.DogPhotoResponse
import com.patitas_web.infrastructure.DatabaseFactory.dbQuery
import com.patitas_web.infrastructure.tables.DogPhotosTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class DogPhotoService {

    private fun toDogPhotoResponse(row: ResultRow): DogPhotoResponse = DogPhotoResponse(
        id = row[DogPhotosTable.id],
        dogId = row[DogPhotosTable.dogId],
        photoUrl = row[DogPhotosTable.photoUrl],
        description = row[DogPhotosTable.description]
    )

    suspend fun findByDogId(dogId: Int): List<DogPhotoResponse> = dbQuery {
        DogPhotosTable.select { DogPhotosTable.dogId eq dogId }
            .map(::toDogPhotoResponse)
    }

    suspend fun create(dogId: Int, request: DogPhotoRequest): DogPhotoResponse {
        val result = dbQuery {
            val insertStatement = DogPhotosTable.insert {
                it[DogPhotosTable.dogId] = dogId
                it[DogPhotosTable.photoUrl] = request.photoUrl
                request.description?.let { desc ->
                    it[DogPhotosTable.description] = desc
                }
            }
            insertStatement.resultedValues?.singleOrNull()?.let(::toDogPhotoResponse)
        }
        return result ?: throw IllegalStateException("Error al guardar la foto.")
    }

    suspend fun delete(photoId: Int): Boolean {
        return dbQuery {
            DogPhotosTable.deleteWhere { DogPhotosTable.id eq photoId } > 0
        }
    }

    suspend fun findById(photoId: Int): DogPhotoResponse? = dbQuery {
        DogPhotosTable.select { DogPhotosTable.id eq photoId }
            .map(::toDogPhotoResponse)
            .singleOrNull()
    }
}