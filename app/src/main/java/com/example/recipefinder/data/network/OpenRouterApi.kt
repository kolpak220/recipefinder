package com.example.recipefinder.data.network

import com.example.recipefinder.data.network.model.AiRequest
import com.example.recipefinder.data.network.model.AiResponse
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface OpenRouterApi {
    @POST("chat/completions")
    suspend fun getCompletion(
        @Header("Authorization") token: String,
        @Body request: AiRequest
    ): AiResponse
}