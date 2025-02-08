package com.example.proyectob2.model

data class ChatMessage(
    val text: String,
    val sender: Sender
)

enum class Sender {
    USER, BOT
}
