# Tratamiento de Errores en Kotlin

## Introducción
Este repositorio está dedicado a la práctica del manejo de errores en Kotlin utilizando Gradle. A través de ejemplos prácticos, aprenderás técnicas efectivas para gestionar excepciones, lo cual es esencial para desarrollar aplicaciones robustas y mantenibles en Kotlin.

## Estructura de Directorio
La estructura organizativa del proyecto es la siguiente:

- `/README.md`: Documentación inicial del proyecto.
-  `/app`: Directorio raíz que contiene todos los archivos del proyecto.
-  `/app/build.gradle`: Configuración de Gradle para manejar dependencias y otras configuraciones.
-  `/app/src/main/kotlin/org/example/App.kt`: Contiene el código principal que muestra cómo manejar diferentes tipos de errores.
-  `/app/src/test/kotlin/org/example/AppTest.kt`: Tests unitarios que validan el manejo de errores.

## Conceptos Previos
En Kotlin, el manejo de errores se realiza principalmente a través del uso de excepciones, que son condiciones de error que alteran el flujo normal de ejecución de un programa. Kotlin ofrece un modelo de excepciones similar al de Java, pero con mejoras y características específicas que facilitan la escritura de código seguro y claro. A continuación, se exploran algunos conceptos fundamentales asociados con el manejo de errores en Kotlin:

- **Excepciones Comprobadas y No Comprobadas**:
En Kotlin, a diferencia de Java, no hay una distinción obligatoria entre excepciones comprobadas (checked) y no comprobadas (unchecked). Todas las excepciones en Kotlin son no comprobadas, lo que significa que no es necesario declararlas o capturarlas obligatoriamente. Esto reduce la verbosidad del código y mejora su legibilidad, al mismo tiempo que permite a los desarrolladores manejar errores de manera más flexible.

- **Bloques Try-Catch-Finally**
El mecanismo principal para el manejo de errores en Kotlin es el bloque try-catch-finally, que se utiliza para capturar excepciones y ejecutar código, independientemente de si ocurre un error:

- `Try`: El bloque `try` contiene el código que puede causar una excepción. Si se lanza una excepción dentro de este bloque, la ejecución se transfiere al bloque catch correspondiente.
- `Catch`: Este bloque captura la excepción y contiene el código que se ejecuta cuando se captura la excepción. Puedes tener varios bloques `catch` para manejar diferentes tipos de excepciones de manera específica.
- `Finally`: El bloque finally se ejecuta después de los bloques `try` y `catch`, independientemente de si se lanzó una excepción. Es útil para realizar tareas de limpieza, como cerrar archivos o liberar recursos.

- **Excepciones Personalizadas**
Kotlin permite la creación de excepciones personalizadas mediante la definición de clases que heredan de la clase Exception o de cualquier subclase de esta. Las excepciones personalizadas son útiles para representar condiciones de error específicas de una aplicación, proporcionando más información sobre el error y facilitando la depuración.

- **La Propagación de Excepciones**
Las excepciones en Kotlin se pueden propagar hacia arriba en la pila de llamadas. Si un método no maneja una excepción, esta se propaga al método que lo llamó, y así sucesivamente, hasta que se encuentra un manejador adecuado o hasta que alcanza el nivel superior del programa, causando la terminación del programa. La propagación de excepciones permite a los desarrolladores organizar el manejo de errores en un nivel más alto de abstracción, manteniendo el código en los niveles inferiores más simple y enfocado en la lógica del negocio.

- **El uso de `try`como Expresión**
En Kotlin, `try` puede usarse como una expresión que devuelve un valor. Esto significa que `try` puede tener un valor de retorno que se puede utilizar directamente en una asignación o en cualquier expresión. Por ejemplo:

```kotlin
val result = try {
    count / divisor
} catch (e: ArithmeticException) {
    null
}
```

Esta característica hace que el código sea conciso y expresa claramente la intención de manejar el error directamente en el punto donde podría ocurrir.

Estos conceptos son esenciales para entender cómo Kotlin maneja el control de errores y excepciones. La habilidad para manejar errores de manera efectiva es crucial para desarrollar aplicaciones confiables y fáciles de mantener.

## Código Ejemplo
El código a continuación implementa varias funciones que demuestran el manejo de errores en Kotlin:

