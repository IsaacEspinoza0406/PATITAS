package com.patitas_web.infrastructure.tables

import org.jetbrains.exposed.sql.Table

object DonationTable: Table("payment_test_data") {
    val id = integer("id").autoIncrement()
    var card_number = varchar("card_number", 25)
    var cardholder_name = varchar("cardholder_name", 255)
    var cvv = varchar("cvv", 4)
    var exp_year = integer("exp_year")
    var email = varchar("email", 255)
    var payment_method = varchar("payment_method", 50)

}