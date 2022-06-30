package io.github.coronado.api

import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import io.github.coronado.tripleapi.components.PublisherPatchAddressRequestBody
import io.github.coronado.tripleapi.components.PublisherPatchAssumedNameRequestBody
import io.github.coronado.tripleapi.components.PublisherPostRequestBody
import io.github.coronado.tripleapi.components.PublisherResponse
import io.github.coronado.tripleapi.components.PublishersResponse
import java.math.BigDecimal
import java.net.URI
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

@OptIn(ExperimentalStdlibApi::class)
class PublisherEntitiesApiIT {

    val cfgNameTripleApiServiceDomain = "TRIPLE_API_SERVICE_DOMAIN"
    val cfgNameTripleApiClientId = "TRIPLE_API_CLIENT_ID"
    val cfgNameTripleApiClientSecret = "TRIPLE_API_CLIENT_SECRET"
    val config = mapOf(
        cfgNameTripleApiServiceDomain to System.getenv()[cfgNameTripleApiServiceDomain]!!,
        cfgNameTripleApiClientId to System.getenv()[cfgNameTripleApiClientId]!!,
        cfgNameTripleApiClientSecret to System.getenv()[cfgNameTripleApiClientSecret]!!
    )
    val partnerPublishersClient = Client.Builder()
        .serviceDomain(config[cfgNameTripleApiServiceDomain]!!)
        .clientId(config[cfgNameTripleApiClientId]!!)
        .clientSecret(config[cfgNameTripleApiClientSecret]!!)
        .scope(Client.OAuth2Scope.PARTNER_PUBLISHERS)
        .build()
    val partnerPortfoliosClient = Client.Builder()
        .serviceDomain(config[cfgNameTripleApiServiceDomain]!!)
        .clientId(config[cfgNameTripleApiClientId]!!)
        .clientSecret(config[cfgNameTripleApiClientSecret]!!)
        .scope(Client.OAuth2Scope.PARTNER_PORTFOLIOS)
        .build()
    val moshi: Moshi = Moshi.Builder()
        .add(BigDecimalAdapter)
        .add(CurrencyAdapter)
        .add(LocalDateAdapter)
        .add(LocalDateTimeAdapter)
        .add(LocalTimeAdapter)
        .add(ZonedDateTimeAdapter).addLast(KotlinJsonAdapterFactory()).build()
    val listPublisherResponseJsonAdapter = moshi.adapter<PublishersResponse>()
    val getPublisherResponseJsonAdapter = moshi.adapter<PublisherResponse>()
    val createPublisherPostRequestBodyJsonAdapter = moshi.adapter<PublisherPostRequestBody>()
    val createPublisherResponseBodyJsonAdapter = moshi.adapter<PublisherResponse>()
    val updatePublisherAssumedNamePatchRequestBodyJsonAdapter = moshi.adapter<PublisherPatchAssumedNameRequestBody>()
    val updatePublisherAddressPatchRequestBodyJsonAdapter = moshi.adapter<PublisherPatchAddressRequestBody>()
    val updatePublisherPatchResponseJsonAdapter = moshi.adapter<PublisherResponse>()

    @Test
    fun `test read publishers`() {
        val listPublishersReqUrl = "${partnerPublishersClient.apiUrl}partner/publishers"
        val listPublishersReq = HttpRequest.newBuilder().uri(URI(listPublishersReqUrl))
            .header("Authorization", "${partnerPublishersClient.tokenType()} ${partnerPublishersClient.token()}")
            .header("content-type", "application/json")
            .GET().build()
        val listPublishersResp = partnerPublishersClient.httpClient.send(listPublishersReq, HttpResponse.BodyHandlers.ofString())
        val listPublishersRespBody = listPublisherResponseJsonAdapter.fromJson(listPublishersResp.body())
        println("listPublishersRespBody: $listPublishersRespBody")
        listPublishersRespBody?.publishers?.forEach {
            println("publisher $it")
            val getPublisherReqUrl = "${partnerPublishersClient.apiUrl}partner/publishers/${it.id}"
            val getPublisherReq = HttpRequest.newBuilder().uri(URI(getPublisherReqUrl))
                .header("Authorization", "${partnerPublishersClient.tokenType()} ${partnerPublishersClient.token()}")
                .header("content-type", "application/json")
                .GET().build()
            val getPublisherResp = partnerPublishersClient.httpClient.send(getPublisherReq, HttpResponse.BodyHandlers.ofString())
            println("getPublisherResp: $getPublisherResp")
            val getPublisherRespBody = getPublisherResponseJsonAdapter.fromJson(getPublisherResp.body())
            println("getPublisherRespBody: $getPublisherRespBody")
        }
    }

