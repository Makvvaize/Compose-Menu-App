package com.example.menu_app.view

import android.annotation.SuppressLint
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.menu_app.model.CartItem
import com.example.menu_app.viewmodel.DishViewModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun CartScreen(
    dishViewModel: DishViewModel,
    navController: NavController
) {
    Scaffold(
        backgroundColor = Color.LightGray,
        topBar = {
            TopAppBar(
                backgroundColor = Color(255,100,80),
                title = { Text("Cart" , color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back" , tint = Color.White)
                    }
                }
            )
        }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            LazyColumn(modifier = Modifier.weight(1f)) {
                items(dishViewModel.cart , key ={it.id}) { cartItem ->
                    Row(Modifier.animateItem(
                        fadeOutSpec = tween(durationMillis = 500)
                    )){
                        CartItemRow(
                            cartItem = cartItem,
                            onRemoveClick = {dishViewModel.removeFromCart(cartItem)}
                        )
                    }

                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Total: $${dishViewModel.getCartTotal()}",
                style = MaterialTheme.typography.h6,
                modifier = Modifier.align(Alignment.End)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                colors = ButtonDefaults.buttonColors(Color(150,255,200)),
                onClick = {
                    dishViewModel.clearCart()
                    navController.popBackStack()
                },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text("Place Order")
            }
        }
    }
}

@Composable
fun CartItemRow(cartItem: CartItem, onRemoveClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "${cartItem.quantity} x ${cartItem.dish.name}",
            style = MaterialTheme.typography.body1
        )
        Button(
            colors = ButtonDefaults.buttonColors(Color(255,100,80)),
            onClick = onRemoveClick
        ) {
            Text("Remove")
        }
    }
}
