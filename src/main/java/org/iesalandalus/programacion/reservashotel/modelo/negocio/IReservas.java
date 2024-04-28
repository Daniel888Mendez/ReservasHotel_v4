package org.iesalandalus.programacion.reservashotel.modelo.negocio;

import org.iesalandalus.programacion.reservashotel.modelo.dominio.Habitacion;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.Huesped;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.Reserva;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.TipoHabitacion;

import javax.naming.OperationNotSupportedException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Locale;

public interface IReservas {
    ArrayList<Reserva> get();
    int getTamano();
    void insertar(Reserva reerva)throws OperationNotSupportedException;
    Reserva buscar(Reserva reserva)throws OperationNotSupportedException;
    void borrar(Reserva reserva)throws OperationNotSupportedException;
    ArrayList<Reserva> getReservas(Huesped huesped);
    ArrayList<Reserva> getReservas(TipoHabitacion tipoHabitacion);
    ArrayList<Reserva> getReservasFuturas(Habitacion habitacion);
    void realizarCheckin(Reserva reserva , LocalDateTime fecha);
    void realizarCheckOut(Reserva reserva , LocalDateTime fecha);
    void comenzar();
    void terminar();

}
