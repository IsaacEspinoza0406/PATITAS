package com.patitas_web.infrastructure.repositories

import com.patitas_web.domain.entities.DogPhoto
import com.patitas_web.domain.ports.DogPhotoRepository
import com.patitas_web.infrastructure.DatabaseFactory.dbQuery
import com.patitas_web.infrastructure.tables.DogPhotosTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class SqlDogPhotoRepository : DogPhotoRepository {

    private fun toDogPhoto(row: ResultRow): DogPhoto = DogPhoto(
        id = row[DogPhotosTable.id],
        dogId = row[DogPhotosTable.dogId],
        photoUrl = row[DogPhotosTable.photoUrl],
        description = row[DogPhotosTable.description]
    )

    override suspend fun findByDogId(dogId: Int): List<DogPhoto> = dbQuery {
        DogPhotosTable.select { DogPhotosTable.dogId eq dogId }
            .map(::toDogPhoto)
    }

    override suspend fun findById(id: Int): DogPhoto? = dbQuery {
        DogPhotosTable.select { DogPhotosTable.id eq id }
            .map(::toDogPhoto)
            .singleOrNull()
    }

    override suspend fun create(photo: DogPhoto): DogPhoto {
        val result = dbQuery {
            val insertStatement = DogPhotosTable.insert {
                it[DogPhotosTable.dogId] = photo.dogId
                it[DogPhotosTable.photoUrl] = photo.photoUrl
                it[DogPhotosTable.description] = photo.description
            }
            insertStatement.resultedValues?.singleOrNull()?.let(::toDogPhoto)
        }
        return result ?: throw IllegalStateException("Error al guardar la foto.")
    }

    override suspend fun delete(id: Int): Boolean {
        return dbQuery {
            DogPhotosTable.deleteWhere { DogPhotosTable.id eq id } > 0
        }
    }
}
