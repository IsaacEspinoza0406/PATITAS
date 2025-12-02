package com.patitas_web.di

import com.patitas_web.application.DogService
import com.patitas_web.domain.ports.DogRepository
import com.patitas_web.infrastructure.repositories.SqlDogRepository

import com.patitas_web.application.AdoptanteService
import com.patitas_web.domain.ports.AdoptanteRepository
import com.patitas_web.infrastructure.repositories.SqlAdoptanteRepository

import com.patitas_web.application.AdoptionService
import com.patitas_web.domain.ports.AdoptionRepository
import com.patitas_web.infrastructure.repositories.SqlAdoptionRepository

import com.patitas_web.application.AuthService
import com.patitas_web.domain.ports.UserRepository
import com.patitas_web.infrastructure.repositories.SqlUserRepository

import com.patitas_web.application.DonationService
import com.patitas_web.domain.ports.DonationRepository
import com.patitas_web.infrastructure.repositories.SqlDonationRepository

import com.patitas_web.application.DogPhotoService
import com.patitas_web.domain.ports.DogPhotoRepository
import com.patitas_web.infrastructure.repositories.SqlDogPhotoRepository

object Dependencies {
    val dogPhotoRepository: DogPhotoRepository by lazy { SqlDogPhotoRepository() }

    val dogRepository: DogRepository by lazy { SqlDogRepository(dogPhotoRepository) }
    val dogService: DogService by lazy { DogService(dogRepository, dogPhotoRepository) }

    val adoptanteRepository: AdoptanteRepository by lazy { SqlAdoptanteRepository() }
    val adoptanteService: AdoptanteService by lazy { AdoptanteService(adoptanteRepository) }

    val adoptionRepository: AdoptionRepository by lazy { SqlAdoptionRepository() }
    val adoptionService: AdoptionService by lazy { AdoptionService(adoptionRepository) }

    val userRepository: UserRepository by lazy { SqlUserRepository() }
    val userService: UserService by lazy { UserService(userRepository) }

    val donationRepository: DonationRepository by lazy { SqlDonationRepository() }
    val donationService: DonationService by lazy { DonationService(donationRepository) }
}
