package com.ichimaya.androidhackathon.food

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.google.firebase.database.*
import com.ichimaya.androidhackathon.food.model.Food


class FoodRepository {

    var foods: MutableLiveData<DataSnapshot> = MutableLiveData()

    fun observeFoods(): LiveData<DataSnapshot> {
        if (foods.value == null) {
            FirebaseDatabase.getInstance()
                    .getReference("foods")
                    .addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onDataChange(dataSnapshot: DataSnapshot) {
                            if (dataSnapshot.exists()) {
                                foods.postValue(dataSnapshot)
                            }
                        }

                        override fun onCancelled(error: DatabaseError) {
                            // TODO
                        }
                    })
        }
        return foods
    }

}