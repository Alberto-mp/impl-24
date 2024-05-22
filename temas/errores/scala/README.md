# Errores en Scala

Este directorio contiene ejemplos de cómo usar gestión de errores en Scala.

## Estructura del Directorio


- [`src/main/scala/example`](src/main/scala/example): Contiene el código de ejemplo en Scala y la aplicación de terminal.
- [`src/test/scala/example`](src/test/scala/example): Contiene los test para el código ejemplo en Scala.
- [`build.sbt`](build.sbt): Archivo de configuración de sbt que define las dependencias y configuraciones del proyecto.
- [`project/build.properties`](project/build.properties): Archivo en el que se especifica la versión de sbt que utilizará el proyecto.

## Explicación teórica

En Scala, la gestión de errores se realiza utilizando varios enfoques distintos, cada uno adecuado para diferentes situaciones. Estos enfoques incluyen `Option`, `Either`, `Try`, y el uso de excepciones tradicionales.

### 1. `Option`
`Option` se utiliza cuando un valor puede estar presente o no. Es una forma de evitar el uso de `null` y maneja la presencia o ausencia de un valor de manera segura. `Option` tiene dos posibles valores:
- `Some(value)`: Indica la presencia de un valor.
- `None`: Indica la ausencia de un valor.

#### Ejemplo:
```scala
def obtenerNombre(id: Int): Option[String] = {
  val nombres = Map(1 -> "Alice", 2 -> "Bob")
  nombres.get(id)
}

val nombre1 = obtenerNombre(1) // Some("Alice")
val nombre2 = obtenerNombre(3) // None
```

### 2. `Either`
`Either` se utiliza para representar un valor que puede ser de uno de dos tipos, comúnmente usado para la gestión de errores. `Either` tiene dos subclases:
- `Left(value)`: Generalmente utilizado para contener un error.
- `Right(value)`: Generalmente utilizado para contener un valor exitoso.

#### Ejemplo

```scala
def dividir(a: Int, b: Int): Either[String, Int] = {
  if (b == 0) Left("División por cero")
  else Right(a / b)
}

val resultado1 = dividir(4, 2) // Right(2)
val resultado2 = dividir(4, 0) // Left("División por cero")
```

### 3. `Try`
`Try` es una clase utilizada para manejar excepciones. Permite capturar y manejar excepciones de forma funcional. `Try` tiene dos subclases:
- `Success(value)`: Indica un resultado exitoso.
- `Failure(exception)`: Indica un fallo con una excepción.

#### Ejemplo

```scala
import scala.util.{Try, Success, Failure}

def parsearEntero(s: String): Try[Int] = Try(s.toInt)

val resultado1 = parsearEntero("123") // Success(123)
val resultado2 = parsearEntero("abc") // Failure(java.lang.NumberFormatException)
```

### 4. Excepciones
Scala también permite el uso de excepciones tradicionales, similar a Java. Aunque es una práctica común en muchos lenguajes de programación, en Scala se recomienda utilizar excepciones solo para errores inesperados o excepciones no recuperables, en lugar de para el control de flujo normal del programa.

#### Ejemplo

```scala
def abrirArchivo(nombre: String): String = {
  val fuente = scala.io.Source.fromFile(nombre)
  try fuente.mkString
  finally fuente.close()
}

try {
  val contenido = abrirArchivo("archivo.txt")
  println(contenido)
} catch {
  case e: java.io.FileNotFoundException => println("Archivo no encontrado")
  case e: Exception => println("Ocurrió un error: " + e.getMessage)
}
```

### Comparación de Enfoques
- **Option**: Ideal para valores opcionales, evitando `null` y manejando la ausencia de valor de manera segura.
- **Either**: Adecuado para funciones que pueden fallar y necesitan comunicar el tipo de error, proporcionando una clara distinción entre éxito y fallo.
- **Try**: Útil para manejo de excepciones en bloques de código que pueden lanzar excepciones, permitiendo un manejo de errores más funcional.
- **Excepciones**: Mejor evitar para control de flujo normal debido a que rompen la transparencia referencial, pero útiles para manejar errores inesperados o no recuperables.

