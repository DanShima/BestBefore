package com.ichimaya.androidhackathon.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import android.content.Context
import com.ichimaya.androidhackathon.food.FoodRepository
import com.ichimaya.androidhackathon.food.model.Food
import com.ichimaya.androidhackathon.user.UserDetailsService

class DetailViewModel: ViewModel() {
    fun observeFoods(context: Context, categoryTitle: String): LiveData<List<Food>> {
        return Transformations
                .map(FoodRepository.getInstance(UserDetailsService().getUUID(context)).observeFoods()) { map -> map[categoryTitle] }
    }

    fun markAsConsumed(context: Context, food: Food) {
        FoodRepository.getInstance(UserDetailsService().getUUID(context)).markFoodAsConsumed(context, food)
    }
}