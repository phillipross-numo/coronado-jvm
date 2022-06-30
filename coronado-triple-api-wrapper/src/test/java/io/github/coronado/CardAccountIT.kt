package io.github.coronado

import io.github.coronado.entity.CardAccount
import io.github.coronado.entity.CardProgram
import java.util.UUID
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class CardAccountIT {

    @Test
    internal fun testEntityInstantiation() {
        val cardProgram1 = CardProgram(UUID.randomUUID(), "externalId-1", "name-1", "USD")
        val cardProgram2 = CardProgram(UUID.randomUUID(), "externalId-2", "name-2", "USD")
        val cardAccount1a = CardAccount(UUID.randomUUID(), cardProgram1)
        val cardAccount1b = CardAccount(cardAccount1a.identity, cardProgram1)
        val cardAccount2 = CardAccount(UUID.randomUUID(), cardProgram2)
        assertEquals(cardAccount1a, cardAccount1b)
        assertNotEquals(cardAccount1a, cardAccount2)
    }
}
