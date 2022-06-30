package io.github.coronado.api

import com.squareup.moshi.Json
import com.squareup.moshi.adapter
import io.github.coronado.tripleapi.components.PublisherPostRequestBody
import io.github.coronado.tripleapi.components.schemas.CreatedAt
import io.github.coronado.tripleapi.components.schemas.EntityId
import io.github.coronado.tripleapi.components.schemas.ExternalId
import io.github.coronado.tripleapi.components.schemas.UpdatedAt
import java.math.BigDecimal
import java.net.URI
import java.net.http.HttpRequest
import java.net.http.HttpResponse

class PublisherMgr(val client: Client) {

    private val servicePath = "partner/publishers"

    @OptIn(ExperimentalStdlibApi::class)
    val createPublisherPostRequestBodyJsonAdapter = client.moshi.adapter<PublisherPostRequestBody>()

    @OptIn(ExperimentalStdlibApi::class)
    val listPublisherResponseJsonAdapter = client.moshi.adapter<ListPublisherResponse>()

    @OptIn(ExperimentalStdlibApi::class)
    val publisherJsonAdapter = client.moshi.adapter<Publisher>()

    fun create(externalId: String, assumedName: String, address: Address, revenueShare: BigDecimal?): Publisher {
        val requestUrl = "${client.apiUrl}$servicePath"
        val json = createPublisherPostRequestBodyJsonAdapter.toJson(
            PublisherPostRequestBody(externalId, assumedName, address, revenueShare)
        )
        val request = HttpRequest.newBuilder().POST(HttpRequest.BodyPublishers.ofString(json))
            .header("Authorization", "${client.tokenType()} ${client.token()}")
            .header("content-type", "application/json")
            .uri(URI(requestUrl)).build()
        val response = client.httpClient.send(request, HttpResponse.BodyHandlers.ofString())
        when (val responseStatusCode = response.statusCode()) {
            201 -> return publisherJsonAdapter.fromJson(response.body())!!
            401 -> throw RuntimeException("Duplicate entity")
            else -> throw RuntimeException("Unhandled status code $responseStatusCode")
        }
    }

    fun list(externalId: String): List<PublisherReference> {
        val parameters = if (externalId.isNotBlank()) "?publisher_external_id=$externalId" else ""
        val requestUrl = "${client.apiUrl}$servicePath$parameters"
        val request = HttpRequest.newBuilder().GET()
            .header("Authorization", "${client.tokenType()} ${client.token()}")
            .header("content-type", "application/json")
            .uri(URI(requestUrl)).build()
        val response = client.httpClient.send(request, HttpResponse.BodyHandlers.ofString())
        when (val responseStatusCode = response.statusCode()) {
            200 -> return listPublisherResponseJsonAdapter.fromJson(response.body())?.publishers ?: emptyList()
            else -> throw RuntimeException("Unhandled status code $responseStatusCode")
        }
    }

    fun list(): List<PublisherReference> = list("")

    fun byId(id: String): Publisher {
        val requestUrl = "${client.apiUrl}$servicePath/$id"
        val request = HttpRequest.newBuilder().GET()
            .header("Authorization", "${client.tokenType()} ${client.token()}")
            .header("content-type", "application/json")
            .uri(URI(requestUrl)).build()
        val response = client.httpClient.send(request, HttpResponse.BodyHandlers.ofString())
        when (val responseStatusCode = response.statusCode()) {
            200 -> return publisherJsonAdapter.fromJson(response.body())!!
            else -> throw RuntimeException("Unhandled status code $responseStatusCode")
        }
    }

    fun updateWith(publisher: Publisher): Publisher {
        val requestUrl = "${client.apiUrl}$servicePath/${publisher.id}"
        val json = publisherJsonAdapter.toJson(publisher)
        val request = HttpRequest.newBuilder().method("PATCH", HttpRequest.BodyPublishers.ofString(json))
            .header("Authorization", "${client.tokenType()} ${client.token()}")
            .header("content-type", "application/json")
            .uri(URI(requestUrl)).build()
        val response = client.httpClient.send(request, HttpResponse.BodyHandlers.ofString())
        when (val responseStatusCode = response.statusCode()) {
            200 -> return publisherJsonAdapter.fromJson(response.body())!!
            else -> throw RuntimeException("Unhandled status code $responseStatusCode")
        }
    }
}

data class ListPublisherResponse(val publishers: List<PublisherReference>)

data class PublisherReference(
    @Json(name = "id") val id: String,
    @Json(name = "external_id") val externalId: String,
    @Json(name = "assumed_name") val assumedName: String
)

data class Publisher(
    var id: EntityId,
    @Json(name = "portfolio_manager_id") var portfolioManagerId: EntityId,
    @Json(name = "external_id") var externalId: ExternalId,
    @Json(name = "assumed_name") var assumedName: String, // Assumed legal name of the Publisher
    var address: Address,
    @Json(name = "revenue_share") var revenueShare: BigDecimal, // The percent-based revenue share of this Publisher. Only Portfolio Managers may set this value for their Publishers. If set, this will override the value set at the Portfolio Manager level, ex: 1.125
    @Json(name = "created_at") var createdAt: CreatedAt,
    @Json(name = "updated_at") var updatedAt: UpdatedAt
)

data class Address(
    @Json(name = "complete_address") var completeAddress: String, // The complete address, as would be written out for mail delivery or route navigation. ex: "7370 BAKER ST STE 100\nPITTSBURGH, PA 15206"
    @Json(name = "line_1") var line1: String?, // ex: "7370 BAKER ST STE 100"
    @Json(name = "line_2") var line2: String?,
    var locality: String?, // City or locality name, ex: "PITTSBURGH"
    var province: String, // State abbreviation or province name, ex: "PA"
    @Json(name = "postal_code") var postalCode: String?, // ZIP Code™, ZIP+4, or postal code, ex: "15206"
    @Json(name = "country_code") var countryCode: String?, // 2-letter ISO-3166 country code, ex: "US"
    var latitude: Latitude,
    var longitude: Longitude
)

typealias Latitude = BigDecimal
typealias Longitude = BigDecimal
