package com.patitas_web.infrastructure.tables

import org.jetbrains.exposed.sql.Table

object DogsTable : Table("dogs") {
    val id = integer("id").autoIncrement()
    val name = varchar("name", 255)
    val age = integer("age").nullable()
    val breed = varchar("breed", 255).nullable()
    val history = text("history").nullable()
    val sterilized = varchar("sterilized", 10).nullable()
    val adopted = varchar("adopted", 10).nullable()
    val vaccines = text("vaccines").nullable()
    val createdBy = integer("created_by").default(1)

    override val primaryKey = PrimaryKey(id)
}

object AdoptantesTable : Table("adoptantes") {
    val id = integer("id").autoIncrement()
    val nombreCompleto = varchar("nombre_completo", 255)
    val telefono = varchar("telefono", 50)
    val edad = varchar("edad", 10)
    val ocupacion = varchar("ocupacion", 255)
    val ingresoMensual = varchar("ingreso_mensual", 255)
    val horasDeTrabajo = varchar("horas_de_trabajo", 255)
    val tienePatio = varchar("tiene_patio", 10)
    val ninosEnCasa = varchar("ninos_en_casa", 10)
    val tipoVivienda = varchar("tipo_vivienda", 255)
    val convivientes = varchar("convivientes", 255)
    val mascotasAnteriores = varchar("mascotas_anteriores", 255)
    val aunConservaMascotas = varchar("aun_conserva_mascotas", 255)
    val responsabilidadesMascota = text("responsabilidades_mascota")
    val opinionEsterilizacion = text("opinion_esterilizacion")

    override val primaryKey = PrimaryKey(id)
}

object DonationsTable : Table("donations") {
    val id = integer("id").autoIncrement()
    val userId = integer("user_id")
    val amount = decimal("amount", 10, 2)
    val methodId = integer("method_id") references PaymentMethodsTable.id
    val transactionId = varchar("transaction_id", 255).nullable()
    val payerEmail = varchar("payer_email", 255).nullable()
    val status = varchar("status", 50).nullable()

    override val primaryKey = PrimaryKey(id)
}

object PaymentMethodsTable : Table("payment_methods") {
    val id = integer("id").autoIncrement()
    val methodName = varchar("method_name", 255)

    override val primaryKey = PrimaryKey(id)
}

object UsersTable : Table("users") {
    val id = integer("id").autoIncrement()
    val name = varchar("name", 255)
    val email = varchar("email", 255).uniqueIndex()
    val password = varchar("password", 255)
    val roleId = integer("role_id") references RolesTable.id

    override val primaryKey = PrimaryKey(id)
}

object RolesTable : Table("roles") {
    val id = integer("id").autoIncrement()
    val name = varchar("name", 50).uniqueIndex()

    override val primaryKey = PrimaryKey(id)
}

object AdoptionQuestionnairesTable : Table("adoption_questionnaires") {
    val id = integer("id").autoIncrement()
    val adoptanteId = integer("adoptante_id") references AdoptantesTable.id
    val dogId = integer("dog_id") references DogsTable.id

    override val primaryKey = PrimaryKey(id)
}

object AdoptionRequestsTable : Table("adoption_requests") {
    val id = integer("id").autoIncrement()
    val adoptanteId = integer("adoptante_id") references AdoptantesTable.id
    val dogId = integer("dog_id") references DogsTable.id

    override val primaryKey = PrimaryKey(id)
}

object DogPhotosTable : Table("dog_photos") {
    val id = integer("id").autoIncrement()
    val dogId = integer("dog_id") references DogsTable.id
    val photoUrl = text("photo_url")
    val description = text("description").nullable()

    override val primaryKey = PrimaryKey(id)
}
