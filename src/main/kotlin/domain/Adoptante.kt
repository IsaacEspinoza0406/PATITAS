package com.patitas_web.domain

import kotlinx.serialization.Serializable

@Serializable
data class AdoptanteRequest(
    val nombreCompleto: String,
    val telefono: String,
    val edad: String,
    val ocupacion: String,
    val ingresoMensual: String,
    val horasDeTrabajo: String,
    val tienePatio: String,
    val ninosEnCasa: String,
    val tipoVivienda: String,
    val convivientes: String,
    val mascotasAnteriores: String,
    val aunConservaMascotas: String,
    val responsabilidadesMascota: String,
    val opinionEsterilizacion: String
)

@Serializable
data class AdoptanteFullResponse(
    val id: Int,
    val nombreCompleto: String,
    val telefono: String,
    val edad: String,
    val ocupacion: String,
    val ingresoMensual: String,
    val horasDeTrabajo: String,
    val tienePatio: String,
    val ninosEnCasa: String,
    val tipoVivienda: String,
    val convivientes: String,
    val mascotasAnteriores: String,
    val aunConservaMascotas: String,
    val responsabilidadesMascota: String,
    val opinionEsterilizacion: String,
)