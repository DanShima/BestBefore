package com.ichimaya.androidhackathon.badges.model

data class Badge(val id: String,
                val name: String,
                val achieveDate: Long)

fun fiveDayStreak(achieveDate: Long): Badge = Badge("five-day-streak", "Five day streak!", achieveDate)