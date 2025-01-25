package com.example.a04_deber01.repository

import com.example.a04_deber01.data.local.UniversidadDataStore
import com.example.a04_deber01.data.model.Carrera
import com.example.a04_deber01.data.model.Facultad
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UniversidadRepository {
    suspend fun getAllFacultades() = withContext(Dispatchers.IO) {
        UniversidadDataStore.facultades.toList()
    }

    suspend fun getCarrerasByFacultad(facultadId: Int) = withContext(Dispatchers.IO) {
        UniversidadDataStore.facultades.find { it.id == facultadId }?.carreras ?: emptyList()
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

    suspend fun insertCarrera(carrera: Carrera, facultadId: Int) = withContext(Dispatchers.IO) {
        UniversidadDataStore.addCarrera(carrera, facultadId)
    }

    suspend fun updateCarrera(carrera: Carrera, facultadId: Int) = withContext(Dispatchers.IO) {
        UniversidadDataStore.updateCarrera(carrera, facultadId)
    }

    suspend fun deleteCarrera(carreraId: Int, facultadId: Int) = withContext(Dispatchers.IO) {
        UniversidadDataStore.deleteCarrera(carreraId, facultadId)
    }
}
