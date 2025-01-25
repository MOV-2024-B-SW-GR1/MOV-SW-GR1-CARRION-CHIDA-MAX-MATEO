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
        onSave: (String, String, Boolean, Int, Double) -> Unit
    ) {
        val binding = DialogAddFacultadBinding.inflate(LayoutInflater.from(context))

        AlertDialog.Builder(context)
            .setTitle("Nueva Facultad")
            .setView(binding.root)
            .setPositiveButton("Guardar") { _, _ ->
                val nombre = binding.etNombre.text.toString()
                val fechaCreacion = binding.etFechaCreacion.text.toString()
                val activa = binding.cbActiva.isChecked
                val numeroDepartamentos = binding.etNumeroDepartamentos.text.toString().toIntOrNull() ?: 0
                val presupuestoAnual = binding.etPresupuestoAnual.text.toString().toDoubleOrNull() ?: 0.0

                if (nombre.isNotEmpty() && fechaCreacion.isNotEmpty() && numeroDepartamentos > 0 && presupuestoAnual > 0.0) {
                    onSave(nombre, fechaCreacion, activa, numeroDepartamentos, presupuestoAnual)
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
        onUpdate: (String, String, Boolean, Int, Double) -> Unit
    ) {
        val binding = DialogAddFacultadBinding.inflate(LayoutInflater.from(context))

        // Rellenamos los campos con los valores actuales
        binding.etNombre.setText(facultad.nombre)
        binding.etFechaCreacion.setText(facultad.fechaCreacion)
        binding.cbActiva.isChecked = facultad.activa
        binding.etNumeroDepartamentos.setText(facultad.numeroDepartamentos.toString())
        binding.etPresupuestoAnual.setText(facultad.presupuestoAnual.toString())

        AlertDialog.Builder(context)
            .setTitle("Editar Facultad")
            .setView(binding.root)
            .setPositiveButton("Actualizar") { _, _ ->
                val nombre = binding.etNombre.text.toString()
                val fechaCreacion = binding.etFechaCreacion.text.toString()
                val activa = binding.cbActiva.isChecked
                val numeroDepartamentos = binding.etNumeroDepartamentos.text.toString().toIntOrNull() ?: 0
                val presupuestoAnual = binding.etPresupuestoAnual.text.toString().toDoubleOrNull() ?: 0.0

                if (nombre.isNotEmpty() && fechaCreacion.isNotEmpty() && numeroDepartamentos > 0 && presupuestoAnual > 0.0) {
                    onUpdate(nombre, fechaCreacion, activa, numeroDepartamentos, presupuestoAnual)
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
        onSave: (String, Int, Boolean, Int, Double) -> Unit
    ) {
        val binding = DialogAddCarreraBinding.inflate(LayoutInflater.from(context))

        AlertDialog.Builder(context)
            .setTitle("Nueva Carrera")
            .setView(binding.root)
            .setPositiveButton("Guardar") { _, _ ->
                val nombre = binding.etNombreCarrera.text.toString()
                val anioInicio = binding.etAnioInicio.text.toString().toIntOrNull() ?: 0
                val acreditada = binding.cbAcreditada.isChecked
                val creditosTotales = binding.etCreditosTotales.text.toString().toIntOrNull() ?: 0
                val mensualidad = binding.etMensualidad.text.toString().toDoubleOrNull() ?: 0.0

                if (nombre.isNotEmpty() && anioInicio > 0 && creditosTotales > 0 && mensualidad > 0.0) {
                    onSave(nombre, anioInicio, acreditada, creditosTotales, mensualidad)
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
        onUpdate: (String, Int, Boolean, Int, Double) -> Unit
    ) {
        val binding = DialogAddCarreraBinding.inflate(LayoutInflater.from(context))

        // Rellenamos los campos con los valores actuales
        binding.etNombreCarrera.setText(carrera.nombre)
        binding.etAnioInicio.setText(carrera.anioInicio.toString())
        binding.cbAcreditada.isChecked = carrera.acreditada
        binding.etCreditosTotales.setText(carrera.creditosTotales.toString())
        binding.etMensualidad.setText(carrera.mensualidad.toString())

        AlertDialog.Builder(context)
            .setTitle("Editar Carrera")
            .setView(binding.root)
            .setPositiveButton("Actualizar") { _, _ ->
                val nombre = binding.etNombreCarrera.text.toString()
                val anioInicio = binding.etAnioInicio.text.toString().toIntOrNull() ?: 0
                val acreditada = binding.cbAcreditada.isChecked
                val creditosTotales = binding.etCreditosTotales.text.toString().toIntOrNull() ?: 0
                val mensualidad = binding.etMensualidad.text.toString().toDoubleOrNull() ?: 0.0

                if (nombre.isNotEmpty() && anioInicio > 0 && creditosTotales > 0 && mensualidad > 0.0) {
                    onUpdate(nombre, anioInicio, acreditada, creditosTotales, mensualidad)
                }
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }
