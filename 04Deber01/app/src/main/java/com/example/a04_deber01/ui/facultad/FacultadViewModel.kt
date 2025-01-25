package com.example.a04_deber01.ui.facultad

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a04_deber01.data.model.Facultad
import com.example.a04_deber01.repository.UniversidadRepository
import kotlinx.coroutines.launch

class FacultadViewModel(
    private val repository: UniversidadRepository
) : ViewModel() {

    fun insertFacultad(facultad: Facultad) = viewModelScope.launch {
        repository.insertFacultad(facultad)
    }

    fun updateFacultad(facultad: Facultad) = viewModelScope.launch {
        repository.updateFacultad(facultad)
    }

    fun deleteFacultad(facultadId: Int) = viewModelScope.launch {
        repository.deleteFacultad(facultadId)
    }
}
