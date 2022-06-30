package io.github.coronado.baseobjects

import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZonedDateTime
import java.util.UUID
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalStdlibApi::class)
class BaseObjectsSerializationTest {

    lateinit var serializer: Moshi

    @BeforeTest
    fun initSerializer() {
        serializer = Moshi.Builder()
            .add(BigDecimalAdapter)
            .add(LocalDateAdapter)
            .add(LocalTimeAdapter)
            .add(ZonedDateTimeAdapter)
            .addLast(KotlinJsonAdapterFactory())
            .build()
    }

    @Test
    fun testTransactionSerialization() {
        val transaction = Transaction(
            UUID.randomUUID().toString(), UUID.randomUUID().toString(), UUID.randomUUID().toString(),
            LocalDate.now(), LocalTime.now(),
            true,
            BigDecimal("12.00"), "USD", "PURCHASE", "Pittsburgh Zoo",
            MerchantCategoryCode("7998", "Aquariums, Dolphinariums, Seaquariums, and Zoos"),
            Address(
                "7370 BAKER ST STE 100\\nPITTSBURGH, PA 15206",
                "7370 BAKER ST STE 100", null,
                "PITTSBURGH", "PA", "15206", "US",
                BigDecimal(40.440624), BigDecimal(-79.995888)
            ),
            "9000012345", "VISA_VMID", "HISTORIC_TRANSACTION",
            listOf(
                RewardDetail(
                    UUID.randomUUID().toString(),
                    BigDecimal("0.00"),
                    "USD",
                    "REJECTED",
                    "PURCHASE_AMOUNT_TOO_LOW",
                    "some notes"
                )
            ),
            ZonedDateTime.now(), ZonedDateTime.now()
        )
        val transactionJsonAdapter = serializer.adapter<Transaction>().indent("    ")
        val transactionJson = transactionJsonAdapter.toJson(transaction)
        println(transactionJson)
        val roundTripReward = transactionJsonAdapter.fromJson(transactionJson)
        assertEquals(transaction, roundTripReward)
        println(roundTripReward)
    }

    @Test
    fun testRewardSerialization() {
        val reward = Reward(
            UUID.randomUUID().toString(), UUID.randomUUID().toString(), UUID.randomUUID().toString(),
            ZonedDateTime.now(),
            "444789", "9876",
            BigDecimal("12.00"), "USD",
            BigDecimal("0.00"), "USD",
            "some offer headline", "some merchant",
            "7370 BAKER ST STE 100\\nPITTSBURGH, PA 15206",
            "REJECTED"
        )
        val rewardJsonAdapter = serializer.adapter<Reward>().indent("    ")
        val rewardJson = rewardJsonAdapter.toJson(reward)
        println(rewardJson)
        val roundTripReward = rewardJsonAdapter.fromJson(rewardJson)
        assertEquals(reward, roundTripReward)
        println(roundTripReward)
    }

    @Test
    fun testPublisherSerialization() {
        val publisher = Publisher(
            UUID.randomUUID().toString(),
            UUID.randomUUID().toString(),
            UUID.randomUUID().toString(),
            "some assumed merchant name",
            Address(
                "7370 BAKER ST STE 100\\nPITTSBURGH, PA 15206",
                "7370 BAKER ST STE 100", null,
                "PITTSBURGH", "PA", "15206", "US",
                BigDecimal(40.440624),
                BigDecimal(-79.995888)
            ),
            BigDecimal("1.125"),
            ZonedDateTime.now(),
            ZonedDateTime.now()
        )
        val publisherJsonAdapter = serializer.adapter<Publisher>().indent("    ")
        val publisherJson = publisherJsonAdapter.toJson(publisher)
        println(publisherJson)
        val roundTripPublisher = publisherJsonAdapter.fromJson(publisherJson)
        assertEquals(publisher, roundTripPublisher)
        println(roundTripPublisher)
    }

    @Test
    fun testDisplayRulesSerialization() {
        val displayRules = DisplayRules(
            UUID.randomUUID().toString(),
            "some description",
            true,
            DisplayRuleScope(UUID.randomUUID().toString(), "some scope level", "some scope name"),
            "MERCHANT_NAME_EQUAL_TO",
            "some value",
            "EXCLUDE"
        )
        val displayRulesJsonAdapter = serializer.adapter<DisplayRules>().indent("    ")
        val displayRulesJson = displayRulesJsonAdapter.toJson(displayRules)
        println(displayRulesJson)
        val roundTripDisplayRules = displayRulesJsonAdapter.fromJson(displayRulesJson)
        assertEquals(displayRules, roundTripDisplayRules)
        println(roundTripDisplayRules)
    }

    @Test
    fun testOfferSerialization() {
        val offer = Offer(
            UUID.randomUUID().toString(),
            true,
            0,
            "USD",
            "AUTOMOTIVE",
            "ACategoryTag",
            setOf("categoryMCC1", "categoryMCC2", "categoryMCC3"),
            "some offer for something",
            "2021-12-01",
            emptySet(),
            "2021-12-31",
            false,
            "Some Offer Headline",
            "1/3M",
            0,
            0,
            MerchantCategoryCode("7998", "Aquariums, Dolphinariums, Seaquariums, and Zoos"),
            "Some Merchant",
            "https://somelogo.com/logo.png?w=120&h=24",
            BigDecimal.ZERO,
            "online",
            0,
            BigDecimal.ZERO,
            "CARD_LINKED",
            "CARD_LINKED",
            "",
            "Ts&Cs",
            "https://somemerch.com/"
        )
        val offerJsonAdapter = serializer.adapter<Offer>().indent("  ")
        val offerJson = offerJsonAdapter.toJson(offer)
        println(offerJson)
        val roundTripOffer = offerJsonAdapter.fromJson(offerJson)
        assertEquals(offer, roundTripOffer)
        println(roundTripOffer)

        val offerActivation = OfferActivation(
            UUID.randomUUID().toString(),
            UUID.randomUUID().toString(),
            "2019-08-24",
            "2019-08-24",
            offer,
            "someMerchant, but with a name and logo"
        )
        val offerActivationJsonAdapter = serializer.adapter<OfferActivation>().indent("  ")
        val offerActivationJson = offerActivationJsonAdapter.toJson(offerActivation)
        println(offerActivationJson)
        val roundTripofferActivation = offerActivationJsonAdapter.fromJson(offerActivationJson)
        assertEquals(offerActivation, roundTripofferActivation)
        println(roundTripofferActivation)
    }

