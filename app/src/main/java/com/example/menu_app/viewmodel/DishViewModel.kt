package com.example.menu_app.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.menu_app.model.CartItem
import com.example.menu_app.model.Dish

class DishViewModel : ViewModel() {
    private val _dishList = mutableStateListOf(
        Dish(1, "Classic Cheeseburger", "A juicy beef patty topped with melted cheddar cheese, crisp lettuce, fresh tomato, onions, pickles, and a dollop of special sauce, all sandwiched in a toasted sesame seed bun.", 8.99),
        Dish(2, "Crispy Chicken Sandwich", "Golden-fried chicken breast served with lettuce, creamy mayo, and tangy pickles on a soft brioche bun.", 6.99),
        Dish(3, "Spicy Buffalo Wings", "Crispy chicken wings tossed in a zesty buffalo sauce, served with a side of cool ranch or blue cheese dressing.", 10.99),
        Dish(4, "Loaded Fries", "A mountain of crispy fries smothered in gooey melted cheese, crispy bacon bits, and a drizzle of tangy ranch dressing.", 7.99),
        Dish(5, "Veggie Delight Wrap", "A healthy mix of grilled vegetables, hummus, fresh greens, and a touch of lemon-tahini dressing, all wrapped in a soft tortilla.", 14.99),
        Dish(6, "Double Decker Bacon Burger", "Two juicy beef patties stacked with layers of crispy bacon, cheddar cheese, grilled onions, lettuce, and BBQ sauce.", 9.99),
        Dish(7, "Classic Hot Dog", "A perfectly grilled hot dog in a toasted bun, topped with ketchup, mustard, onions, and relish.", 5.99),
        Dish(8, "BBQ Pulled Pork Sandwich", "Tender pulled pork slow-cooked in smoky BBQ sauce, served on a toasted bun with a side of coleslaw.", 11.99),
        Dish(9, "Chicken Caesar Salad", "Crisp romaine lettuce topped with grilled chicken, crunchy croutons, shaved Parmesan, and a creamy Caesar dressing.", 10.99),
        Dish(10, "Choco Blast Milkshake", "A rich and creamy milkshake blended with chocolate ice cream, topped with whipped cream, chocolate syrup, and sprinkles.", 10.99),
        Dish(11, "Kelle Paça", "Lezzetiyle damaklarda iz bırakan, kuzu kelle ve paça etlerinden hazırlanan bu geleneksel çorba, sarımsak ve sirke ile tatlandırılarak servis edilir. Sağlık dolu ve doyurucu bir başlangıç arayanlar için birebir!", 10.99)

    )
    private val _favoriteDishes = mutableStateListOf<Dish>()
    private val _cart = mutableStateListOf<CartItem>()

    val dishList: List<Dish> get() = _dishList
    val favoriteDishes: List<Dish> get() = _favoriteDishes
    val cart: List<CartItem> get() = _cart

    fun isFavorite(dish: Dish): Boolean = _favoriteDishes.contains(dish)

    fun toggleFavorite(dish: Dish) {
        if (isFavorite(dish)) {
            _favoriteDishes.remove(dish)
        } else {
            _favoriteDishes.add(dish)
        }
    }

    fun removeFavorite(dish: Dish) {
        _favoriteDishes.remove(dish)
    }

    // Cart Management
    fun addToCart(dish: Dish) {
        val existingItem = _cart.find { it.dish.id == dish.id }
        if (existingItem != null) {
            existingItem.quantity++
        } else {
            _cart.add(CartItem(dish.id, dish, 1))
        }
    }

    fun removeFromCart(cartItem: CartItem) {
            _cart.remove(cartItem)
    }

    fun clearCart() {
        _cart.clear()
    }

    fun getCartTotal(): Double {
        return _cart.sumOf { it.dish.price * it.quantity }
    }
}
