package com.ichimaya.androidhackathon.food.model

data class Category(
    val icon: Int,
    val title: String
)

enum class CategoryType(val title: String) {
    MEAT("Meat"),
    DAIRY("Dairy"),
    FISH("Fish"),
    BREAD("Bread"),
    FRUIT("Fruit"),
    VEGETABLES("Vegetable"),
    DRINK("Drink"),
    MEAL("Meal"),
    UNKNOWN("Unknown");
}

fun categoryTypeFromTitle(title: String): CategoryType {
    return when (title) {
        CategoryType.MEAT.title -> CategoryType.MEAT
        CategoryType.DAIRY.title -> CategoryType.DAIRY
        CategoryType.FISH.title -> CategoryType.FISH
        CategoryType.BREAD.title -> CategoryType.BREAD
        CategoryType.FRUIT.title -> CategoryType.FRUIT
        CategoryType.VEGETABLES.title -> CategoryType.VEGETABLES
        CategoryType.DRINK.title -> CategoryType.DRINK
        CategoryType.MEAL.title -> CategoryType.MEAL
        CategoryType.UNKNOWN.title -> CategoryType.UNKNOWN
        else -> CategoryType.UNKNOWN
    }
}