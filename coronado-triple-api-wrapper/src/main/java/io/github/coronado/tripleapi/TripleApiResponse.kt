package io.github.coronado.tripleapi

open class TripleApiResponse(contentType: String) {

    enum class STATUS { OK_200, CREATED_201, NO_CONTENT_204, NOT_FOUND_404, CONFLICT_409, INTERNAL_SERVER_ERROR_500 }
}
