package io.github.coronado.tripleapi.components.schemas

import com.squareup.moshi.Json
import io.github.coronado.api.Address
import io.github.coronado.api.CardAccountStatus
import io.github.coronado.api.Latitude
import io.github.coronado.api.Longitude
import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZonedDateTime
import java.util.Currency

/**
 * A Kotlin implementation for the schema components established by Triple API Swagger v1.1 (as of 2022-06-27)
 */

data class AffiliateLinkRequestBody(
    val cardAccountId: String, // Partner-defined, external Card Account ID. If the Card Account does not exist for the specified Program, it will be created with an "enrolled" date of the current day (UTC-12)
    val offerId: String, // Triple Offer ID, provided by the API or offers file.
    val programId: String, // The partner-defined, external Card Program ID.
    val publisherId: String // The partner-defined, external Publisher ID. **Required for Portfolio Managers**
)
data class AffiliateSuccess(
    val url: String // A URL for a link for the consumer, ex: "https://example.com/affiliate-link-consumer?id=long-unique-id"
)
data class AffiliateFailure(
    val message: String // A description of the failure, ex: "merchant_id AcmeWidgets does not exist."
)

data class CardAccount(
    val id: EntityId,
    val cardProgramId: EntityId,
    val cardAccountExternalId: ExternalId,
    val status: CardAccountStatus,
    val createdAt: CreatedAt,
    val updatedAt: UpdatedAt
)

// Must provide either a `card_account_id` or an `external_card_account_id`, optionally with a `publisher_external_id`
// and `card_program_id` specified if the `external_card_account_id` does not uniquely resolve.
open class CardAccountIdentifier()
data class InternalCardAccountIdentifier(val cardAccountId: EntityId) : CardAccountIdentifier()
data class ExternalCardAccountIdentifier(
    val cardAccountExternalId: ExternalId,
    val cardProgramExternalId: ExternalId?,
    val publisherExternalId: ExternalId?
) : CardAccountIdentifier()

data class CardProgram(
    val id: EntityId,
    val publisherId: EntityId,
    val externalIid: ExternalId,
    val name: String, // Display name of the Card Program
    val programCurrency: Currency,
    val cardBINs: Set<CardBIN>?, // The Bank Identification Numbers for cards in this Card Program. Providing these values helps Triple validate Transactions during reward processing and enforce card requirements during purchases through Affiliate Offers
    val createdAt: CreatedAt,
    val updatedAt: UpdatedAt
)

// An amount in dollars with decimal cents
typealias Amount = BigDecimal

// A card identifier
typealias CardId = String

// A merchant identifier
typealias MerchantId = String

// A transaction identifier
typealias TransactionId = String

// The Bank Identification Number (BIN) of a payment card.
typealias CardBIN = String

// RFC 3339 date time when this entity was created
typealias CreatedAt = LocalDateTime

// 3-character ISO-4217 currency code. Note that some values are not supported, particularly test and fund codes.
typealias CurrencyCode = Currency

// The Triple-defined ID for the entity
typealias EntityId = String

// "Partner-provided, external ID. External IDs should be **stable** and **never sensitive**.
// External IDs do not need to be globally unique, but we encourage the use of UUIDs. They must be unique for this
// entity type within the scope of its parent entity, if it has one.
// To protect against accidental inclusion of sensitive personal information, external IDs may not be 9-digit numbers
// or use the US Tax ID format (###-##-####).
typealias ExternalId = String

// Cardholder location information used for OfferDisplay
abstract class GeoTarget() { abstract val radius: Radius? }

data class MerchantCategoryCode(
    val code: String, // The 4-digit Merchant Category Code, ex: "7998"
    val description: String // The description of the Merchant Category Code, ex: "Aquariums, Dolphinariums, Seaquariums, and Zoos"
)

// The complete address, latitude and longitude of a Merchant Location
data class OfferLocationGeo(val completeAddress: String, val latitude: Latitude, val longitude: Longitude)

// The type of Merchant ID
enum class ProcessorMIDType {
    AMEX_SE_NUMBER, DISCOVER_MID, MC_AUTH_LOC_ID, MC_AUTH_ACQ_ID, MC_AUTH_ICA, MC_CLEARING_LOC_ID, MC_CLEARING_ACQ_ID,
    MC_CLEARING_ICA, MERCHANT_PROCESSOR, NCR, VISA_VMID, VISA_VSID
}

