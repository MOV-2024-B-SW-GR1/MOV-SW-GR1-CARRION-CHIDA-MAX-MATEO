package com.example.a04_deber01.data.local

import com.example.a04_deber01.data.model.Carrera
import com.example.a04_deber01.data.model.Facultad

object UniversidadDataStore {
    private var facultadIdCounter = 1
    private var carreraIdCounter = 1

    val facultades = mutableListOf<Facultad>()

    // Facultades
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
        } else false
    }

    fun deleteFacultad(facultadId: Int): Boolean {
        val index = facultades.indexOfFirst { it.id == facultadId }
        return if (index != -1) {
            facultades.removeAt(index)
            true
        } else false
    }

    // Carreras
    fun addCarrera(carrera: Carrera, facultadId: Int): Boolean {
        val facultadIndex = facultades.indexOfFirst { it.id == facultadId }
        if (facultadIndex != -1) {
            val newCarrera = carrera.copy(id = carreraIdCounter++)
            facultades[facultadIndex] = facultades[facultadIndex].copy(
                carreras = facultades[facultadIndex].carreras + newCarrera
            )
            return true
        }
        return false
    }

    fun updateCarrera(carrera: Carrera, facultadId: Int): Boolean {
        val facultadIndex = facultades.indexOfFirst { it.id == facultadId }
        if (facultadIndex != -1) {
            val carrerasActualizadas = facultades[facultadIndex].carreras.map {
                if (it.id == carrera.id) carrera else it
            }
            facultades[facultadIndex] = facultades[facultadIndex].copy(
                carreras = carrerasActualizadas
            )
            return true
        }
        return false
    }

    fun deleteCarrera(carreraId: Int, facultadId: Int): Boolean {
        val facultadIndex = facultades.indexOfFirst { it.id == facultadId }
        if (facultadIndex != -1) {
            val carrerasActualizadas = facultades[facultadIndex].carreras.filter {
                it.id != carreraId
            }
            facultades[facultadIndex] = facultades[facultadIndex].copy(
                carreras = carrerasActualizadas
            )
            return true
        }
        return false
    }
}
