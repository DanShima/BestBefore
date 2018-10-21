package com.ichimaya.androidhackathon.challenges

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import android.content.Context
import com.ichimaya.androidhackathon.R
import com.ichimaya.androidhackathon.food.FoodRepository
import com.ichimaya.androidhackathon.food.model.*
import com.ichimaya.androidhackathon.user.UserDetailsService
import com.ichimaya.androidhackathon.utils.toLocalDateTime
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

class ChallengeViewModel: ViewModel() {
    private var challengeList = mutableListOf<Challenge>()
    private lateinit var challenge: Challenge

    private val titles = arrayOf(
        "Fruit Ninja",
        "Vegan  Certificate",
        "Egglicious",
        "Master of Frugality",
        "An apple a day, keeps the doctor away",
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

    private val challengeLength = intArrayOf(
        7,
        21,
        3,
        30,
        30,
        7)

    fun createStartDate(date: Long) {
        challenge.startDate = date
    }

    fun observeChallenges(context: Context): LiveData<Map<Challenge, ChallengeState>> {
        return Transformations
                .map(FoodRepository.getInstance(UserDetailsService().getUUID(context))
                .observeFoods(), {foods -> getChallengeMap(foods.values.flatten())})
    }

    private fun getChallengeMap(foods: List<Food>): Map<Challenge, ChallengeState> {
        val challengeMap = mutableMapOf<Challenge, ChallengeState>()
        for (i in icons.indices) {
            val challenge = Challenge(titles[i], icons[i], descriptions[i], null, challengeLength[i])
            challengeMap[challenge] = getChallengeState(foods, challenge)
        }
        return challengeMap
    }

    fun getChallengeState(foods: List<Food>, challenge: Challenge): ChallengeState {
        challenge.startDate?.let {startDate ->
            val endDate = LocalDateTime.ofInstant(Instant.ofEpochMilli(startDate + (60 * 60 * 24 * 1000) * challenge.challengeLength), ZoneId.systemDefault())
            return if (endDate.isAfter(LocalDateTime.now())) {
                ChallengeState.STARTED
            } else {
                when (challenge.title) {
                    "Fruit Ninja" -> if (noSpoiledFruitForAWeek(foods)) ChallengeState.SUCCEEDED else ChallengeState.FAILED
                    "Vegan  Certificate" -> if (noMeatOrDairyFor3Weeks(foods)) ChallengeState.SUCCEEDED else ChallengeState.FAILED
                    "Egglicious" -> ChallengeState.SUCCEEDED
                    "Master of Frugality" -> if (noSpoiledFoodForAMonth(foods)) ChallengeState.SUCCEEDED else ChallengeState.FAILED
                    "An apple a day, keep the doctor away" -> ChallengeState.SUCCEEDED
                    "Left but not over" -> if (eatLeftoverForAWeek(foods)) ChallengeState.SUCCEEDED else ChallengeState.FAILED
                    else -> ChallengeState.FAILED // ¯\_(ツ)_/¯
                }
            }
        } ?: return ChallengeState.NOT_STARTED
    }
    
    private fun eatLeftoverForAWeek(foods: List<Food>): Boolean {
        val oneWeekAgo = LocalDateTime.now().minusDays(7)
        return foods.none {
            it.category == "Leftover" && it.isExpired() && it.expiryDate.toLocalDateTime().isAfter(oneWeekAgo)
        }
    }

    private fun noMeatOrDairyFor3Weeks(foods: List<Food>): Boolean {
        val threeWeeksAgo = LocalDateTime.now().minusDays(21)
        return foods.none {
            it.isConsumed() && it.containsAnimalProducts() && it.consumeDate?.toLocalDateTime()?.isAfter(threeWeeksAgo) ?: false
        }
    }

    private fun noSpoiledFruitForAWeek(foods: List<Food>): Boolean {
        val sevenDaysAgo = LocalDateTime.now().minusDays(7)
        return foods.none {
            it.getCategoryType() == CategoryType.FRUIT && it.isExpired() && it.expiryDate.toLocalDateTime().isAfter(sevenDaysAgo)
        }
    }

    private fun noSpoiledFoodForAMonth(foods: List<Food>): Boolean {
        val aMonthAgo = LocalDateTime.now().minusMonths(1)
        return foods.none {
            it.isExpired() && it.expiryDate.toLocalDateTime().isAfter(aMonthAgo)
        }
    }

    enum class ChallengeState {
        NOT_STARTED,
        STARTED,
        FAILED,
        SUCCEEDED
    }
}

private fun Food.containsAnimalProducts(): Boolean =
        getCategoryType() == CategoryType.MEAT
                || getCategoryType() == CategoryType.DAIRY
                || getCategoryType() == CategoryType.FISH
