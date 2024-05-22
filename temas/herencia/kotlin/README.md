# Herencia en Kotlin

Este directorio contiene ejemplos de cómo implementar el concepto de herencia utilizando Kotlin.

## Estructura del Directorio

- [`app/`](app/): Contiene el frontend y el backend implementado en JavaScript.
- [`src/main/kotlin`](src/main/kotlin): Contiene el código de ejemplo en kotlin.
- [`src/test/kotlin`](src/test/kotlin): Contiene los test para el código ejemplo en kotlin.
- [`pom.xml`](pom.xml): Para pasar los test, define la configuración del proyecto, incluidos los plugins de Maven y las configuraciones de build específicas necesarias para compilar, testear y ejecutar el código de Kotlin.
- [`Dockerfile`](Dockerfile): Configuración para Docker.
- [`APP.tf`](APP.tf): Configuración para Terraform.

## Explicación teórica

Antes de entrar en detalle con el contenido del directorio, se va a realizar una breve explicación teórica de los conceptos básicos de herencia con Kotlin.

### `interface`

Una interfaz en Kotlin puede contener declaraciones de métodos abstractos, así como implementaciones de métodos (algo nuevo para las interfaces). No se puede declarar una instancia de una interfaz.

```kt
interface Volador {
    fun volar()
    fun aterrizar() {
        println("Aterrizando.")
    }
}
```
Las clases que implementan esta interfaz deben proporcionar una implementación para `volar`, pero pueden utilizar la implementación por defecto de `aterrizar`.

### `open class`

En Kotlin, todas las clases son finales por defecto, lo que significa que no pueden ser heredadas a menos que se marquen explícitamente como open. Esto es diferente de otros lenguajes como Java, donde las clases son heredables por defecto.

```kt
open class Animal {
    open fun comer() {
        println("Este animal está comiendo")
    }
}
```
En este ejemplo, la clase `Animal` es `open`, lo que significa que otras clases pueden heredar de ella. También, el método `comer` es `open`, lo que permite que las clases hijas lo sobrescriban.

### `abstract class`

Una clase abstracta es una clase que no puede ser instanciada por sí misma y debe ser heredada por otras clases. Los métodos o propiedades declarados como abstractos deben ser implementados por las clases derivadas.

```kt
abstract class Vehiculo {
    abstract fun acelerar()
    
    fun detener() {
        println("El vehículo se detiene.")
    }
}
```
Aquí, cualquier clase que herede de `Vehiculo` debe implementar el método `acelerar`, mientras que `detener` ya tiene una implementación por defecto.

### `override`

La palabra clave `override` se utiliza en una clase derivada para indicar que está proporcionando una nueva implementación de un método que está definido en la clase base.

```kt
class Perro : Animal() {
    override fun comer() {
        println("El perro está comiendo croquetas")
    }
}
```
Aquí, `Perro` sobrescribe el método `comer` de su superclase `Animal`.

### `super`

La palabra clave `super` se utiliza para referirse a la superclase inmediata de una clase. Es útil para acceder a implementaciones de la superclase que han sido sobrescritas.

```kt
class Gato : Animal() {
    override fun comer() {
        super.comer()  // Llama al método comer de Animal
        println("El gato come además pescado")
    }
}
```
En este ejemplo, `Gato` llama al método `comer` de `Animal` antes de agregar su propio comportamiento.

### `interface` vs `open class` vs `abstract class`

- **Interfaces**: No tienen estado, pueden definir un "contrato" con implementaciones por defecto, y permiten la herencia múltiple de comportamiento.
- **Open classes**: Pueden ser heredadas y modificadas, contienen estado, y restringen la herencia a una sola clase base.
- **Abstract classes**: No pueden ser instanciadas, deben ser extendidas, pueden contener métodos abstractos que obligan a una implementación en las clases derivadas, y pueden contener estado.

## Contenido

### Código ejemplos

Para representar la herencia con Kotlin, se ha decidido representar diferentes miembros de un grupo musical, incluyendo la gestión y la coordinación de sus actuaciones. A continuación se puede ver el código implementado:

[**MusicBandMembers.kt**](src/main/kotlin/MusicBandMembers.kt)

