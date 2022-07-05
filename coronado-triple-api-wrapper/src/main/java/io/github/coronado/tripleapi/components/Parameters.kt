package io.github.coronado.tripleapi.components

import io.github.coronado.tripleapi.components.schemas.EntityId
import java.time.LocalDate

/**
 * A Kotlin implementation for the parameter components established by Triple API Swagger v1.1 (as of 2022-06-27)
 */

typealias CardAccountExternalIdParameter = String // Partner-provided external ID
typealias CardProgramExternalIdParameter = String // Partner-provided external ID
typealias EndDateParameter = LocalDate // Include only transactions through this date (YYYY-mm-dd)
typealias EntityIdParameter = EntityId
typealias MatchedParameter = Boolean // Include only transactions matched to an active offer. See the Reward Details for more information, such as whether an award is approved.
typealias MerchantExternalIdParameter = String // Partner-provided external ID
typealias MerchantLocationExternalIdParameter = String // Partner-provided external ID
typealias OfferExternalIdParameter = String // Partner-provided external ID
typealias PublisherExternalIdParameter = String // Partner-provided external ID
typealias StartDateParameter = LocalDate // Include only transactions starting from this date (YYYY-mm-dd)
typealias TransactionExternalIdParameter = String // Partner-provided external ID
