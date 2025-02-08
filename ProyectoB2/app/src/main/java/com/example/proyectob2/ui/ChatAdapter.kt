package com.example.proyectob2.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectob2.R
import com.example.proyectob2.model.ChatMessage
import com.example.proyectob2.model.Sender

class ChatAdapter(private val messages: MutableList<ChatMessage>) : RecyclerView.Adapter<ChatAdapter.ChatViewHolder>() {

    inner class ChatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvMessage: TextView = itemView.findViewById(R.id.tvMessage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_chat_message, parent, false)
        return ChatViewHolder(view)
    }

    override fun getItemCount(): Int = messages.size

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        val message = messages[position]
        holder.tvMessage.text = message.text

        // Ajusta la alineación según el remitente
        if (message.sender == Sender.USER) {
            // Alinear a la derecha para mensajes del usuario
            holder.tvMessage.textAlignment = View.TEXT_ALIGNMENT_VIEW_END
        } else {
            // Alinear a la izquierda para mensajes del bot
            holder.tvMessage.textAlignment = View.TEXT_ALIGNMENT_VIEW_START
        }
    }

    // Método para agregar un nuevo mensaje y notificar el cambio
    fun addMessage(message: ChatMessage) {
        messages.add(message)
        notifyItemInserted(messages.size - 1)
    }
}
