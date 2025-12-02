package com.patitas_web.domain.entities

import com.patitas_web.domain.DogPhotoResponse

data class Dog(
    val id: Int = 0,
    val name: String,
    val age: Int?,
    val breed: String?,
    val history: String?,
    val sterilized: String?,
    val adopted: String?,
    val vaccines: String? = null,
    val photos: List<DogPhotoResponse> = emptyList()
)
