package io.github.coronado

import io.github.coronado.entity.Address
import io.github.coronado.entity.MerchantCategoryCode
import io.github.coronado.entity.ProcessorMIDType
import java.math.BigDecimal

interface Transactions {
    fun createTransaction(
        cardProgramExternalId: String,
        cardAccountExternalId: String,
        transactionExternalId: String,
        localDate: String,
        debit: Boolean,
        amount: BigDecimal,
        transactionType: String,
        description: String,
        merchantCategoryCode: MerchantCategoryCode,
        merchantAddress: Address,
        processorMID: String,
        processorMIDType: ProcessorMIDType,
        publisherExternalId: String?,
        cardBIN: String?,
        cardLast4: String?,
        localTime: String?,
        currencyCode: String?
    ) // CreateTransaction createTransaction POST /partner/transactions
    fun updateTransactionBatch() // UploadTransactionBatch  ? POST   /partner/batch/transactions
    fun getTransactionById(transactionId: String) // GetTransaction getTransactionById GET /partner/transactions/{id}
    fun listTransactions(
        publisherExternalId: String?,
        cardProgramExternalId: String?,
        cardAccountExternalId: String?,
        transactionExternalId: String?,
        startDate: String?,
        endDate: String?,
        matched: Boolean?
    ) // GetTransactionList listTransactions GET /partner/transactions
}
