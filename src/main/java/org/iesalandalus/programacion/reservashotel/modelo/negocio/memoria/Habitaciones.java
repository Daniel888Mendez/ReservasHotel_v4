package org.iesalandalus.programacion.reservashotel.modelo.negocio.memoria;

import org.iesalandalus.programacion.reservashotel.vista.Consola;
import org.iesalandalus.programacion.reservashotel.vista.Vista;
import org.iesalandalus.programacion.reservashotel.modelo.Modelo;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.*;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.IHabitaciones;

import javax.naming.OperationNotSupportedException;
import java.util.ArrayList;
import java.util.Iterator;

public class Habitaciones implements IHabitaciones {

    private ArrayList<Habitacion> coleccionHabitaciones;

    public  Habitaciones(){
        this.coleccionHabitaciones=new ArrayList<>();
    }

    public ArrayList<Habitacion> get(){
        ArrayList<Habitacion> habitacionArrayList=new ArrayList<>();
        Iterator<Habitacion> habitacionIterator=coleccionHabitaciones.iterator();

        while (habitacionIterator.hasNext()){
            Habitacion habitacionBuscada=habitacionIterator.next();
            if (habitacionBuscada instanceof Simple  ){
                habitacionArrayList.add(new Simple((Simple) habitacionBuscada));
            }
            if (habitacionBuscada instanceof Doble){
                habitacionArrayList.add(new Doble((Doble) habitacionBuscada));
            }
            if (habitacionBuscada instanceof Triple ){
                habitacionArrayList.add(new Triple((Triple) habitacionBuscada));
            }
            if (habitacionBuscada instanceof Suite ){
                habitacionArrayList.add(new Suite((Suite) habitacionBuscada));
            }
        }
        return habitacionArrayList;
    }

    public ArrayList<Habitacion> get(TipoHabitacion tipoHabitacion){

        ArrayList<Habitacion> habitacionArrayList=new ArrayList<>();

        for (Habitacion habitacion:coleccionHabitaciones){
            switch (tipoHabitacion){
                case SIMPLE -> {
                    if (habitacion instanceof Simple){
                        habitacionArrayList.add(habitacion);
                    }
                }
                case DOBLE -> {
                    if (habitacion instanceof Doble){
                        habitacionArrayList.add(habitacion);
                    }
                }
                case TRIPLE -> {
                    if (habitacion instanceof Triple){
                        habitacionArrayList.add(habitacion);
                    }
                }
                case SUITE -> {
                    if (habitacion instanceof Suite){
                        habitacionArrayList.add(habitacion);
                    }
                }

            }
        }
        return habitacionArrayList;

    }


    public void  insertar(Habitacion habitacion) throws OperationNotSupportedException {
        if (habitacion==null){
            throw new NullPointerException("ERROR: No se puede insertar una habitación nula.");
        }

        if (coleccionHabitaciones.contains(habitacion)){
            throw new OperationNotSupportedException("ERROR: Ya existe una habitación con ese identificador.");
        }else {
            coleccionHabitaciones.add(habitacion);
        }

    }

    public  Habitacion buscar(Habitacion habitacion){

        if (habitacion==null){
            throw new NullPointerException("ERROR: No se puede buscar una habitación nula.");
        }

        Iterator<Habitacion> habitacionIterator= get().iterator();
        while (habitacionIterator.hasNext()){
            Habitacion habitacionBuscada=habitacionIterator.next();
            if (habitacionBuscada.equals(habitacion)){
                return habitacionBuscada;
            }
        }
        return null;

    }
    public  void  borrar(Habitacion habitacion) throws OperationNotSupportedException {
        if (habitacion==null){
            throw new NullPointerException("ERROR: No se puede borrar una habitación nula.");
        }
        if (coleccionHabitaciones.contains(habitacion)) {
            coleccionHabitaciones.remove(habitacion);
        }else {
            throw new OperationNotSupportedException("ERROR: No existe ninguna habitación como la indicada.");
        }
    }

    public int getTamano(){
        return get().size();
    }
    public  void  comenzar(){
    }
    public void  terminar(){
    }


}
