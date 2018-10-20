package com.ichimaya.androidhackathon.food.model

data class Food(val id: String,
                val name: String,
                val expiryDate: Long, // expiry date in milliseconds
                val category: String,
                val consumeDate: Long?) { // consume date in milliseconds

    fun isConsumed() = consumeDate != null
}
