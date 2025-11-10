package com.patitas_web.infrastructure.tables

import org.jetbrains.exposed.sql.Table

object DogPhotosTable : Table("dog_photos") {
    val id = integer("id").autoIncrement()
    val dogId = integer("dog_id")  // FK hacia dogs.id
    val photoUrl = varchar("photo_url", 255)
    val description = varchar("description", 255).nullable()
    
    override val primaryKey = PrimaryKey(id)
}