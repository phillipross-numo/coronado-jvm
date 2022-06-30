package io.github.coronado.tripleapi.internal.entity

import java.util.UUID
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class CardProgramTest {

    @Test
    internal fun testEntityInstantiation() {
        val cardProgram1a = CardProgram(UUID.randomUUID(), "externalId-1", "name-1", "USD")
        val cardProgram1b = CardProgram(cardProgram1a.identity, "externalId-1", "name-1", "USD")
        val cardProgram2 = CardProgram(UUID.randomUUID(), "externalId-2", "name-2", "USD")
        assertEquals(cardProgram1a, cardProgram1b)
        assertNotEquals(cardProgram1a, cardProgram2)
        assertEquals(cardProgram1a.currency, cardProgram2.currency)
    }
}
