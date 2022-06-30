package io.github.coronado

interface OfferDisplay {
//    fun searchOffersForDisplay() // SearchOffers searchOffersForDisplay GET /partner/offer-display/search-offers
//    fun searchOffersForDisplay() // SearchOffers searchOffersForDisplay POST /partner/offer-display/search-offers
    fun getOfferDetailsById() // GetOfferDetails getOfferDetailsById GET    /partner/offer-display/details/{id}
}
