package com.patitas_web.application

import com.patitas_web.domain.AdoptionRequest
import com.patitas_web.domain.AdoptionResponse
import com.patitas_web.domain.entities.Adoption
import com.patitas_web.domain.ports.AdoptionRepository

class AdoptionService(private val repository: AdoptionRepository) {

    private fun toAdoption(request: AdoptionRequest): Adoption = Adoption(
        adoptanteId = request.adoptanteId,
        dogId = request.dogId
    )

    private fun toAdoptionResponse(adoption: Adoption): AdoptionResponse = AdoptionResponse(
        id = adoption.id,
        adoptanteId = adoption.adoptanteId,
        dogId = adoption.dogId,
        dogName = adoption.dogName,
        nombreCompleto = adoption.nombreCompleto,
        telefono = adoption.telefono,
        edad = adoption.edad,
        ocupacion = adoption.ocupacion,
        ingresoMensual = adoption.ingresoMensual,
        horasDeTrabajo = adoption.horasDeTrabajo,
        tienePatio = adoption.tienePatio,
        ninosEnCasa = adoption.ninosEnCasa,
        tipoVivienda = adoption.tipoVivienda,
        convivientes = adoption.convivientes,
        mascotasAnteriores = adoption.mascotasAnteriores,
        aunConservaMascotas = adoption.aunConservaMascotas,
        responsabilidadesMascota = adoption.responsabilidadesMascota,
        opinionEsterilizacion = adoption.opinionEsterilizacion
    )

    suspend fun findAll(): List<AdoptionResponse> {
        return repository.findAll().map(::toAdoptionResponse)
    }

    suspend fun create(request: AdoptionRequest): AdoptionResponse {
        val adoption = toAdoption(request)
        val createdAdoption = repository.create(adoption)
        return toAdoptionResponse(createdAdoption)
    }

    suspend fun delete(id: Int): Boolean {
        return repository.delete(id)
    }

    suspend fun accept(id: Int): Boolean {
        return repository.accept(id)
    }
}
