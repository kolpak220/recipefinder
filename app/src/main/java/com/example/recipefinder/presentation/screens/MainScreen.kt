package com.example.recipefinder.presentation.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.recipefinder.domain.model.Difficulty
import com.example.recipefinder.presentation.viewmodel.MainViewModel
import com.example.recipefinder.ui.PercentageCircle


fun productSuffix(count: Int): String {
    val lastDigit = count % 10
    val lastTwoDigits = count % 100
    return when {
        count == 0 -> "ов"
        lastTwoDigits in 11..14 -> "ов"
        lastDigit == 1 -> ""
        lastDigit in 2..4 -> "а"
        else -> "ов"
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    navController: NavController,
    viewModel: MainViewModel
) {
    var basketExpanded by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        Text(
            text = "ВАШИ ПРОДУКТЫ",
            style = MaterialTheme.typography.bodySmall.copy(
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            ),
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "Начните вводить по одному",
            style = MaterialTheme.typography.bodySmall.copy(
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            ),
        )
        Spacer(modifier = Modifier.height(6.dp))


        OutlinedTextField(
            shape = RoundedCornerShape(16.dp),
            value = viewModel.searchQuery,
            onValueChange = { viewModel.searchIngredients(it) },
            placeholder = { Text("Сыр...") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.secondaryContainer,
                cursorColor = MaterialTheme.colorScheme.primary,
                focusedContainerColor = MaterialTheme.colorScheme.surface,
                unfocusedContainerColor = MaterialTheme.colorScheme.surface
        ),
        )

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth().padding(top=6.dp)
        ) {
            items(viewModel.searchResults) { ingredient ->
                AssistChip(
                    shape = CircleShape,
                    border = null,
                    onClick = { viewModel.addIngredient(ingredient) },
                    label = { Text(ingredient.name, fontSize = 14.sp) },
                    colors = AssistChipDefaults.assistChipColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        labelColor = Color.White,
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(6.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.Transparent
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
        ) {
            Column() {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.clickable { basketExpanded = !basketExpanded }
                    ) {
                        Text(
                            text = "${viewModel.basket.size} продукт${productSuffix(viewModel.basket.size)}",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp
                            )
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Icon(
                            imageVector = if (basketExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                            contentDescription = null
                        )
                    }

                    Spacer(modifier = Modifier.weight(1f))


                    Text(
                        text = "Очистить",
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontWeight = FontWeight.Normal,
                        fontSize = 12.sp,
                        modifier = Modifier
                            .clickable { viewModel.clearBasket() }
                            .padding(4.dp)
                    )
                }

                AnimatedVisibility(basketExpanded) {
                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(1.dp),
                                modifier = Modifier
                            .padding(top = 4.dp)
                            .fillMaxWidth()
                    ) {
                        viewModel.basket.forEach { ingredient ->
                            AssistChip(
                                onClick = { viewModel.removeIngredient(ingredient) },
                                border = null,
                                label = { Text(ingredient.name, fontSize = 12.sp) },
                                trailingIcon = {
                                    Icon(
                                        imageVector = Icons.Default.Close,
                                        contentDescription = "Удалить",
                                        tint = Color.White,
                                        modifier = Modifier.size(16.dp).padding(end = 2.dp)
                                    )
                                },
                                colors = AssistChipDefaults.assistChipColors(
                                    containerColor = MaterialTheme.colorScheme.primary,
                                    labelColor = Color.White,
                                ),
                                shape = CircleShape
                            )
                        }
                    }
                }
        }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(MaterialTheme.colorScheme.onSurfaceVariant.copy( 0.15f))
            )

        Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = buildAnnotatedString {
                    append("Найдено рецептов: ")
                    withStyle(style = SpanStyle(
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )) {
                        append("${viewModel.recommendedRecipes.size}")
                    }
                },
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(6.dp))


                Row(modifier = Modifier.padding(16.dp)) {

                }


            if (viewModel.recommendedRecipes.isEmpty() && viewModel.basket.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Text(
                            text = "Начните с продуктов",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Добавьте ингредиенты из вашей кухни, и мы подберём рецепты",
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = 2.dp, end = 2.dp)
                ) {
                    items(viewModel.recommendedRecipes) { recipe ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { navController.navigate("recipe_detail/${recipe.id}") },
                            shape = MaterialTheme.shapes.medium,
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                        ) {
                            Box(modifier = Modifier.fillMaxWidth()) {

                                Column(modifier = Modifier
                                    .padding(16.dp)
                                    .fillMaxWidth()
                                ) {
                                    Text(
                                        text = recipe.name,
                                        style = MaterialTheme.typography.titleLarge.copy(
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 14.sp
                                        ),
                                        color = Color.Black,
                                        modifier = Modifier.fillMaxWidth(0.66f)
                                    )

                                    Spacer(modifier = Modifier.height(4.dp))

                                    Row(
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = "${recipe.minutes} мин",
                                            fontSize = 12.sp,
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
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = difficultyColor
                                        )
                                    }

                                    Spacer(modifier = Modifier.height(8.dp))

                                    val missingIngredientsNames = recipe.ingredients
                                        .filter { recipeId -> viewModel.basket.none { it.id == recipeId } }
                                        .map { id -> viewModel.getIngredientNameById(id) }

                                    val missingText = if (missingIngredientsNames.isNotEmpty()) {
                                        "Не хватает: ${missingIngredientsNames.joinToString(", ")}"
                                    } else {
                                        ""
                                    }

                                    if (missingText.isNotEmpty()) {
                                        Text(
                                            text = missingText,
                                            style = MaterialTheme.typography.bodyMedium.copy(
                                                fontSize = 14.sp,
                                                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
                                            )
                                        )
                                    }

                                    Spacer(modifier = Modifier.height(8.dp))

                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.End,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = "Подробнее",
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.Normal,
                                            color = MaterialTheme.colorScheme.primary
                                        )
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Icon(
                                            imageVector = Icons.Default.ArrowForward,
                                            contentDescription = "Подробнее",
                                            tint = MaterialTheme.colorScheme.primary,
                                            modifier = Modifier.size(16.dp)
                                        )
                                    }
                                }

                                // Процентное кольцо
                                val percent = if (recipe.ingredients.isNotEmpty()) {
                                    val matchedCount = viewModel.basket.count { it.id in recipe.ingredients }
                                    (matchedCount.toFloat() / recipe.ingredients.size.toFloat()) * 100f
                                } else 0f

                                PercentageCircle(
                                    percent = percent,
                                    size = 40.dp,
                                    strokeWidth = 4.dp,
                                    modifier = Modifier
                                        .align(Alignment.TopEnd)
                                        .padding(end = 16.dp, top = 20.dp)
                                )
                            }
                        }
                    }
                }
            }


    }
}}