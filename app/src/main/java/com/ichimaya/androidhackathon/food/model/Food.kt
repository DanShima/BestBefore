package com.ichimaya.androidhackathon.food.model

import com.google.firebase.database.DataSnapshot
import java.util.*

data class Food(val id: String,
                val name: String,
                val addDate: Long, // add date in milliseconds
                val expiryDate: Long, // expiry date in milliseconds
                val category: String,
                val consumeDate: Long?) { // consume date in milliseconds
}

fun Food.isConsumed() = consumeDate != null
fun Food.isExpired() = expirationState() == ExpirationState.EXPIRED

fun Food.expirationState(): ExpirationState {
    consumeDate?.let {
        return if (consumeDate < expiryDate) ExpirationState.NOT_EXPIRED else ExpirationState.EXPIRED
    }
    val timeLeft = expiryDate - Calendar.getInstance().timeInMillis
    val limit = 24L * 60L * 60L * 1000L
    if (timeLeft < 0) {
        return ExpirationState.EXPIRED
    } else if (timeLeft < limit) {
        return ExpirationState.SOON
    }

    return ExpirationState.NOT_EXPIRED
}

fun DataSnapshot.toFood(): Food? {
    return Food(
            id = child("id").getValue<String>(String::class.java) ?: return null,
            name = child("name").getValue<String>(String::class.java) ?: return null,
            addDate = child("addDate").getValue<Long>(Long::class.java) ?: return null,
            expiryDate = child("expiryDate").getValue<Long>(Long::class.java) ?: 0L,
            category = child("category").getValue<String>(String::class.java) ?: return null,
            consumeDate = child("consumeDate").getValue<Long>(Long::class.java)
    )
}

enum class ExpirationState {
    SOON,
    EXPIRED,
    NOT_EXPIRED
}

fun createFood(name: String, expiryDate: Long, category: String) =
        Food(id = UUID.randomUUID().toString(),
                name = name,
                addDate = Calendar.getInstance().timeInMillis,
                expiryDate = expiryDate,
                category = category,
                consumeDate = null)