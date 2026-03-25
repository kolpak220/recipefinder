package com.example.recipefinder.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.recipefinder.data.database.entities.AiCacheEntity

@Dao
interface AiCacheDao {
    @Query("SELECT * FROM ai_cache WHERE cacheKey = :key")
    suspend fun getCachedEvaluation(key: String): AiCacheEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(cache: AiCacheEntity)
}