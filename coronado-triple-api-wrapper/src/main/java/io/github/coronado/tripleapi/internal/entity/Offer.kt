package io.github.coronado.tripleapi.internal.entity

import java.util.UUID

data class Offer(val identity: UUID)

enum class OfferType { CARD_LINKED, AFFILIATE, CATEGORICAL }

enum class MarketingFeeType { FIXED, PERCENTAGE }

enum class OfferCategory {
    AUTOMOTIVE, CHILDREN_AND_FAMILY, ELECTRONICS, ENTERTAINMENT, FINANCIAL_SERVICES, FOOD, HEALTH_AND_BEAUTY, HOME,
    OFFICE_AND_BUSINESS, RETAIL, TRAVEL, UTILITIES_AND_TELECOM
}
