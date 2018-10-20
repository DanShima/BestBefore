package com.ichimaya.androidhackathon.food

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.google.firebase.database.*
import com.ichimaya.androidhackathon.food.model.Food
import java.util.*


class FoodRepository {

    var foods: MutableLiveData<List<Food>> = MutableLiveData()

    fun observeFoods(): LiveData<List<Food>> {
        if (foods.value == null) {
            FirebaseDatabase.getInstance()
                    .getReference("foods")
                    .addListenerForSingleValueEvent(object : ValueEventListener {
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
                                })
                            }
                        }

                        override fun onCancelled(error: DatabaseError) {
                            // TODO
                        }
                    })
        }
        return foods
    }

    fun registerFood(food: Food) {
        FirebaseDatabase.getInstance()
                .getReference("foods")
                .updateChildren(mapOf(food.id to food))
    }

    fun markFoodAsConsumed(food: Food) {
        registerFood(food.copy(consumeDate = Calendar.getInstance().timeInMillis)) // set consumeDate to now
    }

    fun deleteFood(foodId: String) {
        FirebaseDatabase.getInstance()
                .getReference("foods")
                .child(foodId)
                .removeValue()
    }

}