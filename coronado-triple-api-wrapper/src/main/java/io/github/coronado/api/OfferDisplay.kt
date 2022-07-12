package io.github.coronado.api

import com.squareup.moshi.Json
import com.squareup.moshi.adapter
import io.github.coronado.tripleapi.components.schemas.OfferCategory
import io.github.coronado.tripleapi.components.schemas.OfferMode
import io.github.coronado.tripleapi.components.schemas.OfferType
import java.net.URI
import java.net.http.HttpRequest
import java.net.http.HttpResponse

class OfferDisplay(val client: Client) {

    @OptIn(ExperimentalStdlibApi::class)
    val searchOfferJsonAdapter = client.moshi.adapter<OfferSearchGetOrPostRequestBody>()

    fun search(proximityTarget: GeoTarget, cardAccountIdentifier: CardAccountIdentifier, textQuery: String?, pageSize: Int?, pageOffset: Int?): Any? {
        val servicePath = "partner/offer-display/search-offers"
        val requestUrl = "${client.apiUrl}$servicePath"
        val offerSearchRequestBody = OfferSearchGetOrPostRequestBody(proximityTarget, cardAccountIdentifier, null, null, null, null)
        val json = searchOfferJsonAdapter.toJson(offerSearchRequestBody)
        println(json)
        val request = HttpRequest.newBuilder().POST(HttpRequest.BodyPublishers.ofString(json))
            .header("Authorization", "${client.tokenType()} ${client.token()}")
            .header("content-type", "application/json")
            .uri(URI(requestUrl)).build()
        val response = client.httpClient.send(request, HttpResponse.BodyHandlers.ofString())
        when (val responseStatusCode = response.statusCode()) {
            401 -> throw RuntimeException("Not enough permissions")
            else -> println("$responseStatusCode ${response.body()}")
        }
        return null
    }

}

data class OfferSearchGetOrPostRequestBody(
    @Json(name = "proximity_target") val proximityTarget: GeoTarget,
    @Json(name = "card_account_identifier") val cardAccountIdentifier: CardAccountIdentifier,
    @Json(name = "text_query") val textQuery: String?,
    @Json(name = "page_size") val pageSize: Int?,
    @Json(name = "page_offset") val pageOffset: Int?,
    @Json(name = "apply_filter") val applyFilter: OfferSearchFilter?
)

data class GeoTarget(
    @Json(name = "postal_code") val postalCode: String?,
    @Json(name = "county_code") val countryCode: String?,
    val radius: Int?
)

data class CardAccountIdentifier(
    @Json(name = "card_account_id") val cardAccountId: String?,
    @Json(name = "external_card_account_id") val externalCardAccountId: String?,
    @Json(name = "publisher_external_id") val publisherExternalId: String?,
    @Json(name = "card_program_id") val cardProgramId: String?
)

data class OfferSearchFilter(
    val offerCategory: OfferCategory,
    val mode: OfferMode,
    val type: OfferType
)