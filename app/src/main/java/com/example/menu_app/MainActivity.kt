package com.example.menu_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.menu_app.view.FavoritesScreen
import com.example.menu_app.view.MenuScreen
import com.example.menu_app.ui.theme.Menu_appTheme
import com.example.menu_app.view.CartScreen
import com.example.menu_app.viewmodel.DishViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Menu_appTheme {
                val navController: NavHostController = rememberNavController()
                val dishViewModel: DishViewModel = viewModel()

                NavHost(
                    navController = navController,
                    startDestination = "menu_screen"
                ) {
                    composable("menu_screen") {
                        MenuScreen(
                            dishViewModel = dishViewModel,
                            onNavigateToFavorites = { navController.navigate("favorites_screen") },
                            onNavigateToCart = {navController.navigate("cart_screen")}
                        )
                    }
                    composable("favorites_screen") {
                        FavoritesScreen(dishViewModel = dishViewModel,
                            navController = navController)
                    }
                    composable("cart_screen") {
                        CartScreen(
                            dishViewModel = dishViewModel,
                            navController = navController
                        )
                    }
                }
            }
        }
    }
}
