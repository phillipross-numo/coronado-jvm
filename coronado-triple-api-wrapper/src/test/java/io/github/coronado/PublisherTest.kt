package io.github.coronado

import io.github.coronado.entity.Address
import io.github.coronado.entity.Publisher
import java.math.BigDecimal
import java.util.UUID
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class PublisherTest {

    @Test
    internal fun testEntityInstantiation() {
        val publisher1a = Publisher(UUID.randomUUID())
        val publisher1b = Publisher(publisher1a.identity)
        val publisher2 = Publisher(UUID.randomUUID())
        assertEquals(publisher1a, publisher1b)
        assertNotEquals(publisher1a, publisher2)

        val address1 = Address("someCompleteAddress", "l1", "l2", "locality", "PA", "15213", "US", BigDecimal.ZERO, BigDecimal.ZERO)
        println("address1: $address1")
    }
}
