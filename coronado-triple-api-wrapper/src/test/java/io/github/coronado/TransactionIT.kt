package io.github.coronado

import io.github.coronado.entity.CardAccount
import io.github.coronado.entity.CardProgram
import io.github.coronado.entity.Publisher
import io.github.coronado.entity.Transaction
import java.util.UUID
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class TransactionIT {

    @Test
    internal fun testEntityInstantiation() {
        val cardProgram1 = CardProgram(UUID.randomUUID(), "externalId-1", "name-1", "USD")
        val cardProgram2 = CardProgram(UUID.randomUUID(), "externalId-2", "name-2", "USD")
        val cardAccount1 = CardAccount(UUID.randomUUID(), cardProgram1)
        val cardAccount2 = CardAccount(UUID.randomUUID(), cardProgram2)
        val publisher1 = Publisher(UUID.randomUUID())
        val publisher2 = Publisher(UUID.randomUUID())
        val transaction1a = Transaction(UUID.randomUUID(), publisher1, cardAccount1)
        val transaction1b = Transaction(transaction1a.identity, publisher1, cardAccount1)
        val transaction2 = Transaction(UUID.randomUUID(), publisher2, cardAccount2)
        assertEquals(transaction1a, transaction1b)
        assertNotEquals(transaction1a, transaction2)
    }
}
