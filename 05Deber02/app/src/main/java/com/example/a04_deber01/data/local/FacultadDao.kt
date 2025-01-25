package com.example.a04_deber01.data.local

import androidx.room.*
import com.example.a04_deber01.data.model.Facultad
import kotlinx.coroutines.flow.Flow

@Dao
interface FacultadDao {
    // Cambia el retorno a Flow y quita suspend
    @Query("SELECT * FROM facultades")
    fun getAllFacultades(): Flow<List<Facultad>>

    @Insert
    suspend fun insertFacultad(facultad: Facultad)

    @Update
    suspend fun updateFacultad(facultad: Facultad)

    @Query("DELETE FROM facultades WHERE id = :facultadId")
    suspend fun deleteFacultadById(facultadId: Int)
}