package com.patitas_web.domain

import kotlinx.serialization.Serializable

@Serializable
data class DonationRequest(
    val id: Int,
    val card_number: String,
    val cardholder_name: String,
    val cvv: String,
    val exp_year: Int,
    val email: String,
    val payment_method: String
)

@Serializable
data class DonationResponse(
    val id: Int,
    val card_number: String,
    val cardholder_name: String,
    val cvv: String,
    val exp_year: Int,
    val email: String,
    val payment_method: String
)

