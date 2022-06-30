package io.github.coronado

import java.math.BigDecimal

interface PortfolioManagement {
    fun createPublisher(publisherExternalId: String, publisherAssumedName: String, address: Address, revenueShare: BigDecimal) // CreatePublisher createPublisher POST /partner/publishers
    fun updatePublisher(publisherId: String, publisherAssumedName: String?, address: Address?) // UpdatePublisher updatePublisher PATCH  /partner/publishers/{id}
    fun getPublisherById(publisherId: String) // GetPublisher  getPublisher GET /partner/publishers/{id}
    fun listPublishersByExternalPublisherId(publisherExternalId: String) // GetListOfPublishers listPublishers GET /partner/publishers
}
