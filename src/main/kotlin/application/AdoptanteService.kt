package com.patitas_web.application

import com.patitas_web.domain.entities.Adoptante
import com.patitas_web.domain.ports.AdoptanteRepository

class AdoptanteService(private val repository: AdoptanteRepository) {

    suspend fun create(adoptante: Adoptante): Adoptante {
        return repository.create(adoptante)
    }
}
