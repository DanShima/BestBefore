package com.ichimaya.androidhackathon.food

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.content.Context
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.ichimaya.androidhackathon.food.model.Food
import com.ichimaya.androidhackathon.food.model.isConsumed
import com.ichimaya.androidhackathon.notifications.NotificationHandler
import java.util.*


class FoodRepository(val uuid: String) {

    var foods: MutableLiveData<List<Food>> = MutableLiveData()

    fun observeFoods(categoryTitle: String): LiveData<List<Food>> {
        if (foods.value == null) {
            FirebaseDatabase.getInstance()
                    .getReference("foods")
                    .child(uuid)
                    .addValueEventListener(object : ValueEventListener {
                        override fun onDataChange(dataSnapshot: DataSnapshot) {
                            if (dataSnapshot.exists()) {
                                foods.postValue(dataSnapshot.children.mapNotNull {
                                    val food = dataSnapshot.child(it.key!!)
                                    Food(
                                            id = food.child("id").getValue<String>(String::class.java) ?: return,
                                            name = food.child("name").getValue<String>(String::class.java) ?: return,
                                            expiryDate = food.child("expiryDate").getValue<Long>(Long::class.java) ?: 0L,
                                            category = food.child("category").getValue<String>(String::class.java) ?: return,
                                            consumeDate = food.child("consumeDate").getValue<Long>(Long::class.java)
                                    )
                                }.filter { it.consumeDate == null && it.category == categoryTitle })
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

}