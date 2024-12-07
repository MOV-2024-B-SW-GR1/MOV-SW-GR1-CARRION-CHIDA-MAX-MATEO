package usecase.facultad

import domain.repository.FacultadRepository

class DeleteFacultadUseCase(private val facultadRepository: FacultadRepository) {
    fun execute(id: Int) = facultadRepository.deleteFacultad(id)
}
