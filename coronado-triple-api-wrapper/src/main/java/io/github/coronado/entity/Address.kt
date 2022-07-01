package io.github.coronado.entity

import java.math.BigDecimal

data class Address(val completeAddress: String) {
    constructor(
        completeAddress: String,
        line1: String?,
        line2: String?,
        locality: String?,
        province: String?,
        postalCode: String?,
        countryCode: String?,
        latitude: BigDecimal?,
        longitude: BigDecimal?
    ) : this (completeAddress)
}
