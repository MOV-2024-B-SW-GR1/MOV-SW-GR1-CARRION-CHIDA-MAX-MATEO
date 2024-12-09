package domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Facultad(
    val id: Int,
    val nombre: String,
    val fechaCreacion: String,
    val activa: Boolean,
    val numeroDepartamentos: Int,
    val presupuestoAnual: Double,
    val carreras: List<Carrera> = emptyList()
)