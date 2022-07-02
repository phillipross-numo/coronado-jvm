package io.github.coronado

interface CardPrograms {
    fun createCardProgram(cardProgramExternalId: String, name: String, programCurrency: String) // createCardProgram POST /partner/card-programs
    fun createCardProgram(cardProgramExternalId: String, name: String, programCurrency: String, publisherExternalId: String, cardBINS: Set<String>) // createCardProgram POST /partner/card-programs
    fun updateCardProgram(id: String, name: String) // updateCardProgram PATCH  /partner/card-programs/{id}
    fun getCardProgramById(id: String) // getCardProgramById GET /partner/card-programs/{id}
    fun listCardPrograms(publisherExternalId: String, cardProgramExternalId: String) // listCardPrograms GET /partner/card-programs
}
