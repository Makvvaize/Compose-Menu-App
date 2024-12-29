package com.example.menu_app.view

import android.annotation.SuppressLint
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.menu_app.model.Dish
import com.example.menu_app.viewmodel.DishViewModel
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun FavoritesScreen(dishViewModel: DishViewModel,
                    navController: NavController
) {
    Scaffold(
        backgroundColor = Color.LightGray,
        topBar = {
            TopAppBar(
                backgroundColor = Color(255,100,80),
                title = { Text("Favorites" , color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back" , tint = Color.White)
                    }
                }
            )
        }
    ) {
        LazyColumn(modifier = Modifier.padding(16.dp)) {
            items(dishViewModel.favoriteDishes , key = { it.id }) { dish ->
                Row(Modifier.animateItem(
                    fadeOutSpec = tween(durationMillis = 500),
                    placementSpec = spring(stiffness = Spring.StiffnessLow, dampingRatio = Spring.DampingRatioMediumBouncy))
                ){
                    FavoriteDishCard(
                        dish = dish,
                        onRemoveClick = {dishViewModel.removeFavorite(dish)},
                        onAddToCartClick = {dishViewModel.addToCart(dish)}
                    )
                }

            }
        }
    }
}



@Composable
fun FavoriteDishCard(dish: Dish, onRemoveClick: () -> Unit , onAddToCartClick: () -> Unit) {
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
                Row{
                    IconButton(onClick = onRemoveClick) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = null,
                            tint = Color.Gray
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
