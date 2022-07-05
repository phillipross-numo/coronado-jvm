package io.github.coronado.tripleapi.partner

import io.github.coronado.tripleapi.TripleApiResponse

interface CardAccounts {
    // ListCardAccounts listCardAccounts GET /partner/card-accounts
    fun listCardAccounts(
        publisherExternalId: String?,
        cardProgramExternalId: String?,
        cardAccountExternalId: String?
    ): ListCardAccountsResponse // list of card accounts
    data class ListCardAccountsResponse(val id: List<ListCardAccountsResponseItem>) : TripleApiResponse()
    data class ListCardAccountsResponseItem(val id: String, val externalId: String, val status: String)

    // CreateCardAccount createCardAccount POST /partner/card-accounts
    fun createCardAccount(publisherExternalId: String, cardProgramExternalId: String, cardAccountExternalId: String, status: CardAccountStatus = CardAccountStatus.ENROLLED)
    enum class CardAccountStatus { ENROLLED, NOT_ENROLLED, CLOSED }
    data class CreateCardAccountsResponse(val id: List<ListCardAccountsResponseItem>) : TripleApiResponse()
}
