package com.patitas_web.domain.ports

import com.patitas_web.domain.entities.Dog

interface DogRepository {
    suspend fun findAll(): List<Dog>
    suspend fun findById(id: Int): Dog?
    suspend fun create(dog: Dog): Dog
    suspend fun update(id: Int, dog: Dog): Dog?
    suspend fun delete(id: Int): Boolean
}
