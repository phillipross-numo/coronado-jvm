package io.github.coronado.api

import com.squareup.moshi.Json
import com.squareup.moshi.adapter
import io.github.coronado.tripleapi.components.CardProgramPostRequestBody
import io.github.coronado.tripleapi.components.schemas.CreatedAt
import io.github.coronado.tripleapi.components.schemas.UpdatedAt
import java.net.URI
import java.net.URLEncoder
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.nio.charset.StandardCharsets

class CardProgramMgr(val client: Client) {

    private val servicePath = "partner/card-programs"

    @OptIn(ExperimentalStdlibApi::class)
    val createCardProgramPostRequestBodyJsonAdapter = client.moshi.adapter<CardProgramPostRequestBody>()

    @OptIn(ExperimentalStdlibApi::class)
    val listCardProgramResponseJsonAdapter = client.moshi.adapter<ListCardProgramResponse>()

    @OptIn(ExperimentalStdlibApi::class)
    val cardProgramJsonAdapter = client.moshi.adapter<CardProgram>()

    fun create(cardProgramExternalId: String, name: String, programCurrency: String, publisherExternalId: String?, cardBINS: List<String>?): CardProgram {
        val requestUrl = "${client.apiUrl}$servicePath"
        val json = createCardProgramPostRequestBodyJsonAdapter.toJson(
            CardProgramPostRequestBody(cardProgramExternalId, name, programCurrency, publisherExternalId, cardBINS)
        )
        val request = HttpRequest.newBuilder().POST(HttpRequest.BodyPublishers.ofString(json))
            .header("Authorization", "${client.tokenType()} ${client.token()}")
            .header("content-type", "application/json")
            .uri(URI(requestUrl)).build()
        val response = client.httpClient.send(request, HttpResponse.BodyHandlers.ofString())
        when (val responseStatusCode = response.statusCode()) {
            201 -> return cardProgramJsonAdapter.fromJson(response.body())!!
            409 -> throw RuntimeException("Duplicate entity")
            else -> throw RuntimeException("Unhandled status code $responseStatusCode ${response.body()}")
        }
    }

    // create w/o publisher_external_id fails
    fun create(cardProgramExternalId: String, name: String, programCurrency: String): CardProgram =
        create(cardProgramExternalId, name, programCurrency, null, null)

    fun list(publisherExternalId: String?, cardProgramExternalId: String?): List<CardProgramReference> {
        var parameters = "?"
        if (publisherExternalId?.isNotBlank() == true) {
            parameters += "card_program_external_id=${URLEncoder.encode(publisherExternalId, StandardCharsets.UTF_8)}"
        }
        if (cardProgramExternalId?.isNotBlank() == true) {
            if (parameters.last() != '?') parameters += "&"
            parameters += "publisher_external_id=${URLEncoder.encode(publisherExternalId, StandardCharsets.UTF_8)}"
        }
        val requestUrl = "${client.apiUrl}$servicePath$parameters"
        val request = HttpRequest.newBuilder().GET()
            .header("Authorization", "${client.tokenType()} ${client.token()}")
            .header("content-type", "application/json")
            .uri(URI(requestUrl)).build()
        val response = client.httpClient.send(request, HttpResponse.BodyHandlers.ofString())
        when (val responseStatusCode = response.statusCode()) {
            200 -> return listCardProgramResponseJsonAdapter.fromJson(response.body())?.cardPrograms ?: emptyList()
            else -> throw RuntimeException("Unhandled status code $responseStatusCode ${response.body()}")
        }
    }

    fun list(): List<CardProgramReference> = list("", "")

    fun byId(id: String): CardProgram {
        val requestUrl = "${client.apiUrl}$servicePath/$id"
        val request = HttpRequest.newBuilder().GET()
            .header("Authorization", "${client.tokenType()} ${client.token()}")
            .header("content-type", "application/json")
            .uri(URI(requestUrl)).build()
        val response = client.httpClient.send(request, HttpResponse.BodyHandlers.ofString())
        when (val responseStatusCode = response.statusCode()) {
            200 -> return cardProgramJsonAdapter.fromJson(response.body())!!
            else -> throw RuntimeException("Unhandled status code $responseStatusCode")
        }
    }

    fun updateWith(cardProgram: CardProgram): CardProgram {
        val requestUrl = "${client.apiUrl}$servicePath/${cardProgram.id}"
        val json = cardProgramJsonAdapter.toJson(cardProgram)
        val request = HttpRequest.newBuilder().method("PATCH", HttpRequest.BodyPublishers.ofString(json))
            .header("Authorization", "${client.tokenType()} ${client.token()}")
            .header("content-type", "application/json")
            .uri(URI(requestUrl)).build()
        val response = client.httpClient.send(request, HttpResponse.BodyHandlers.ofString())
        when (val responseStatusCode = response.statusCode()) {
            200 -> return cardProgramJsonAdapter.fromJson(response.body())!!
            else -> throw RuntimeException("Unhandled status code $responseStatusCode")
        }
    }
}

data class ListCardProgramResponse(@Json(name = "card_programs") val cardPrograms: List<CardProgramReference>)

data class CardProgramReference(
    val id: String,
    @Json(name = "external_id") val externalId: String,
    val name: String
)

data class CardProgram(
    var id: String,
    @Json(name = "publisher_id") var publisherId: String,
    @Json(name = "external_id") var externalIid: String,
    var name: String, // Display name of the Card Program
    @Json(name = "program_currency") var programCurrency: String,
    @Json(name = "card_bins") var cardBINs: Set<CardBIN>?, // The Bank Identification Numbers for cards in this Card Program. Providing these values helps Triple validate Transactions during reward processing and enforce card requirements during purchases through Affiliate Offers
    @Json(name = "created_at") var createdAt: CreatedAt,
    @Json(name = "updated_at") var updatedAt: UpdatedAt
)

typealias CardBIN = String
