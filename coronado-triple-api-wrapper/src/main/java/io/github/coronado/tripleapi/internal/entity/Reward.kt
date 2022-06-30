package io.github.coronado.tripleapi.internal.entity

import java.util.UUID

data class Reward(val identity: UUID)

enum class RewardStatus {
    REJECTED, PENDING_MERCHANT_APPROVAL, DENIED_BY_MERCHANT, PENDING_MERCHANT_FUNDING, PENDING_TRANSFER_TO_PUBLISHER,
    DISTRIBUTED_TO_PUBLISHER, DISTRIBUTED_TO_CARDHOLDER
} // default PENDING_MERCHANT_APPROVAL

enum class RewardType { FIXED, PERCENTAGE }
