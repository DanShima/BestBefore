package com.ichimaya.androidhackathon.food.model

import android.os.SystemClock
import timber.log.Timber
import java.util.*

data class Food(val id: String,
                val name: String,
                val expiryDate: Long, // expiry date in milliseconds
                val category: String,
                val consumeDate: Long?) { // consume date in milliseconds
}

fun Food.isConsumed() = consumeDate != null

fun Food.expirationState(): ExpirationState {
    val timeLeft = expiryDate - Calendar.getInstance().timeInMillis
    val limit = 24L * 60L * 60L * 1000L
    if (timeLeft < 0) {
        return ExpirationState.EXPIRED
    } else if (timeLeft < limit) {
        return ExpirationState.SOON
    }

    return ExpirationState.NOT_EXPIRED
}

enum class ExpirationState {
    SOON,
    EXPIRED,
    NOT_EXPIRED
}

fun createFood(name: String, expiryDate: Long, category: String) =
        Food(UUID.randomUUID().toString(), name, expiryDate, category, null)