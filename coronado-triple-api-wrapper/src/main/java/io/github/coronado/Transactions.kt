package io.github.coronado

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

enum class TransactionType { CHECK, DEPOSIT, FEE, PAYMENT, PURCHASE, REFUND, TRANSFER, WITHDRAWAL }

enum class ProcessorMIDType {
    AMEX_SE_NUMBER,
    DISCOVER_MID,
    MC_AUTH_LOC_ID,
    MC_AUTH_ACQ_ID,
    MC_AUTH_ICA,
    MC_CLEARING_LOC_ID,
    MC_CLEARING_ACQ_ID,
    MC_CLEARING_ICA,
    MERCHANT_PROCESSOR,
    NCR,
    VISA_VMID,
    VISA_VSID
}
