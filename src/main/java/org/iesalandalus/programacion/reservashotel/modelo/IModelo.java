package org.iesalandalus.programacion.reservashotel.modelo;

import org.iesalandalus.programacion.reservashotel.modelo.dominio.Habitacion;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.Huesped;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.Reserva;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.TipoHabitacion;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.IFuenteDatos;

import javax.naming.OperationNotSupportedException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public interface IModelo {



    public void comenzar()throws OperationNotSupportedException;





    public void terminar()throws OperationNotSupportedException;




    public  void insertar(Huesped huesped)throws OperationNotSupportedException;



    public  Huesped buscar(Huesped huesped)throws OperationNotSupportedException;


    public  void borrar(Huesped huesped)throws OperationNotSupportedException;



    public ArrayList<Huesped> getHuespedes();


    public  void insertar(Habitacion habitacion)throws OperationNotSupportedException;


    public  Habitacion buscar(Habitacion habitacion)throws OperationNotSupportedException;


    public  void borrar(Habitacion habitacion)throws OperationNotSupportedException;



    public ArrayList<Habitacion> getHabitaciones();



    public ArrayList<Habitacion> getHabitaciones(TipoHabitacion tipoHabitacion);


    public  void insertar(Reserva reserva)throws OperationNotSupportedException;




    public  Reserva buscar(Reserva reserva)throws OperationNotSupportedException;


    public  void borrar(Reserva reserva)throws OperationNotSupportedException;




    public ArrayList<Reserva> getReservas();



    public  ArrayList<Reserva> getReservas(Huesped huesped);


    public  ArrayList<Reserva> getReservas(Habitacion habitacion)throws OperationNotSupportedException;


    public ArrayList<Reserva> getReservas(TipoHabitacion tipoHabitacion) throws OperationNotSupportedException;


    public ArrayList<Reserva> getReservasFuturas(Habitacion habitacion);


    public void realizarCheckin(Reserva reserva, LocalDateTime feha);




    public void realizarCheckOut(Reserva reserva, LocalDateTime feha);







}
