package org.iesalandalus.programacion.reservashotel.controlador;

import org.iesalandalus.programacion.reservashotel.vista.Vista;
import org.iesalandalus.programacion.reservashotel.modelo.IModelo;
import org.iesalandalus.programacion.reservashotel.modelo.Modelo;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.Habitacion;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.Huesped;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.Reserva;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.TipoHabitacion;

import javax.naming.OperationNotSupportedException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Controlador {
    private  static  IModelo modelo;
    private  final Vista vista;

    public Controlador(IModelo modelo , Vista vista){
        if (modelo==null){
            throw new NullPointerException("ERROR el modelo no puede ser nulo.");
        }
        if (vista==null){
            throw new NullPointerException("EROOR: la vista no puede sr nula");
        }
        this.modelo=modelo;
        this.vista=vista;
        this.vista.setControlador(this);
    }


    public void comenzar() throws OperationNotSupportedException {
        modelo.comenzar();
        vista.comenzar();

    }
    public void terminar() throws OperationNotSupportedException {
        modelo.terminar();

    }

    public  void insertar(Huesped huesped) throws OperationNotSupportedException {
        modelo.insertar(huesped);

    }
    public  Huesped buscar(Huesped huesped) throws OperationNotSupportedException {
        return modelo.buscar(huesped);
    }
    public  void borrar(Huesped huesped) throws OperationNotSupportedException {
        modelo.borrar(huesped);

    }

    public ArrayList<Huesped> getHuespedes() {
        return modelo.getHuespedes();

    }
    public  void insertar(Habitacion habitacion) throws OperationNotSupportedException {
        modelo.insertar(habitacion);

    }
    public  Habitacion buscar(Habitacion habitacion) throws OperationNotSupportedException {
        return modelo.buscar(habitacion);
    }
    public  void borrar(Habitacion habitacion) throws OperationNotSupportedException {
        modelo.borrar(habitacion);

    }

    public ArrayList<Habitacion> getHabitaciones() {

        return modelo.getHabitaciones();

    }
    public ArrayList<Habitacion> getHabitaciones(TipoHabitacion tipoHabitacion) {
        return modelo.getHabitaciones(tipoHabitacion);

    }
    public  void insertar(Reserva reserva) throws OperationNotSupportedException {
        modelo.insertar(reserva);


    }
    public  Reserva buscar(Reserva reserva) throws OperationNotSupportedException {
        return modelo.buscar(reserva);
    }
    public  void borrar(Reserva reserva) throws OperationNotSupportedException {
        modelo.borrar(reserva);

    }

    public static ArrayList<Reserva> getReservas() {

        return modelo.getReservas();

    }
    public  ArrayList<Reserva> getReservas(Huesped huesped){
        return modelo.getReservas(huesped);
    }
    public  ArrayList<Reserva> getReservas(Habitacion habitacion) throws OperationNotSupportedException {
        return modelo.getReservas(habitacion);
    }
    public ArrayList<Reserva> getReservas(TipoHabitacion tipoHabitacion) throws OperationNotSupportedException {
        return modelo.getReservas(tipoHabitacion);
    }
    public ArrayList<Reserva> getReservasFuturas(Habitacion habitacion){
        return modelo.getReservasFuturas(habitacion);
    }



    public void realizarCheckin(Reserva reserva, LocalDateTime fecha){
        modelo.realizarCheckin(reserva,fecha);

    }
    public void realizarCheckOut(Reserva reserva, LocalDateTime fecha){
        modelo.realizarCheckOut(reserva,fecha);

    }
}
