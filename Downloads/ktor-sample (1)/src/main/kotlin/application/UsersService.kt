package com.patitas_web.application

import com.patitas_web.domain.UserRequest
import com.patitas_web.domain.UserFullResponse
import com.patitas_web.infrastructure.tables.UsersTable
import com.patitas_web.infrastructure.DatabaseFactory
import com.patitas_web.infrastructure.DatabaseFactory.dbQuery
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll

class UsersService {
    private fun toUserFullResponse(row: ResultRow): UserFullResponse = UserFullResponse(
        id = row[UsersTable.id],
        role_id = row[UsersTable.role_id],
        name = row[UsersTable.name],
        email = row[UsersTable.email],
        password = row[UsersTable.password],
        telefono = row[UsersTable.telefono]
    )

//    suspend fun findByEmail(email: String): UserFullResponse? = dbQuery {
//
//    }

    suspend fun findAll(): List<UserFullResponse> = dbQuery {
        UsersTable.selectAll().map(::toUserFullResponse)
    }

    suspend fun create (request: UserRequest): UserFullResponse{
        val result = dbQuery {
            val insertStatement = UsersTable.insert { table ->
                table[UsersTable.id] = request.id
                table[UsersTable.role_id] = request.role_id
                table[UsersTable.name] = request.name
                table[UsersTable.email] = request.email
                table[UsersTable.password] = request.password
                table[UsersTable.telefono] = request.telefono
            }
            insertStatement.resultedValues?.singleOrNull()?.let(::toUserFullResponse)
        }
        return result ?: throw IllegalStateException("Error al guardar al usuario en la base de datos.")
    }

    suspend fun delete (request: UserRequest){
        val result = dbQuery {
            val insertStatement = UsersTable.deleteWhere { UsersTable.id eq request.id }
        }
        return result ?: throw IllegalStateException("Error al eliminar usuario de la base de datos")
    }

}