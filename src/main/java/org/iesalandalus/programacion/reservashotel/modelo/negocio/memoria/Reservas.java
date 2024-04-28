package org.iesalandalus.programacion.reservashotel.modelo.negocio.memoria;

import org.iesalandalus.programacion.reservashotel.modelo.dominio.*;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.IReservas;

import javax.naming.OperationNotSupportedException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;

public class Reservas implements IReservas {
    private ArrayList<Reserva> coleccionReservas;

    public Reservas(){
        this.coleccionReservas=new ArrayList<>();
    }

    public ArrayList<Reserva> get(){
        ArrayList<Reserva> reservaArrayList=new ArrayList<>();
        Iterator<Reserva> reservaIterator=coleccionReservas.iterator();
        while (reservaIterator.hasNext()){
            Reserva reserva=new Reserva(reservaIterator.next());
            reservaArrayList.add(reserva);
        }
        return reservaArrayList;
    }
    public int getTamano(){
        return coleccionReservas.size();
    }

    public void  insertar(Reserva reserva) throws OperationNotSupportedException {
        if (reserva==null){
            throw new NullPointerException("ERROR: No se puede insertar una reserva nula.");
        }
        if (coleccionReservas.contains(reserva)){
            throw new OperationNotSupportedException("ERROR: Ya existe una reserva igual.");
        }else{
            coleccionReservas.add(reserva);
        }
    }

    public  Reserva buscar(Reserva reserva){
        if (reserva==null){
            throw new NullPointerException("ERROR: No se puede buscar una reserva nula.");
        }
        Iterator<Reserva> reservaIterator=get().iterator();
        while (reservaIterator.hasNext()){
            Reserva reservaBuscada=reservaIterator.next();
            if (reservaBuscada.equals(reserva)){
                return reserva;
            }
        }
        return null;
    }
    public  void  borrar(Reserva reserva) throws OperationNotSupportedException {
        if (reserva==null){
            throw new NullPointerException("ERROR: No se puede borrar una reserva nula.");
        }
        if (coleccionReservas.contains(reserva)){
            coleccionReservas.remove(reserva);
        }else {
            throw  new OperationNotSupportedException("ERROR: No existe ninguna reserva como la indicada.");
        }

    }

    public  ArrayList<Reserva> getReservas(Huesped huesped)  {
        if (huesped==null){
            throw new NullPointerException("ERROR: No se pueden buscar reservas de un huésped nulo.");
        }
        ArrayList<Reserva> reservaArrayList=new ArrayList<>();
        Iterator<Reserva> reservaIterator=get().iterator();
        while (reservaIterator.hasNext()){
            Reserva reservaBuscada=reservaIterator.next();
            if (reservaBuscada.getHuesped().equals(huesped)){
                reservaArrayList.add(reservaBuscada);
            }
        }
        return reservaArrayList;
    }

    public ArrayList<Reserva> getReservas(TipoHabitacion tipoHabitacion)  {
        if (tipoHabitacion==null){
            throw new NullPointerException("ERROR: No se pueden buscar reservas de un tipo de habitación nula.");
        }
        ArrayList<Reserva> reservasTipoHabitacion=new ArrayList<>();
        Iterator<Reserva> reservaIterator=get().iterator();
        while (reservaIterator.hasNext()){
            Reserva reservaBuscada=reservaIterator.next();
            switch (tipoHabitacion){
                case SIMPLE:
                    if (reservaBuscada.getHabitacion() instanceof Simple) {
                        reservasTipoHabitacion.add(new Reserva(reservaBuscada));
                    }
                    break;
                case DOBLE:
                    if (reservaBuscada.getHabitacion() instanceof Doble) {
                        reservasTipoHabitacion.add(new Reserva(reservaBuscada));
                    }
                    break;
                case TRIPLE:
                    if (reservaBuscada.getHabitacion() instanceof Triple) {
                        reservasTipoHabitacion.add(new Reserva(reservaBuscada));
                    }
                    break;
                case SUITE:
                    if (reservaBuscada.getHabitacion() instanceof Suite) {
                        reservasTipoHabitacion.add(new Reserva(reservaBuscada));
                    }
                    break;
            }
        }
        return reservasTipoHabitacion;
    }

    public ArrayList<Reserva> getReservasFuturas(Habitacion habitacion){
        if (habitacion==null){
            throw new NullPointerException("ERROR: No se pueden buscar reservas de una habitación nula.");
        }
        ArrayList<Reserva> reservaArrayList=new ArrayList<>();
        Iterator<Reserva> reservaIterator=get().iterator();
        while (reservaIterator.hasNext()){
            Reserva reserva=reservaIterator.next();
            if (reserva.getHabitacion().equals(habitacion)){
                reservaArrayList.add(reserva);
            }
        }

        return reservaArrayList;
    }

    public void realizarCheckin(Reserva reserva, LocalDateTime fecha){

        Iterator<Reserva> reservaIterator=coleccionReservas.iterator();
        while (reservaIterator.hasNext()){
            Reserva reserva1 = reservaIterator.next();
            if (reserva1!=null){
                if (reserva1.equals(reserva)){
                    reserva1.setCheckIn(fecha);
                }
            }
        }







    }
    public void realizarCheckOut(Reserva reserva, LocalDateTime fecha){
        Iterator<Reserva> reservaIterator=coleccionReservas.iterator();
        while (reservaIterator.hasNext()){
            Reserva reserva1 = reservaIterator.next();
            if (reserva1!=null){
                if (reserva1.equals(reserva)){
                    reserva1.setCheckOut(fecha);
                }
            }
        }


    }
    public  void  comenzar(){
    }
    public void  terminar(){
    }

}
