package io.github.coronado.api

import com.squareup.moshi.Json
import com.squareup.moshi.adapter
import io.github.coronado.tripleapi.components.CardAccountPostRequestBody
import io.github.coronado.tripleapi.components.schemas.CreatedAt
import io.github.coronado.tripleapi.components.schemas.EntityId
import io.github.coronado.tripleapi.components.schemas.ExternalId
import io.github.coronado.tripleapi.components.schemas.UpdatedAt
import java.net.URI
import java.net.URLEncoder
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.nio.charset.StandardCharsets

class CardAccountMgr(val client: Client) {

    private val servicePath = "partner/card-accounts"

    @OptIn(ExperimentalStdlibApi::class)
    val createCardAccountPostRequestBodyJsonAdapter = client.moshi.adapter<CardAccountPostRequestBody>()

    @OptIn(ExperimentalStdlibApi::class)
    val listCardAccountResponseJsonAdapter = client.moshi.adapter<ListCardAccountResponse>()

    @OptIn(ExperimentalStdlibApi::class)
    val cardAccountJsonAdapter = client.moshi.adapter<CardAccount>()

    fun create(cardProgramExternalId: String, cardAccountExternalId: String, status: CardAccountStatus, publisherExternalId: String?): CardAccount {
        val requestUrl = "${client.apiUrl}$servicePath"
        val json = createCardAccountPostRequestBodyJsonAdapter.toJson(
            CardAccountPostRequestBody(cardProgramExternalId, cardAccountExternalId, publisherExternalId, status)
        )
        val request = HttpRequest.newBuilder().POST(HttpRequest.BodyPublishers.ofString(json))
            .header("Authorization", "${client.tokenType()} ${client.token()}")
            .header("content-type", "application/json")
            .uri(URI(requestUrl)).build()
        val response = client.httpClient.send(request, HttpResponse.BodyHandlers.ofString())
        when (val responseStatusCode = response.statusCode()) {
            201 -> return cardAccountJsonAdapter.fromJson(response.body())!!
            409 -> throw RuntimeException("Duplicate entity")
            else -> throw RuntimeException("Unhandled status code $responseStatusCode ${response.body()}")
        }
    }

    fun create(cardProgramExternalId: String, cardAccountExternalId: String, status: CardAccountStatus): CardAccount =
        create(cardProgramExternalId, cardAccountExternalId, status, null)

    fun list(publisherExternalId: String?, cardProgramExternalId: String?, cardAccountExternalId: String?): List<CardAccountReference> {
        var parameters = "?"
        if (publisherExternalId?.isNotBlank() == true) {
            parameters += "card_program_external_id=${URLEncoder.encode(publisherExternalId, StandardCharsets.UTF_8)}"
        }
        if (cardProgramExternalId?.isNotBlank() == true) {
            if (parameters.last() != '?') parameters += "&"
            parameters += "publisher_external_id=${URLEncoder.encode(publisherExternalId, StandardCharsets.UTF_8)}"
        }
        if (cardAccountExternalId?.isNotBlank() == true) {
            if (parameters.last() != '?') parameters += "&"
            parameters += "card_account_external_id=${URLEncoder.encode(cardAccountExternalId, StandardCharsets.UTF_8)}"
        }
        val requestUrl = "${client.apiUrl}$servicePath$parameters"
        val request = HttpRequest.newBuilder().GET()
            .header("Authorization", "${client.tokenType()} ${client.token()}")
            .header("content-type", "application/json")
            .uri(URI(requestUrl)).build()
        val response = client.httpClient.send(request, HttpResponse.BodyHandlers.ofString())
        when (val responseStatusCode = response.statusCode()) {
            200 -> return listCardAccountResponseJsonAdapter.fromJson(response.body())?.cardAccounts ?: emptyList()
            else -> throw RuntimeException("Unhandled status code $responseStatusCode ${response.body()}")
        }
    }

    fun list(): List<CardAccountReference> = list("", "", "")

    fun byId(id: String): CardAccount {
        val requestUrl = "${client.apiUrl}$servicePath/$id"
        val request = HttpRequest.newBuilder().GET()
            .header("Authorization", "${client.tokenType()} ${client.token()}")
            .header("content-type", "application/json")
            .uri(URI(requestUrl)).build()
        val response = client.httpClient.send(request, HttpResponse.BodyHandlers.ofString())
        when (val responseStatusCode = response.statusCode()) {
            200 -> return cardAccountJsonAdapter.fromJson(response.body())!!
            else -> throw RuntimeException("Unhandled status code $responseStatusCode")
        }
    }

    fun updateWith(cardAccount: CardAccount): CardAccount {
        val requestUrl = "${client.apiUrl}$servicePath/${cardAccount.id}"
        val json = cardAccountJsonAdapter.toJson(cardAccount)
        val request = HttpRequest.newBuilder().method("PATCH", HttpRequest.BodyPublishers.ofString(json))
            .header("Authorization", "${client.tokenType()} ${client.token()}")
            .header("content-type", "application/json")
            .uri(URI(requestUrl)).build()
        val response = client.httpClient.send(request, HttpResponse.BodyHandlers.ofString())
        when (val responseStatusCode = response.statusCode()) {
            200 -> return cardAccountJsonAdapter.fromJson(response.body())!!
            else -> throw RuntimeException("Unhandled status code $responseStatusCode")
        }
    }
}

data class ListCardAccountResponse(@Json(name = "card_accounts") val cardAccounts: List<CardAccountReference>)

data class CardAccountReference(
    val id: String,
    @Json(name = "external_id") val externalId: String,
    val status: CardAccountStatus
)

data class CardAccount(
    var id: EntityId,
    @Json(name = "card_program_id") var cardProgramId: EntityId,
    @Json(name = "card_program_external_id") var cardProgramExternalId: ExternalId?,
    @Json(name = "external_id") var cardAccountExternalId: ExternalId,
    @Json(name = "publisher_external_id") val publisherExternalId: ExternalId?,
    var status: CardAccountStatus,
    @Json(name = "created_at") var createdAt: CreatedAt,
    @Json(name = "updated_at") var updatedAt: UpdatedAt
)

enum class CardAccountStatus {
    ENROLLED, // The account is enrolled for rewards
    NOT_ENROLLED, // The account is not enrolled for rewards
    CLOSED // The account is closed and cannot receive rewards
}
