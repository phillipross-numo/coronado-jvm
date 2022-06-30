package io.github.coronado.entity

import java.util.UUID

data class Transaction(val identity: UUID, val publisher: Publisher, val cardAccount: CardAccount)
