package com.patitas_web.presentation

import com.patitas_web.di.Dependencies
import com.patitas_web.domain.entities.Dog
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.http.content.*

fun Application.configureDogRoutes() {
    val service = Dependencies.dogService
    
    routing {
        route("/dogs") {
            get {
                call.respond(service.findAll())
            }
            
            get("/{id}") {
                val id = call.parameters["id"]?.toIntOrNull()
                if (id == null) {
                    call.respond(HttpStatusCode.BadRequest, "ID inválido")
                    return@get
                }
                val dog = service.findById(id)
                if (dog != null) {
                    call.respond(dog)
                } else {
                    call.respond(HttpStatusCode.NotFound, "Perro no encontrado")
                }
            }
            
            post {
                val dog = call.receive<Dog>()
                val createdDog = service.create(dog)
                call.respond(HttpStatusCode.Created, createdDog)
            }
            
            put("/{id}") {
                val id = call.parameters["id"]?.toIntOrNull()
                if (id == null) {
                    call.respond(HttpStatusCode.BadRequest, "ID inválido")
                    return@put
                }
                val dog = call.receive<Dog>()
                val updatedDog = service.update(id, dog)
                if (updatedDog != null) {
                    call.respond(HttpStatusCode.OK, updatedDog)
                } else {
                    call.respond(HttpStatusCode.NotFound, "Perro no encontrado")
                }
            }
            
            delete("/{id}") {
                val id = call.parameters["id"]?.toIntOrNull()
                if (id == null) {
                    call.respond(HttpStatusCode.BadRequest, "ID inválido")
                    return@delete
                }
                if (service.delete(id)) {
                    call.respond(HttpStatusCode.OK, "Perro eliminado")
                } else {
                    call.respond(HttpStatusCode.NotFound, "Perro no encontrado")
                }
            }

            post("/{id}/photos/upload-photo") {
                val id = call.parameters["id"]?.toIntOrNull()
                if (id == null) {
                    call.respond(HttpStatusCode.BadRequest, "ID inválido")
                    return@post
                }
                
                // Verify dog exists
                if (service.findById(id) == null) {
                    call.respond(HttpStatusCode.NotFound, "Perro no encontrado")
                    return@post
                }
                
                try {
                    val multipart = call.receiveMultipart()
                    var photoUrl = ""
                    
                    multipart.forEachPart { part ->
                        if (part is PartData.FileItem) {
                            val fileName = part.originalFileName as String
                            // In a real scenario, save the file to storage (S3/Disk)
                            // For demonstration, we generate a placeholder URL based on the filename
                            photoUrl = "https://placehold.co/400x400?text=$fileName"
                            part.dispose()
                        }
                    }
                    
                    if (photoUrl.isNotEmpty()) {
                        val photo = service.addPhoto(id, photoUrl, "Uploaded photo")
                        call.respond(HttpStatusCode.Created, photo)
                    } else {
                        call.respond(HttpStatusCode.BadRequest, "No file uploaded")
                    }
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.InternalServerError, "Error uploading photo: ${e.message}")
                }
            }
        }
    }
}