// The Merchant ID (MID) value
typealias ProcessorMIDValue = String

data class PostalCodeCountryCodeGeoTarget(
    val postalCode: String,
    val countryCode: String,
    override val radius: Radius?
) : GeoTarget()

data class LatitudeLongitudeGeoTarget(
    val latitude: Latitude,
    val longitude: Longitude,
    override val radius: Radius?
) : GeoTarget()

// A Merchant (or brand) is a business or retailer with one or more Merchant Locations (online or physical locations).
// Consumers who make purchases at Merchants with valid Offers may receive Rewards.
data class Merchant(
    val id: EntityId,
    val eternalId: ExternalId?,
    val assumedName: String, // The (doing-business-as) name of the Merchant
    val address: Address,
    val merchantCategoryCode: MerchantCategoryCode,
    val logoUrl: String?
)

data class MerchantLocation(
    val id: EntityId,
    val externalId: ExternalId,
    val merchantId: EntityId,
    val locationName: String?, // The name of the Merchant Location. If not specified, the Merchant name is used
    val online: Boolean, // Indicates whether or not this is an online location.
    val emailAddress: String?, // An email address for the location.
    val phoneNumber: String?, // A phone number for the location.
    val address: Address,
    val processorMerchantIds: List<ProcessorMID>
)

typealias DayPartTimes = List<String>
data class DayParts(
    val sunday: DayPartTimes?,
    val monday: DayPartTimes?,
    val tuesday: DayPartTimes?,
    val wednesday: DayPartTimes?,
    val thursday: DayPartTimes?,
    val friday: DayPartTimes?,
    val saturday: DayPartTimes?
)

abstract class OfferSearchFilter
data class OfferSearchCategoryFilter(val offerCategory: OfferCategory) : OfferSearchFilter()
data class OfferSearchOnlinePhysicalFilter(val mode: OfferMode) : OfferSearchFilter()
data class OfferSearchOfferTypeFilter(val type: OfferType) : OfferSearchFilter()

typealias FilterCategory = OfferCategory
typealias FilterOnlinePhysical = Boolean
typealias FilterType = OfferType
data class Offer(
    val id: EntityId,
    val externalIid: ExternalId,
    val mode: OfferMode, // The offer delivery mode
    val logoUrl: String?, // A link to the merchant's logo
    val category: OfferCategory?, // The offer category
    val categoryTags: String?, // A single-space-delimited collection of Offer tags
    val currencyCode: CurrencyCode, // The currency for the offer reward, minimum spend requirement, and maximum reward limitations. The value is an ISO-4217 currency code.
    val categoryMerchantCategoryCodes: Set<MerchantCategoryCode>?, // The Merchant Category Codes included for a categorical offer.
    val description: String?, // A long-form text description of or promotional content for an offer
    val effectiveDate: LocalDate, // The first date on which the offer is valid.
    val expirationDate: LocalDate, // The last date on which the offer is valid.
    val activated: Boolean, // True if an offer has been activated.
    val merchantId: EntityId,
    val headline: String, // The headline or title for the offer, appropriate for top-level display.
    val rewardRate: BigDecimal?, // The reward percentage if this offer has a reward_type of percentage, the amount will be between 0.0 and 100.0
    val rewardType: OfferRewardType, // Indicates whether this offer is for a fixed or percentage-based amount.
    val rewardValue: BigDecimal?, // The fixed reward value if this offer has a reward_type of fixed, the amount must be greater than or equal to 0
    val type: OfferType, // The type of offer - card-linked, affiliate, categorical, etc.
    val minimumSpend: BigDecimal, // The minimum monetary value that a cardholder must spend for a transaction to qualify for this offer.
    val maximumRewardPerTransaction: BigDecimal?, // The maximum monetary value a cardholder may earn for a percentage-based reward on any single transaction.
    val maximumRewardCumulative: BigDecimal?, // The maximum monetary value a cardholder may earn for this offer across multiple transactions.
    val merchantWebsite: String?,
    val marketingFee: BigDecimal?,
    val marketingFeeType: OfferMarketingFeeType?,
    val marketingFeeCurrencyCode: CurrencyCode?,
    val maxRedemptions: String?, // The maximum number of times a cardholder may receive a reward for this offer over a given period of time. The numerator of this value is the number of times a reward can be earned. The denominator is the period of time over which the limit is enforced. Time periods must conform to the following restrictions:- a period of years (Y) must be 1-3 - a period of months (M) must be 1-36 - a period of weeks (W) must be 1-156 - a period of days (D) must be 1-365
    val activationRequired: Boolean, // Indicates whether a cardholder must activate this offer prior to transacting and qualifying for the offer reward; If true, transactions that occur prior to the date of the cardholder activating the offer will not qualify for the offer's reward.
    val activateDurationInDays: Int?, // If specified, the number of days (inclusive) for which an offer activation is valid. If a transaction falls outside of this duration, it will not qualify for the offer. The day of activation is considered day 1. So, if a cardholder activates an offer on Monday with a 3 day duration, they must make their transaction on or before Wednesday.
    val termsAndConditions: String?, // Terms and conditions for the offer, not inclusive of other limits specified by the offer properties, such as minimum spend, excluded dates, or activation requirements.
    val validDayParts: DayParts?, // If specified, the days of the week and times during which the offer is valid. If unspecified, the offer is valid for all days and times during the week.
    val excludedDates: List<String>? // Dates for which the offer is not valid. Transactions made on these dates will not qualify for a reward.
)
enum class OfferMode { ONLINE, IN_PERSON, IN_PERSON_AND_ONLINE }
enum class OfferCategory {
    AUTOMOTIVE, CHILDREN_AND_FAMILY, ELECTRONICS, ENTERTAINMENT, FINANCIAL_SERVICES, FOOD, HEALTH_AND_BEAUTY, HOME,
    OFFICE_AND_BUSINESS, RETAIL, TRAVEL, UTILITIES_AND_TELECOM
}
enum class OfferMarketingFeeType { FIXED, PERCENTAGE }
enum class OfferType { CARD_LINKED, AFFILIATE, CATEGORICAL }
enum class OfferRewardType { FIXED, PERCENTAGE }