```kt
open class miembroGrupo(val name: String) {
    open fun actua() {
        println("$name hace su actuación,")
    }

    open fun preparacion() {
        println("$name se prepara para la actuación.")
    }
}

abstract class musicoConInstrumento(name:String) : miembroGrupo(name) {
    abstract fun tocaInstrumento()

    override fun actua() {
        super.actua()
        tocaInstrumento()
    }
}

interface Vocalista {
    fun canta()
}

class Cantante(name: String) : miembroGrupo(name), Vocalista {
    override fun actua() {
        super.actua()
        canta()
    }

    override fun preparacion() {
        println("$name calienta sus cuerdas vocales.")
    }

    override fun canta() {
        println("$name tiene una voz de oro!")
    }
}

class Guitarrista(name: String) : musicoConInstrumento(name) {

    override fun preparacion() {
        println("$name comprueba la afinación de su guitarra.")
    }

    override fun tocaInstrumento() {
        println("$name toca la guitarra fenomenal.")
    }
}

class Pianista(name: String) : musicoConInstrumento(name) {

    override fun preparacion() {
        println("$name calienta tocando un solo de piano.")
    }

    override fun tocaInstrumento() {
        println("$name desliza sus dedos sobre las teclas con gran destreza.")
    }
}

class CantanteGuitarrista(name: String) : musicoConInstrumento(name), Vocalista {
    private val cantante = Cantante(name)
    private val guitarrista = Guitarrista(name)

    override fun preparacion() {
        println("$name realiza una doble preparacion de voz y guitarra.")
        cantante.preparacion()
        guitarrista.preparacion()
    }

    override fun actua() {
        super.actua()
        canta()
    }

    override fun tocaInstrumento() {
        guitarrista.tocaInstrumento()
    }

    override fun canta() {
        cantante.canta()
    }
}

class Manager {
    fun preparaGrupo(miembros: List<miembroGrupo>) {
        println("El manager dice a la banda que se prepare.")
        println("------------------------------------------")
        miembros.forEach { 
            it.preparacion()
        }
        println("------------------------------------------")
        println("La banda está lista para actuar.")
        println("==========================================")
    }

    fun actuaGrupo(miembros: List<miembroGrupo>) {
        println("El manager da la señal para que comience la actuación.")
        println("------------------------------------------")
        miembros.forEach {
            it.actua()
        }
        println("------------------------------------------")
        println("¡La actuación ha sido un exitazo!")
    }
}
```
`open class miembroGrupo`

- **Propósito**: Sirve como clase base para cualquier miembro del grupo.
- **Propiedades**:
    - name: Nombre del miembro del grupo.
- **Métodos**:
    - actua(): Imprime un mensaje indicando que el miembro está actuando.
    - preparacion(): Imprime un mensaje indicando que el miembro se está preparando para actuar.
  
`abstract class musicoConInstrumento`
- **Propósito**: Extiende miembroGrupo para representar músicos que tocan un instrumento, es abstracta porque requiere la implementación del método tocaInstrumento().
- **Métodos**:
    - tocaInstrumento(): Método abstracto que debe ser implementado por las subclases, específico para el instrumento que tocan.
    - actua(): Sobrescribe actua() de miembroGrupo para incluir tocar el instrumento después de la actuación básica.

`interface Vocalista`
- **Propósito**: Define un contrato para cualquier miembro del grupo que sea vocalista.
- **Método**:
    - canta(): Método que deben implementar los vocalistas.

`class Cantante`
- **Propósito**: Extiende miembroGrupo e implementa Vocalista, representando a un cantante.
- **Métodos**:
    - actua(): Incluye cantar después de la actuación básica.
    - preparacion(): Personaliza la preparación enfocándose en calentar las cuerdas vocales.
    - canta(): Implementa el método de la interfaz Vocalista.

`class Guitarrista`
- **Propósito**: Extiende musicoConInstrumento, específicamente para un guitarrista.
- **Métodos**:
    - preparacion(): Personaliza la preparación para verificar la afinación de la guitarra.
    - tocaInstrumento(): Implementa el método abstracto para tocar la guitarra.

`class Pianista`
- **Propósito**: Extiende musicoConInstrumento, específicamente para un pianista.
- **Métodos**:
    - preparacion(): Personaliza la preparación para calentar tocando un solo de piano.
    - tocaInstrumento(): Implementa el método abstracto para tocar el piano.

`class CantanteGuitarrista`
- **Propósito**: Extiende musicoConInstrumento e implementa Vocalista, representando a alguien que canta y toca la guitarra.
- **Propiedades**:
    - cantante: Instancia de Cantante para manejar las acciones de canto.
    - guitarrista: Instancia de Guitarrista para manejar las acciones de tocar la guitarra.
