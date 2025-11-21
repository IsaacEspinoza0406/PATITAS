package com.patitas_web.domain

import kotlinx.serialization.Serializable

@Serializable
data class DonationRequest(
    val userId: Int,
    val amount: Double,
    val methodName: String, // "PayPal", "Card", etc.
    val transactionId: String?,
    val payerEmail: String?
)

@Serializable
data class DonationResponse(
    val id: Int,
    val userId: Int,
    val amount: Double,
    val methodName: String,
    val transactionId: String?,
    val payerEmail: String?,
    val status: String?
)
