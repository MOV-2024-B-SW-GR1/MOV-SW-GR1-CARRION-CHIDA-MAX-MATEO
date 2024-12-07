package data.repository

import data.storage.JsonStorage
import domain.model.Facultad
import domain.repository.FacultadRepository

class JsonFacultadRepository(private val storage: JsonStorage) : FacultadRepository {

    override fun createFacultad(facultad: Facultad): Facultad {
        val facultades = storage.loadFacultades().toMutableList()
        val newFacultad = facultad.copy(id = generateId(facultades))
        facultades.add(newFacultad)
        storage.saveFacultades(facultades)
        return newFacultad
    }

    override fun getAllFacultades(): List<Facultad> {
        return storage.loadFacultades()
    }

    override fun updateFacultad(facultad: Facultad): Facultad? {
        val facultades = storage.loadFacultades().toMutableList()
        val index = facultades.indexOfFirst { it.id == facultad.id }
        return if (index != -1) {
            facultades[index] = facultad
            storage.saveFacultades(facultades)
            facultad
        } else {
            null
        }
    }

    override fun deleteFacultad(id: Int): Boolean {
        val facultades = storage.loadFacultades().toMutableList()
        val removed = facultades.removeIf { it.id == id }
        if (removed) {
            storage.saveFacultades(facultades)
        }
        return removed
    }

    override fun getFacultadById(id: Int): Facultad? {
        return storage.loadFacultades().find { it.id == id }
    }

    private fun generateId(facultades: List<Facultad>): Int {
        return if (facultades.isEmpty()) 1 else facultades.maxOf { it.id } + 1
    }
}
