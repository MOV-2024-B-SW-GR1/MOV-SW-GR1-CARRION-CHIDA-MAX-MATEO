package com.example.a04_deber01.ui.carrera

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a04_deber01.data.model.Carrera
import com.example.a04_deber01.repository.UniversidadRepository
import kotlinx.coroutines.launch

class CarreraViewModel(
    private val repository: UniversidadRepository,
    private val facultadId: Int
) : ViewModel() {

    suspend fun getCarreras() = repository.getCarrerasByFacultad(facultadId)

    fun insertCarrera(carrera: Carrera, facultadId: Int) = viewModelScope.launch {
        repository.insertCarrera(carrera, facultadId)
    }

    fun updateCarrera(carrera: Carrera, facultadId: Int) = viewModelScope.launch {
        repository.updateCarrera(carrera, facultadId)
    }

    fun deleteCarrera(carreraId: Int, facultadId: Int) = viewModelScope.launch {
        repository.deleteCarrera(carreraId, facultadId)
    }
}
