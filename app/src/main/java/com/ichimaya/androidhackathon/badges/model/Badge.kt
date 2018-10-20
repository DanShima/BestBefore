package com.ichimaya.androidhackathon.badges.model

data class Badge(val id: String,
                 val name: String,
                 val description: String,
                 val achieveDate: Long)

fun fiveDayStreak(achieveDate: Long): Badge = Badge(
        id = "five-day-streak",
        name = "Five day streak!",
        description = "Nothing expired for five days, go you!",
        achieveDate = achieveDate
)

fun fiveFoodsConsumed(achieveDate: Long): Badge = Badge(
        id = "consumed-five-foods",
        name = "Consumed five foods",
        description = "You consumed five foods before their expiration date! Om nom nom.",
        achieveDate = achieveDate)