Cada uno de estos enfoques proporciona una manera distinta de manejar errores y excepciones, permitiendo a los desarrolladores elegir el más adecuado según el contexto y la naturaleza del problema que se está resolviendo.


## Contenido

### Código ejemplos

Para representar errores en Scala, se ha decidido representar un sistema de reservas de vuelos.

### Case Classes Reserva y Vuelo

[`Reserva.scala`](src/main/scala/example/Reserva.scala)
```scala
case class Reserva(numeroVuelo: String, nombrePasajero: String)
```
[`Vuelo.scala`](src/main/scala/example/Vuelo.scala)

```scala
case class Vuelo(numeroVuelo: String, asientosDisponibles: Int)
```
Las **case classes** en **Scala**, como `Reserva` y `Vuelo`, son estructuras inmutables y comparables por valor. `Reserva` almacena el número de vuelo y el nombre del pasajero, mientras que `Vuelo` guarda el número de vuelo y los asientos disponibles. Estas clases facilitan la manipulación y comparación de datos relacionados con vuelos y reservas.

### Sistema de reservas

[`SistemaReservaVuelos.scala`](src/main/scala/example/SistemaReservaVuelos.scala)

```scala
import scala.collection.mutable
import scala.util.{Try, Success, Failure}

object SistemaReservaVuelos {
  
    // Base de datos
    private val vuelos: mutable.Map[String, Vuelo] = mutable.Map()
    private val asientosOriginales: mutable.Map[String, Int] = mutable.Map()
    private val reservas: mutable.Map[String, mutable.Map[String, Reserva]] = mutable.Map()

    // Método para añadir vuelos
    def agregarVuelo(numeroVuelo: String, asientosDisponibles: Int): Either[String, String] = {
        if (numeroVuelo.isEmpty || asientosDisponibles < 0) {
            Left("Detalles del vuelo inválidos.")
        } else {
            vuelos.get(numeroVuelo) match {
                case Some(_) => Left("El vuelo ya existe.")
                case None => 
                    vuelos(numeroVuelo) = Vuelo(numeroVuelo, asientosDisponibles)
                    asientosOriginales(numeroVuelo) = asientosDisponibles
                    Right(s"Vuelo $numeroVuelo agregado exitosamente con $asientosDisponibles asientos.")
            }
        }
    }

    // Método para buscar vuelo
    def buscarVuelo(numeroVuelo: String): Option[Vuelo] = {
        vuelos.get(numeroVuelo)
    }

    // Método para realizar reservas
    def reservarVuelo(numeroVuelo: String, nombrePasajero: String): Either[String, Reserva] = {
        vuelos.get(numeroVuelo) match {
            case Some(vuelo) if vuelo.asientosDisponibles > 0 =>
                val nuevaReserva = Reserva(numeroVuelo, nombrePasajero)
                val reservasVuelo = reservas.getOrElseUpdate(numeroVuelo, mutable.Map())
                reservasVuelo.get(nombrePasajero) match {
                    case Some(_) => Left("Ya existe una reserva para este pasajero.")
                    case None =>
                        vuelos.update(numeroVuelo, vuelo.copy(asientosDisponibles = vuelo.asientosDisponibles - 1))
                        reservasVuelo(nombrePasajero) = nuevaReserva
                        Right(nuevaReserva)
                }
            case Some(_) =>
                Left(s"No hay asientos disponibles en el vuelo $numeroVuelo.")
            case None =>
                Left(s"Vuelo $numeroVuelo no encontrado.")
        }
    }

    // Método para cancelar reserva
    def cancelarReserva(reserva: Reserva): Either[String, String] = {
        if (reserva.numeroVuelo.isEmpty || reserva.nombrePasajero.isEmpty) {
            Left("Detalles de la reserva inválidos.")
        } else {
            vuelos.get(reserva.numeroVuelo) match {
                case Some(vuelo) =>
                    reservas.get(reserva.numeroVuelo).flatMap(_.get(reserva.nombrePasajero)) match {
                        case Some(_) =>
                            vuelos.update(reserva.numeroVuelo, vuelo.copy(asientosDisponibles = vuelo.asientosDisponibles + 1))
                            reservas(reserva.numeroVuelo).remove(reserva.nombrePasajero)
                            Right("Reserva cancelada exitosamente.")
                        case None =>
                            Left("No existe tal reserva.")
                    }
                case None =>
                    Left("Vuelo no encontrado.")
            }
        }
    }

    // Método para eliminar un vuelo
    def eliminarVuelo(numeroVuelo: String): Either[String, String] = {
        vuelos.get(numeroVuelo) match {
            case Some(vuelo) if vuelo.asientosDisponibles == asientosOriginales(numeroVuelo) =>
                vuelos.remove(numeroVuelo)
                asientosOriginales.remove(numeroVuelo)
                reservas.remove(numeroVuelo)
                Right(s"Vuelo $numeroVuelo eliminado exitosamente.")
            case Some(_) =>
                Left("No se puede eliminar el vuelo con reservas activas.")
            case None =>
                Left("Vuelo no encontrado.")
        }
    }
}

```

