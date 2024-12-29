package com.example.menu_app.model

data class Dish(
    val id: Int,
    val name: String,
    val description: String,
    val price: Double,
    var isFavorite: Boolean = false
)
