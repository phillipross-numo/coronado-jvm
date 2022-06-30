package io.github.coronado.tripleapi.partner

import io.github.coronado.tripleapi.TripleApiResponse
import io.github.coronado.tripleapi.components.schemas.CardAccount

interface CardAccounts {

    // ListCardAccounts listCardAccounts GET /partner/card-accounts
    fun listCardAccounts(
        publisherExternalId: String?,
        cardProgramExternalId: String?,
        cardAccountExternalId: String?
    ): TripleApiResponse // list of card accounts
    class ListCardAccountsResponse200(val id: List<ListCardAccountsResponseItem>) : TripleApiResponse("application/json")
    data class ListCardAccountsResponseItem(val id: String, val externalId: String, val status: String)

    // CreateCardAccount createCardAccount POST /partner/card-accounts
    fun createCardAccount(
        publisherExternalId: String,
        cardProgramExternalId: String,
        cardAccountExternalId: String,
        status: CardAccountStatus = CardAccountStatus.ENROLLED
    ): TripleApiResponse
    enum class CardAccountStatus { ENROLLED, NOT_ENROLLED, CLOSED }
    data class CreateCardAccountsResponse201(val cardAccount: CardAccount) : TripleApiResponse("application/json")
    data class CreateCardAccountsResponse409(val detail: String) : TripleApiResponse("application/json")

    // GetCardAccount getCardAccountById GET /partner/card-accounts/{id}
    fun getCardAccount(id: String): TripleApiResponse
    data class GetCardAccountResponse200(val cardAccount: CardAccount) : TripleApiResponse("application/json")

    // UpdateCardAccount updateCardAccountById PATCH  /partner/card-accounts/{id}
    fun updateCardAccount(cardAccount: CardAccount): TripleApiResponse
    data class UpdateCardAccountResponse200(val cardAccount: CardAccount) : TripleApiResponse("application/json")
}