- **Métodos**:
    - preparacion(): Combina la preparación vocal y de guitarra.
    - actua(): Combina actuar (incluyendo cantar).
    - tocaInstrumento() y canta(): Delega estas funciones a las instancias internas respectivas.

`class Manager`
- **Propósito**: Encargado de coordinar y preparar a los miembros del grupo para actuar.
- **Métodos**:
    - preparaGrupo(miembros: List<miembroGrupo>): Prepara a todos los miembros del grupo para la actuación.
    - actuaGrupo(miembros: List<miembroGrupo>): Inicia y coordina la actuación del grupo.

## Código tests

Para los tests, se ha utilizado Maven y JUnit para gestionar las dependencias y ejecutar las pruebas de forma automatizada, permitiendo una verificación efectiva del comportamiento de cada clase dentro del contexto definido en el código de la aplicación musical.

[**TestCantante.kt**](src/test/kotlin/TestCantante.kt)

```kt

class CantanteTest {
    private val standardOut = System.out
    private val outputStreamCaptor = ByteArrayOutputStream()

    @BeforeEach
    fun setUp() {
        System.setOut(PrintStream(outputStreamCaptor))
    }

    @AfterEach
    fun tearDown() {
        System.setOut(standardOut)
    }

    @Test
    fun testCanta() {
        val cantante = Cantante("Camarón")
        cantante.canta()
        
        assertEquals("Camarón tiene una voz de oro!\n", outputStreamCaptor.toString())
    }

    @Test
    fun testActua() {
        val cantante = Cantante("Camarón")
        cantante.actua()
        
        assertEquals("Camarón hace su actuación,\nCamarón tiene una voz de oro!\n", outputStreamCaptor.toString())
    }

    @Test
    fun testPreparacion() {
        val cantante = Cantante("Camarón")
        cantante.preparacion()
        
        assertEquals("Camarón calienta sus cuerdas vocales.\n", outputStreamCaptor.toString())
    }
}
```
- `testCanta()`: Verifica que el método canta() de la clase Cantante produce la salida correcta.
- `testActua()`: Comprueba que la combinación de métodos actua() y canta() en la clase Cantante genera la secuencia de salida esperada.
- `testPreparacion()`: Confirma que el método preparacion() en Cantante ejecuta y retorna correctamente el proceso de calentamiento vocal.

[**TestGuitarrista.kt**](src/test/kotlin/TestGuitarrista.kt)

```kt

class GuitarristaTest {
    private val standardOut = System.out
    private val outputStreamCaptor = ByteArrayOutputStream()

    @BeforeEach
    fun setUp() {
        System.setOut(PrintStream(outputStreamCaptor))
    }

    @AfterEach
    fun tearDown() {
        System.setOut(standardOut)
    }

    @Test
    fun testTocaInstrumento() {
        val guitarrista = Guitarrista("Paco")
        guitarrista.tocaInstrumento()
        
        assertEquals("Paco toca la guitarra fenomenal.\n", outputStreamCaptor.toString())
    }

    @Test
    fun testActua() {
        val guitarrista = Guitarrista("Paco")
        guitarrista.actua()
        
        assertEquals("Paco hace su actuación,\nPaco toca la guitarra fenomenal.\n", outputStreamCaptor.toString())
    }

    @Test
    fun testPreparacion() {
        val guitarrista = Guitarrista("Paco")
        guitarrista.preparacion()
        
        assertEquals("Paco comprueba la afinación de su guitarra.\n", outputStreamCaptor.toString())
    }
}
```
- `testTocaInstrumento()`: Evalúa que el método tocaInstrumento() en la clase Guitarrista produce la salida adecuada.
- `testActua()`: Testea que la ejecución del método actua() en Guitarrista llama adecuadamente a los métodos subyacentes y resulta en la secuencia de impresión esperada.
- `testPreparacion()`: Asegura que la preparación de Guitarrista ajusta y verifica correctamente la afinación de la guitarra.


[**TestPianista.kt**](src/test/kotlin/TestPianista.kt)

