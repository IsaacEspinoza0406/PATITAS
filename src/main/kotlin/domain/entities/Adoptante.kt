package com.patitas_web.domain.entities

import kotlinx.serialization.Serializable

@Serializable
data class Adoptante(
    val id: Int = 0,
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