data class OfferActivation(
    val id: EntityId,
    val cardAccountId: EntityId,
    val activatedAt: LocalDate,
    val activationExpiresOn: LocalDate?, // The last date on which this Offer Activation is valid. If absent,the activation is valid until the Offer expires.
    val offer: OfferActivationOffer,
    val merchant: OfferActivationMerchant?
)
data class OfferActivationOffer(
    val id: EntityId,
    val offerType: OfferType,
    val headline: String,
    val rewardRate: BigDecimal?,
    val rewardType: OfferRewardType,
    val rewardValue: BigDecimal?,
    val currencyCode: CurrencyCode?
)
data class OfferActivationMerchant(
    val name: String,
    val logoUrl: String?
)

data class OfferDetail(
    val id: EntityId,
    val activationRequired: Boolean,
    val activateDurationInDays: Int?,
    val currencyCode: CurrencyCode,
    val category: OfferCategory?,
    val categoryTags: String?,
    val categoryMerchantCategoryCodes: Set<MerchantCategoryCode>?,
    val description: String?,
    val effectiveDate: LocalDate,
    val excludedDates: List<String>?,
    val expirationDate: LocalDate?,
    val activated: Boolean,
    val headline: String,
    val maxRedemptions: String?,
    val maximumRewardPerTransaction: BigDecimal?,
    val maximumRewardCumulative: BigDecimal?,
    val merchantCategoryCode: MerchantCategoryCode?,
    val merchantName: String?, // The name of the merchant associated with this offer.
    val merchantLogoUrl: String?,
    val minimumSpend: BigDecimal,
    val mode: OfferMode,
    val rewardRate: BigDecimal?,
    val rewardValue: BigDecimal?,
    val rewardType: OfferRewardType,
    val type: OfferType,
    val validDayParts: DayParts?
)

data class OfferDisplayRule(
    val id: EntityId,
    val description: String?,
    val enabled: Boolean?,
    val scope: OfferDisplayRuleScope,
    val type: OfferDisplayRuleType,
    val value: String, // one of: (assumed name, merchant category code, or merchant category code range)
    val action: OfferDisplayRuleAction?
)
data class OfferDisplayRuleScope(
    val level: OfferDisplayRuleScopeLevel,
    val id: EntityId,
    val name: String
)
enum class OfferDisplayRuleScopeLevel { PORTFOLIO_MANAGER, PORTFOLIO, PUBLISHER, CARD_PROGRAM }
enum class OfferDisplayRuleType {
    MERCHANT_NAME_EQUAL_TO,
    MERCHANT_NAME_LIKE,
    MERCHANT_NAME_NOT_EQUAL_TO,
    MERCHANT_NAME_NOT_LIKE,
    MCC_EQUAL_TO,
    MCC_IN_RANGE,
    MCC_NOT_EQUAL_TO
}
enum class OfferDisplayRuleAction { EXCLUDE, INCLUDE }

