package com.ichimaya.androidhackathon.food.model

data class Food(val id: String,
                val name: String,
                val expiryDate: Long,
                val consumeDate: Long?)