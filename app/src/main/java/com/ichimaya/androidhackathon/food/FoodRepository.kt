package com.ichimaya.androidhackathon.food

import android.arch.lifecycle.MutableLiveData
import android.content.Context
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.ichimaya.androidhackathon.food.model.Food
import com.ichimaya.androidhackathon.food.model.isConsumed
import com.ichimaya.androidhackathon.food.model.toFood
import com.ichimaya.androidhackathon.notifications.NotificationHandler
import java.util.*


class FoodRepository(val uuid: String) {

    var foods: MutableLiveData<Map<String, List<Food>>> = MutableLiveData()

    fun observeFoods(): MutableLiveData<Map<String, List<Food>>> {
        if (foods.value == null) {
            FirebaseDatabase.getInstance()
                    .getReference("foods")
                    .child(uuid)
                    .addValueEventListener(object : ValueEventListener {
                        override fun onDataChange(dataSnapshot: DataSnapshot) {
                            if (dataSnapshot.exists()) {
                                val newFoods = dataSnapshot.children.mapNotNull {
                                    dataSnapshot.child(it.key!!).toFood()
                                }
                                foods.postValue(newFoods.asSequence().filter { it.consumeDate == null }.groupBy { it.category })
                            }
                        }

                        override fun onCancelled(error: DatabaseError) {
                            // TODO
                        }
                    })
        }
        return foods
    }

    fun registerFood(context: Context, food: Food) {
        if (food.isConsumed()) {
            NotificationHandler().cancelNotification(context, food)
        } else {
            NotificationHandler().scheduleNotification(context, food)
        }
        FirebaseDatabase.getInstance()
                .getReference("foods")
                .child(uuid)
                .updateChildren(mapOf(food.id to food))
    }

    fun markFoodAsConsumed(context: Context, food: Food) {
        registerFood(context, food.copy(consumeDate = Calendar.getInstance().timeInMillis)) // set consumeDate to now
    }

    fun deleteFood(context: Context, food: Food) {
        NotificationHandler().cancelNotification(context, food)
        FirebaseDatabase.getInstance()
                .getReference("foods")
                .child(food.id)
                .removeValue()
    }


    companion object {
        var repository: FoodRepository? = null

        fun getInstance(uuid: String): FoodRepository {
            val repo = repository
            return if (repo == null) {
                repository = FoodRepository(uuid)
                repository!!
            } else {
                repo
            }
        }
    }
}