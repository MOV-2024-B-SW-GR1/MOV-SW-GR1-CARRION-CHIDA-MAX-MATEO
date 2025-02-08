package com.example.proyectob2.data

import com.example.proyectob2.model.ChatRequest
import com.example.proyectob2.model.ChatResponse
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface ChatGPTService {
    @POST("v1/chat/completions")
    suspend fun getChatCompletion(
        @Body request: ChatRequest,
        @Header("Authorization") auth: String
    ): ChatResponse
}
