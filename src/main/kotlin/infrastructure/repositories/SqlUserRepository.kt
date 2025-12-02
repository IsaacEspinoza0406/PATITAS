package com.patitas_web.infrastructure.repositories

import com.patitas_web.domain.entities.User
import com.patitas_web.domain.ports.UserRepository
import com.patitas_web.infrastructure.DatabaseFactory.dbQuery
import com.patitas_web.infrastructure.tables.RolesTable
import com.patitas_web.infrastructure.tables.UsersTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class SqlUserRepository : UserRepository {

    private fun toUser(row: ResultRow): User = User(
        id = row[UsersTable.id],
        name = row[UsersTable.name],
        email = row[UsersTable.email],
        password = row[UsersTable.password],
        roleId = row[UsersTable.roleId],
        roleName = row.getOrNull(RolesTable.name)
    )

    override suspend fun findByEmail(email: String): User? = dbQuery {
        (UsersTable innerJoin RolesTable)
            .select { UsersTable.email eq email }
            .map(::toUser)
            .singleOrNull()
    }

    override suspend fun findById(id: Int): User? = dbQuery {
        (UsersTable innerJoin RolesTable)
            .select { UsersTable.id eq id }
            .map(::toUser)
            .singleOrNull()
    }

    override suspend fun create(user: User): User {
        val id = dbQuery {
            UsersTable.insert {
                it[UsersTable.name] = user.name
                it[UsersTable.email] = user.email
                it[UsersTable.password] = user.password
                it[UsersTable.roleId] = user.roleId
            } get UsersTable.id
        }
        return findById(id) ?: throw IllegalStateException("Error creating user")
    }

    override suspend fun roleExists(roleId: Int): Boolean = dbQuery {
        RolesTable.select { RolesTable.id eq roleId }.count() > 0
    }

    override suspend fun getRoleName(roleId: Int): String? = dbQuery {
        RolesTable.select { RolesTable.id eq roleId }
            .map { it[RolesTable.name] }
            .singleOrNull()
    }
}
