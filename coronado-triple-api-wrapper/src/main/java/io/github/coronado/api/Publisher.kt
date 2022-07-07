package io.github.coronado.api

import com.squareup.moshi.Json
import java.math.BigDecimal

class Publisher {

    val serviceUrl = "partner/publishers"
}

// A physical address in the world.
// Addresses may be normalized by Triple for countries with known, standardized formatting rules.
data class Address(
    @Json(name = "complete_address") val completeAddress: String, // The complete address, as would be written out for mail delivery or route navigation. ex: "7370 BAKER ST STE 100\nPITTSBURGH, PA 15206"
    @Json(name = "line_1") val line1: String?, // ex: "7370 BAKER ST STE 100"
    @Json(name = "line_2") val line2: String?,
    val locality: String?, // City or locality name, ex: "PITTSBURGH"
    val province: String, // State abbreviation or province name, ex: "PA"
    @Json(name = "postal_code") val postalCode: String?, // ZIP Code™, ZIP+4, or postal code, ex: "15206"
    @Json(name = "country_code") val countryCode: String?, // 2-letter ISO-3166 country code, ex: "US"
    val latitude: Latitude,
    val longitude: Longitude
)

typealias Latitude = BigDecimal
typealias Longitude = BigDecimal
