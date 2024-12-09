package usecase.carrera

import domain.repository.CarreraRepository

class GetCarrerasByFacultadUseCase(private val carreraRepository: CarreraRepository) {
    fun execute(facultadId: Int) = carreraRepository.getCarrerasByFacultad(facultadId)
}
