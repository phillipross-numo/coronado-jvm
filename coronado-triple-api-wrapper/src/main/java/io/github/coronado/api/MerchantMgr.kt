package io.github.coronado.api

import com.squareup.moshi.Json
import com.squareup.moshi.adapter
import io.github.coronado.tripleapi.components.MerchantPostRequestBody
import io.github.coronado.tripleapi.components.schemas.EntityId
import io.github.coronado.tripleapi.components.schemas.ExternalId
import java.net.URI
import java.net.http.HttpRequest
import java.net.http.HttpResponse

class MerchantMgr(val client: Client) {

    private val servicePath = "partner/merchants"

    @OptIn(ExperimentalStdlibApi::class)
    val createMerchantPostRequestBodyJsonAdapter = client.moshi.adapter<MerchantPostRequestBody>()

    @OptIn(ExperimentalStdlibApi::class)
    val listMerchantResponseJsonAdapter = client.moshi.adapter<ListMerchantResponse>()

    @OptIn(ExperimentalStdlibApi::class)
    val merchantJsonAdapter = client.moshi.adapter<Merchant>()

    fun list(merchantExternalId: ExternalId): List<MerchantReference> {
        val parameters = if (merchantExternalId.isNotBlank()) "?merchant_external_id=$merchantExternalId" else ""
        val requestUrl = "${client.apiUrl}$servicePath$parameters"
        val request = HttpRequest.newBuilder().GET()
            .header("Authorization", "${client.tokenType()} ${client.token()}")
            .header("content-type", "application/json")
            .uri(URI(requestUrl)).build()
        val response = client.httpClient.send(request, HttpResponse.BodyHandlers.ofString())
        when (val responseStatusCode = response.statusCode()) {
            200 -> return listMerchantResponseJsonAdapter.fromJson(response.body())?.merchants ?: emptyList()
            else -> throw RuntimeException("Unhandled status code $responseStatusCode ${response.body()}")
        }
    }

}

data class ListMerchantResponse(@Json(name = "merchants") val merchants: List<MerchantReference>)

data class MerchantReference(
    val id: String
)

data class Merchant(
    val id: EntityId,
    val externalId: ExternalId?,
    val assumedName: String, // The (doing-business-as) name of the Merchant
    val address: Address,
    val merchantCategoryCode: MerchantCategoryCode,
    val logoUrl: String?
)

data class MerchantCategoryCode(
    val code: String, // The 4-digit Merchant Category Code, ex: "7998"
    val description: String // The description of the Merchant Category Code, ex: "Aquariums, Dolphinariums, Seaquariums, and Zoos"
)