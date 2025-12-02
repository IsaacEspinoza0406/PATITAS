package com.patitas_web.application

import com.patitas_web.domain.entities.DogPhoto
import com.patitas_web.domain.ports.DogPhotoRepository

class DogPhotoService(private val repository: DogPhotoRepository) {
    suspend fun create(photo: DogPhoto): DogPhoto {
        return repository.create(photo)
    }

    suspend fun findByDogId(dogId: Int): List<DogPhoto> {
        return repository.findByDogId(dogId)
    }

    suspend fun findById(id: Int): DogPhoto? {
        return repository.findById(id)
    }

    suspend fun delete(id: Int): Boolean {
        return repository.delete(id)
    }
}
