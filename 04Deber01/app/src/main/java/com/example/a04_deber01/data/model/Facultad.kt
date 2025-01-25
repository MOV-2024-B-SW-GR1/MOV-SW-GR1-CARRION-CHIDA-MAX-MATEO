package com.example.a04_deber01.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Facultad(
    val id: Int = 0,
    val nombre: String,
    val fechaCreacion: String,
    val activa: Boolean,
    val numeroDepartamentos: Int,
    val presupuestoAnual: Double,
    val carreras: List<Carrera> = emptyList()
)