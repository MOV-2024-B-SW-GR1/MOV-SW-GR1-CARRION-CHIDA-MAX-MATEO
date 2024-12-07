package usecase.carrera

import domain.model.Carrera
import domain.repository.CarreraRepository

class UpdateCarreraUseCase(private val carreraRepository: CarreraRepository) {
    fun execute(facultadId: Int, carrera: Carrera) = carreraRepository.updateCarrera(facultadId, carrera)
}
