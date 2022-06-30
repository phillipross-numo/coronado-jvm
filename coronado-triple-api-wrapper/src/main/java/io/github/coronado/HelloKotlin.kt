package io.github.coronado

fun main() {
    hello()
    hello("World")
    hello("Universe")
}

fun hello(name: String = "Kotlin") = println("Hello $name")
