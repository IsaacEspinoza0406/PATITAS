package com.patitas_web.domain.ports

import com.patitas_web.domain.entities.Adoptante

interface AdoptanteRepository {
    suspend fun findAll(): List<Adoptante>
    suspend fun findById(id: Int): Adoptante?
    suspend fun create(adoptante: Adoptante): Adoptante
    suspend fun update(id: Int, adoptante: Adoptante): Adoptante?
    suspend fun delete(id: Int): Boolean
}
