package scala.example

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
