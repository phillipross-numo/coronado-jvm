package io.github.coronado

import io.github.coronado.entity.Offer
import java.util.UUID
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class OfferTest {

    @Test
    internal fun testEntityInstantiation() {
        val offer1a = Offer(UUID.randomUUID())
        val offerd1b = Offer(offer1a.identity)
        val offerd2 = Offer(UUID.randomUUID())
        assertEquals(offer1a, offerd1b)
        assertNotEquals(offer1a, offerd2)
    }
}
