package io.github.coronado.tripleapi.components

import com.squareup.moshi.Json
import io.github.coronado.tripleapi.components.schemas.EntityId
import io.github.coronado.tripleapi.components.schemas.ExternalId
import io.github.coronado.tripleapi.components.schemas.Publisher

/**
 * A Kotlin implementation for the response components established by Triple API Swagger v1.1 (as of 2022-06-27)
 */

data class PublishersResponse(val publishers: List<PublisherResponsePublisherItem>)
data class PublisherResponsePublisherItem(val id: EntityId, @Json(name = "external_id") val externalId: ExternalId, @Json(name = "assumed_name") val assumedName: String)
typealias PublisherResponse = Publisher
