package io.github.coronado.tripleapi.internal

data class ApiError(val status: String, val timestamp: String, val message: String, val debugMessage: String, val subErrors: List<ApiSubError>)
data class ApiSubError(val message: String)
