package io.github.coronado.api

data class ApiError(val status: String, val timestamp: String, val message: String, val debugMessage: String, val subErrors: List<ApiSubError>)
data class ApiSubError(val message: String)
