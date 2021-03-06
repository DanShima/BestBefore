package com.ichimaya.androidhackathon.challenges

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import android.content.Context
import android.content.SharedPreferences
import com.ichimaya.androidhackathon.R
import com.ichimaya.androidhackathon.food.FoodRepository
import com.ichimaya.androidhackathon.food.model.*
import com.ichimaya.androidhackathon.user.UserDetailsService
import com.ichimaya.androidhackathon.utils.toLocalDateTime
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

class ChallengeViewModel: ViewModel() {

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

    fun observeChallenges(context: Context): LiveData<Map<Challenge, ChallengeState>> {
        return Transformations
                .map(FoodRepository.getInstance(UserDetailsService().getUUID(context))
                .observeFoods()) { foods -> getChallengeMap(context.getSharedPreferences("challengePrefs", Context.MODE_PRIVATE), foods.values.flatten())}
    }

    private fun getChallengeMap(preferences: SharedPreferences, foods: List<Food>): Map<Challenge, ChallengeState> {
        val challengeMap = mutableMapOf<Challenge, ChallengeState>()
        for (i in icons.indices) {
            val long = preferences.getLong(titles[i], 0L)
            val startDate = if (long == 0L) null else long
            val challenge = Challenge(titles[i], icons[i], descriptions[i], startDate, challengeLength[i])
            challengeMap[challenge] = getChallengeState(foods, challenge)
        }
        return challengeMap
    }

    private fun getChallengeState(foods: List<Food>, challenge: Challenge): ChallengeState {
        challenge.startDate?.let {startDate ->
            val endDate = LocalDateTime.ofInstant(Instant.ofEpochMilli(startDate + (60 * 60 * 24 * 1000) * challenge.challengeLength), ZoneId.systemDefault())
            return if (endDate.isAfter(LocalDateTime.now())) {
                ChallengeState.STARTED
            } else {
                when (challenge.title) {
                    "Fruit Ninja" -> if (noSpoiledFruitForAWeek(foods)) ChallengeState.SUCCEEDED else ChallengeState.FAILED
                    "Vegan Certificate" -> if (noMeatOrDairyFor3Weeks(foods)) ChallengeState.SUCCEEDED else ChallengeState.FAILED
                    "Egglicious" -> if (finishEggsInThreeDays(foods)) ChallengeState.SUCCEEDED else ChallengeState.FAILED
                    "Master of Frugality" -> if (noSpoiledFoodForAMonth(foods)) ChallengeState.SUCCEEDED else ChallengeState.FAILED
                    "An apple a day, keep the doctor away" -> if (eatApplesForAMonth(foods)) ChallengeState.SUCCEEDED else ChallengeState.FAILED
                    "Left but not over" -> if (eatLeftoverForAWeek(foods)) ChallengeState.SUCCEEDED else ChallengeState.FAILED
                    else -> ChallengeState.FAILED // ¯\_(ツ)_/¯
                }
            }
        } ?: return ChallengeState.NOT_STARTED
    }

    private fun eatApplesForAMonth(foods: List<Food>): Boolean {
        val oneMonth = LocalDateTime.now().minusDays(30)
        return foods.none {
            it.getCategoryType() == CategoryType.FRUIT && it.isExpired() && it.expiryDate.toLocalDateTime().isAfter(oneMonth)
        }
    }

    private fun finishEggsInThreeDays(foods: List<Food>): Boolean {
        val threeDays = LocalDateTime.now().minusDays(3)
        return foods.none {
            it.getCategoryType() == CategoryType.DAIRY && it.isExpired() && it.expiryDate.toLocalDateTime().isAfter(threeDays)
        }
    }
    
    private fun eatLeftoverForAWeek(foods: List<Food>): Boolean {
        val oneWeekAgo = LocalDateTime.now().minusDays(7)
        return foods.none {
            it.getCategoryType() == CategoryType.MEAL && it.isExpired() && it.expiryDate.toLocalDateTime().isAfter(oneWeekAgo)
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
