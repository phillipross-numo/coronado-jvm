package io.github.coronado.tripleapi.internal

import io.github.coronado.tripleapi.internal.entity.RewardStatus

interface Rewards {
    fun listRewards(rewardStatus: RewardStatus) //  GET /partner/rewards
    fun approveReward(transactionId: String, offerId: String) //  POST /partner/rewards.approve
    fun denyReward(transactionId: String, offerId: String, notes: String) // POST /partner/rewards.deny
}
