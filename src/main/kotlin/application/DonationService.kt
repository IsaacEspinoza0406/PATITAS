package com.patitas_web.application

import com.patitas_web.domain.entities.Donation
import com.patitas_web.domain.ports.DonationRepository

class DonationService(private val repository: DonationRepository) {

    suspend fun create(donation: Donation): Donation {
        return repository.create(donation)
    }

    suspend fun findAll(): List<Donation> {
        return repository.findAll()
    }
}
