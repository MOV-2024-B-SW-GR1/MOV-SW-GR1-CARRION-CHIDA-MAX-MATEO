package com.example.a04_deber01.ui.carrera

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a04_deber01.data.model.Carrera
import com.example.a04_deber01.repository.UniversidadRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class CarreraViewModel(
    private val repository: UniversidadRepository,
    private val facultadId: Int
) : ViewModel() {


    fun getCarreras(): Flow<List<Carrera>> = repository.getCarrerasByFacultad(facultadId)

    fun insertCarrera(carrera: Carrera) = viewModelScope.launch {
        repository.insertCarrera(carrera)
    }

    fun updateCarrera(carrera: Carrera) = viewModelScope.launch {
        repository.updateCarrera(carrera)
    }

    fun deleteCarrera(carreraId: Int) = viewModelScope.launch {
        repository.deleteCarreraById(carreraId)
    }
}
