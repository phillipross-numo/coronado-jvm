package io.github.coronado

import io.github.coronado.entity.Merchant
import java.util.UUID
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class MerchantTest {

    @Test
    internal fun testEntityInstantiation() {
        val merchant1a = Merchant(UUID.randomUUID())
        val merchant1b = Merchant(merchant1a.identity)
        val merchant2 = Merchant(UUID.randomUUID())
        assertEquals(merchant1a, merchant1b)
        assertNotEquals(merchant1a, merchant2)
    }
}
