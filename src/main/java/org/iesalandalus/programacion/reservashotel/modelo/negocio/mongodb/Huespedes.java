package org.iesalandalus.programacion.reservashotel.modelo.negocio.mongodb;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Sorts;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.Habitacion;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.Huesped;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.IHuespedes;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.mongodb.utilidades.MongoDB;

import javax.naming.OperationNotSupportedException;
import java.util.ArrayList;
import java.util.Iterator;
import org.bson.Document;

import static com.mongodb.client.model.Filters.eq;

public class Huespedes implements IHuespedes {
    private  static final String COLECCION="huespedes";
    private MongoCollection<Document> coleccionHuespedes;

    public Huespedes(){
        comenzar();

    }

    public ArrayList<Huesped> get() {
        ArrayList<Huesped> huespedArrayList=new ArrayList<>();
        FindIterable<Document> documentFindIterable=coleccionHuespedes.find().sort(Sorts.ascending(MongoDB.DNI));

        FindIterable<Document> coleccionHuespedes=MongoDB.getBD().getCollection("huespedes").find();
        Iterator<Document> documentIterator= coleccionHuespedes.iterator();

        //new huesped?
        for (Document  documento:coleccionHuespedes){
            huespedArrayList.add(new Huesped(MongoDB.getHuesped(documento)));
        }
        return huespedArrayList;
    }



    public void insertar(Huesped huesped) throws OperationNotSupportedException {
        if (huesped == null) {
            throw new NullPointerException("ERROR: No se puede insertar un huésped nulo.");
        }
        if (buscar(huesped)!= null){
            throw new OperationNotSupportedException("El huesped ya existe");
        }else {
            coleccionHuespedes.insertOne(MongoDB.getDocumento(huesped));
        }

    }


    public Huesped buscar(Huesped huesped)  {
        if (huesped==null){
            throw new NullPointerException("ERROR: No se puede buscar un huésped nulo.");
        }


        Document documentHuesped=coleccionHuespedes.find().filter(eq(MongoDB.DNI,huesped.getDni())).first();
        if (documentHuesped!=null){
            return MongoDB.getHuesped(documentHuesped);
        }
        else return null;

    }
    public void borrar(Huesped huesped) throws OperationNotSupportedException {
        if (huesped==null){
            throw new NullPointerException("ERROR: No se puede borrar un huésped nulo.");
        }
        if (buscar(huesped)!=null){
            coleccionHuespedes.deleteOne(eq(MongoDB.DNI, huesped.getDni()));
        }else {
            throw new OperationNotSupportedException("El Huesped a borrar no existe");
        }
    }

    @Override
    public void comenzar() {
        coleccionHuespedes= MongoDB.getBD().getCollection(COLECCION);
    }

    @Override
    public void terminar() {
        MongoDB.cerrarConexion();
    }

    public int getTamano() {
        return (int) coleccionHuespedes.countDocuments();
    }
}
