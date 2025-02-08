package com.example.proyectob2.ui

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectob2.R
import com.example.proyectob2.data.ChatGPTRepository
import com.example.proyectob2.model.ChatMessage
import com.example.proyectob2.model.Sender
import com.example.proyectob2.util.loadPdfContext
import kotlinx.coroutines.launch

class ChatActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var inputEditText: EditText
    private lateinit var sendButton: ImageButton
    private lateinit var chatAdapter: ChatAdapter

    // Lista mutable de mensajes
    private val messagesList = mutableListOf<ChatMessage>()

    // Variable para almacenar el contexto extraído del PDF
    private var pdfContext: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        recyclerView = findViewById(R.id.recyclerView)
        inputEditText = findViewById(R.id.etMessage)
        sendButton = findViewById<ImageButton>(R.id.btnSend)

        recyclerView.layoutManager = LinearLayoutManager(this)
        chatAdapter = ChatAdapter(messagesList)
        recyclerView.adapter = chatAdapter

        // Agrega un mensaje de bienvenida del bot (mensaje quemado)
        val welcomeMessage = "¡Bienvenido a la Asistencia EPN! Estoy aquí para ayudarte con los procesos de la guía del estudiante. ¿En qué puedo ayudarte hoy?"
        chatAdapter.addMessage(ChatMessage(welcomeMessage, Sender.BOT))

        // Carga el contexto del PDF (si lo necesitas)
        pdfContext = loadPdfContext(this)

        sendButton.setOnClickListener {
            val userMessage = inputEditText.text.toString().trim()
            if (userMessage.isNotEmpty()) {
                // Agrega el mensaje del usuario
                chatAdapter.addMessage(ChatMessage(userMessage, Sender.USER))
                inputEditText.text.clear()

                // Agrega el indicador de "typing" del bot
                chatAdapter.addMessage(ChatMessage(ChatAdapter.TYPING_INDICATOR, Sender.BOT))
                recyclerView.smoothScrollToPosition(messagesList.size - 1)

                // Llama a la API de ChatGPT usando el contexto del PDF
                lifecycleScope.launch {
                    try {
                        val response = ChatGPTRepository.askQuestion(userMessage, pdfContext)
                        // Remueve el indicador de typing
                        chatAdapter.removeTypingIndicator()
                        // Agrega el mensaje del bot
                        chatAdapter.addMessage(ChatMessage(response, Sender.BOT))
                        recyclerView.smoothScrollToPosition(messagesList.size - 1)
                    } catch (e: Exception) {
                        chatAdapter.removeTypingIndicator()
                        Toast.makeText(this@ChatActivity, "Error: ${e.message}", Toast.LENGTH_LONG).show()
                    }
                }
            } else {
                Toast.makeText(this, "Escribe un mensaje", Toast.LENGTH_SHORT).show()
            }
        }

    }
}
