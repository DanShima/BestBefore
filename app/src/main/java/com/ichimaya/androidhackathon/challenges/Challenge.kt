package com.ichimaya.androidhackathon.challenges

data class Challenge(
    val title: String,
    val badge: Int,
    val description: String,
    var startDate: Long? = null,
    val challengeLength: Int  // the number of days to complete the challenge
)