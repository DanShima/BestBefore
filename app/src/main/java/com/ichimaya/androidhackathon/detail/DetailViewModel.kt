package com.ichimaya.androidhackathon.detail

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import android.content.Context
import com.ichimaya.androidhackathon.food.FoodRepository
import com.ichimaya.androidhackathon.food.model.Food
import com.ichimaya.androidhackathon.user.UserDetailsService

class DetailViewModel: ViewModel() {
    fun observeFoods(context: Context, categoryTitle: String): LiveData<List<Food>> {
        return FoodRepository(UserDetailsService().getUUID(context)).observeFoods(categoryTitle)
    }

    fun markAsConsumed(context: Context, food: Food) {
        FoodRepository(UserDetailsService().getUUID(context)).markFoodAsConsumed(context, food)
    }
}