// The Processor Merchant ID (MID) is a unique number used to identify the business for credit and debit card payment
// processing. Physical locations and online retailers may have multiple MIDs, even for the same processor network,
// usually for different departments or checkouts.
data class ProcessorMID(val mid: ProcessorMIDValue, val type: ProcessorMIDType?)

data class Publisher(
    val id: EntityId,
    @Json(name = "portfolio_manager_id") val portfolioManagerId: EntityId,
    @Json(name = "external_id") val externalId: ExternalId,
    @Json(name = "assumed_name") val assumedName: String, // Assumed legal name of the Publisher
    val address: Address,
    @Json(name = "revenue_share") val revenueShare: BigDecimal, // The percent-based revenue share of this Publisher. Only Portfolio Managers may set this value for their Publishers. If set, this will override the value set at the Portfolio Manager level, ex: 1.125
    @Json(name = "created_at") val createdAt: CreatedAt,
    @Json(name = "updated_at") val updatedAt: UpdatedAt
)

// The distance in meters from the provided location. Default value of `35000m` is adjusted according to population
// density of a location (ie. Rural locations will target a broader search radius than urban locations).
typealias Radius = Int

data class RewardWebhook(
    val message: String, // The message for the cardholder
    val cardId: String, // The `card_id` originally registered with triple
    val merchantId: String, // The identifier of the merchant
    val merchantName: String, // The full name of the merchant offering the reward
    val transactionId: String, // The matching transaction which caused the reward to be triggered
    val transactionAmount: String, // The amount of initial transaction
    val rewardAmount: String // The amount of the reward earned
)

data class Transaction(
    val id: EntityId,
    val cardAccountId: EntityId,
    val externalId: String, // "Partner-provided, external ID. External IDs should be\n<strong>stable</strong> and <strong>never sensitive</strong>.\n\nExternal IDs do not need to be globally unique, but we encourage\nthe use of UUIDs. They must be unique for Transactions within\nthe scope of the associated Card Account.\n",
    val localDate: LocalDate, // The local date on which the transaction occurred, ex: "2021-12-01"
    val localTime: LocalTime?, // The local time (HH:mm[:ss[.SSSSSS]]) at which the transaction occurred, ex: "13:45:00"
    val debit: Boolean, // Whether this is a debit or credit
    val amount: BigDecimal, // The amount of the transaction. An error will be returned if the field contains fractional amounts smaller than those allowed by the transaction's currency code, ex: 12
    val currencyCode: CurrencyCode,
    val transactionType: TransactionType,
    val description: String, // The transaction description, usually the merchant name, ex: "Pittsburgh Zoo"
    val merchantCategoryCode: MerchantCategoryCode?,
    val merchantAddress: Address?,
    val processorMID: ProcessorMIDValue?,
    val processorMIDType: ProcessorMIDType?,
    val matchingStatus: TransactionMatchingStatus,
    val rewardDetails: TransactionRewardDetails?,
    val createdAt: CreatedAt,
    val updatedAt: UpdatedAt
)

// Details for a transaction
data class TransactionDetails(val cardId: CardId, val merchantId: MerchantId, val amount: Amount, val dateTime: ZonedDateTime)

// The status of Triple matching the Transaction to Offers.
enum class TransactionMatchingStatus {
    HISTORIC_TRANSACTION, // The Transaction was more than 3 days old when it was submitted to Triple. It is not eligible for a Reward.
    QUEUED, // The Transaction is waiting for Offer matching.
    NOT_APPLICABLE, // The Transaction is not a Purchase or Return, or is otherwise not eligible for a Reward.
    NOT_ENROLLED, // The Card Account was not enrolled for Rewards at the time of the Transaction.
    NO_ACTIVE_OFFER, // The Transaction did not match any active Offer or meet Offer terms.
    MATCHED // The Transaction was matched to Offers and is potentially eligible for a reward. See `reward_details` for more information.
}

