package io.github.coronado

interface CardAccounts {

    // CreateCardAccount createCardAccount POST /partner/card-accounts
    fun createCardAccount(cardProgramExternalId: String, cardAccountExternalId: String, publisherExternalId: String, status: CardAccountStatus = CardAccountStatus.ENROLLED)

    fun createCardAccounts() // CreateOrUpdateCardAccountBatch ? POST   /partner/batch/card-accounts

    fun updateCardAccountById(status: CardAccountStatus) // UpdateCardAccount updateCardAccountById PATCH  /partner/card-accounts/{id}
    fun getCardAccountById(id: String) // GetCardAccount getCardAccountById GET /partner/card-accounts/{id}
    fun listCardAccountsByCardProgramExternalId(cardProgramExternalId: String) // ListCardAccounts listCardAccounts GET /partner/card-accounts
    fun listCardAccountsByCardAccountExternalId(cardAccountExternalId: String) // ListCardAccounts listCardAccounts GET /partner/card-accounts
    fun listCardAccountsByPublisherExternalId(publisherExternalId: String) // ListCardAccounts listCardAccounts GET /partner/card-accounts
}

enum class CardAccountStatus { ENROLLED, NOT_ENROLLED, CLOSED } // default ENROLLED
