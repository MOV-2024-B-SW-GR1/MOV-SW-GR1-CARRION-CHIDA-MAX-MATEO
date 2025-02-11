package com.example.a04_deber01.repository

import com.example.a04_deber01.data.local.CarreraDao
import com.example.a04_deber01.data.local.FacultadDao
import com.example.a04_deber01.data.model.Carrera
import com.example.a04_deber01.data.model.Facultad
import kotlinx.coroutines.flow.Flow

class UniversidadRepository(
    private val facultadDao: FacultadDao,
    private val carreraDao: CarreraDao
) {
    // Facultades
    fun getAllFacultades(): Flow<List<Facultad>> = facultadDao.getAllFacultades()
    suspend fun insertFacultad(facultad: Facultad) = facultadDao.insertFacultad(facultad)
    suspend fun updateFacultad(facultad: Facultad) = facultadDao.updateFacultad(facultad)
    suspend fun deleteFacultadById(facultadId: Int) = facultadDao.deleteFacultadById(facultadId)

    // Carreras
     fun getCarrerasByFacultad(facultadId: Int): Flow<List<Carrera>> = carreraDao.getCarrerasByFacultad(facultadId)
    suspend fun insertCarrera(carrera: Carrera) = carreraDao.insertCarrera(carrera)
    suspend fun updateCarrera(carrera: Carrera) = carreraDao.updateCarrera(carrera)
    suspend fun deleteCarreraById(carreraId: Int) = carreraDao.deleteCarreraById(carreraId)
}