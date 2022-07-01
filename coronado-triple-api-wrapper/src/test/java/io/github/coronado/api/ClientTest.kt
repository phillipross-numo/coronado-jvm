package io.github.coronado.api

import kotlin.test.Test

class ClientTest {

    @Test
    fun testClient() {
        val client = Client("sandbox.tripleup.dev", "someClientId", "someSecret")
        println("client: $client")
        println("auth url: ${client.authURL}")
        println("client: $client")
        println("auth url: ${client.authURL}")
    }
}
