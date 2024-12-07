package usecase.carrera

import domain.model.Carrera
import domain.repository.CarreraRepository

class CreateCarreraUseCase(private val carreraRepository: CarreraRepository) {
    fun execute(facultadId: Int, carrera: Carrera): Carrera? {
        return carreraRepository.addCarreraToFacultad(facultadId, carrera)
    }
}
