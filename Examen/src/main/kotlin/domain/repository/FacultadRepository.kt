package domain.repository

import domain.model.Facultad

interface FacultadRepository {
    fun createFacultad(facultad: Facultad): Facultad
    fun getAllFacultades(): List<Facultad>
    fun updateFacultad(facultad: Facultad): Facultad?
    fun deleteFacultad(id: Int): Boolean
    fun getFacultadById(id: Int): Facultad?
}
