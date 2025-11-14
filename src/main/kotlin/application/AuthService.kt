package com.patitas_web.application

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.patitas_web.domain.*
import com.patitas_web.infrastructure.DatabaseFactory.dbQuery
import com.patitas_web.infrastructure.tables.RolesTable
import com.patitas_web.infrastructure.tables.UsersTable
import org.jetbrains.exposed.sql.*
import java.util.*

object AuthService {
    
  
    suspend fun register(request: RegisterRequest): UserResponse {
        return dbQuery {
            
            
            val existingUser = UsersTable.select { UsersTable.email eq request.email }
                .singleOrNull() 
            
            if (existingUser != null) {
                throw IllegalArgumentException("El email ya está registrado")
            }
            
            
            val roleExists = RolesTable.select { RolesTable.id eq request.roleId }
                .singleOrNull()
            
            if (roleExists == null) {
                throw IllegalArgumentException("El rol con ID ${request.roleId} no existe")
            }
            

            val insertedId = UsersTable.insert {
                it[name] = request.name       
                it[email] = request.email        
                it[password] = request.password   
                it[roleId] = request.roleId      
            } get UsersTable.id  
            
            val roleName = RolesTable.select { RolesTable.id eq request.roleId }
                .single()[RolesTable.name]  
            
            
            UserResponse(
                id = insertedId,
                name = request.name,
                email = request.email,
                roleId = request.roleId,
                roleName = roleName
            )
        }
    }
    

    suspend fun login(request: LoginRequest): AuthResponse {
        return dbQuery {
            
          
            val userRow = (UsersTable innerJoin RolesTable)
                .select { UsersTable.email eq request.email }
                .singleOrNull()  
            
            if (userRow == null) {
                throw IllegalArgumentException("Email o contraseña incorrectos")
            }
            
            
            val storedPassword = userRow[UsersTable.password]
            
           
            if (request.password != storedPassword) {
                throw IllegalArgumentException("Email o contraseña incorrectos")
            }
            
          
            val token = JWT.create()
                .withIssuer("patitas-api")          
                .withSubject("authentication")       
                .withClaim("userId", userRow[UsersTable.id])   
                .withClaim("roleId", userRow[UsersTable.roleId]) 
                .withExpiresAt(Date(System.currentTimeMillis() + 86400000)) 
                .sign(Algorithm.HMAC256("mi-secreto-super-seguro"))  
            
            
            val userResponse = UserResponse(
                id = userRow[UsersTable.id],
                name = userRow[UsersTable.name],
                email = userRow[UsersTable.email],
                roleId = userRow[UsersTable.roleId],
                roleName = userRow[RolesTable.name]
            )
            
          
            AuthResponse(
                token = token,
                user = userResponse
            )
        }
    }
    
   
    suspend fun findById(userId: Int): UserResponse? {
        return dbQuery {
            
            val userRow = (UsersTable innerJoin RolesTable)
                .select { UsersTable.id eq userId }
                .singleOrNull()  
            
            userRow?.let {
                UserResponse(
                    id = it[UsersTable.id],
                    name = it[UsersTable.name],
                    email = it[UsersTable.email],
                    roleId = it[UsersTable.roleId],
                    roleName = it[RolesTable.name]
                )
            }
        }
    }
}
