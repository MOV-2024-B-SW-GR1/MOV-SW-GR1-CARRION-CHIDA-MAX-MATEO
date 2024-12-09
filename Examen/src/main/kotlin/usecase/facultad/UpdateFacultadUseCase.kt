package usecase.facultad

import domain.model.Facultad
import domain.repository.FacultadRepository

class UpdateFacultadUseCase(private val facultadRepository: FacultadRepository) {
    fun execute(facultad: Facultad) = facultadRepository.updateFacultad(facultad)
}
