package io.github.coronado.api

import com.squareup.moshi.Json
import com.squareup.moshi.adapter
import io.github.coronado.tripleapi.components.schemas.EntityId
import io.github.coronado.tripleapi.components.schemas.ExternalId
import io.github.coronado.tripleapi.components.schemas.Offer
import io.github.coronado.tripleapi.components.schemas.OfferRewardType
import io.github.coronado.tripleapi.components.schemas.OfferType
import java.math.BigDecimal
import java.net.URI
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.time.LocalDate

class OfferMgr(val client: Client) {

    private val servicePath = "partner/offers"

    @OptIn(ExperimentalStdlibApi::class)
    val offerCreationRequestJsonAdapter = client.moshi.adapter<OfferCreationRequest>()

    @OptIn(ExperimentalStdlibApi::class)
    val offerJsonAdapter = client.moshi.adapter<Offer>()

    fun create(offerCreationRequest: OfferCreationRequest): Offer {
        val requestUrl = "${client.apiUrl}$servicePath"
        val json = offerCreationRequestJsonAdapter.toJson(offerCreationRequest)
        val request = HttpRequest.newBuilder().POST(HttpRequest.BodyPublishers.ofString(json))
            .header("Authorization", "${client.tokenType()} ${client.token()}")
            .header("content-type", "application/json")
            .uri(URI(requestUrl)).build()
        val response = client.httpClient.send(request, HttpResponse.BodyHandlers.ofString())
        when (val responseStatusCode = response.statusCode()) {
            201 -> return offerJsonAdapter.fromJson(response.body())!!
            409 -> throw RuntimeException("Duplicate entity")
            else -> throw RuntimeException("Unhandled status code $responseStatusCode ${response.body()}")
        }
    }
}

class OfferCreationRequest(
    @Json(name = "external_id") val externalId: ExternalId,
    val type: OfferType,
    @Json(name = "currency_code") val currencyCode: String,
    val headline: String,
    @Json(name = "reward_type") val rewardType: OfferRewardType,
    @Json(name = "effective_date") val effectiveDate: LocalDate,
    @Json(name = "expiration_date") val expirationDate: LocalDate,
    @Json(name = "minimum_spend") val minimumSpend: BigDecimal,
    @Json(name = "activation_request") val activationRequest: Boolean,
    @Json(name = "category_mccs") val categoryMerchantCategoryCodes: Set<MerchantCategoryCode>?,
    @Json(name = "merchant_id") val merchantId: EntityId?,
    @Json(name = "category") val category: OfferCategory?,
    @Json(name = "reward_rate") val rewardRate: BigDecimal?,
    @Json(name = "reward_value") val rewardValue: BigDecimal?,
    @Json(name = "maximum_reward_per_transaction") val maximumRewardPerTransaction: BigDecimal?,
    @Json(name = "maximum_reward_cumulative") val maximumRewardCumulative: BigDecimal?,
    @Json(name = "terms_and_conditions") val termsAndConditions: String?,
    @Json(name = "merchant_website") val merchantWebsite: String?,
    @Json(name = "marketing_fee") val marketingFee: BigDecimal?,
    @Json(name = "marketing_fee_type") val marketingFeeType: MarketingFeeType?,
    @Json(name = "marketing_fee_currency_code") val marketingFeeCurrencyCode: String?,
    @Json(name = "max_redemptions") val maxRedemptions: String?,
    @Json(name = "activation_duration_in_days") val activationDurationInDays: Int?,
    @Json(name = "valid_day_parts") val validDayParts: String?,
    @Json(name = "excluded_dates") val excludedDates: List<LocalDate>?
)

data class MerchantCategoryCode(
    val code: String, // The 4-digit Merchant Category Code, ex: "7998"
    val description: String // The description of the Merchant Category Code, ex: "Aquariums, Dolphinariums, Seaquariums, and Zoos"
)

enum class OfferCategory {
    AUTOMOTIVE, CHILDREN_AND_FAMILY, ELECTRONICS, ENTERTAINMENT, FINANCIAL_SERVICES, FOOD, HEALTH_AND_BEAUTY, HOME,
    OFFICE_AND_BUSINESS, RETAIL, TRAVEL, UTILITIES_AND_TELECOM
}

enum class MarketingFeeType { FIXED, PERCENTAGE }