`SistemaReservaVuelos.scala` implementa un sistema de manejo de reservas de vuelos en Scala usando estructuras de datos mutables. Este contiene funcionalidades para agregar, buscar, reservar, cancelar y eliminar vuelos. Utiliza manejo de errores con **Either** para validar operaciones y **Option** para búsquedas seguras de vuelos y reservas, asegurando la integridad de los datos y la gestión efectiva de las disponibilidades y reservaciones.

### Aplicación de terminal en MAIN

[`MAIN.scala`](src/main/scala/example/MAIN.scala)

```scala
import scala.io.StdIn
import scala.sys.process._
import scala.util.{Try, Success, Failure}

object MAIN {
  def main(args: Array[String]): Unit = {
    var continuar = true
    limpiarPantalla()
    while (continuar) {
      println("\nBienvenido al Sistema de Reserva de Vuelos")
      println("------------------------------------------")
      println("¿Eres Administrador, Cliente, o deseas Salir? (Administrador/Cliente/Salir)")
      val tipoUsuario = StdIn.readLine().trim.toLowerCase
      limpiarPantalla()
      tipoUsuario match {
        case "administrador" => 
          menuAdministrador()
        case "cliente" => 
          menuCliente()
        case "salir" =>
          println("Gracias por usar el sistema. ¡Adiós!")
          continuar = false
        case _ =>
          println("Tipo de usuario inválido. Por favor ingrese 'Administrador', 'Cliente', o 'Salir'.")
      }
    }
  }

  def menuAdministrador(): Unit = {
    var enFuncionamiento = true
    while (enFuncionamiento) {
      println("\n--- Menú Administrador ---")
      println("1. Agregar un Vuelo")
      println("2. Eliminar un Vuelo")
      println("3. Salir")
      print("Seleccione una opción: ")

      val opcion = Try(StdIn.readInt()).getOrElse(-1)
      limpiarPantalla()
      opcion match {
        case 1 =>
          println("Ingrese el Número del Vuelo:")
          val numeroVuelo = StdIn.readLine()
          println("Ingrese el número de asientos:")
          val asientos = Try(StdIn.readInt()).getOrElse(-1)
          limpiarPantalla()
          if (asientos >= 0) {
            SistemaReservaVuelos.agregarVuelo(numeroVuelo, asientos) match {
              case Right(exito) => println(exito)
              case Left(error) => println(error)
            }
          } else {
            println("Número de asientos inválido. Por favor ingrese un número positivo.")
          }

        case 2 =>
          println("Ingrese el Número del Vuelo a eliminar:")
          val numeroVuelo = StdIn.readLine()
          limpiarPantalla()
          SistemaReservaVuelos.eliminarVuelo(numeroVuelo) match {
            case Right(exito) => println(exito)
            case Left(error) => println(error)
          }

        case 3 =>
          println("Saliendo del menú administrador.")
          enFuncionamiento = false

        case _ =>
          println("Opción inválida. Por favor ingrese un número del 1 al 3.")
      }
    }
  }

  def menuCliente(): Unit = {
    var enFuncionamiento = true
    while (enFuncionamiento) {
      println("\n--- Menú Cliente ---")
      println("1. Reservar un Vuelo")
      println("2. Cancelar una Reserva")
      println("3. Salir")
      print("Seleccione una opción: ")

      val opcion = Try(StdIn.readInt()).getOrElse(-1)
      limpiarPantalla()
      opcion match {
        case 1 =>
          println("Ingrese el Número del Vuelo para reservar:")
          val numeroVuelo = StdIn.readLine()
          println("Ingrese el Nombre del Pasajero:")
          val nombrePasajero = StdIn.readLine()
          limpiarPantalla()
          SistemaReservaVuelos.reservarVuelo(numeroVuelo, nombrePasajero) match {
            case Right(reserva) => println(s"Vuelo $numeroVuelo correctamente reservado para $nombrePasajero")
            case Left(error) => println(error)
          }

        case 2 =>
          println("Ingrese el Número del Vuelo para cancelar la reserva:")
          val numeroVuelo = StdIn.readLine()
          println("Ingrese el Nombre del Pasajero:")
          val nombrePasajero = StdIn.readLine()
          limpiarPantalla()
          SistemaReservaVuelos.cancelarReserva(Reserva(numeroVuelo, nombrePasajero)) match {
            case Right(mensaje) => println(mensaje)
            case Left(error) => println(error)
          }

        case 3 =>
          println("Saliendo del menú cliente.")
          enFuncionamiento = false

        case _ =>
          println("Opción inválida. Por favor intente nuevamente.")
      }
    }
  }

  def limpiarPantalla(): Unit = {
    val os = System.getProperty("os.name").toLowerCase()
    if (os.contains("win")) {
      "cls".!
    } else {
      "clear".!
    }
  }
}
```
El archivo `MAIN.scala` implementa una aplicación de consola para gestionar un sistema de reservas de vuelos. No guarda información de una ejecución a otra. Divide su funcionalidad en menús específicos para Administradores y Clientes:

- **Administradores** pueden agregar o eliminar vuelos.
- **Clientes** pueden realizar o cancelar reservas.
- Utiliza un bucle principal para manejar las interacciones del usuario y procesar las opciones seleccionadas, ofreciendo una navegación entre las funciones disponibles.

### Tests del sistema

[`SistemaReservaVuelosTest`](src/test/scala/example/SistemaReservaVuelosTest.scala)

```scala
import scala.example.SistemaReservaVuelos
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class SistemaReservaVuelosTest extends AnyFlatSpec with Matchers {

  "agregarVuelo" should "agregar un vuelo exitosamente si los detalles del vuelo son válidos y el vuelo no existe" in {
    SistemaReservaVuelos.agregarVuelo("VU123", 150) should be (Right("Vuelo VU123 agregado exitosamente con 150 asientos."))
  }

  it should "fallar al agregar un vuelo si los detalles del vuelo son inválidos" in {
    SistemaReservaVuelos.agregarVuelo("", -1) should be (Left("Detalles del vuelo inválidos."))
  }

  it should "fallar al agregar un vuelo si el vuelo ya existe" in {
    SistemaReservaVuelos.agregarVuelo("VU123", 150)  // Asumiendo que este vuelo fue agregado en un test previo
    SistemaReservaVuelos.agregarVuelo("VU123", 150) should be (Left("El vuelo ya existe."))
  }

  "buscarVuelo" should "devolver el vuelo correcto si existe" in {
    SistemaReservaVuelos.agregarVuelo("VU456", 200)
    SistemaReservaVuelos.buscarVuelo("VU456").isDefined shouldBe true
  }

  it should "devolver None si el vuelo no existe" in {
    SistemaReservaVuelos.buscarVuelo("VU999") shouldBe None
  }

  "reservarVuelo" should "permitir la reserva si hay asientos disponibles y no existe una reserva previa para el pasajero" in {
    SistemaReservaVuelos.agregarVuelo("VU789", 1)
    SistemaReservaVuelos.reservarVuelo("VU789", "Juan Pérez") should be (Right(Reserva("VU789", "Juan Pérez")))
  }

  it should "rechazar la reserva si no hay asientos disponibles" in {
    SistemaReservaVuelos.reservarVuelo("VU789", "Juana Pérez") should be (Left("No hay asientos disponibles en el vuelo VU789."))
  }

  it should "rechazar la reserva si el vuelo no existe" in {
    SistemaReservaVuelos.reservarVuelo("VU000", "Juan Pérez") should be (Left("Vuelo VU000 no encontrado."))
  }

  "cancelarReserva" should "cancelar una reserva exitosamente si existe" in {
    val reserva = Reserva("VU789", "Juan Pérez")
    SistemaReservaVuelos.cancelarReserva(reserva) should be (Right("Reserva cancelada exitosamente."))
  }

  it should "fallar al cancelar una reserva que no existe" in {
    val reserva = Reserva("VU789", "Persona Falsa")
    SistemaReservaVuelos.cancelarReserva(reserva) should be (Left("No existe tal reserva."))
  }

  "eliminarVuelo" should "eliminar un vuelo exitosamente si no hay reservas activas" in {
    SistemaReservaVuelos.eliminarVuelo("VU456") should be (Right("Vuelo VU456 eliminado exitosamente."))
  }

  it should "fallar al eliminar un vuelo si hay reservas activas" in {
    SistemaReservaVuelos.agregarVuelo("VU101", 1)
    SistemaReservaVuelos.reservarVuelo("VU101", "Alicia Maravilla")
    SistemaReservaVuelos.eliminarVuelo("VU101") should be (Left("No se puede eliminar el vuelo con reservas activas."))
  }
}
```

