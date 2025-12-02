package com.patitas_web.application

import com.patitas_web.domain.entities.Dog
import com.patitas_web.domain.entities.DogPhoto
import com.patitas_web.domain.ports.DogPhotoRepository
import com.patitas_web.domain.ports.DogRepository

class DogService(
    private val repository: DogRepository,
    private val photoRepository: DogPhotoRepository
) {

    suspend fun findAll(): List<Dog> {
        return repository.findAll()
    }

    suspend fun findById(id: Int): Dog? {
        return repository.findById(id)
    }

    suspend fun create(dog: Dog): Dog {
        return repository.create(dog)
    }

    suspend fun update(id: Int, dog: Dog): Dog? {
        return repository.update(id, dog)
    }

    suspend fun delete(id: Int): Boolean {
        return repository.delete(id)
    }

    suspend fun addPhoto(dogId: Int, photoUrl: String, description: String?): DogPhoto {
        val photo = DogPhoto(0, dogId, photoUrl, description)
        return photoRepository.create(photo)
    }
}