typealias TransactionRewardDetails = List<TransactionRewardDetailsItem>

data class TransactionRewardDetailsItem(
    val offerId: EntityId,
    val amount: BigDecimal?,
    val currencyCode: CurrencyCode?,
    val status: TransactionRewardDetailsItemStatus,
    val rejectionReason: TransactionRewardDetailsItemRejectionReason?,
    val notes: String?
)

// The Reward status
enum class TransactionRewardDetailsItemStatus {
    REJECTED, // The Transaction did not meet the offer terms.
    PENDING_MERCHANT_APPROVAL, // The Transaction is waiting for the Merchant or Content Provider to approve or deny the Reward.
    DENIED_BY_MERCHANT, // The Merchant or Content Provider denied the Reward. A reason will be included in `reward_details`
    PENDING_MERCHANT_FUNDING, // The Reward was approved and is awaiting funding by the Merchant.
    PENDING_TRANSFER_TO_PUBLISHER, // The Reward is funded and funds are awaiting distribution to the Publisher.
    DISTRIBUTED_TO_PUBLISHER, // Reward funds have been sent to the Publisher.
    DISTRIBUTED_TO_CARDHOLDER // The Publisher has reported that the Reward has been given to the cardholder.
}

// The reason this matched offer was not applied to the Transaction.
enum class TransactionRewardDetailsItemRejectionReason {
    PURCHASE_AMOUNT_TOO_LOW, ACTIVATION_REQUIRED, MAX_USES_MET, MAX_REWARD_MET, DAY_OF_WEEK_RESTRICTION,
    TIME_OF_WEEK_RESTRICTION, EXCLUDED_DATE, NON_PARTICIPATING_LOCATION, OTHER
}

// "The type of transaction. Triple will only calculate offers based on a history of Purchases and Refunds.",
enum class TransactionType { CHECK, DEPOSIT, FEE, PAYMENT, PURCHASE, REFUND, TRANSFER, WITHDRAWAL }

// "RFC 3339 date time when this entity was most recently updated"
typealias UpdatedAt = LocalDateTime

data class SuccessBody(
    val success: Boolean?, // Was this request successful?
    val message: String? // A description of a failure or of the successful action taken
)
data class FailureBody(
    val success: Boolean?, // "false" to indicate failure
    val message: String? // A description of a failure
)
data class Error(val details: List<String>)
data class CardExists(
    val success: Boolean?, // "false" to indicate failure, ex: false
    val message: String? // A description of the failure, ex: "Card already exists"
)
data class DuplicateTransaction(
    val success: Boolean?, // "false" to indicate failure, ex: false
    val message: String? // A description of the failure, ex: "Duplicate Transaction"
)

data class HealthCheck(
    val apiVersion: String, // The semantic version of the API
    val build: String // A commit hash of the build
)

val TripleApiSwaggerComponentsSchemasKeysJson = """
    [
      "_Address",
      "_AffiliateFailure",
      "_AffiliateLinkRequestBody",
      "_AffiliateSuccess",
      "_Amount",
      "_CardAccount",
      "_CardAccountIdentifier",
      "_CardBIN",
      "_CardExists",
      "_CardId",
      "_CardProgram",
      "_CreatedAt",
      "_CurrencyCode",
      "_DayPartTimes",
      "_DayParts",
      "_DuplicateTransaction",
      "_EntityId",
      "_Error",
      "_ExternalId",
      "_FailureBody",
      "_FilterCategory",
      "_FilterOnlinePhysical",
      "_FilterType",
      "_GeoTarget",
      "_Healthcheck",
      "_Latitude",
      "_Longitude",
      "_Merchant",
      "_MerchantCategoryCode",
      "_MerchantId",
      "_MerchantLocation",
      "_Offer",
      "_OfferActivation",
      "_OfferDetail",
      "_OfferDisplayRule",
      "_OfferLocationGeo",
      "_ProcessorMID",
      "_ProcessorMIDType",
      "_ProcessorMIDValue",
      "_Publisher",
      "_Radius",
      "_RewardWebhook",
      "_SuccessBody",
      "_Transaction",
      "_TransactionDetail",
      "_TransactionId",
      "_TransactionType",
      "_UpdatedAt"
    ]    
""".trimIndent()
