package com.patitas_web.domain

import kotlinx.serialization.Serializable

@Serializable
data class PaymentRequest(
    val id: Int,
    val user_id: Int,
    val amount: Double,
    val method: String,
    val status: String,
)

@Serializable
data class PaymentFullResponse(
    val id: Int,
    val user_id: Int,
    val amount: Double,
    val method: String,
    val status: String,
    val created_at: String
)