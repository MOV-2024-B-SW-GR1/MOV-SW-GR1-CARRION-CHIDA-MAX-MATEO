package domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Carrera(
    val id: Int,
    val nombre: String,
    val anioInicio: Int,
    val acreditada: Boolean,
    val creditosTotales: Int,
    val mensualidad: Double
)

