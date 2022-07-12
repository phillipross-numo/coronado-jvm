package io.github.coronado.tripleapi.components

import com.squareup.moshi.Json
import io.github.coronado.api.Address
import io.github.coronado.api.CardAccountStatus
import io.github.coronado.tripleapi.components.schemas.CardAccountIdentifier
import io.github.coronado.tripleapi.components.schemas.CardBIN
import io.github.coronado.tripleapi.components.schemas.CurrencyCode
import io.github.coronado.tripleapi.components.schemas.EntityId
import io.github.coronado.tripleapi.components.schemas.ExternalId
import io.github.coronado.tripleapi.components.schemas.GeoTarget
import io.github.coronado.tripleapi.components.schemas.MerchantCategoryCode
import io.github.coronado.tripleapi.components.schemas.OfferDisplayRuleAction
import io.github.coronado.tripleapi.components.schemas.OfferDisplayRuleScopeLevel
import io.github.coronado.tripleapi.components.schemas.OfferDisplayRuleType
import io.github.coronado.tripleapi.components.schemas.OfferSearchFilter
import io.github.coronado.tripleapi.components.schemas.ProcessorMID
import io.github.coronado.tripleapi.components.schemas.ProcessorMIDType
import io.github.coronado.tripleapi.components.schemas.TransactionType
import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalTime

/**
 * A Kotlin implementation for the request body components established by Triple API Swagger v1.1 (as of 2022-06-27)
 */

class CardAccountPatchRequestBody
data class CardAccountPostRequestBody(
    @Json(name = "card_program_external_id") val cardProgramExternalId: ExternalId,
    @Json(name = "external_id") val cardAccountExternalId: ExternalId,
    @Json(name = "publisher_external_id") val publisherExternalId: ExternalId?,
    val status: CardAccountStatus?
)
class CardProgramPatchRequestBody

data class CardProgramPostRequestBody(
    @Json(name = "external_id") val externalId: ExternalId,
    @Json(name = "name") val name: String,
    @Json(name = "program_currency") val programCurrency: String,
    @Json(name = "publisher_external_id") val publisherExternalId: ExternalId?,
    @Json(name = "card_bins") val cardBins: List<String>?
)

class MerchantPatchRequestBody
class MerchantPostRequestBody(
    @Json(name = "external_id") val externalId: ExternalId,
    @Json(name = "assumed_name") val assumedName: String,
    val address: Address,
    @Json(name = "merchant_category_code") val merchantCategoryCode: String,
    val logo: String
)
class MerchantLocationPatchRequestBody
class MerchantLocationPostRequestBody
class OfferPatchRequestBody
class OfferPostRequestBody
class OfferActivationPostRequestBody
class OfferDisplayRulePatchRequestBody

data class OfferDisplayRulePostRequestBody(
    val description: String?,
    val enabled: Boolean? = true,
    val scope: OfferDisplayRulePostRequestBodyScope,
    val type: OfferDisplayRuleType,
    val value: BigDecimal,
    val action: OfferDisplayRuleAction
)
data class OfferDisplayRulePostRequestBodyScope(
    val level: OfferDisplayRuleScopeLevel,
    val id: EntityId
)

data class OfferDetailsGetOrPostRequestBody(
    val proximityTarget: GeoTarget,
    val cardAccountIdentifier: CardAccountIdentifier,
    val merchantLocationId: EntityId?
)

data class OfferSearchGetOrPostRequestBody(
    val proximityTarget: GeoTarget,
    val cardAccountIdentifier: CardAccountIdentifier,
    val textQuery: String?,
    val pageSize: Int?,
    val pageOffset: Int?,
    val applyFilter: OfferSearchFilter?
)

abstract class PublisherPatchRequestBody()
data class PublisherPatchAssumedNameRequestBody(@Json(name = "assumed_name") val assumedName: String) : PublisherPatchRequestBody()
data class PublisherPatchAddressRequestBody(val address: Address) : PublisherPatchRequestBody()

data class PublisherPostRequestBody(
    @Json(name = "external_id") val externalId: ExternalId,
    @Json(name = "assumed_name") val assumedName: String,
    val address: Address,
    @Json(name = "revenue_share") val revenueShare: BigDecimal?
)

data class TransactionPostRequestBody(
    val publisherExternalId: ExternalId?,
    val cardProgramExternalId: ExternalId,
    val cardAccountExternalId: ExternalId,
    val externalId: ExternalId,
    val cardBIN: CardBIN?,
    val cardLast4: String?,
    val localDate: LocalDate,
    val localTime: LocalTime?,
    val debit: Boolean,
    val amount: BigDecimal,
    val currencyCode: CurrencyCode?,
    val transactionType: TransactionType,
    val description: String,
    val merchantCategoryCode: MerchantCategoryCode,
    val merchantAddress: Address,
    val processorMID: ProcessorMID,
    val processorMIDType: ProcessorMIDType
)
