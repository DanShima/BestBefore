package com.ichimaya.androidhackathon.detail

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import com.ichimaya.androidhackathon.food.FoodRepository
import com.ichimaya.androidhackathon.food.model.Food

class DetailViewModel: ViewModel() {
    fun observeFoods(): LiveData<List<Food>> {
        return FoodRepository().observeFoods() // TODO observe per category!
    }
}