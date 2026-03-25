package com.example.recipefinder.data.database.entities

import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    tableName = "ingredient_substitutions",
    primaryKeys = ["baseId", "substituteId"],
    foreignKeys = [
        ForeignKey(
            entity = IngredientEntity::class,
            parentColumns = ["id"],
            childColumns = ["baseId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = IngredientEntity::class,
            parentColumns = ["id"],
            childColumns = ["substituteId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class IngredientSubstitutionEntity(
    val baseId: Int,
    val substituteId: Int
)