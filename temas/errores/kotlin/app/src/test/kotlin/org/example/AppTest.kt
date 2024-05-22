package org.example

import kotlin.test.Test
import kotlin.test.assertNotNull
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import java.io.IOException

class AppTest {
    @Test 
    fun appHasAGreeting() {
        val classUnderTest = App()
        assertNotNull(classUnderTest.greeting, "app should have a greeting")
    }
    
    @Test
    fun testDivisionByZero() {
        assertFailsWith<ArithmeticException> {
            divide(10, 0)
        }
    }
    
    @Test
    fun testInvalidAgeException() {
        val exception = assertFailsWith<InvalidAgeException> {
            checkAge(16)
        }
        assertEquals("No tienes la edad suficiente.", exception.message, "Unexpected exception message")
    }
    
    @Test
    fun testIOExceptionReading() {
        val fileName = "nonexistent_file.txt"
        val exception = assertFailsWith<IOException> {
            readFile(fileName)
        }
        assertEquals("No se pudo leer el archivo '$fileName'.", exception.message)
    }
    
    @Test
    fun testIndexOutOfBoundsException() {
        assertFailsWith<IndexOutOfBoundsException> {
            mightThrowException()
        }
    }
    
    // Aquí puedes agregar un test para writeFile si decides simular la operación de escritura
    @Test
    fun testIOExceptionWriting() {
        val fileName = "path/to/readonly/directory/temp_file.txt"
        val content = "Some content"
        val exception = assertFailsWith<IOException> {
            writeFile(fileName, content)
        }
        assertEquals("No se pudo escribir en el archivo '$fileName'.", exception.message)
    }
}