```kotlin
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
```

## Métodos Usados En El Ejemplo
En el ejemplo proporcionado, varios métodos demuestran técnicas eficaces de manejo de errores en Kotlin, utilizando un enfoque que es tanto práctico como instructivo.

- **checkAge**
Este método valida si una persona cumple con la edad mínima requerida y se encuentra en `App.kt`. Si la edad es menor que 18 años, lanza una `InvalidAgeException`, una excepción personalizada que proporciona una descripción clara del error. Esta excepción ayuda a asegurar que las reglas de negocio específicas se cumplan y facilita la identificación de errores de validación durante la depuración.

```kotlin
fun checkAge(age: Int) {
    if (age < 18) {
        throw InvalidAgeException("No tienes la edad suficiente.")
    }
}
```

- **mightThrowException**
Este método intenta acceder a un elemento de una lista usando un índice que está fuera de los límites permitidos, lo que típicamente resulta en una `IndexOutOfBoundsException`.

```kotlin
fun mightThrowException() {
    val numbers = listOf(1, 2, 3)
    println(numbers[5])  // Esto lanzará una excepción de índice fuera de límites
}
```
Al capturar y manejar esta excepción dentro del método, se previene que el programa falle abruptamente y se proporciona una salida de error que puede ayudar a diagnosticar el problema.

- **divide**
Este método realiza una división y maneja explícitamente el caso de división por cero, lanzando una `ArithmeticException` con un mensaje personalizado si el divisor es cero.

```kotlin
fun divide(a: Int, b: Int): Int {
    if (b == 0) {
        throw ArithmeticException("No se puede dividir por cero.")
    }
    return a / b
}
```
Este enfoque previene errores de tiempo de ejecución y hace que el error sea más comprensible para otros desarrolladores o para el usuario final, clarificando exactamente qué fue lo que salió mal.

- **readFile y writeFile**
Estos métodos interactúan con el sistema de archivos, uno leyendo y el otro escribiendo en archivos. Ambos métodos están diseñados para manejar `IOExceptions`, proporcionando información específica sobre el error mediante el lanzamiento de excepciones con mensajes claros.

```kotlin
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
```
- **main**
El método `main` actúa como el punto de entrada del programa y utiliza todos los métodos anteriores para demostrar cómo se manejan diferentes tipos de errores en la práctica.

## Código de Test
Los tests unitarios verifican la correcta implementación del manejo de errores:

```kotlin
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
```

## Ejecución desde una Terminal
Aquí te proporciono una guía completa desde la instalación de las herramientas necesarias como Java, Kotlin y Gradle, hasta la compilación y ejecución de un proyecto Kotlin usando Gradle desde una terminal Linux.

### 1.Instalacion de Java JDK 17
Java es un requisito previo para Gradle y Kotlin, ya que ambos se ejecutan en la JVM (Java Virtual Machine). Aquí te explico cómo instalar Java JDK 17:
#### Actualiza el paquete de listas de tu sistema
```bash
sudo apt update
```
#### Instala Java JDK 17
```bash
sudo apt install openjdk-17-jdk
```
#### Verificar la instalacion
```bash
java -version
```
### 2.Instalación de Kotlin
Kotlin puede ser instalado directamente usando el gestor de paquetes Snap en sistemas Linux:
```bash
sudo snap install kotlin --classic
```
Tambien se puede realizar la instalacion usando el paquete apt:
```bash
sudo apt install kotlin --classic
```
#### Verificar la instlación de Kotlin
```bash
kotlin -version
```
### 3.Instalacion de Gradle
Gradle es una herramienta poderosa para la automatización de la compilación que también facilita la gestión de dependencias y la ejecución de pruebas.
#### Instalar Gradle 
Gradle puede ser instalado directamente usando el gestor de paquetes Snap en sistemas Linux:
```bash
sudo snap install gradle --classic
```
Tambien se puede realizar la instalacion usando el paquete apt:
```bash
sudo apt install gradle --classic
```
#### Verificar la instlación de Gradle
```bash
gradle -version
```
### 4. Clonar el Repositorio
#### Si el código está disponible en GitHub, puedes clonar el repositorio utilizando Git.
#### Si aún no tienes Git instalado, puedes descargarlo desde:
#### https://git-scm.com/downloads
```bash
git clone https://github.com/sistemas-sw/impl-24.git
```
### 5. Cambiar al Directorio del Repositorio
#### Una vez clonado el repositorio, navega al directorio donde se encuentra el código:
```bash
cd impl-24/temas/errores/kotlin/
```
### 6.Compilacion del Proyecto con Gradle
Una vez en el directorio del proyecto, puedes compilar el proyecto usando Gradle:
```bash
gradle build
```
Este comando compilará el proyecto, incluyendo la resolución de dependencias y la compilación de todos los archivos Kotlin y recursos del proyecto.
### 7.Ejecución del Proyecto con Gradle
Para ejecutar el proyecto, puedes utilizar el siguiente comando si el proyecto está configurado para ejecutarse con Gradle:
```bash
gradle run
```
Este comando ejecutará la aplicación principal definida en tu configuración de Gradle.
### 7.Ejecución de Pruebas Unitarias con Gradle
Para ejecutar las pruebas unitarias del proyecto, utiliza:
```bash
gradle test
```
Este comando ejecutará todas las pruebas unitarias definidas en el proyecto, utilizando el framework de pruebas configurado (como JUnit o Kotlin Test). Gradle mostrará un resumen de las pruebas, indicando cuáles pasaron y cuáles fallaron.

