import data.repository.JsonCarreraRepository
import data.repository.JsonFacultadRepository
import data.storage.JsonStorage
import domain.model.Carrera
import domain.model.Facultad
import usecase.carrera.CreateCarreraUseCase
import usecase.carrera.DeleteCarreraUseCase
import usecase.carrera.GetCarrerasByFacultadUseCase
import usecase.carrera.UpdateCarreraUseCase
import usecase.facultad.CreateFacultadUseCase
import usecase.facultad.DeleteFacultadUseCase
import usecase.facultad.GetAllFacultadesUseCase
import usecase.facultad.UpdateFacultadUseCase

fun main() {
    val storage = JsonStorage("src/main/resources/facultades.json")
    val facultadRepository = JsonFacultadRepository(storage)
    val carreraRepository = JsonCarreraRepository(storage)

    val createFacultadUseCase = CreateFacultadUseCase(facultadRepository)
    val getAllFacultadesUseCase = GetAllFacultadesUseCase(facultadRepository)
    val updateFacultadUseCase = UpdateFacultadUseCase(facultadRepository)
    val deleteFacultadUseCase = DeleteFacultadUseCase(facultadRepository)

    val createCarreraUseCase = CreateCarreraUseCase(carreraRepository)
    val getCarrerasByFacultadUseCase = GetCarrerasByFacultadUseCase(carreraRepository)
    val updateCarreraUseCase = UpdateCarreraUseCase(carreraRepository)
    val deleteCarreraUseCase = DeleteCarreraUseCase(carreraRepository)

    loop@ while (true) {
        println("==== MENÚ PRINCIPAL ====")
        println("1. CRUD Facultad")
        println("2. CRUD Carrera")
        println("0. Salir")
        when (readlnOrNull()?.trim()) {
            "1" -> {
                println("---- CRUD Facultad ----")
                println("1. Crear Facultad")
                println("2. Listar Facultades")
                println("3. Actualizar Facultad")
                println("4. Eliminar Facultad")
                println("0. Regresar")
                when (readlnOrNull()?.trim()) {
                    "1" -> {
                        println("Ingrese nombre:")
                        val nombre = readlnOrNull()?.trim() ?: ""
                        println("Ingrese fecha de creación (yyyy-MM-dd):")
                        val fecha = readlnOrNull()?.trim() ?: ""
                        println("¿Activa? (true/false):")
                        val activa = readlnOrNull()?.trim()?.toBoolean() ?: false
                        println("Número de departamentos (int):")
                        val numDept = readlnOrNull()?.trim()?.toIntOrNull() ?: 0
                        println("Presupuesto anual (double):")
                        val presupuesto = readlnOrNull()?.trim()?.toDoubleOrNull() ?: 0.0

                        val nuevaFacultad = Facultad(
                            id = 0,
                            nombre = nombre,
                            fechaCreacion = fecha,
                            activa = activa,
                            numeroDepartamentos = numDept,
                            presupuestoAnual = presupuesto
                        )
                        val creada = createFacultadUseCase.execute(nuevaFacultad)
                        println("Facultad creada: $creada")
                    }
                    "2" -> {
                        val facultades = getAllFacultadesUseCase.execute()
                        facultades.forEach { println(it) }
                    }
                    "3" -> {
                        println("Ingrese ID de la facultad a actualizar:")
                        val id = readlnOrNull()?.trim()?.toIntOrNull() ?: -1
                        val facultad = facultadRepository.getFacultadById(id)
                        if (facultad == null) {
                            println("No se encontró la facultad.")
                        } else {
                            println("Ingrese nuevo nombre (actual: ${facultad.nombre}):")
                            val nombre = readlnOrNull()?.trim() ?: facultad.nombre
                            println("Ingrese nueva fecha creación (actual: ${facultad.fechaCreacion}):")
                            val fecha = readlnOrNull()?.trim() ?: facultad.fechaCreacion
                            println("¿Activa? (actual: ${facultad.activa}):")
                            val activa = readlnOrNull()?.trim()?.toBoolean() ?: facultad.activa
                            println("Número departamentos (actual: ${facultad.numeroDepartamentos}):")
                            val numDept = readlnOrNull()?.trim()?.toIntOrNull() ?: facultad.numeroDepartamentos
                            println("Presupuesto (actual: ${facultad.presupuestoAnual}):")
                            val presupuesto = readlnOrNull()?.trim()?.toDoubleOrNull() ?: facultad.presupuestoAnual

                            val actualizada = updateFacultadUseCase.execute(
                                facultad.copy(
                                    nombre = nombre,
                                    fechaCreacion = fecha,
                                    activa = activa,
                                    numeroDepartamentos = numDept,
                                    presupuestoAnual = presupuesto
                                )
                            )
                            println("Facultad actualizada: $actualizada")
                        }
                    }
                    "4" -> {
                        println("Ingrese ID de la facultad a eliminar:")
                        val id = readlnOrNull()?.trim()?.toIntOrNull() ?: -1
                        val resultado = deleteFacultadUseCase.execute(id)
                        println(if (resultado) "Facultad eliminada." else "No se pudo eliminar la facultad.")
                    }
                    else -> {}
                }
            }
            "2" -> {
                println("---- CRUD Carrera ----")
                println("1. Crear Carrera en una Facultad")
                println("2. Listar Carreras de una Facultad")
                println("3. Actualizar Carrera")
                println("4. Eliminar Carrera")
                println("0. Regresar")
                when (readlnOrNull()?.trim()) {
                    "1" -> {
                        println("Ingrese ID de la facultad a la que agregará la carrera:")
                        val facId = readlnOrNull()?.trim()?.toIntOrNull() ?: -1
                        val facultad = facultadRepository.getFacultadById(facId)
                        if (facultad == null) {
                            println("Facultad no encontrada.")
                        } else {
                            println("Ingrese nombre de la carrera:")
                            val nombre = readlnOrNull()?.trim() ?: ""
                            println("Ingrese año de inicio (int):")
                            val anioInicio = readlnOrNull()?.trim()?.toIntOrNull() ?: 2024
                            println("¿Acreditada? (true/false):")
                            val acreditada = readlnOrNull()?.trim()?.toBoolean() ?: false
                            println("Créditos totales (int):")
                            val creditos = readlnOrNull()?.trim()?.toIntOrNull() ?: 0
                            println("Mensualidad (double):")
                            val mensualidad = readlnOrNull()?.trim()?.toDoubleOrNull() ?: 0.0

                            val nuevaCarrera = Carrera(
                                id = 0,
                                nombre = nombre,
                                anioInicio = anioInicio,
                                acreditada = acreditada,
                                creditosTotales = creditos,
                                mensualidad = mensualidad
                            )
                            val creada = createCarreraUseCase.execute(facId, nuevaCarrera)
                            println("Carrera creada: $creada")
                        }
                    }
                    "2" -> {
                        println("Ingrese ID de la facultad:")
                        val facId = readlnOrNull()?.trim()?.toIntOrNull() ?: -1
                        val carreras = getCarrerasByFacultadUseCase.execute(facId)
                        carreras.forEach { println(it) }
                    }
                    "3" -> {
                        println("Ingrese ID de la facultad a la que pertenece la carrera:")
                        val facId = readlnOrNull()?.trim()?.toIntOrNull() ?: -1
                        val facultad = facultadRepository.getFacultadById(facId)
                        if (facultad == null) {
                            println("Facultad no encontrada.")
                        } else {
                            println("Ingrese ID de la carrera a actualizar:")
                            val carId = readlnOrNull()?.trim()?.toIntOrNull() ?: -1
                            val carrera = carreraRepository.getCarreraById(facId, carId)
                            if (carrera == null) {
                                println("Carrera no encontrada.")
                            } else {
                                println("Nuevo nombre (actual: ${carrera.nombre}):")
                                val nombre = readlnOrNull()?.trim()?.ifBlank { carrera.nombre } ?: carrera.nombre
                                println("Nuevo año inicio (actual: ${carrera.anioInicio}):")
                                val anioInicio = readlnOrNull()?.trim()?.toIntOrNull() ?: carrera.anioInicio
                                println("¿Acreditada? (actual: ${carrera.acreditada}):")
                                val acreditada = readlnOrNull()?.trim()?.toBoolean() ?: carrera.acreditada
                                println("Créditos totales (actual: ${carrera.creditosTotales}):")
                                val creditos = readlnOrNull()?.trim()?.toIntOrNull() ?: carrera.creditosTotales
                                println("Mensualidad (actual: ${carrera.mensualidad}):")
                                val mensualidad = readlnOrNull()?.trim()?.toDoubleOrNull() ?: carrera.mensualidad

                                val actualizada = updateCarreraUseCase.execute(
                                    facId,
                                    carrera.copy(
                                        nombre = nombre,
                                        anioInicio = anioInicio,
                                        acreditada = acreditada,
                                        creditosTotales = creditos,
                                        mensualidad = mensualidad
                                    )
                                )
                                println("Carrera actualizada: $actualizada")
                            }
                        }
                    }
                    "4" -> {
                        println("Ingrese ID de la facultad de la carrera a eliminar:")
                        val facId = readlnOrNull()?.trim()?.toIntOrNull() ?: -1
                        println("Ingrese ID de la carrera a eliminar:")
                        val carId = readlnOrNull()?.trim()?.toIntOrNull() ?: -1
                        val eliminado = deleteCarreraUseCase.execute(facId, carId)
                        println(if (eliminado) "Carrera eliminada." else "No se pudo eliminar la carrera.")
                    }
                    else -> {}
                }
            }
            "0" -> {
                println("Saliendo...")
                break@loop
            }
            else -> println("Opción inválida")
        }
    }
}
