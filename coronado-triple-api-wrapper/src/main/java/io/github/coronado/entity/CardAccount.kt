package io.github.coronado.entity

import java.util.UUID

data class CardAccount(val identity: UUID) {
    constructor(identity: UUID, cardProgram: CardProgram) : this(identity)
}
