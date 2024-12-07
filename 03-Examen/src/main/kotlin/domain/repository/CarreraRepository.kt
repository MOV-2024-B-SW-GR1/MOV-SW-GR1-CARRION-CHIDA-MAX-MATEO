package domain.repository

import domain.model.Carrera

interface CarreraRepository {
    fun addCarreraToFacultad(facultadId: Int, carrera: Carrera): Carrera?
    fun getCarrerasByFacultad(facultadId: Int): List<Carrera>
    fun updateCarrera(facultadId: Int, carrera: Carrera): Carrera?
    fun deleteCarrera(facultadId: Int, carreraId: Int): Boolean
    fun getCarreraById(facultadId: Int, carreraId: Int): Carrera?
}
