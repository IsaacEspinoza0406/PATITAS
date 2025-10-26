package com.patitas_web.infrastructure.tables

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.datetime
import java.time.LocalDateTime

object AdoptantesTable : Table("adoptantes") {
    val id = integer("id").autoIncrement()

    val nombreCompleto = varchar("nombre_completo", 255)
    val telefono = varchar("telefono", 20)
    val edad = varchar("edad", 10)
    val ocupacion = varchar("ocupacion", 255)
    val ingresoMensual = varchar("ingreso_mensual", 100)
    val horasDeTrabajo = varchar("horas_de_trabajo", 100)

    val tienePatio = varchar("tiene_patio", 50)
    val ninosEnCasa = varchar("ninos_en_casa", 255)
    val tipoVivienda = varchar("tipo_vivienda", 50)
    val convivientes = varchar("convivientes", 10)

    val mascotasAnteriores = text("mascotas_anteriores")
    val aunConservaMascotas = text("aun_conserva_mascotas")
    val responsabilidadesMascota = text("responsabilidades_mascota")
    val opinionEsterilizacion = text("opinion_esterilizacion")


    override val primaryKey = PrimaryKey(id)
}