package com.ichimaya.androidhackathon.detail

import android.arch.lifecycle.ViewModel
import android.util.Log
import com.ichimaya.androidhackathon.food.model.Food

class DetailViewModel: ViewModel() {
    fun testtest() : MutableList<Food> {
        val foodList = mutableListOf<Food>()
        foodList.add(Food("bla", "Broccoli", 575757, "Veggie", 3333))
        Log.d("TestBla", "DO SOMETHING")
        return foodList
    }
}