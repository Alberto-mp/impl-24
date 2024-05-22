package org.example
import java.io.File
import java.io.IOException

class App {
    val greeting: String
        get() = "Ejecucion de los diferentes ejemplos de manejo de errores."
}

class InvalidAgeException(message: String) : Exception(message)

fun checkAge(age: Int) {
    if (age < 18) {
        throw InvalidAgeException("No tienes la edad suficiente.")
    }
}

fun mightThrowException() {
    val numbers = listOf(1, 2, 3)
    println(numbers[5]) // Esto lanzará una excepción de índice fuera de límites
}

fun divide(a: Int, b: Int): Int {
    if (b == 0) {
        throw ArithmeticException("No se puede dividir por cero.")
    }
    return a / b
}

fun readFile(fileName: String): String {
    try {
        return File(fileName).readText()
    } catch (e: IOException) {
        throw IOException("No se pudo leer el archivo '$fileName'.")
    }
}

fun writeFile(fileName: String, content: String) {
    try {
        File(fileName).writeText(content)
    } catch (e: IOException) {
        throw IOException("No se pudo escribir en el archivo '$fileName'.")
    }
}

fun main() {
    val ejercicio1 = "Ejercicio 1: Manejo de excepción de división por cero"
    val ejercicio2 = "Ejercicio 2: Lanzar y capturar una excepción personalizada"
    val ejercicio3 = "Ejercicio 3: Manejo de excepción de E/S (Entrada/Salida)"
    val ejercicio4 = "Ejercicio 4: Propagación y manejo de excepciones"
    val ejercicio5 = "Ejercicio 5: Creación y manejo de una excepción personalizada de E/S"

    println(App().greeting)
    
    // Ejercicio 1: Manejo de excepción de división por cero
    println("\n$ejercicio1")
    try {
        val result = divide(10, 0)
        println("Resultado: $result")
    } catch (e: ArithmeticException) {
        println("Error: No se puede dividir por cero.")
    } finally {
        println("Esta línea siempre se ejecuta después del primer try-catch.")
    }
    
    // Ejercicio 2: Lanzar y capturar una excepción personalizada
    println("\n$ejercicio2")
    try {
        checkAge(16)
    } catch (e: InvalidAgeException) {
        println("Error: ${e.message}")
    }
    
    // Ejercicio 3: Manejo de excepción de E/S (Entrada/Salida)
    println("\n$ejercicio3")
    val fileName = "test.txt"
    try {
        val fileContent = readFile(fileName)
        println("Contenido del archivo: $fileContent")
    } catch (e: IOException) {
        println("Error: No se pudo leer el archivo '$fileName'.")
    }
    
    // Ejercicio 4: Propagación y manejo de excepciones
    println("\n$ejercicio4")
    try {
        mightThrowException()
    } catch (e: IndexOutOfBoundsException) {
        println("Error: Índice fuera de los límites permitidos.")
    }
    
    // Ejercicio 5: Creación y manejo de una excepción personalizada de E/S
    println("\n$ejercicio5")
    val newFileName = "new_file.txt"
    val newContent = "Este es un nuevo contenido para el archivo."
    try {
        writeFile(newFileName, newContent)
        println("Se ha creado el archivo '$newFileName' con éxito.")
    } catch (e: IOException) {
        println("Error: No se pudo escribir en el archivo '$newFileName'.")
    }
}