```kt

class PianistaTest {
    private val standardOut = System.out
    private val outputStreamCaptor = ByteArrayOutputStream()

    @BeforeEach
    fun setUp() {
        System.setOut(PrintStream(outputStreamCaptor))
    }

    @AfterEach
    fun tearDown() {
        System.setOut(standardOut)
    }

    @Test
    fun testTocaInstrumento() {
        val pianista = Pianista("Diego")
        pianista.tocaInstrumento()
        
        assertEquals("Diego desliza sus dedos sobre las teclas con gran destreza.\n", outputStreamCaptor.toString())
    }

    @Test
    fun testActua() {
        val pianista = Pianista("Diego")
        pianista.actua()
        
        assertEquals("Diego hace su actuación,\nDiego desliza sus dedos sobre las teclas con gran destreza.\n", outputStreamCaptor.toString())
    }

    @Test
    fun testPreparacion() {
        val pianista = Pianista("Diego")
        pianista.preparacion()
        
        assertEquals("Diego calienta tocando un solo de piano.\n", outputStreamCaptor.toString())
    }
}
```
- `testTocaInstrumento()`: Verifica que el método tocaInstrumento() del Pianista proporciona la salida esperada, demostrando habilidad en el piano.
- `testActua()`: Confirma que Pianista actúa correctamente, integrando el acto de tocar el piano durante la actuación.
- `testPreparacion()`: Evalúa que el Pianista se prepara adecuadamente, calentando con un solo de piano.

[**TestCantanteGuitarrista.kt**](src/test/kotlin/TestCantanteGuitarrista.kt)

```kt

class TestCantanteGuitarrista {
    private val standardOut = System.out
    private val outputStreamCaptor = ByteArrayOutputStream()

    @BeforeEach
    fun setUp() {
        System.setOut(PrintStream(outputStreamCaptor))
    }

    @AfterEach
    fun tearDown() {
        System.setOut(standardOut)
    }

    @Test
    fun testTocaInstrumento() {
        val cantanteGuitarrista = CantanteGuitarrista("David")
        cantanteGuitarrista.tocaInstrumento()
        
        assertEquals("David toca la guitarra fenomenal.\n", outputStreamCaptor.toString())
    }

    @Test
    fun testCanta() {
        val cantanteGuitarrista = CantanteGuitarrista("David")
        cantanteGuitarrista.canta()
        
        assertEquals("David tiene una voz de oro!\n", outputStreamCaptor.toString())
    }

    @Test
    fun testActua() {
        val cantanteGuitarrista = CantanteGuitarrista("David")
        cantanteGuitarrista.actua()
        
        assertEquals("David hace su actuación,\nDavid toca la guitarra fenomenal.\nDavid tiene una voz de oro!\n", outputStreamCaptor.toString())
    }

    @Test
    fun testPreparacion() {
        val cantanteGuitarrista = CantanteGuitarrista("David")
        cantanteGuitarrista.preparacion()
        
        assertEquals("David realiza una doble preparacion de voz y guitarra.\nDavid calienta sus cuerdas vocales.\nDavid comprueba la afinación de su guitarra.\n", outputStreamCaptor.toString())
    }
}
```
- `testTocaInstrumento()`: Comprueba que el método tocaInstrumento() en CantanteGuitarrista refleja la habilidad para tocar la guitarra.
- `testCanta()`: Verifica que el método canta() en CantanteGuitarrista mantiene la calidad vocal esperada.
- `testActua()`: Testea la actuación completa de CantanteGuitarrista, asegurando que se integran adecuadamente el canto y el toque de guitarra.
- `testPreparacion()`: Examina la preparación doble (vocal y guitarra) y su correcta ejecución.

[**TestManager.kt**](src/test/kotlin/TestManager.kt)

