package io.github.coronado.api

data class Client(val endpoint: String, val clientId: String, val clientSecret: String) {

    val authURL = "https://auth.$endpoint/oauth2/token"

    fun showTokenUrl() = println(authURL)
    fun showApiUrl() = println("https://api.$endpoint/")
}
