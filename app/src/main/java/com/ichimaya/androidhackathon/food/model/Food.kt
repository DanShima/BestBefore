package com.ichimaya.androidhackathon.food.model

import java.util.*

data class Food(val id: String,
                val name: String,
                val expiryDate: Long, // expiry date in milliseconds
                val category: String,
                val consumeDate: Long?) { // consume date in milliseconds
}

fun Food.isConsumed() = consumeDate != null

fun createFood(name: String, expiryDate: Long, category: String) =
        Food(UUID.randomUUID().toString(), name, expiryDate, category, null)