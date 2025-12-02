package com.patitas_web.domain.entities

data class Donation(
    val id: Int = 0,
    val userId: Int,
    val amount: Double,
    val methodName: String,
    val transactionId: String?,
    val payerEmail: String?,
    val status: String?
)
