package com.ichimaya.androidhackathon.detail

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.ichimaya.androidhackathon.food.FoodRepository
import com.ichimaya.androidhackathon.food.model.Food

class DetailViewModel: ViewModel() {
    fun observeFoods(categoryTitle: String): LiveData<List<Food>> {
        return FoodRepository().observeFoods(categoryTitle)
    }
}