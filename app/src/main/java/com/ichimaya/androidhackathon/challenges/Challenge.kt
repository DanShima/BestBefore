package com.ichimaya.androidhackathon.challenges

data class Challenge(
    val title: String,
    val badge: Int,
    val description: String,
    var startDate: Int? = null,
    val endDate: Int
)