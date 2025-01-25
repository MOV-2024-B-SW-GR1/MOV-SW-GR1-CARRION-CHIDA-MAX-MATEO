package com.example.a04_deber01.data.local

import androidx.room.*
import com.example.a04_deber01.data.model.Carrera
import kotlinx.coroutines.flow.Flow

@Dao
interface CarreraDao {
    // Cambia el retorno a Flow y quita suspend
    @Query("SELECT * FROM carreras WHERE facultad_id = :facultadId")
    fun getCarrerasByFacultad(facultadId: Int): Flow<List<Carrera>>

    @Insert
    suspend fun insertCarrera(carrera: Carrera)

    @Update
    suspend fun updateCarrera(carrera: Carrera)

    @Query("DELETE FROM carreras WHERE id = :carreraId")
    suspend fun deleteCarreraById(carreraId: Int)
}