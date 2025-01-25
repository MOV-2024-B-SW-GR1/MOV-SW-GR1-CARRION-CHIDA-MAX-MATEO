package com.example.a04_deber01.ui.facultad.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.a04_deber01.data.model.Facultad
import com.example.a04_deber01.databinding.ItemFacultadBinding

class FacultadAdapter(
    private val onItemClick: (Facultad) -> Unit,
    private val onDeleteClick: (Int) -> Unit,
    private val onEditClick: (Facultad) -> Unit
) : ListAdapter<Facultad, FacultadAdapter.ViewHolder>(DiffCallback()) {

    inner class ViewHolder(private val binding: ItemFacultadBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(facultad: Facultad) {
            binding.tvNombre.text = facultad.nombre
            binding.tvFechaCreacion.text = "Fecha Creación: ${facultad.fechaCreacion}"
            binding.tvActiva.text = "Activa: ${if (facultad.activa) "Sí" else "No"}"
            binding.tvNumeroDepartamentos.text = "Departamentos: ${facultad.numeroDepartamentos}"
            binding.tvPresupuesto.text = "Presupuesto: $${facultad.presupuestoAnual}"

            binding.root.setOnClickListener {
                onItemClick(facultad)
            }
            binding.btnDelete.setOnClickListener {
                onDeleteClick(facultad.id)
            }
            binding.btnEdit.setOnClickListener {
                onEditClick(facultad)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemFacultadBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class DiffCallback : DiffUtil.ItemCallback<Facultad>() {
        override fun areItemsTheSame(oldItem: Facultad, newItem: Facultad) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Facultad, newItem: Facultad) = oldItem == newItem
    }
}
