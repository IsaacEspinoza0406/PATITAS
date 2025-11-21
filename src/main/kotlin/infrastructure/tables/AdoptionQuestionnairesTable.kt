package com.patitas_web.infrastructure.tables

import org.jetbrains.exposed.sql.Table

object AdoptionQuestionnairesTable : Table("adoption_questionnaires") {
    val id = integer("id").autoIncrement()
    val userId = integer("user_id").references(UsersTable.id)
    val dogId = integer("dog_id").references(DogsTable.id)

    override val primaryKey = PrimaryKey(id)
}
