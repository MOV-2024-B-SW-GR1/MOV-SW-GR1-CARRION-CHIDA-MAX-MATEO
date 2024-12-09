package usecase.carrera

import domain.repository.CarreraRepository

class DeleteCarreraUseCase(private val carreraRepository: CarreraRepository) {
    fun execute(facultadId: Int, carreraId: Int) = carreraRepository.deleteCarrera(facultadId, carreraId)
}
