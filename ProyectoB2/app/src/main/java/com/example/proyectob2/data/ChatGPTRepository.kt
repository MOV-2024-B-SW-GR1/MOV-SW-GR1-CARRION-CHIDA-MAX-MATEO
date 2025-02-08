package com.example.proyectob2.data

import com.example.proyectob2.model.ChatRequest
import com.example.proyectob2.model.Message
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ChatGPTRepository {
    // Reemplaza "YOUR_OPENAI_API_KEY" por tu API key real de OpenAI
     // Asegúrate de que el BASE_URL contenga "v1/" o ajusta el endpoint en la interfaz
    private const val BASE_URL = "https://api.openai.com/"

    // Crea el interceptor para loguear el body de request y response
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    // Configura el OkHttpClient con el interceptor
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    // Crea el Retrofit usando el OkHttpClient configurado
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val service = retrofit.create(ChatGPTService::class.java)

    /**
     * Envía una pregunta a la API de ChatGPT.
     * Si se proporciona texto extraído del PDF, se incluye en un mensaje “system”.
     */
    suspend fun askQuestion(question: String, pdfContext: String?): String {
        val messages = mutableListOf<Message>()
        if (!pdfContext.isNullOrEmpty()) {
            messages.add(
                Message(
                    role = "system",
                    content = "Utiliza la siguiente información extraída de un PDF para responder de forma precisa: $pdfContext"
                )
            )
        }
        messages.add(Message(role = "user", content = question))
        val request = ChatRequest(model = "gpt-3.5-turbo", messages = messages)

        // Realiza la solicitud
        val response = service.getChatCompletion(request, "Bearer $API_KEY")

        // Regresa el contenido del primer choice o un mensaje de error
        return response.choices.firstOrNull()?.message?.content ?: "No se pudo obtener respuesta."
    }
}
