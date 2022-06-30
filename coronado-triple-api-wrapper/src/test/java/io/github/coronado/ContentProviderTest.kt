package io.github.coronado

import io.github.coronado.entity.ContentProvider
import java.util.UUID
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class ContentProviderTest {

    @Test
    internal fun testEntityInstantiation() {
        val contentProvider1a = ContentProvider(UUID.randomUUID())
        val contentProvider1b = ContentProvider(contentProvider1a.identity)
        val contentProvider2 = ContentProvider(UUID.randomUUID())
        assertEquals(contentProvider1a, contentProvider1b)
        assertNotEquals(contentProvider1a, contentProvider2)
    }
}
