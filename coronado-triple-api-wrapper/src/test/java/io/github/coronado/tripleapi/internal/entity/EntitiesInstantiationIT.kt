package io.github.coronado.tripleapi.internal.entity

import java.util.UUID
import kotlin.test.Test
import kotlin.test.assertNotEquals

class EntitiesInstantiationIT {

    /*   OPERATIONS
    * "operationId": "heathcheck",
    * "operationId": "listCardAccounts",
    * "operationId": "createCardAccount",
    * "operationId": "getCardAccountById",
    * "operationId": "updateCardAccountById",
    * "operationId": "listCardPrograms",
    * "operationId": "createCardProgram",
    * "operationId": "getCardProgramById",
    * "operationId": "updateCardProgram",
    * "operationId": "listPublishers",
    * "operationId": "createPublisher",
    * "operationId": "getPublisher",
    * "operationId": "updatePublisher",
    * "operationId": "listTransactions",
    * "operationId": "createTransaction",
    * "operationId": "getTransactionById",
    * "operationId": "listMerchants",
    * "operationId": "createMerchant",
    * "operationId": "getMerchantById",
    * "operationId": "updateMerchant",
    * "operationId": "listMerchantLocations",
    * "operationId": "createMerchantLocation",
    * "operationId": "getMerchantLocationById",
    * "operationId": "updateMerchantLocation",
    * "operationId": "createOffer",
    * "operationId": "updateOffer",
    * "operationId": "getOfferById",
    * "operationId": "listOffers",
    * "operationId": "createOfferActivation",
    * "operationId": "getOfferActivations",
    * "operationId": "searchOffersForDisplay",  why are there two? GET /partner/offer-display/search-offers
    * "operationId": "searchOffersForDisplay",  why are there two? POST /partner/offer-display/search-offers
    * "operationId": "getOfferDetailsById",     why are there two? GET /partner/offer-display/details/{id}
    * "operationId": "getOfferDetailsById",     why are there two? POST /partner/offer-display/details/{id}
    * "operationId": "createOfferExclusions",
    * "operationId": "updateOfferDisplayRule",
    * "operationId": "getOfferDisplayRuleById",
    * "operationId": "listOfferDisplayRules",
    * "operationId": "deleteOfferDisplayRuleById",
    * "operationId": "listRewards",      GET /partner/rewards
    * "operationId": "approveReward",    POST /partner/rewards.approve
    * "operationId": "denyReward",       POST /partner/rewards.deny

            But what about:
   ListMatchedTransactionsPublishers        GET    /partner/reports/publishers/matched-transactions
   ListMatchedTransactionsContentProviders  GET    /partner/reports/content-providers/matched-transactions
*/

    @Test
    internal fun testEntityInstantiation() {
        val cardProgram1 = CardProgram(UUID.randomUUID(), "externalId-1", "name-1", "USD")
        val cardProgram2 = CardProgram(UUID.randomUUID(), "externalId-2", "name-2", "USD")
        assertNotEquals(cardProgram1, cardProgram2)

        val cardAccount1 = CardAccount(UUID.randomUUID(), cardProgram1)
        val cardAccount2 = CardAccount(UUID.randomUUID(), cardProgram2)
        val cardAccount3 = CardAccount(UUID.randomUUID(), cardProgram2)
        val cardAccount4 = CardAccount(UUID.randomUUID())
        assertNotEquals(cardAccount1, cardAccount2)
        assertNotEquals(cardAccount1, cardAccount3)
        assertNotEquals(cardAccount1, cardAccount4)

        val publisher1 = Publisher(UUID.randomUUID())
        val transaction1 = Transaction(UUID.randomUUID(), publisher1, cardAccount1)
        val transaction2 = Transaction(UUID.randomUUID(), publisher1, cardAccount2)
        assertNotEquals(transaction1, transaction2)

        val contentProvider1 = ContentProvider(UUID.randomUUID())
        val merchant1 = Merchant(UUID.randomUUID())
        val merchantLocation1 = MerchantLocation(UUID.randomUUID())
        val offer1 = Offer(UUID.randomUUID())
        val offerDisplayRule1 = OfferDisplayRule(UUID.randomUUID())

        assertNotEquals(contentProvider1.identity, merchant1.identity)
        assertNotEquals(merchantLocation1.identity, offer1.identity)
        assertNotEquals(contentProvider1.identity, offerDisplayRule1.identity)
    }
}
