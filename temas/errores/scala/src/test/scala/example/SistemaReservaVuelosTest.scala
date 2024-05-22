package scala.example

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