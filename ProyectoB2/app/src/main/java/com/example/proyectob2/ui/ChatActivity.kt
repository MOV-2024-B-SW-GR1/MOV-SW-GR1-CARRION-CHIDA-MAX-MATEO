package com.example.proyectob2.ui

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectob2.R
import com.example.proyectob2.data.ChatGPTRepository
import com.example.proyectob2.data.FirebaseAuthRepository
import com.example.proyectob2.model.ChatMessage
import com.example.proyectob2.model.Sender
import com.example.proyectob2.util.loadPdfContext
import kotlinx.coroutines.launch

class ChatActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var inputEditText: EditText
    private lateinit var sendButton: ImageButton
    private lateinit var chatAdapter: ChatAdapter
    private lateinit var logoutButton: Button
    private lateinit var userNameTextView: TextView

    private val messagesList = mutableListOf<ChatMessage>()
    private var pdfContext: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        recyclerView = findViewById(R.id.recyclerView)
        inputEditText = findViewById(R.id.etMessage)
        sendButton = findViewById(R.id.btnSend)
        logoutButton = findViewById(R.id.btnLogout)  // Asegúrate de tener este botón en tu XML
        userNameTextView = findViewById(R.id.tvUserName)  // Asegúrate de tener este TextView en tu XML

        recyclerView.layoutManager = LinearLayoutManager(this)
        chatAdapter = ChatAdapter(messagesList)
        recyclerView.adapter = chatAdapter

        // Obtener y mostrar el nombre del usuario autenticado
        userNameTextView.text = "Hola, ${FirebaseAuthRepository.getUserName()}"

        val welcomeMessage = "¡Bienvenido a la Asistencia EPN! ¿En qué puedo ayudarte hoy?"
        chatAdapter.addMessage(ChatMessage(welcomeMessage, Sender.BOT))

        pdfContext = loadPdfContext(this)

        sendButton.setOnClickListener {
            val userMessage = inputEditText.text.toString().trim()
            if (userMessage.isNotEmpty()) {
                chatAdapter.addMessage(ChatMessage(userMessage, Sender.USER))
                inputEditText.text.clear()

                chatAdapter.addMessage(ChatMessage(ChatAdapter.TYPING_INDICATOR, Sender.BOT))
                recyclerView.smoothScrollToPosition(messagesList.size - 1)

                lifecycleScope.launch {
                    try {
                        val response = ChatGPTRepository.askQuestion(userMessage, pdfContext)
                        chatAdapter.removeTypingIndicator()
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

        // Botón para cerrar sesión
        logoutButton.setOnClickListener {
            FirebaseAuthRepository.signOut()
            Toast.makeText(this, "Sesión cerrada", Toast.LENGTH_SHORT).show()
            finish() // Cierra la actividad y regresa al login
        }
    }
}
