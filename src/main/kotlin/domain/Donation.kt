package com.patitas_web.domain

import kotlinx.serialization.Serializable

@Serializable
data class DonationRequest(
    val paymentMethodToken: String,
    val amount: Long,
    val currency: String,
)
@Serializable
data class DonationResponse(
    val status: String,
    val transactionId: String,
    val message: String,
)