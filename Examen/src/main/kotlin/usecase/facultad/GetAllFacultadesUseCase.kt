package usecase.facultad

import domain.repository.FacultadRepository

class GetAllFacultadesUseCase(private val facultadRepository: FacultadRepository) {
    fun execute() = facultadRepository.getAllFacultades()
}
