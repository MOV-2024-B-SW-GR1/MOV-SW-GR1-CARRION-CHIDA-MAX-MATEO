package usecase.facultad

import domain.model.Facultad
import domain.repository.FacultadRepository

class CreateFacultadUseCase(private val facultadRepository: FacultadRepository) {
    fun execute(facultad: Facultad): Facultad {
        return facultadRepository.createFacultad(facultad)
    }
}
