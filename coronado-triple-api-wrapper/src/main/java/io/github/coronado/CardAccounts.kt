package io.github.coronado

import io.github.coronado.tripleapi.partner.CardAccounts

interface CardAccounts {

    fun updateCardAccountById(status: CardAccounts.CardAccountStatus) // UpdateCardAccount updateCardAccountById PATCH  /partner/card-accounts/{id}
    fun getCardAccountById(id: String) // GetCardAccount getCardAccountById GET /partner/card-accounts/{id}
}
