package com.patitas_web.application

import DogsTable
import com.patitas_web.domain.DogRequest
import com.patitas_web.domain.DogResponse
import com.patitas_web.infrastructure.DatabaseFactory
import com.patitas_web.infrastructure.DatabaseFactory.dbQuery
import com.patitas_web.infrastructure.tables.DogsTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.statements.UpdateBuilder

class DogService {
    private fun toDogResponse(row: ResultRow)= DogResponse (
        id = row[DogsTable.id],
        name = row[Dogstable.name],
        age =  row [DogsTable.age]
        breed = row[DogsTable.breed],
        history = row[]
}