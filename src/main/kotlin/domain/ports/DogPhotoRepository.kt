package com.patitas_web.domain.ports

import com.patitas_web.domain.entities.DogPhoto

interface DogPhotoRepository {
    suspend fun findByDogId(dogId: Int): List<DogPhoto>
    suspend fun findById(id: Int): DogPhoto?
    suspend fun create(photo: DogPhoto): DogPhoto
    suspend fun delete(id: Int): Boolean
}
