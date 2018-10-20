package com.ichimaya.androidhackathon.challenges

import android.arch.lifecycle.ViewModel
import android.util.Log
import com.ichimaya.androidhackathon.R
import com.ichimaya.androidhackathon.food.model.Category

class ChallengeViewModel: ViewModel() {
    private var challengeList = mutableListOf<Challenge>()

    private val titles = arrayOf(
        "Fruit Ninja",
        "Vegan  Certificate",
        "Egglicious",
        "Master of Frugality",
        "An apple a day, keep the doctor away",
        "Left but not over")

    private val icons = intArrayOf(
        R.drawable.ic_fruits,
        R.drawable.ic_vegan,
        R.drawable.ic_eggs,
        R.drawable.ic_coins,
        R.drawable.ic_educate,
        R.drawable.ic_leftover)

    private val descriptions = arrayOf(
        "No spoiled fruit for a week",
        "Eat meat and dairy free for 3 weeks",
        "Use up a carton of eggs within 3 days",
        "Live frugal without spoiling food for a month",
        "Eat an apple a day for a month",
        "Save lunch money by eating 5 leftovers in a week")


    fun setupChallengeList(): MutableList<Challenge> {
        for (i in icons.indices) {
            addChallengesToList(icons[i], titles[i], descriptions[i])
        }
        return challengeList
    }

    private fun addChallengesToList(icon: Int, title: String, description: String):
        MutableList<Challenge> {
        val challenge = Challenge(title, icon, description)
        challengeList.add(challenge)
        return challengeList
    }
}