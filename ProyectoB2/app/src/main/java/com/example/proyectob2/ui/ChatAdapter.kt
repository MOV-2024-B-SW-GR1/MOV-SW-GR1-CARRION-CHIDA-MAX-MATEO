package com.example.proyectob2.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectob2.R
import com.example.proyectob2.model.ChatMessage
import com.example.proyectob2.model.Sender

class ChatAdapter(private val messages: MutableList<ChatMessage>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val VIEW_TYPE_BOT = 0
        private const val VIEW_TYPE_USER = 1
        private const val VIEW_TYPE_TYPING = 2
        const val TYPING_INDICATOR = "TYPING_INDICATOR"
    }

    // ViewHolder para mensajes del bot
    inner class BotViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvMessage: TextView = itemView.findViewById(R.id.tvMessage)
    }

    // ViewHolder para mensajes del usuario
    inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvMessage: TextView = itemView.findViewById(R.id.tvMessage)
    }

    // ViewHolder para el indicador de "typing"
    inner class TypingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val progressBar: ProgressBar = itemView.findViewById(R.id.progressBarTyping)
    }

    override fun getItemViewType(position: Int): Int {
        val message = messages[position]
        return if (message.sender == Sender.BOT && message.text == TYPING_INDICATOR) {
            VIEW_TYPE_TYPING
        } else if (message.sender == Sender.USER) {
            VIEW_TYPE_USER
        } else {
            VIEW_TYPE_BOT
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType) {
            VIEW_TYPE_USER -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_chat_message_user, parent, false)
                UserViewHolder(view)
            }
            VIEW_TYPE_TYPING -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_typing_indicator, parent, false)
                TypingViewHolder(view)
            }
            else -> { // VIEW_TYPE_BOT
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_chat_message_bot, parent, false)
                BotViewHolder(view)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val message = messages[position]
        when(holder) {
            is UserViewHolder -> {
                holder.tvMessage.text = message.text
            }
            is BotViewHolder -> {
                holder.tvMessage.text = message.text
            }
            is TypingViewHolder -> {
                // No es necesario establecer texto: se muestra un ProgressBar
            }
        }
    }

    override fun getItemCount(): Int = messages.size

    fun addMessage(message: ChatMessage) {
        messages.add(message)
        notifyItemInserted(messages.size - 1)
    }

    fun removeTypingIndicator() {
        val index = messages.indexOfFirst { it.sender == Sender.BOT && it.text == TYPING_INDICATOR }
        if (index != -1) {
            messages.removeAt(index)
            notifyItemRemoved(index)
        }
    }
}
