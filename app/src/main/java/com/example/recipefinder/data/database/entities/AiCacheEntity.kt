package com.example.recipefinder.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ai_cache")
data class AiCacheEntity(
    @PrimaryKey val cacheKey: String,
    val score: Int
)