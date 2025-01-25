package com.example.a04_deber01.data.local

import com.example.a04_deber01.data.model.Carrera
import com.example.a04_deber01.data.model.Facultad

/**
 * Simula el almacenamiento en memoria de las Facultades y Carreras.
 * En un futuro, puedes reemplazar esto con un Dao/Room u otra base de datos real.
 */
object UniversidadDataStore {

    private var facultadIdCounter = 1
    private var carreraIdCounter = 1

    val facultades = mutableListOf<Facultad>()
    val carreras = mutableListOf<Carrera>()

    // CRUD Facultades
    fun addFacultad(facultad: Facultad): Boolean {
        val newFacultad = facultad.copy(id = facultadIdCounter++)
        facultades.add(newFacultad)
        return true
    }

    fun updateFacultad(facultad: Facultad): Boolean {
        val index = facultades.indexOfFirst { it.id == facultad.id }
        return if (index != -1) {
            facultades[index] = facultad
            true
        } else {
            false
        }
    }

    fun deleteFacultad(facultadId: Int): Boolean {
        val index = facultades.indexOfFirst { it.id == facultadId }
        return if (index != -1) {
            // Eliminar carreras asociadas
            carreras.removeAll { it.facultadId == facultadId }
            facultades.removeAt(index)
            true
        } else {
            false
        }
    }

    // CRUD Carreras
    fun addCarrera(carrera: Carrera): Boolean {
        val newCarrera = carrera.copy(id = carreraIdCounter++)
        carreras.add(newCarrera)
        return true
    }

    fun updateCarrera(carrera: Carrera): Boolean {
        val index = carreras.indexOfFirst { it.id == carrera.id }
        return if (index != -1) {
            carreras[index] = carrera
            true
        } else {
            false
        }
    }

    fun deleteCarrera(carreraId: Int): Boolean {
        val index = carreras.indexOfFirst { it.id == carreraId }
        return if (index != -1) {
            carreras.removeAt(index)
            true
        } else {
            false
        }
    }
}