## Errores Comunes y sus soluciones
Durante la instalación de Java, Gradle y Kotlin en sistemas Linux, pueden surgir algunos problemas comunes. Aquí te proporciono una guía paso a paso para resolver estos problemas, con comandos específicos que deberías ejecutar en la terminal para diagnosticar y corregir estos errores.
### 1.JAVA
#### 1.1.Error: Java no se encuentra después de la instalación
#### Síntomas: A pesar de instalar Java, los comandos como java -version no reconocen la instalación.
#### Soluciones
- **Verifica y configura las variables de entorno:**
```bash
echo $JAVA_HOME
echo $PATH
```
Asegúrate de que `JAVA_HOME` esté configurado correctamente y que el `PATH` incluya `$JAVA_HOME/bin`. Puedes agregar o modificar estas líneas en tu archivo `~/.bashrc` o `~/.profile`:

```bash
export JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64
export PATH=$JAVA_HOME/bin:$PATH
```
- **Aplica los cambios y verifica:**
```bash
source ~/.bashrc
java -version
```
#### 1.2.Error: La versión instalada de Java no es la esperada
#### Síntomas: java -version muestra una versión diferente a la que se esperaba instalar.
#### Soluciones:
- **Selecciona la versión correcta de Java:** 
```bash
sudo update-alternatives --config java
```

### 2.Gradle
#### 2.1.Error: Gradle no encuentra Java
#### Síntomas: Gradle no inicia porque no puede encontrar la JVM.
#### Soluciones:
- **Revisa y configura JAVA_HOME:**
```bash
echo $JAVA_HOME
export JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64
echo $JAVA_HOME
```
- **Reinicia la terminal o aplica los cambios:**
```bash
source ~/.bashrc
gradle -version
```
#### 2.2. Error: Problemas de permisos al instalar Gradle
#### Síntomas: Mensajes de error relacionados con permisos durante la instalación.
#### Soluciones:
- **Instala Gradle usando apt:**
```bash
sudo apt update
sudo apt install gradle
```
- **Verifica la instalación:**
```bash
gradle -version
```
### 3.Kotlin
### 3.1.Error: Kotlin no se ejecuta después de la instalación
### Síntomas: Los comandos de Kotlin, como kotlin -version, no funcionan o no muestran la versión instalada.
### Soluciones:
- **Verifica la instalación y configura el PATH si es necesario:**
```bash
kotlin -version
```
- **Si no funciona, intenta reinstalar Kotlin:**
```bash
sudo snap remove kotlin
sudo snap install kotlin --classic
kotlin -version
```

Estos comandos te ayudarán a manejar y corregir los problemas más comunes que puedas enfrentar al instalar Java, Gradle y Kotlin en tu sistema Linux. Mantener un registro de las versiones y las configuraciones de entorno puede evitar problemas futuros y facilitar la gestión de tu entorno de desarrollo.
