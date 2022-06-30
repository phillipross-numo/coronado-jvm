package io.github.coronado.entity

import java.util.UUID

data class Transaction(val identity: UUID, val publisher: Publisher, val cardAccount: CardAccount)

enum class TransactionType { CHECK, DEPOSIT, FEE, PAYMENT, PURCHASE, REFUND, TRANSFER, WITHDRAWAL }

enum class ProcessorMIDType {
    AMEX_SE_NUMBER, DISCOVER_MID, MC_AUTH_LOC_ID, MC_AUTH_ACQ_ID, MC_AUTH_ICA, MC_CLEARING_LOC_ID, MC_CLEARING_ACQ_ID,
    MC_CLEARING_ICA, MERCHANT_PROCESSOR, NCR, VISA_VMID, VISA_VSID
}
