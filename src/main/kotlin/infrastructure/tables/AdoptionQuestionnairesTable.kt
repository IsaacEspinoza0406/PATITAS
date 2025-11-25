package com.patitas_web.infrastructure.tables

import org.jetbrains.exposed.sql.Table

object AdoptionQuestionnairesTable : Table("adoption_requests") {
    val id = integer("id").autoIncrement()
    val adoptanteId = integer("adoptante_id").references(AdoptantesTable.id)
    val dogId = integer("dog_id").references(DogsTable.id)

    override val primaryKey = PrimaryKey(id)
}
