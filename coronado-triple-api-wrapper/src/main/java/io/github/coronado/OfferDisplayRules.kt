package io.github.coronado

interface OfferDisplayRules {
    fun createOfferExclusions() // CreateOfferDisplayRules createOfferExclusions POST   /partner/offer-display-rules
    fun updateOfferDisplayRule() // UpdateOfferDisplayRule updateOfferDisplayRule PATCH  /partner/offer-display-rules/{id}
    fun getOfferDisplayRuleById() // GetOfferDisplayRule getOfferDisplayRuleById GET    /partner/offer-display-rules/{id}
    fun listOfferDisplayRules() // ListOfferDisplayRules listOfferDisplayRules GET    /partner/offer-display-rules
    fun deleteOfferDisplayRuleById() // DeleteOfferDisplayRule deleteOfferDisplayRuleById DEL    /partner/offer-display-rules/{id}
}
