package com.example.menu_app.model

data class CartItem(
    val id: Int,
    val dish: Dish,
    var quantity: Int
)