package org.iesalandalus.programacion.reservashotel.modelo.negocio.memoria;

import org.iesalandalus.programacion.reservashotel.modelo.dominio.Huesped;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.IHuespedes;

import javax.naming.OperationNotSupportedException;
import java.util.ArrayList;
import java.util.Iterator;

public class Huespedes implements IHuespedes {
    private ArrayList<Huesped> coleccionHuespedes;

    public Huespedes(){
        this.coleccionHuespedes=new ArrayList<>();
    }

    public ArrayList<Huesped> get() {
        ArrayList<Huesped> huespedArrayList=new ArrayList<>();
        Iterator<Huesped> huespedIterator=coleccionHuespedes.iterator();

        while (huespedIterator.hasNext()){
            Huesped huesped=new Huesped(huespedIterator.next());
            huespedArrayList.add(huesped);
        }
        return huespedArrayList;
    }



    public void insertar(Huesped huesped) throws OperationNotSupportedException {
        if (huesped == null) {
            throw new NullPointerException("ERROR: No se puede insertar un huésped nulo.");
        }

        if (coleccionHuespedes.contains(huesped)){
            throw new OperationNotSupportedException("ERROR: Ya existe un huésped con ese dni.");
        }else {
            coleccionHuespedes.add(new Huesped(huesped));
        }
    }


    public Huesped buscar(Huesped huesped)  {
        if (huesped==null){
            throw new NullPointerException("ERROR: No se puede buscar un huésped nulo.");
        }
        Iterator<Huesped> huespedIterator=get().iterator();
        while (huespedIterator.hasNext()){
            Huesped huespedBuscado=huespedIterator.next();
            if (huespedBuscado.equals(huesped)){
                return huesped;
            }
        }
        return null;


    }
    public void borrar(Huesped huesped) throws OperationNotSupportedException {
        if (huesped==null){
            throw new NullPointerException("ERROR: No se puede borrar un huésped nulo.");
        }
        if (coleccionHuespedes.contains(huesped)){
            coleccionHuespedes.remove(huesped);
        }else {
            throw new OperationNotSupportedException("ERROR: No existe ningún huésped como el indicado.");
        }
    }

    public int getTamano() {
        return coleccionHuespedes.size();
    }

    public  void  comenzar(){
    }
    public void  terminar(){
    }
}
