package com.example.a04_deber01.ui.carrera.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.a04_deber01.data.model.Carrera
import com.example.a04_deber01.databinding.ItemCarreraBinding

class CarreraAdapter(
    private val onDeleteClick: (Int) -> Unit,
    private val onEditClick: (Carrera) -> Unit
) : ListAdapter<Carrera, CarreraAdapter.ViewHolder>(DiffCallback()) {

    inner class ViewHolder(private val binding: ItemCarreraBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(carrera: Carrera) {
            binding.tvNombreCarrera.text = carrera.nombre
            binding.tvAnioInicio.text = "Año Inicio: ${carrera.anioInicio}"
            binding.tvAcreditada.text = "Acreditada: ${if (carrera.acreditada) "Sí" else "No"}"
            binding.tvCreditosTotales.text = "Créditos: ${carrera.creditosTotales}"
            binding.tvMensualidad.text = "Mensualidad: $${carrera.mensualidad}"

            binding.btnDeleteCarrera.setOnClickListener {
                onDeleteClick(carrera.id)
            }
            binding.btnEditCarrera.setOnClickListener {
                onEditClick(carrera)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCarreraBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class DiffCallback : DiffUtil.ItemCallback<Carrera>() {
        override fun areItemsTheSame(oldItem: Carrera, newItem: Carrera) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Carrera, newItem: Carrera) = oldItem == newItem
    }
}
