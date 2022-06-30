package io.github.coronado.tripleapi.internal.entity

import java.util.UUID
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class MerchantLocationTest {

    @Test
    internal fun testEntityInstantiation() {
        val merchantLocation1a = MerchantLocation(UUID.randomUUID())
        val merchantLocation1b = MerchantLocation(merchantLocation1a.identity)
        val merchantLocation2 = MerchantLocation(UUID.randomUUID())
        assertEquals(merchantLocation1a, merchantLocation1b)
        assertNotEquals(merchantLocation1a, merchantLocation2)
    }
}
