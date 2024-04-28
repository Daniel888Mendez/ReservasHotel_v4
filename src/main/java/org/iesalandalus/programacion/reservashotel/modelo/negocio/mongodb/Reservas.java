package org.iesalandalus.programacion.reservashotel.modelo.negocio.mongodb;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.model.Updates;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.*;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.IReservas;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.mongodb.utilidades.MongoDB;

import javax.naming.OperationNotSupportedException;
import org.bson.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;

public class Reservas implements IReservas {
    private  static final String COLECCION="reservas";
    private MongoCollection<Document> coleccionReservas;

    public Reservas(){
        comenzar();

    }

    public ArrayList<Reserva> get(){
        ArrayList<Reserva> reservaArrayList=new ArrayList<>();
        FindIterable<Document> documentFindIterable = coleccionReservas.find().sort(Sorts.orderBy(Sorts.ascending(MongoDB.FECHA_INICIO_RESERVA),Sorts.ascending(MongoDB.IDENTIFICADOR)));
        for (Document documentReserva:documentFindIterable){
            Reserva reserva=MongoDB.getReserva(documentReserva);

            if (!documentReserva.getString(MongoDB.CHECKIN).equals("no registrado")){
                LocalDateTime fechaCheckin =LocalDateTime.parse(documentReserva.getString(MongoDB.CHECKIN),MongoDB.FORMATO_DIA_HORA);
                reserva.setCheckIn(fechaCheckin);
            }
            if (!documentReserva.getString(MongoDB.CHECKOUT).equals("no registrado")){
                LocalDateTime fechaCheckOut =LocalDateTime.parse(documentReserva.getString(MongoDB.CHECKOUT),MongoDB.FORMATO_DIA_HORA);
                reserva.setCheckOut(fechaCheckOut);
            }
            reservaArrayList.add(reserva);
        }
        return reservaArrayList;
    }
    public int getTamano(){
        return (int) coleccionReservas.countDocuments();
    }

    public void  insertar(Reserva reserva) throws OperationNotSupportedException {
        if (reserva==null){
            throw new NullPointerException("ERROR: No se puede insertar una reserva nula.");
        }

        if (buscar(reserva)!=null){
            throw new OperationNotSupportedException("ERROR: Ya existe una reserva igual.");
        }else{
            coleccionReservas.insertOne(MongoDB.getDocumento(reserva));
        }
    }

    public  Reserva buscar(Reserva reserva){
        if (reserva==null){
            throw new NullPointerException("ERROR: No se puede buscar una reserva nula.");
        }
        Document documentReserva=coleccionReservas.find().filter(and(
                eq(MongoDB.HUESPED_DNI,reserva.getHuesped().getDni()),
                eq(MongoDB.HABITACION_IDENTIFICADOR,reserva.getHabitacion().getIdentificador())
        )).first();

        if (documentReserva != null){
            return new Reserva(MongoDB.getReserva(documentReserva));
        }
        return null;

    }
    public  void  borrar(Reserva reserva) throws OperationNotSupportedException {
        if (reserva==null){
            throw new NullPointerException("ERROR: No se puede borrar una reserva nula.");
        }
        if (buscar(reserva)!=null){
            coleccionReservas.deleteOne(MongoDB.getDocumento(reserva));
        }else {
            throw  new OperationNotSupportedException("ERROR: No existe ninguna reserva como la indicada.");
        }

    }

    public  ArrayList<Reserva> getReservas(Huesped huesped)  {
        if (huesped==null){
            throw new NullPointerException("ERROR: No se pueden buscar reservas de un hu�sped nulo.");
        }
        ArrayList<Reserva> reservaArrayList=new ArrayList<>();
        FindIterable<Document> documentFindIterable=coleccionReservas.find().sort(Sorts.orderBy((Sorts.ascending(MongoDB.FECHA_INICIO_RESERVA))));



        for (Document documentReserva:documentFindIterable){
            Reserva reserva=MongoDB.getReserva(documentReserva);
            if (reserva.getHuesped().equals(huesped)){
                if (!documentReserva.getString(MongoDB.CHECKIN).equals("no registrado")){
                    LocalDateTime fechaCheckin =LocalDateTime.parse(documentReserva.getString(MongoDB.CHECKIN),MongoDB.FORMATO_DIA_HORA);
                    reserva.setCheckIn(fechaCheckin);
                }
                if (!documentReserva.getString(MongoDB.CHECKOUT).equals("no registrado")){
                    LocalDateTime fechaCheckOut =LocalDateTime.parse(documentReserva.getString(MongoDB.CHECKOUT),MongoDB.FORMATO_DIA_HORA);
                    reserva.setCheckOut(fechaCheckOut);
                }
                reservaArrayList.add(reserva);
            }

        }
        return reservaArrayList;
    }

