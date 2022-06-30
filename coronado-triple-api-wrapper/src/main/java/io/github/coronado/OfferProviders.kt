package io.github.coronado

import java.math.BigDecimal

interface OfferProviders {
    fun createMerchant(merchantExternalId: String, merchantAssumedName: String, address: Address, merchantCategoryCode: MerchantCategoryCode?, logoUrl: String?) // CreateMerchant createMerchant POST /partner/merchants
    fun updateMerchant(merchantId: String, merchantAssumedName: String, address: Address, merchantCategoryCode: MerchantCategoryCode?, logoUrl: String?) // UpdateMerchant updateMerchant PATCH /partner/merchants/{id}
    fun getMerchantById(merchantlId: String) // GetMerchant getMerchantById GET /partner/merchants/{id}
    fun listMerchants(merchantExternalId: String) //  ListMerchants listMerchants GET /partner/merchants
    fun createMerchantLocation(merchantId: String, merchantExternalId: String, online: Boolean, address: Address, locationName: String?, emailAddress: String?, phoneNumber: String?, processorMerchantIds: List<String>?) // CreateMerchantLocation createMerchantLocation POST /partner/merchant-locations
    fun updateMerchantLocation(merchantLocationId: String, locationName: String?, address: Address, emailAddress: String?, phoneNumber: String?, processorMerchantIds: List<String>?) // UpdateMerchantLocation updateMerchantLocation PATCH /partner/merchant-locations/{id}
    fun getMerchantLocationById(merchantLocationId: String) // GetMerchantLocation getMerchantLocationById GET /partner/merchant-locations/{id}
    fun listMerchantLocations(merchantLocationExternalId: String) // ListMerchantLocations listMerchantLocations GET /partner/merchant-locations

    fun createOffer(
        offerExternalId: String,
        offerType: OfferType,
        currencyCode: String,
        headline: String,
        effectiveDate: String,
        expirationDate: String,
        minimumSpend: BigDecimal,
        activationRequire: Boolean,
        merchantCategoryCodes: Set<MerchantCategoryCode>?,
        merchantId: String?,
        offerCategory: OfferCategory?,
        rewardRate: BigDecimal?,
        rewardValue: BigDecimal?,
        maximumRewardValue: BigDecimal?,
        maximumRewardCumulative: BigDecimal?,
        termsAndConditions: String?,
        merchantWebsiteUrl: String?,
        marketingFee: BigDecimal?,
        marketingFeeType: MarketingFeeType?,
        marketingFeeCurrencyCode: String?,
        maxRedemptions: String?,
        activationDurationInDays: Int?,
        validDayParts: String?,
        excludedDates: String?
    ) // CreateOffer createOffer POST   /partner/offers

    fun updateOffer(
        offerId: String,
        merchantCategoryCodes: Set<MerchantCategoryCode>?,
        headline: String?,
        offerCategory: OfferCategory?,
        expirationDate: String?,
        termsAndConditions: String?,
        merchantWebsiteUrl: String?,
        excludedDates: String?
    ) // UpdateOffer updateOffer PATCH  /partner/offers/{id}
    fun getOfferById(offerId: String) // GetOffer getOfferById GET    /partner/offers/{id}
    fun listOffers(offerExternalId: String?, merchantLocationExternalId: String?) // ListOffers listOffers  GET    /partner/offers
}

enum class MarketingFeeType { FIXED, PERCENTAGE }

enum class OfferCategory {
    AUTOMOTIVE,
    CHILDREN_AND_FAMILY,
    ELECTRONICS,
    ENTERTAINMENT,
    FINANCIAL_SERVICES,
    FOOD,
    HEALTH_AND_BEAUTY,
    HOME,
    OFFICE_AND_BUSINESS,
    RETAIL,
    TRAVEL,
    UTILITIES_AND_TELECOM
}

enum class OfferType { CARD_LINKED, AFFILIATE, CATEGORICAL }
enum class RewardType { FIXED, PERCENTAGE }
