package org.example

import java.util.*

// Punto de entrada de la aplicación
fun main(args: Array<String>) {
    // --------------------
    // SECCIÓN 1: VARIABLES
    // --------------------

    val inmutable: String = "Max Mateo Carrion Chida"
    var mutable: String = "Carrion"
    mutable = "Chida" // Esto es válido

    val ejemploVariable = " Max Mateo Carrion Chida "
    ejemploVariable.trim()

    val edadEjemplo: Int = 25
    val nombreProfesor: String = "Max Mateo Carrion Chida"
    val sueldo: Double = 1.2
    val estadoCivil: Char = 'C'
    val mayorEdad: Boolean = true
    val fechaNacimiento: Date = Date()

    // ---------------------
    // SECCIÓN 2: CONTROL DE FLUJO
    // ---------------------

    val estadoCivilWhen = "C"
    when (estadoCivilWhen) {
        "C" -> println("Casado")
        "S" -> println("Soltero")
        else -> println("No sabemos")
    }

    val esSoltero = (estadoCivilWhen == "S")
    val coqueteo = if (esSoltero) "Sí" else "No"

    // ----------------------
    // SECCIÓN 3: FUNCIONES
    // ----------------------

    imprimirNombre("MaX MaTeO CaRriOn ChiDA")
    calcularSueldo(10.00)
    calcularSueldo(10.00, 15.00, 20.00)
    calcularSueldo(10.00, bonoEspecial = 20.00)
    println(calcularSueldo(bonoEspecial = 20.00, sueldo = 10.00, tasa = 14.00))

    // ------------------------
    // SECCIÓN 4: CLASES Y OOP
    // ------------------------

    val suma = Suma(10, 20)
    println("Resultado de la suma: ${suma.sumar()}")
    println("Valor de PI: ${Suma.pi}")
    println("Historial de sumas: ${Suma.historialSumas}")
    Suma.agregarHistorial(30)
    println("Historial actualizado: ${Suma.historialSumas}")

    // ---------------------
    // SECCIÓN 5: COLECCIONES
    // ---------------------

    // Declaración de arreglos
    val arregloEstatico: Array<Int> = arrayOf(1, 2, 3)
    val arregloDinamico: ArrayList<Int> = arrayListOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)

    // forEach: Iterar elementos
    println("Usando forEach:")
    arregloDinamico.forEach { println("Valor actual: $it") }

    // map: Crear un nuevo arreglo transformado
    println("Usando map:")
    val arregloMapeado = arregloDinamico.map { it * 2 }
    println("Arreglo mapeado: $arregloMapeado")

    // filter: Filtrar elementos que cumplan una condición
    println("Usando filter:")
    val elementosFiltrados = arregloDinamico.filter { it > 5 }
    println("Elementos filtrados (mayores a 5): $elementosFiltrados")

    // any: ¿Algún elemento cumple la condición?
    val hayMayoresADiez = arregloDinamico.any { it > 10 }
    println("¿Hay elementos mayores a 10? $hayMayoresADiez")

    // all: ¿Todos los elementos cumplen la condición?
    val todosMayoresACinco = arregloDinamico.all { it > 5 }
    println("¿Todos son mayores a 5? $todosMayoresACinco")
}

// -------------------------
// FUNCIONES
// -------------------------

fun imprimirNombre(nombre: String): Unit {
    fun otraFuncionAdentro() {
        println("Otra función interna")
    }
    println("Nombre: ${nombre.uppercase()}")
    otraFuncionAdentro()
}

fun calcularSueldo(
    sueldo: Double,
    tasa: Double = 12.00,
    bonoEspecial: Double? = null
): Double {
    return if (bonoEspecial == null) {
        sueldo * (100 / tasa)
    } else {
        sueldo * (100 / tasa) * bonoEspecial
    }
}

// -------------------------
// CLASES Y HERENCIA
// -------------------------

abstract class Numeros(
    protected val numeroUno: Int,
    protected val numeroDos: Int
) {
    init {
        println("Inicializando la clase Numeros")
    }
}

class Suma(
    unoParametro: Int,
    dosParametro: Int
) : Numeros(unoParametro, dosParametro) {
    init {
        println("Inicializando la clase Suma")
    }

    fun sumar(): Int {
        val total = numeroUno + numeroDos
        agregarHistorial(total)
        return total
    }

    companion object {
        val pi = 3.14
        val historialSumas = arrayListOf<Int>()

        fun agregarHistorial(valor: Int) {
            historialSumas.add(valor)
        }
    }
}
