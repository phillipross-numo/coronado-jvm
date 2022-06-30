package io.github.coronado.tripleapi.internal

interface OfferActivation {
    fun createOfferActivation(cardAccountId: String, offerId: String, localTimezoneOffset: String) // ActivateOffer createOfferActivation POST /partner/card-accounts/{id}/offer-activations
    fun getOfferActivations(offerId: String, includeExpired: Boolean = false) // GetActivatedOffers getOfferActivations GET /partner/card-accounts/{id}/offer-activations
}
