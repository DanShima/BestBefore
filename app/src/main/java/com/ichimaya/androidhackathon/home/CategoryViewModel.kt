package com.ichimaya.androidhackathon.home

import android.arch.lifecycle.ViewModel
import android.util.Log
import com.ichimaya.androidhackathon.R
import com.ichimaya.androidhackathon.food.model.Category

class CategoryViewModel: ViewModel() {

    private var categoryList = mutableListOf<Category>()

    private val categoryIcons = intArrayOf(
        R.drawable.ic_fruit,
        R.drawable.ic_veggie,
        R.drawable.ic_seafood,
        R.drawable.ic_meat,
        R.drawable.ic_milk,
        R.drawable.ic_bread,
        R.drawable.ic_cheese,
        R.drawable.ic_meal,
        R.drawable.ic_unknown_food)

    private val categoryTitles = arrayOf(
        "Fruit",
        "Vegetable",
        "Fish",
        "Meat",
        "Drink",
        "Bread",
        "Dairy",
        "Meal",
        "Unknown")

    fun getCategories(): Array<String> {
        return categoryTitles
    }

    fun setupCategoryList(): MutableList<Category> {
        for (i in categoryIcons.indices) {
            addOptionsToList(categoryIcons[i], categoryTitles[i])
        }
        return categoryList
    }

    private fun addOptionsToList(icon: Int, title: String):
        MutableList<Category> {
        val option = Category(icon, title)
        categoryList.add(option)
        Log.d("List", "$categoryList")
        return categoryList
    }
}