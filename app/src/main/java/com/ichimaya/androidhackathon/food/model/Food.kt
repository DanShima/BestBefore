package com.ichimaya.androidhackathon.food.model

data class Food(val id: String,
                val name: String,
                val category: Category,
                val expiryDate: Long,
                val consumeDate: Long?)