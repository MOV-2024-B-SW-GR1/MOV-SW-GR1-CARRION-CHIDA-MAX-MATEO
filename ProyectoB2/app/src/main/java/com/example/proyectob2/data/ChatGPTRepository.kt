package com.example.proyectob2.data

import com.example.proyectob2.model.ChatRequest
import com.example.proyectob2.model.Message
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ChatGPTRepository {

    private const val API_KEY = "API_KEY"

    private const val BASE_URL = "https://api.openai.com/"


    private val okHttpClient = OkHttpClient.Builder()
        .build()


    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val service = retrofit.create(ChatGPTService::class.java)

    /**
     * Envia una pregunta a la API de ChatGPT sobre trámites y procedimientos de la guía del estudiante de la EPN 2024-B.
     * - Si hay contenido extraído del PDF, se incluye en un mensaje “system” para mejorar la precisión.
     * - Siempre que se pregunte sobre un trámite o formulario, se proporciona información relevante y el enlace correspondiente.
     */
    suspend fun askQuestion(question: String, pdfContext: String?): String {
        val messages = mutableListOf<Message>()

        if (!pdfContext.isNullOrEmpty()) {
            messages.add(
                Message(
                    role = "system",
                    content = """
                    Eres un asistente especializado en la Guía del Estudiante de la EPN 2024-B. 
                    Usa la siguiente información extraída del PDF para responder con precisión a consultas sobre trámites, matrículas, retiros, pagos y otros procedimientos académicos. 
                    Si la pregunta está relacionada con un trámite o formulario, proporciona el enlace oficial del documento si está disponible en el PDF. Por favor cuando envies una URL o links hazlo sin parentesis 
                    
                    Información extraída del PDF:
                    $pdfContext
                """.trimIndent()
                )
            )
        }

        messages.add(Message(role = "user", content = question))

        val request = ChatRequest(model = "gpt-4o-mini", messages = messages)
        // Realiza la solicitud
        val response = service.getChatCompletion(request, "Bearer $API_KEY")

        // Regresa la respuesta procesada
        return response.choices.firstOrNull()?.message?.content ?: "No se encontró información relevante en la guía del estudiante."
    }
}
