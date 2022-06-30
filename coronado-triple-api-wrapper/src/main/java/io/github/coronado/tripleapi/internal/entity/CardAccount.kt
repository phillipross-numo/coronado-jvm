package io.github.coronado.tripleapi.internal.entity

import java.util.UUID

data class CardAccount(val identity: UUID) {
    constructor(identity: UUID, cardProgram: CardProgram) : this(identity)
}

enum class CardAccountStatus { ENROLLED, NOT_ENROLLED, CLOSED } // default ENROLLED
