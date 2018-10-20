package com.ichimaya.androidhackathon.badges

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.ichimaya.androidhackathon.badges.model.Badge
import com.ichimaya.androidhackathon.badges.model.fiveDayStreak
import com.ichimaya.androidhackathon.badges.model.fiveFoodsConsumed
import com.ichimaya.androidhackathon.food.model.Food
import com.ichimaya.androidhackathon.food.model.isConsumed
import com.ichimaya.androidhackathon.food.model.isExpired
import com.ichimaya.androidhackathon.food.model.toFood
import org.threeten.bp.Instant
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneId
import java.util.*


class BadgeRepository(val uuid: String) {

    var badges: MutableLiveData<List<Badge>> = MutableLiveData()

    fun updateBadges() {
        FirebaseDatabase.getInstance()
                .getReference("foods")
                .child(uuid)
                .addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        if (dataSnapshot.exists()) {
                            val newFoods = dataSnapshot.children.mapNotNull {
                                dataSnapshot.child(it.key!!).toFood()
                            }
                            val fiveDaysAgo = LocalDateTime.now()
                                    .minusDays(5)
                            if (!foodsExpiredPastFiveDays(newFoods, fiveDaysAgo) && foodWasAddedFiveDaysAgo(newFoods, fiveDaysAgo)) {
                                registerAchievement(fiveDayStreak(Calendar.getInstance().timeInMillis))
                            }
                            if (consumedFiveFoods(newFoods)) {
                                registerAchievement(fiveFoodsConsumed(Calendar.getInstance().timeInMillis))
                            }
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        // TODO
                    }
                })
    }

    fun observeBadges(): LiveData<List<Badge>> {
        if (badges.value == null) {
            FirebaseDatabase.getInstance()
                    .getReference("badges")
                    .child(uuid)
                    .addValueEventListener(object : ValueEventListener {
                        override fun onDataChange(dataSnapshot: DataSnapshot) {
                            if (dataSnapshot.exists()) {
                                badges.postValue(dataSnapshot.children.mapNotNull {
                                    val food = dataSnapshot.child(it.key!!)
                                    Badge(
                                            id = food.child("id").getValue<String>(String::class.java) ?: return,
                                            name = food.child("name").getValue<String>(String::class.java) ?: return,
                                            achieveDate = food.child("achieveDate").getValue<Long>(Long::class.java) ?: 0L,
                                            description = food.child("description").getValue<String>(String::class.java) ?: return
                                    )
                                })
                            }
                        }

                        override fun onCancelled(error: DatabaseError) {
                            // TODO
                        }
                    })
        }
        return badges
    }

    fun registerAchievement(badge: Badge) {
        FirebaseDatabase.getInstance()
                .getReference("badges")
                .child(uuid)
                .updateChildren(mapOf(badge.id to badge))
    }

    private fun consumedFiveFoods(foods: List<Food>): Boolean {
        return foods.filter {
            it.isConsumed() && !it.isExpired()
        }.size >= 5
    }

    private fun foodsExpiredPastFiveDays(foods: List<Food>, fiveDaysAgo: LocalDateTime): Boolean {
        return foods.any {
            val expiry = LocalDateTime.ofInstant(Instant.ofEpochMilli(it.expiryDate), ZoneId.systemDefault())
            !it.isConsumed() && it.isExpired() && expiry.isAfter(fiveDaysAgo)
        }
    }

    private fun foodWasAddedFiveDaysAgo(foods: List<Food>, fiveDaysAgo: LocalDateTime): Boolean {
        return foods.any {
            val addDate = LocalDateTime.ofInstant(Instant.ofEpochMilli(it.addDate), ZoneId.systemDefault())
            addDate.dayOfYear == fiveDaysAgo.dayOfYear
        }
    }

}