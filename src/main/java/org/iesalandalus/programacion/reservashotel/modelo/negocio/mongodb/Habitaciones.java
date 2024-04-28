package org.iesalandalus.programacion.reservashotel.modelo.negocio.mongodb;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Sorts;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.*;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.IHabitaciones;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.mongodb.utilidades.MongoDB;

import javax.naming.OperationNotSupportedException;
import org.bson.Document;
import java.util.ArrayList;
import java.util.Iterator;

import static com.mongodb.client.model.Filters.eq;

public class Habitaciones implements IHabitaciones {
    private  static final String COLECCION="habitaciones";

    private MongoCollection<Document> coleccionHabitaciones;

    public  Habitaciones(){
        comenzar();

    }

    public ArrayList<Habitacion> get(){
        ArrayList<Habitacion> habitacionArrayList=new ArrayList<>();
        FindIterable<Document> documentFindIterable=coleccionHabitaciones.find().sort(Sorts.ascending(MongoDB.IDENTIFICADOR));
        Iterator<Document> documentIterator=documentFindIterable.iterator();



        while (documentIterator.hasNext()){
            Habitacion habitacionBuscada=MongoDB.gethabitacion(documentIterator.next());
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
        FindIterable<Document> documentFindIterable= coleccionHabitaciones.find();

        for (Document documentoTipoHabitacion:documentFindIterable){
            Habitacion habitacion= MongoDB.gethabitacion(documentoTipoHabitacion);
            switch (tipoHabitacion){
                case SIMPLE -> {
                    if (habitacion instanceof  Simple){
                        habitacionArrayList.add(habitacion);
                    }
                }
                case DOBLE -> {
                    if (habitacion instanceof  Doble){
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

        if (buscar(habitacion)!=null){
            throw new OperationNotSupportedException("ERROR: Ya existe una habitación con ese identificador.");
        }else {
            coleccionHabitaciones.insertOne(MongoDB.getDocumento(habitacion));
        }

    }

    public  Habitacion buscar(Habitacion habitacion){

        if (habitacion==null){
            throw new NullPointerException("ERROR: No se puede buscar una habitación nula.");
        }

        Document documentoHabitacion=coleccionHabitaciones.find().filter(eq(MongoDB.IDENTIFICADOR,habitacion.getIdentificador())).first();
        if (documentoHabitacion!=null){
            return MongoDB.gethabitacion(documentoHabitacion);
        }else return null;


    }
    public  void  borrar(Habitacion habitacion) throws OperationNotSupportedException {
        if (habitacion==null){
            throw new NullPointerException("ERROR: No se puede borrar una habitación nula.");
        }
        if (buscar(habitacion)!=null) {
            coleccionHabitaciones.deleteOne(eq(MongoDB.IDENTIFICADOR ,habitacion.getIdentificador()));
        }else {
            throw new OperationNotSupportedException("ERROR: No existe ninguna habitación como la indicada.");
        }
    }

    public int getTamano(){
        return (int) coleccionHabitaciones.countDocuments();
    }

    public void comenzar() {
       coleccionHabitaciones= MongoDB.getBD().getCollection(COLECCION);
    }

    @Override
    public void terminar() {
        MongoDB.cerrarConexion();
    }


}
