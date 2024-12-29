package com.example.menu_app.view

import android.annotation.SuppressLint
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.menu_app.model.Dish
import com.example.menu_app.viewmodel.DishViewModel
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.sp

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MenuScreen(dishViewModel: DishViewModel,
               onNavigateToFavorites: () -> Unit,
               onNavigateToCart: () -> Unit) {
    Scaffold(
        backgroundColor = Color.LightGray,
        topBar = {
            TopAppBar(
                backgroundColor = Color(255,100,80),
                title = { Text("Menu" , color = Color.White) },
                actions = {
                    Row {
                        IconButton(onClick = onNavigateToCart) {
                            Icon(
                                imageVector = Icons.Default.ShoppingCart,
                                contentDescription = "Cart",
                                tint = Color.White
                            )
                        }
                        IconButton(onClick = onNavigateToFavorites) {
                            Icon(
                                imageVector = Icons.Default.Favorite,
                                contentDescription = "Favorites",
                                tint = Color.White
                            )
                        }
                    }
                },
            )
        }
    ) {
        LazyColumn(modifier = Modifier.padding(16.dp)) {
            items(dishViewModel.dishList) { dish ->
                DishCard(
                    dish = dish,
                    isFavorite = dishViewModel.isFavorite(dish),
                    onFavoriteClick = { dishViewModel.toggleFavorite(dish) },
                    onAddToCartClick = { dishViewModel.addToCart(dish) }
                )
            }
        }
    }
}


@Composable
fun DishCard(dish: Dish, isFavorite: Boolean, onFavoriteClick: () -> Unit, onAddToCartClick: () -> Unit) {
    var expanded by rememberSaveable { mutableStateOf(false) }
    val extraPadding by animateDpAsState(
        if (expanded) 16.dp else 0.dp,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioHighBouncy,
            stiffness = Spring.StiffnessLow
        ), label = ""
    )

    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val sizeScale by animateFloatAsState(if (isPressed) 0.8f else 1f, label = "")

    Card(
        shape = RoundedCornerShape(8.dp),
        elevation = 4.dp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Column{
            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(modifier = Modifier
                    .width(130.dp)){
                    Text(text = dish.name, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.h6)
                    Text(text = "Price: $${dish.price}", style = MaterialTheme.typography.body1)
                }
                Row {
                    IconButton(onClick = onFavoriteClick) {
                        Icon(
                            imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                            contentDescription = null,
                            tint = if (isFavorite) Color.Red else Color.Gray
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(
                        colors = ButtonDefaults.buttonColors(Color(255,100,80)),
                        onClick = onAddToCartClick,
                        modifier = Modifier
                            .wrapContentSize()
                            .graphicsLayer(
                                scaleX = sizeScale,
                                scaleY = sizeScale
                            ),
                        interactionSource = interactionSource)
                    {
                        Text("Add to Cart", fontSize = 15.sp, color = Color.White)
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    IconButton(onClick = {expanded = !expanded}) {
                        Icon(
                            imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                            contentDescription = null,
                            tint = Color.Gray
                        )
                    }
                }
            }
            Row(
                modifier = Modifier
                    .padding(16.dp,0.dp)
                    .fillMaxWidth(),
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(bottom = extraPadding.coerceAtLeast(0.dp))
                )
                {
                    if (expanded) {
                        Text(
                            text = dish.description,
                            style = MaterialTheme.typography.body2,
                            color = Color.Gray
                        )
                    }
                }
            }

        }


    }
}