    public ArrayList<Reserva> getReservas(TipoHabitacion tipoHabitacion)  {
        if (tipoHabitacion==null){
            throw new NullPointerException("ERROR: No se pueden buscar reservas de un tipo de habitaci�n nula.");
        }

        ArrayList<Reserva> reservasTipoHabitacion=new ArrayList<>();
        FindIterable<Document> documentFindIterable= coleccionReservas.find().filter(eq(MongoDB.HABITACION_TIPO,tipoHabitacion.name()));
        for (Document document:documentFindIterable){
            Reserva reserva=MongoDB.getReserva(document);
            if (!document.getString(MongoDB.CHECKIN).equals("no registrado")){
                LocalDateTime fechaCheckin =LocalDateTime.parse(document.getString(MongoDB.CHECKIN),MongoDB.FORMATO_DIA_HORA);
                reserva.setCheckIn(fechaCheckin);
            }
            if (!document.getString(MongoDB.CHECKOUT).equals("no registrado")){
                LocalDateTime fechaCheckOut =LocalDateTime.parse(document.getString(MongoDB.CHECKOUT),MongoDB.FORMATO_DIA_HORA);
                reserva.setCheckOut(fechaCheckOut);
            }
            reservasTipoHabitacion.add(reserva);

        }

        return reservasTipoHabitacion;
    }
    public ArrayList<Reserva> getReservas(Habitacion habitacion){
        if (habitacion==null){
            throw new NullPointerException("ERROR: No se pueden buscar reservas de una habitaci�n nula.");
        }
        ArrayList<Reserva> reservaArrayList=new ArrayList<>();
        FindIterable<Document> documentFindIterable= coleccionReservas.find().filter(eq(MongoDB.HABITACION,habitacion));

        for (Document document:documentFindIterable){
            Reserva reserva=MongoDB.getReserva(document);
            reservaArrayList.add(reserva);

        }

        return reservaArrayList;
    }


    public ArrayList<Reserva> getReservasFuturas(Habitacion habitacion){
        if (habitacion==null){
            throw new NullPointerException("ERROR: No se pueden buscar reservas de una habitaci�n nula.");
        }
        ArrayList<Reserva> reservaArrayList=new ArrayList<>();
        FindIterable<Document> documentFindIterable= coleccionReservas.find().filter(eq(MongoDB.HABITACION,habitacion));

        for (Document document:documentFindIterable){
            Reserva reserva=MongoDB.getReserva(document);
            if (reserva.getHabitacion().equals(habitacion) && reserva.getFechaInicioReserva().isAfter(LocalDate.now())){
                reservaArrayList.add(reserva);
            }

        }

        return reservaArrayList;
    }

    public void realizarCheckin(Reserva reserva, LocalDateTime fecha){
        if (reserva==null || fecha == null){
            throw new NullPointerException("La reserva o la fecha no pueden ser nulas");
        }
        Document document=coleccionReservas.find().filter(and(
                eq(MongoDB.HUESPED_DNI,reserva.getHuesped().getDni()),
                eq(MongoDB.HABITACION_IDENTIFICADOR,reserva.getHabitacion().getIdentificador())
        )).first();
        if (document != null){
            reserva.setCheckIn(fecha);
            String cadenaFecha= fecha.format(MongoDB.FORMATO_DIA_HORA);

            coleccionReservas.updateOne(Filters.eq(MongoDB.CHECKIN,document.getString(MongoDB.CHECKIN)), Updates.set(MongoDB.CHECKIN,cadenaFecha));
        }











    }
    public void realizarCheckOut(Reserva reserva, LocalDateTime fecha){
        if (reserva==null | fecha == null){
            throw new NullPointerException("La reserva o la fecha no pueden ser nulas");
        }
        Document document=coleccionReservas.find().filter(and(
                eq(MongoDB.HUESPED_DNI,reserva.getHuesped().getDni()),
                eq(MongoDB.HABITACION_IDENTIFICADOR,reserva.getHabitacion().getIdentificador())
        )).first();
        if (document != null){
            reserva.setCheckIn(fecha);
            String cadenaFecha= fecha.format(MongoDB.FORMATO_DIA_HORA);

            coleccionReservas.updateOne(Filters.eq(MongoDB.CHECKOUT,document.getString(MongoDB.CHECKOUT)), Updates.set(MongoDB.CHECKOUT,cadenaFecha));
            coleccionReservas.updateOne(Filters.eq(MongoDB.PRECIO_RESERVA,document.getDouble(MongoDB.PRECIO_RESERVA)), Updates.set(MongoDB.PRECIO_RESERVA ,reserva.getPrecio()));
        }



    }
    public void comenzar() {
        coleccionReservas= MongoDB.getBD().getCollection(COLECCION);
    }

    @Override
    public void terminar() {
        MongoDB.cerrarConexion();
    }


}
