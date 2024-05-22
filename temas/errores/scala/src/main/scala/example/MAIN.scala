package scala.example

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
