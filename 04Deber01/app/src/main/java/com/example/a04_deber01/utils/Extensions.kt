package com.example.a04_deber01.utils

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import com.example.a04_deber01.databinding.DialogAddCarreraBinding
import com.example.a04_deber01.databinding.DialogAddFacultadBinding
import com.example.a04_deber01.data.model.Carrera
import com.example.a04_deber01.data.model.Facultad

/**
 * Diálogo para crear nueva Facultad
 */
fun showFacultadDialog(
    context: Context,
    onSave: (String, String, String) -> Unit
) {
    val binding = DialogAddFacultadBinding.inflate(LayoutInflater.from(context))

    AlertDialog.Builder(context)
        .setTitle("Nueva Facultad")
        .setView(binding.root)
        .setPositiveButton("Guardar") { _, _ ->
            val nombre = binding.etNombre.text.toString()
            val ubicacion = binding.etUbicacion.text.toString()
            val fecha = binding.etFecha.text.toString()
            if (nombre.isNotEmpty() && ubicacion.isNotEmpty() && fecha.isNotEmpty()) {
                onSave(nombre, ubicacion, fecha)
            }
        }
        .setNegativeButton("Cancelar", null)
        .show()
}

/**
 * Diálogo para actualizar Facultad
 */
fun showUpdateFacultadDialog(
    context: Context,
    facultad: Facultad,
    onUpdate: (String, String, String) -> Unit
) {
    val binding = DialogAddFacultadBinding.inflate(LayoutInflater.from(context))

    // Rellenamos los campos con los valores actuales
    binding.etNombre.setText(facultad.nombre)
    binding.etUbicacion.setText(facultad.ubicacion)
    binding.etFecha.setText(facultad.fechaCreacion)

    AlertDialog.Builder(context)
        .setTitle("Editar Facultad")
        .setView(binding.root)
        .setPositiveButton("Actualizar") { _, _ ->
            val nombre = binding.etNombre.text.toString()
            val ubicacion = binding.etUbicacion.text.toString()
            val fecha = binding.etFecha.text.toString()
            if (nombre.isNotEmpty() && ubicacion.isNotEmpty() && fecha.isNotEmpty()) {
                onUpdate(nombre, ubicacion, fecha)
            }
        }
        .setNegativeButton("Cancelar", null)
        .show()
}

/**
 * Diálogo para crear nueva Carrera
 */
fun showCarreraDialog(
    context: Context,
    onSave: (String, Int) -> Unit
) {
    val binding = DialogAddCarreraBinding.inflate(LayoutInflater.from(context))

    AlertDialog.Builder(context)
        .setTitle("Nueva Carrera")
        .setView(binding.root)
        .setPositiveButton("Guardar") { _, _ ->
            val nombre = binding.etNombreCarrera.text.toString()
            val duracionStr = binding.etDuracionCarrera.text.toString()
            val duracion = duracionStr.toIntOrNull() ?: 0
            if (nombre.isNotEmpty() && duracion > 0) {
                onSave(nombre, duracion)
            }
        }
        .setNegativeButton("Cancelar", null)
        .show()
}

/**
 * Diálogo para actualizar Carrera
 */
fun showUpdateCarreraDialog(
    context: Context,
    carrera: Carrera,
    onUpdate: (String, Int) -> Unit
) {
    val binding = DialogAddCarreraBinding.inflate(LayoutInflater.from(context))

    // Rellenamos los campos con los valores actuales
    binding.etNombreCarrera.setText(carrera.nombre)
    binding.etDuracionCarrera.setText(carrera.duracion.toString())

    AlertDialog.Builder(context)
        .setTitle("Editar Carrera")
        .setView(binding.root)
        .setPositiveButton("Actualizar") { _, _ ->
            val nombre = binding.etNombreCarrera.text.toString()
            val duracionStr = binding.etDuracionCarrera.text.toString()
            val duracion = duracionStr.toIntOrNull() ?: 0
            if (nombre.isNotEmpty() && duracion > 0) {
                onUpdate(nombre, duracion)
            }
        }
        .setNegativeButton("Cancelar", null)
        .show()
}
