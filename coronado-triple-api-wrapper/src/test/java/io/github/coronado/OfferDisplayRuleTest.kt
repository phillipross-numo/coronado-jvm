package io.github.coronado

import io.github.coronado.entity.OfferDisplayRule
import java.util.UUID
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class OfferDisplayRuleTest {

    @Test
    internal fun testEntityInstantiation() {
        val offerDisplayRule1a = OfferDisplayRule(UUID.randomUUID())
        val offerDisplayRule1b = OfferDisplayRule(offerDisplayRule1a.identity)
        val offerDisplayRule2 = OfferDisplayRule(UUID.randomUUID())
        assertEquals(offerDisplayRule1a, offerDisplayRule1b)
        assertNotEquals(offerDisplayRule1a, offerDisplayRule2)
    }
}
