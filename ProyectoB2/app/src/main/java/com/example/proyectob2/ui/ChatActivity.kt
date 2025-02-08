package com.example.proyectob2.ui

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
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
    private lateinit var sendButton: Button

    // Lista mutable de mensajes
    private val messagesList = mutableListOf<ChatMessage>()
    private lateinit var chatAdapter: ChatAdapter

    // Variable para almacenar el contexto extraído del PDF
    private var pdfContext: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        recyclerView = findViewById(R.id.recyclerView)
        inputEditText = findViewById(R.id.etMessage)
        sendButton = findViewById(R.id.btnSend)

        // Configura el RecyclerView con un LayoutManager y el adaptador
        recyclerView.layoutManager = LinearLayoutManager(this)
        chatAdapter = ChatAdapter(messagesList)
        recyclerView.adapter = chatAdapter

        // Carga el contexto del PDF desde res/raw (asegúrate de que el PDF esté en res/raw y que la función se actualice para ello)
        pdfContext = loadPdfContext(this)

        sendButton.setOnClickListener {
            val userMessage = inputEditText.text.toString().trim()
            if (userMessage.isNotEmpty()) {
                // Agrega el mensaje del usuario a la lista y actualiza el adaptador
                chatAdapter.addMessage(ChatMessage(userMessage, Sender.USER))
                inputEditText.text.clear()

                // Llama a la API de ChatGPT usando el contexto del PDF
                lifecycleScope.launch {
                    try {
                        val response = ChatGPTRepository.askQuestion(userMessage, pdfContext)
                        // Agrega el mensaje del bot
                        chatAdapter.addMessage(ChatMessage(response, Sender.BOT))
                        // Desplaza el RecyclerView al último mensaje
                        recyclerView.smoothScrollToPosition(messagesList.size - 1)
                    } catch (e: Exception) {
                        Toast.makeText(this@ChatActivity, "Error: ${e.message}", Toast.LENGTH_LONG).show()
                    }
                }
            } else {
                Toast.makeText(this, "Escribe un mensaje", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
