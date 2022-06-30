package io.github.coronado.tripleapi.internal.entity

import java.util.UUID

data class CardProgram(val identity: UUID, val externalId: String, val name: String, val currency: String)