`SistemaReservaVuelosTest.scala` contiene pruebas unitarias para `SistemaReservaVuelos.scala` utilizando **ScalaTest**, una biblioteca de pruebas para Scala. Las pruebas verifican varias funcionalidades del sistema:

- **Agregar vuelos**: Pruebas para asegurar que solo se puedan agregar vuelos con datos válidos y que no se dupliquen en el sistema.
- **Buscar vuelos**: Verifica que la función de búsqueda retorne el vuelo correcto si existe, o None si no existe.
- **Reservar vuelos**: Confirma que las reservas se manejen correctamente, permitiendo reservas solo si hay asientos disponibles y no existen reservas previas para el pasajero; también maneja casos donde el vuelo no existe o no hay asientos disponibles.
- **Cancelar reservas**: Pruebas para asegurar que las reservas puedan ser canceladas si existen, y maneja correctamente el caso de intentar cancelar reservas no existentes.
- **Eliminar vuelos**: Verifica que un vuelo pueda ser eliminado solo si no hay reservas activas, y gestiona adecuadamente los intentos de eliminar vuelos con reservas.

Cada función del sistema es probada en diferentes escenarios para garantizar la robustez y el manejo adecuado de errores del sistema de reservas.

## Cómo ejecutar los test

### Requisitos

- JDK 18
- Sbt
- Scala

### En la terminal

1. Nos movemos al directorio de trabajo en caso de no estar ya en él:
    ```bash
    cd temas/errores/scala
    ```
2. Ejecutamos los test con mvn:
    ```bash
    sbt compile
    sbt test 
    ```
   
Tras seguir los dos pasos anteriores podrás visualizar en la terminal los resultados de los tests.

## Ejecución de ejemplos aplicación de terminal

1. Nos movemos al directorio de trabajo en caso de no estar ya en el:

   ```bash
   cd temas/errores/scala
   ```
2. Una vez en el directorio de trabajo compilar y ejecutar con sbt.

    ```bash
    sbt compile
    sbt run
    ```

3. Se iniciará la aplicación de terminal en la que podrás interacturar con el sistema de reserva de vuelos.