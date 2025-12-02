package com.patitas_web.domain.ports

import com.patitas_web.domain.entities.Adoption

interface AdoptionRepository {
    suspend fun findAll(): List<Adoption>
    suspend fun create(adoption: Adoption): Adoption
    suspend fun delete(id: Int): Boolean
    suspend fun accept(id: Int): Boolean
}
