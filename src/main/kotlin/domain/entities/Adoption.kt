package com.patitas_web.domain.entities

data class Adoption(
    val id: Int = 0,
    val adoptanteId: Int,
    val dogId: Int,
    val dogName: String? = null,
    val nombreCompleto: String? = null,
    val telefono: String? = null,
    val edad: String? = null,
    val ocupacion: String? = null,
    val ingresoMensual: String? = null,
    val horasDeTrabajo: String? = null,
    val tienePatio: String? = null,
    val ninosEnCasa: String? = null,
    val tipoVivienda: String? = null,
    val convivientes: String? = null,
    val mascotasAnteriores: String? = null,
    val aunConservaMascotas: String? = null,
    val responsabilidadesMascota: String? = null,
    val opinionEsterilizacion: String? = null,
    val dogImageUrl: String? = null
)
