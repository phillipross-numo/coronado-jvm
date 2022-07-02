package io.github.coronado

import io.github.coronado.entity.CardAccountStatus

interface CardAccounts {

    // CreateCardAccount createCardAccount POST /partner/card-accounts
    fun createCardAccount(cardProgramExternalId: String, cardAccountExternalId: String, publisherExternalId: String, status: CardAccountStatus = CardAccountStatus.ENROLLED)

    fun createCardAccounts() // CreateOrUpdateCardAccountBatch ? POST   /partner/batch/card-accounts

    fun updateCardAccountById(status: CardAccountStatus) // UpdateCardAccount updateCardAccountById PATCH  /partner/card-accounts/{id}
    fun getCardAccountById(id: String) // GetCardAccount getCardAccountById GET /partner/card-accounts/{id}

    // ListCardAccounts listCardAccounts GET /partner/card-accounts
    fun listCardAccounts(
        publisherExternalId: String,
        cardProgramExternalId: String
    ): List<String> // list of card accounts
    data class CardAccountResponse(val id: String, val externalId: String, val status: String)
}