    @Test
    fun `test write publisher`() {
        val newPublisherToCreate = PublisherPostRequestBody(
            "0_812_${System.currentTimeMillis().toString().reversed()}", // (OU812 didn't work :( )
            "BenevolentFI, Ltd_${System.currentTimeMillis().toString().reversed()}",
            Address(
                "123 Sesame Street, 2nd Floor, New York, NY 10003, US",
                "123 Sesame Street",
                "2nd Floor",
                "New York",
                "NY",
                "10003",
                "US",
                Latitude("40.737139"),
                Longitude("-73.990400")
            ),
            BigDecimal("0.21")
        )
        val publisherPostRequestBody = assertNotNull(createPublisherPostRequestBodyJsonAdapter.toJson(newPublisherToCreate))
        println(publisherPostRequestBody)

        val createPublisherReq = HttpRequest.newBuilder()
            .uri(URI("${partnerPublishersClient.apiUrl}partner/publishers"))
            .header("Authorization", "${partnerPortfoliosClient.tokenType()} ${partnerPortfoliosClient.token()}")
            .header("content-type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(publisherPostRequestBody))
            .build()!!
        val createPublishersResp = assertNotNull(
            partnerPortfoliosClient.httpClient.send(createPublisherReq, HttpResponse.BodyHandlers.ofString())
        )
        println("createPublishersResp status: ${createPublishersResp.statusCode()}") // gotta handle 422 response code
        println("createPublishersResp body: ${createPublishersResp.body()}")
        val newlyCreatedPublisher = assertNotNull(createPublisherResponseBodyJsonAdapter.fromJson(createPublishersResp.body()))
        println("newlyCreatedPublisher: $newlyCreatedPublisher}")

        val getPublisherReqUrl = "${partnerPublishersClient.apiUrl}partner/publishers/${newlyCreatedPublisher.id}"
        val getPublisherReq = HttpRequest.newBuilder().uri(URI(getPublisherReqUrl))
            .header("Authorization", "${partnerPublishersClient.tokenType()} ${partnerPublishersClient.token()}")
            .header("content-type", "application/json")
            .GET().build()!!
        val getPublisherResp = assertNotNull(partnerPublishersClient.httpClient.send(getPublisherReq, HttpResponse.BodyHandlers.ofString()))
        println("getPublisherResp: $getPublisherResp")
        val getPublisherRespBody = assertNotNull(getPublisherResponseJsonAdapter.fromJson(getPublisherResp.body()))
        println("getPublisherRespBody: $getPublisherRespBody")

        assertEquals(newlyCreatedPublisher.assumedName, newPublisherToCreate.assumedName)
        assertEquals(newlyCreatedPublisher.revenueShare, newPublisherToCreate.revenueShare)
        assertEquals(newlyCreatedPublisher.address.completeAddress, newPublisherToCreate.address.completeAddress)

        val updatedPublisherPatchAssumedNameRequestBody = assertNotNull(updatePublisherAssumedNamePatchRequestBodyJsonAdapter.toJson(PublisherPatchAssumedNameRequestBody("${newlyCreatedPublisher.assumedName}_UPD")))
        println("updatedPublisherPatchAssumedNameRequestBody: $updatedPublisherPatchAssumedNameRequestBody")
        val updatePublisherReq = HttpRequest.newBuilder().uri(URI(getPublisherReqUrl))
            .header("Authorization", "${partnerPublishersClient.tokenType()} ${partnerPublishersClient.token()}")
            .header("content-type", "application/json")
            .method("PATCH", HttpRequest.BodyPublishers.ofString(updatedPublisherPatchAssumedNameRequestBody)).build()!!
        val updatePublishersResp = assertNotNull(partnerPortfoliosClient.httpClient.send(updatePublisherReq, HttpResponse.BodyHandlers.ofString()))
        println("updatePublishersResp status: ${updatePublishersResp.statusCode()}") // gotta handle 422 response code?
        println("updatePublishersResp body: ${updatePublishersResp.body()}")
        val updatedPublisher = assertNotNull(updatePublisherPatchResponseJsonAdapter.fromJson(updatePublishersResp.body()))
        println("updatedPublisher: $updatedPublisher")

        val updatedPublisherPatchAddressRequestBody = assertNotNull(updatePublisherAddressPatchRequestBodyJsonAdapter.toJson(PublisherPatchAddressRequestBody(newlyCreatedPublisher.address.copy(completeAddress = "${newlyCreatedPublisher.address.completeAddress}_UPD"))))
        println("updatedPublisherPatchAddressRequestBody: $updatedPublisherPatchAddressRequestBody")
        val updatePublisherReq2 = HttpRequest.newBuilder().uri(URI(getPublisherReqUrl))
            .header("Authorization", "${partnerPublishersClient.tokenType()} ${partnerPublishersClient.token()}")
            .header("content-type", "application/json")
            .method("PATCH", HttpRequest.BodyPublishers.ofString(updatedPublisherPatchAddressRequestBody)).build()!!
        val updatePublishersResp2 = assertNotNull(partnerPortfoliosClient.httpClient.send(updatePublisherReq2, HttpResponse.BodyHandlers.ofString()))
        println("updatePublishersResp2 status: ${updatePublishersResp2.statusCode()}") // gotta handle 422 response code?
        println("updatePublishersResp2 body: ${updatePublishersResp2.body()}")
        val updatedPublisher2 = assertNotNull(updatePublisherPatchResponseJsonAdapter.fromJson(updatePublishersResp2.body()))
        println("updatedPublisher2: $updatedPublisher2")
        println("original Publisher: $newlyCreatedPublisher")

        val updatePublisherReq3 = HttpRequest.newBuilder().uri(URI(getPublisherReqUrl))
            .header("Authorization", "${partnerPublishersClient.tokenType()} ${partnerPublishersClient.token()}")
            .header("content-type", "application/json")
            .method("PATCH", HttpRequest.BodyPublishers.ofString(publisherPostRequestBody)).build()!!
        val updatePublishersResp3 = assertNotNull(partnerPortfoliosClient.httpClient.send(updatePublisherReq3, HttpResponse.BodyHandlers.ofString()))
        println("updatePublishersResp3 status: ${updatePublishersResp3.statusCode()}") // gotta handle 422 response code?
        println("updatePublishersResp3 body: ${updatePublishersResp3.body()}")

        val getPublisherReqUrl2 = "${partnerPublishersClient.apiUrl}partner/publishers/${newlyCreatedPublisher.id}"
        val getPublisherReq2 = HttpRequest.newBuilder().uri(URI(getPublisherReqUrl2))
            .header("Authorization", "${partnerPublishersClient.tokenType()} ${partnerPublishersClient.token()}")
            .header("content-type", "application/json")
            .GET().build()!!
        val getPublisherResp2 = assertNotNull(partnerPublishersClient.httpClient.send(getPublisherReq2, HttpResponse.BodyHandlers.ofString()))
        println("getPublisherResp2: $getPublisherResp2")
        val getPublisherRespBody2 = assertNotNull(getPublisherResponseJsonAdapter.fromJson(getPublisherResp2.body()))
        println("getPublisherRespBody2: $getPublisherRespBody2")
    }
}
