package com.example.recipefinder.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.recipefinder.domain.model.Difficulty
import com.example.recipefinder.presentation.viewmodel.MainViewModel

@Composable
fun RecipeDetailScreen(
    navController: NavController,
    viewModel: MainViewModel,
    recipeId: Int
) {
    val recipe = viewModel.recommendedRecipes.find { it.id == recipeId }

    if (recipe == null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("Рецепт не найден", style = MaterialTheme.typography.bodyLarge)
        }
        return
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = recipe.name,
                    style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                    modifier = Modifier.weight(1f)
                )

                IconButton(
                    onClick = { navController.popBackStack() }
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Закрыть",
                        tint = Color.Black
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))


            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${recipe.minutes} мин",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
                )
                Spacer(modifier = Modifier.width(8.dp))

                val (difficultyText, difficultyColor) = when(recipe.difficulty) {
                    Difficulty.EASY -> "Легко" to Color(0xFF4CAF50)
                    Difficulty.MEDIUM -> "Средне" to Color(0xFFFFC107)
                    Difficulty.HARD -> "Сложно" to Color(0xFFF44336)
                }

                Text(
                    text = difficultyText,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = difficultyColor
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "ИНГРЕДИЕНТЫ",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Normal),
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )

            Spacer(modifier = Modifier.height(8.dp))

        }

        items(recipe.ingredients) { ingredientId ->
            val ingredientName = viewModel.getIngredientNameById(ingredientId)
            val isInBasket = viewModel.basket.any { it.id == ingredientId }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 1.dp),
                colors = CardDefaults.cardColors(containerColor = Color.Transparent)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(6.dp)
                ) {
                    // Иконка перед ингредиентом
                    Box(
                        modifier = Modifier
                            .size(20.dp)
                            .background(
                                color = if (isInBasket) Color(0x2032CD32) else Color(0x20FF0000),
                                shape = CircleShape
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = if (isInBasket) Icons.Default.Check else Icons.Default.Close,
                            contentDescription = null,
                            tint = if (isInBasket) Color.Green else Color.Red,
                            modifier = Modifier.size(12.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        text = ingredientName,
                        fontSize = 14.sp,
                        color = if (isInBasket) Color.Black else MaterialTheme.colorScheme.onSurfaceVariant,
                        textDecoration = if (isInBasket) null else TextDecoration.LineThrough
                    )
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "ПРИГОТОВЛЕНИЕ",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Normal),
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            Spacer(modifier = Modifier.height(8.dp))
        }

        itemsIndexed(recipe.steps) { index, step ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 2.dp),
                colors = CardDefaults.cardColors(containerColor = Color.Transparent)

            ) {
                Row(
                    verticalAlignment = Alignment.Top,
                    modifier = Modifier.padding(6.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .background(
                                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.20f),
                                shape = CircleShape
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "${index + 1}",
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp,
                            color = Color.Black,
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))

                    Text(text = step,
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Normal),
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                }

            }
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}