    @Test
    fun testMerchantLocationSerialization() {
        val merchantLocation = MerchantLocation(
            UUID.randomUUID().toString(),
            "Some Merchant Downtown",
            false,
            "some.merchant@downtown",
            "555-123-9876",
            Address(
                "7370 BAKER ST STE 100\\nPITTSBURGH, PA 15206",
                "7370 BAKER ST STE 100", null,
                "PITTSBURGH", "PA", "15206", "US",
                BigDecimal(40.440624), BigDecimal(-79.995888)
            )
        )
        val merchantLocationJsonAdapter = serializer.adapter<MerchantLocation>().indent("  ")
        val merchantLocationJson = merchantLocationJsonAdapter.toJson(merchantLocation)
        println(merchantLocationJson)
        val roundTripMerchantLocation = merchantLocationJsonAdapter.fromJson(merchantLocationJson)
        assertEquals(merchantLocation, roundTripMerchantLocation)
        println(roundTripMerchantLocation)
    }

    @Test
    fun testMerchantCategoryCodeSerialization() {
        val merchantCategoryCode = MerchantCategoryCode("7998", "Aquariums, Dolphinariums, Seaquariums, and Zoos")
        val merchantCategoryCodeJsonAdapter = serializer.adapter<MerchantCategoryCode>().indent("  ")
        val merchantCategoryCodeJson = merchantCategoryCodeJsonAdapter.toJson(merchantCategoryCode)
        println(merchantCategoryCodeJson)
        val roundTripMerchantCategoryCode = merchantCategoryCodeJsonAdapter.fromJson(merchantCategoryCodeJson)
        assertEquals(merchantCategoryCode, roundTripMerchantCategoryCode)
        println(roundTripMerchantCategoryCode)
    }

    @Test
    fun testCardProgramSerialization() {
        val cardProgram = CardProgram(
            UUID.randomUUID().toString(),
            UUID.randomUUID().toString(),
            UUID.randomUUID().toString(),
            "Great Card Program",
            "someCurrency",
            setOf("bin1", "bin2"),
            ZonedDateTime.now(),
            ZonedDateTime.now()
        )
        val cardProgramJsonAdapter = serializer.adapter<CardProgram>().indent("  ")
        val cardProgramJson = cardProgramJsonAdapter.toJson(cardProgram)
        println(cardProgramJson)
        val roundTripCardProgram = cardProgramJsonAdapter.fromJson(cardProgramJson)
        assertEquals(cardProgram, roundTripCardProgram)
        println(roundTripCardProgram)
    }

    @Test
    fun testCardAccountSerialization() {
        val cardAccount = CardAccount(
            UUID.randomUUID().toString(),
            UUID.randomUUID().toString(),
            UUID.randomUUID().toString(),
            CardAccountStatus.ENROLLED,
            ZonedDateTime.now(),
            ZonedDateTime.now()
        )
        val cardAccountJsonAdapter = serializer.adapter<CardAccount>().indent("  ")
        val cardAccountJson = cardAccountJsonAdapter.toJson(cardAccount)
        println(cardAccountJson)
        val roundTripCardAccount = cardAccountJsonAdapter.fromJson(cardAccountJson)
        assertEquals(cardAccount, roundTripCardAccount)
        println(roundTripCardAccount)

        val cardAccountIdentifier = CardAccountIdentifier(
            UUID.randomUUID().toString(),
            cardAccount.cardProgramId,
            cardAccount.externalId,
            cardAccount.status.toString()
        )
        val cardAccountIdentifierJsonAdapter = serializer.adapter<CardAccountIdentifier>().indent("  ")
        val cardAccountIdentifierJson = cardAccountIdentifierJsonAdapter.toJson(cardAccountIdentifier)
        println(cardAccountIdentifierJson)
        val roundTripCardAccountIdentifier = cardAccountIdentifierJsonAdapter.fromJson(cardAccountIdentifierJson)
        assertEquals(cardAccountIdentifier, roundTripCardAccountIdentifier)
        println(roundTripCardAccountIdentifier)
    }

    @Test
    fun testAddressSerialization() {
        val testAddress = Address(
            "7370 BAKER ST STE 100\\nPITTSBURGH, PA 15206",
            "7370 BAKER ST STE 100", null,
            "PITTSBURGH", "PA", "15206", "US",
            BigDecimal(40.440624), BigDecimal(-79.995888)
        )
        val addressJsonAdapter = serializer.adapter<Address>().indent("  ")
        val addressJson = addressJsonAdapter.toJson(testAddress)
        println(addressJson)

        val roundTripAddress = addressJsonAdapter.fromJson(addressJson)
        assertEquals(testAddress, roundTripAddress)
        println(roundTripAddress)
    }
}
