package com.patitas_web.presentation

import com.patitas_web.application.DogPhotoService
import com.patitas_web.domain.DogPhotoRequest
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.http.content.*
import java.io.File

fun Application.configureDogPhotoRoutes() {
    val service = DogPhotoService()

    routing {
        route("/dogs/{dogId}/photos") {
            
            get {
                val dogId = call.parameters["dogId"]?.toIntOrNull()
                if (dogId == null) {
                    call.respond(HttpStatusCode.BadRequest, "ID de perro inválido")
                    return@get
                }
                
                val photos = service.findByDogId(dogId)
                call.respond(HttpStatusCode.OK, photos)
            }

            post {
                val dogId = call.parameters["dogId"]?.toIntOrNull()
                if (dogId == null) {
                    call.respond(HttpStatusCode.BadRequest, "ID de perro inválido")
                    return@post
                }

                try {
                    val request = call.receive<DogPhotoRequest>()
                    val photo = service.create(dogId, request)
                    call.respond(HttpStatusCode.Created, photo)
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.BadRequest, "Error en los datos: ${e.message}")
                }
            }

            post("/upload-photo") {
                val dogId = call.parameters["dogId"]?.toIntOrNull()
                if (dogId == null) {
                    call.respond(HttpStatusCode.BadRequest, "ID de perro inválido")
                    return@post
                }

                var photoUrl = ""
                var description = ""

                val multipart = call.receiveMultipart()
                multipart.forEachPart { part ->
                    if (part is PartData.FileItem) {
                        val fileName = "${System.currentTimeMillis()}_${part.originalFileName as String}"
                        val fileBytes = part.streamProvider().readBytes()
                        val file = File("/opt/apps/backend/uploads/$fileName")
                        if (!file.parentFile.exists()) file.parentFile.mkdirs()
                        file.writeBytes(fileBytes)
                        val baseUrl = System.getenv("BASE_URL") ?: "http://localhost:8080"
                        photoUrl = "$baseUrl/uploads/$fileName"
                    }
                    part.dispose()
                }

                if (photoUrl.isNotEmpty()) {
                    val request = DogPhotoRequest(photoUrl, description)
                    val photo = service.create(dogId, request)
                    call.respond(HttpStatusCode.Created, photo)
                } else {
                    call.respond(HttpStatusCode.BadRequest, "No se subió ninguna imagen")
                }
            }
        }

        route("/photos") {
            
            get("/{photoId}") {
                val photoId = call.parameters["photoId"]?.toIntOrNull()
                if (photoId == null) {
                    call.respond(HttpStatusCode.BadRequest, "ID de foto inválido")
                    return@get
                }

                val photo = service.findById(photoId)
                if (photo != null) {
                    call.respond(HttpStatusCode.OK, photo)
                } else {
                    call.respond(HttpStatusCode.NotFound, "Foto no encontrada")
                }
            }

            delete("/{photoId}") {
                val photoId = call.parameters["photoId"]?.toIntOrNull()
                if (photoId == null) {
                    call.respond(HttpStatusCode.BadRequest, "ID de foto inválido")
                    return@delete
                }

                val deleted = service.delete(photoId)
                if (deleted) {
                    call.respond(HttpStatusCode.OK, "Foto eliminada exitosamente")
                } else {
                    call.respond(HttpStatusCode.NotFound, "Foto no encontrada")
                }
            }
        }
    }
}