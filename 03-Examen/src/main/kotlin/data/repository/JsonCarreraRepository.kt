package data.repository

import data.storage.JsonStorage
import domain.model.Carrera
import domain.model.Facultad
import domain.repository.CarreraRepository

class JsonCarreraRepository(private val storage: JsonStorage) : CarreraRepository {

    override fun addCarreraToFacultad(facultadId: Int, carrera: Carrera): Carrera? {
        val facultades = storage.loadFacultades().toMutableList()
        val index = facultades.indexOfFirst { it.id == facultadId }
        if (index == -1) return null
        val facultad = facultades[index]
        val newCarrera = carrera.copy(id = generateCarreraId(facultad))
        val updatedFacultad = facultad.copy(carreras = facultad.carreras + newCarrera)
        facultades[index] = updatedFacultad
        storage.saveFacultades(facultades)
        return newCarrera
    }

    override fun getCarrerasByFacultad(facultadId: Int): List<Carrera> {
        val facultad = storage.loadFacultades().find { it.id == facultadId }
        return facultad?.carreras ?: emptyList()
    }

    override fun updateCarrera(facultadId: Int, carrera: Carrera): Carrera? {
        val facultades = storage.loadFacultades().toMutableList()
        val indexFac = facultades.indexOfFirst { it.id == facultadId }
        if (indexFac == -1) return null

        val facultad = facultades[indexFac]
        val carreras = facultad.carreras.toMutableList()
        val indexCar = carreras.indexOfFirst { it.id == carrera.id }
        if (indexCar == -1) return null

        carreras[indexCar] = carrera
        facultades[indexFac] = facultad.copy(carreras = carreras)
        storage.saveFacultades(facultades)
        return carrera
    }

    override fun deleteCarrera(facultadId: Int, carreraId: Int): Boolean {
        val facultades = storage.loadFacultades().toMutableList()
        val indexFac = facultades.indexOfFirst { it.id == facultadId }
        if (indexFac == -1) return false

        val facultad = facultades[indexFac]
        val carreras = facultad.carreras.toMutableList()
        val removed = carreras.removeIf { it.id == carreraId }
        if (!removed) return false

        facultades[indexFac] = facultad.copy(carreras = carreras)
        storage.saveFacultades(facultades)
        return true
    }

    override fun getCarreraById(facultadId: Int, carreraId: Int): Carrera? {
        val facultad = storage.loadFacultades().find { it.id == facultadId }
        return facultad?.carreras?.find { it.id == carreraId }
    }

    private fun generateCarreraId(facultad: Facultad): Int {
        return if (facultad.carreras.isEmpty()) 1 else facultad.carreras.maxOf { it.id } + 1
    }
}