```kt

class TestManager {
    private val manager = Manager()
    private val originalOut = System.out
    private val outputStreamCaptor = ByteArrayOutputStream()

    @BeforeEach
    fun setUp() {
        System.setOut(PrintStream(outputStreamCaptor))
    }

    @AfterEach
    fun tearDown() {
        System.setOut(originalOut)
    }

    @Test
    fun testPreparaGrupo() {
        val miembros = listOf(
            Cantante("John"),
            Guitarrista("Paul"),
            Pianista("George")
        )
        manager.preparaGrupo(miembros)

        val expectedOutput = "El manager dice a la banda que se prepare.\n" +
                             "------------------------------------------\n" +
                             "John calienta sus cuerdas vocales.\n" +
                             "Paul comprueba la afinación de su guitarra.\n" +
                             "George calienta tocando un solo de piano.\n" +
                             "------------------------------------------\n" +
                             "La banda está lista para actuar.\n" +
                             "==========================================\n"

        assertTrue(outputStreamCaptor.toString().contains(expectedOutput))
    }

    @Test
    fun testActuaGrupo() {
        val miembros = listOf(
            Cantante("John"),
            Guitarrista("Paul"),
            Pianista("George")
        )
        manager.actuaGrupo(miembros)

        val expectedOutput = "El manager da la señal para que comience la actuación.\n" +
                             "------------------------------------------\n" +
                             "John hace su actuación,\n" +
                             "John tiene una voz de oro!\n" +
                             "Paul hace su actuación,\n" +
                             "Paul toca la guitarra fenomenal.\n" +
                             "George hace su actuación,\n" +
                             "George desliza sus dedos sobre las teclas con gran destreza.\n" +
                             "------------------------------------------\n" +
                             "¡La actuación ha sido un exitazo!\n"

        assertTrue(outputStreamCaptor.toString().contains(expectedOutput))
    }
}
```

- `testPreparaGrupo()`: Verifica que el método preparaGrupo() del Manager organiza y prepara correctamente a cada miembro del grupo, produciendo la secuencia de preparación deseada.
- `testActuaGrupo()`: Confirma que el método actuaGrupo() del Manager coordina una actuación exitosa del grupo, asegurando que cada miembro actúe conforme a su rol.



## Código APP Web

Para la aplicación web se ha usado Node.js y React, los ficheros que conforman la aplicación son los siguientes:

[**package.json**](app/src/package.json)

Es un archivo de configuración esencial en proyectos que utilizan Node.js y React. Define el nombre, versión, punto de entrada principal del código, scripts para operaciones comunes como iniciar, construir, probar y desplegar la aplicación, así como las dependencias necesarias para el funcionamiento del servidor y la interfaz de usuario. 

[**backend.js**](app/src/backend.js)

Es un script del servidor creado con Node.js que utiliza el framework Express para manejar solicitudes HTTP. Define --Completar--

[**App.js**](app/src/App.js)

Es un componente principal en React que gestiona la interfaz de usuario para visualizar y ejecutar código y pruebas de Ruby mediante llamadas API. El usuario puede introducir nuevos miembros a la banda introduciendo su nombre y rol. Al seleccionar ejecutar se crea un archivo de Kotlin que contiene el main que será ejecutado y lo ejecutará, mostrando el resultado de la ejecución que es una actuación de la banda.

[**index.js**](app/src/index.js)

Es el punto de entrada para la aplicación React, donde se realiza la renderización del componente principal App en el DOM. Utiliza ReactDOM.render para inyectar el componente App dentro del elemento HTML con id root.

[**index.html**](app/public/index.html)

Este sirve como plantilla base para la aplicación React, definiendo el esqueleto HTML sobre el cual React construirá la interfaz de usuario. Contiene un div con id root, que es el punto de montaje para el componente React principal (App) renderizado por index.js.

## Cómo ejecutar los test

### Requisitos

- JDK mínimo: JDK 17
- Compilador de Kotlin para JVM: **`kotlinc-jvm`**
- Docker
- Terraform

### -EJECUCIÓN SIMPLE DESDE TERMINAL-

1. Nos movemos al directorio de trabajo en caso de no estar ya en él:
    ```bash
    cd temas/herencia/kotlin
    ```
2. Ejecutamos los test con ruby:
    ```bash
    mvn test 
    ```
   
Tras seguir los dos pasos anteriores podrás visualizar en la terminal los resultados de los tests.

## Ejecución de ejemplos con app web

1. Construye y corre el contenedor Docker con Terraform:

   ```bash
   cd temas/abstraccion/ruby # Nos movemos al directorio de trabajo
   terraform init # Iniciamos terraform
   terraform apply # Lanzamos el docker
   ```
2. Accede desde tu navegador a la dirección localhost:3100

3. Introduce tantos integrantes a la banda como quieras seleccionando un nombre y un rol.

4. Para introducir más integrantes pulse el botón "Añadir más"

5. Cuando haya terminado de añadir miembros, pulse "Ejecutar" y tras unos segundos verá el resultado de la actuación en la pantalla.

6. Cuando haya terminado para evitar gasto de recursos innecesarios no olvide ejecutar la siguiente orden en el directorio de trabajo desde terminal:

```bash
terraform destroy
```
Esto eliminará el docker que se levantó.

