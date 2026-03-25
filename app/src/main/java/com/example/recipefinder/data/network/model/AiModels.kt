package com.example.recipefinder.data.network.model

import kotlinx.serialization.Serializable

@Serializable
data class AiRequest(
    val model: String,
    val messages: List<AiMessage>
)

@Serializable
data class AiMessage(
    val role: String,
    val content: String
)

@Serializable
data class AiResponse(
    val choices: List<AiChoice>
)

@Serializable
data class AiChoice(
    val message: AiMessage
)