package com.patitas_web.domain.ports

import com.patitas_web.domain.entities.Donation

interface DonationRepository {
    suspend fun create(donation: Donation): Donation
    suspend fun findAll(): List<Donation>
}
