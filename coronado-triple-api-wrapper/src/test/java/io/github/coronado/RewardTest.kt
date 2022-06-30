package io.github.coronado

import io.github.coronado.entity.Reward
import java.util.UUID
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class RewardTest {

    @Test
    internal fun testEntityInstantiation() {
        val reward1a = Reward(UUID.randomUUID())
        val reward1b = Reward(reward1a.identity)
        val reward2 = Reward(UUID.randomUUID())
        assertEquals(reward1a.identity, reward1b.identity)
        assertNotEquals(reward1a, reward2)
    }
}
