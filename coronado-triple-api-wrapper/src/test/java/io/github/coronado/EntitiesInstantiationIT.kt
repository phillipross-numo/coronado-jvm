package io.github.coronado

import io.github.coronado.entity.CardAccount
import io.github.coronado.entity.CardProgram
import io.github.coronado.entity.ContentProvider
import io.github.coronado.entity.Merchant
import io.github.coronado.entity.MerchantLocation
import io.github.coronado.entity.Offer
import io.github.coronado.entity.OfferDisplayRule
import io.github.coronado.entity.Publisher
import io.github.coronado.entity.Transaction
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

        val cardAccount1 = CardAccount(UUID.randomUUID(), cardProgram1)
        val cardAccount2 = CardAccount(UUID.randomUUID(), cardProgram2)
        val cardAccount3 = CardAccount(UUID.randomUUID(), cardProgram2)
        val cardAccount4 = CardAccount(UUID.randomUUID())

        val publisher1 = Publisher(UUID.randomUUID())
        val transaction1 = Transaction(UUID.randomUUID(), publisher1, cardAccount1)

        val contentProvider1 = ContentProvider(UUID.randomUUID())
        val merchant1 = Merchant(UUID.randomUUID())
        val merchantLocation1 = MerchantLocation(UUID.randomUUID())
        val offer1 = Offer(UUID.randomUUID())
        val offerDisplayRule1 = OfferDisplayRule(UUID.randomUUID())

        assertNotEquals(cardAccount1.identity, cardProgram1.identity)
    }
}
