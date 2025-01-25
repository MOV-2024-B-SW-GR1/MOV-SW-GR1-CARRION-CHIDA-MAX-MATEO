package com.example.a04_deber01.repository

import com.example.a04_deber01.data.local.UniversidadDataStore
import com.example.a04_deber01.data.model.Carrera
import com.example.a04_deber01.data.model.Facultad
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Se encarga de la lógica de acceso a datos.
 * Actualmente usa el DataStore en memoria, pero se podría
 * cambiar fácilmente a una BD real (Room, SQLite, etc.).
 */
class UniversidadRepository {

    // Facultades
    suspend fun getAllFacultades(): List<Facultad> = withContext(Dispatchers.IO) {
        UniversidadDataStore.facultades.toList()
    }

    suspend fun insertFacultad(facultad: Facultad) = withContext(Dispatchers.IO) {
        UniversidadDataStore.addFacultad(facultad)
    }

    suspend fun updateFacultad(facultad: Facultad) = withContext(Dispatchers.IO) {
        UniversidadDataStore.updateFacultad(facultad)
    }

    suspend fun deleteFacultad(facultadId: Int) = withContext(Dispatchers.IO) {
        UniversidadDataStore.deleteFacultad(facultadId)
    }

    // Carreras
    suspend fun getCarrerasByFacultad(facultadId: Int): List<Carrera> = withContext(Dispatchers.IO) {
        UniversidadDataStore.carreras.filter { it.facultadId == facultadId }
    }

    suspend fun insertCarrera(carrera: Carrera) = withContext(Dispatchers.IO) {
        UniversidadDataStore.addCarrera(carrera)
    }

    suspend fun updateCarrera(carrera: Carrera) = withContext(Dispatchers.IO) {
        UniversidadDataStore.updateCarrera(carrera)
    }

    suspend fun deleteCarrera(carreraId: Int) = withContext(Dispatchers.IO) {
        UniversidadDataStore.deleteCarrera(carreraId)
    }
}
