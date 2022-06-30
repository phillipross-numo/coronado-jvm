package io.github.coronado

interface Rewards {
    fun listRewards(rewardStatus: RewardStatus) //  GET /partner/rewards
    fun approveReward(transactionId: String, offerId: String) //  POST /partner/rewards.approve
    fun denyReward(transactionId: String, offerId: String, notes: String) // POST /partner/rewards.deny
}

enum class RewardStatus {
    REJECTED,
    PENDING_MERCHANT_APPROVAL,
    DENIED_BY_MERCHANT,
    PENDING_MERCHANT_FUNDING,
    PENDING_TRANSFER_TO_PUBLISHER,
    DISTRIBUTED_TO_PUBLISHER,
    DISTRIBUTED_TO_CARDHOLDER
} // default PENDING_MERCHANT_APPROVAL
