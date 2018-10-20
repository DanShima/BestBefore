package com.ichimaya.androidhackathon.food.model

data class Food(val id: String,
                val name: String,
                val expiryDate: Long,
                val category: String,
                val consumeDate: Long?)