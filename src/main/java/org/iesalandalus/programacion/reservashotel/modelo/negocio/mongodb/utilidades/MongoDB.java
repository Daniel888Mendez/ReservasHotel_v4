package org.iesalandalus.programacion.reservashotel.modelo.negocio.mongodb.utilidades;

import com.mongodb.*;
import com.mongodb.client.*;
import com.mongodb.client.MongoDatabase;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.*;
import org.bson.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

import static com.mongodb.client.model.Filters.eq;

public class MongoDB {
    public static final DateTimeFormatter FORMATO_DIA = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    public static final DateTimeFormatter FORMATO_DIA_HORA= DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
    private static final int PUERTO = 27017;
    private static final String BD = "reservashotel";
    private static final String USUARIO = "reservashotel";
    private static final String CONTRASENA = "reservashotel-2024";
    private static final String SERVIDOR="dam.8vqfvt3.mongodb.net";



    public static final String HUESPED = "huesped";
    public static final String NOMBRE = "nombre";
    public static final String DNI = "dni";
    public static final String TELEFONO = "telefono";
    public static final String CORREO="correo";
    public static final String FECHA_NACIMIENTO="fecha_nacimiento";
    public static final String HUESPED_DNI = HUESPED + "." + DNI;
    public static final String HABITACION = "habitacion";
    public static final String IDENTIFICADOR = "identificador";
    public static final String PLANTA = "planta";
    public static final String PUERTA = "puerta";
    public static final String PRECIO="precio";
    public static final String HABITACION_IDENTIFICADOR = HABITACION + "." + IDENTIFICADOR;
    public static final String TIPO = "tipo";
    public static final String HABITACION_TIPO=HABITACION + "." + TIPO;
    public static final String TIPO_SIMPLE="SIMPLE";
    public static final String TIPO_DOBLE="DOBLE";
    public static final String TIPO_TRIPLE="TRIPLE";
    public static final String TIPO_SUITE="SUITE";
    public static final String CAMAS_INDIVIDUALES="camas_individuales";
    public static final String CAMAS_DOBLE="camas_dobles";
    public static final String BANOS="banos";
    public static final String JACUZZI="jacuzzi";
    public static final String REGIMEN = "regimen";
    public static final String FECHA_INICIO_RESERVA="fecha_inicio_reserva";
    public static final String FECHA_FIN_RESERVA="fecha_fin_reserva";
    public static final String CHECKIN = "checkin";
    public static final String CHECKOUT = "checkout";
    public static final String PRECIO_RESERVA="precio_reserva";
    public static final String NUMERO_PERSONAS="numero_personas";

    private static MongoClient conexion;

    private MongoDB() {

    }

    public static MongoDatabase getBD() {
        if (conexion == null) {
            establecerConexion();
        }

        return conexion.getDatabase(BD);
    }

    private static void establecerConexion()
    {

        String connectionString;
        ServerApi serverApi;
        MongoClientSettings settings;

        if (!SERVIDOR.equals("localhost"))
        {
            connectionString = "mongodb+srv://"+ USUARIO+ ":" + CONTRASENA + "@"+ SERVIDOR +"/?retryWrites=true&w=majority";
            serverApi = ServerApi.builder()
                    .version(ServerApiVersion.V1)
                    .build();

            settings = MongoClientSettings.builder()
                    .applyConnectionString(new ConnectionString(connectionString))
                    .serverApi(serverApi)
                    .build();
        }
        else
        {
            connectionString="mongodb://" + USUARIO + ":" + CONTRASENA + "@" + SERVIDOR + ":" + PUERTO ;
            MongoCredential credenciales = MongoCredential.createScramSha1Credential(USUARIO, BD, CONTRASENA.toCharArray());

            settings = MongoClientSettings.builder()
                    .applyConnectionString(new ConnectionString(connectionString))
                    .credential(credenciales)
                    .build();
        }

        //Creamos la conexión con el serveridos según el setting anterior
        conexion = MongoClients.create(settings);

        try
        {
            if (!SERVIDOR.equals("localhost"))
            {
                MongoDatabase database = conexion.getDatabase(BD);
                database.runCommand(new Document("ping", 1));
            }
        }
        catch (MongoException e)
        {
            e.printStackTrace();

        }

        System.out.println("Conexión a MongoDB realizada correctamente.");
    }
    public static void cerrarConexion() {
        if (conexion != null) {
            conexion.close();
            conexion = null;
            System.out.println("Conexión a MongoDB cerrada.");
        }
    }
    public static Document getDocumento(Huesped huesped){
        return new Document().append(NOMBRE,huesped.getNombre()).append(DNI,huesped.getDni()).append(CORREO,huesped.getCorreo()).append(TELEFONO,huesped.getTelefono()).append(FECHA_NACIMIENTO, huesped.getFechaNacimiento().format(FORMATO_DIA));
    }

    public static Huesped getHuesped(Document documentoHuesped){
        if (documentoHuesped==null){
            throw new NullPointerException("ERROR. El documento del huesped no puede ser nulo");
        }
        String fechaHuesped=documentoHuesped.getString(FECHA_NACIMIENTO);
        LocalDate fecha=LocalDate.parse(fechaHuesped,FORMATO_DIA);
        return new Huesped(documentoHuesped.getString(NOMBRE),documentoHuesped.getString(DNI),documentoHuesped.getString(CORREO),documentoHuesped.getString(TELEFONO),fecha);
    }
    public static Document getDocumento(Habitacion habitacion){

        if (habitacion instanceof Simple){
            return new Document().append(PLANTA,habitacion.getPlanta()).append(PUERTA,habitacion.getPuerta()).append(IDENTIFICADOR,habitacion.getIdentificador()).append(PRECIO,habitacion.getPrecio()).append(TIPO,TIPO_SIMPLE);
        }
        if (habitacion instanceof Doble){
            return new Document().append(PLANTA,habitacion.getPlanta()).append(PUERTA,habitacion.getPuerta()).append(IDENTIFICADOR,habitacion.getIdentificador()).append(PRECIO,habitacion.getPrecio()).append(TIPO,TIPO_DOBLE).append(CAMAS_INDIVIDUALES,((Doble) habitacion).getNumCamasIndividuales()).append(CAMAS_DOBLE,((Doble) habitacion).getNumCamasDobles());
        }
        if (habitacion instanceof Triple){
            return new Document().append(PLANTA,habitacion.getPlanta()).append(PUERTA,habitacion.getPuerta()).append(IDENTIFICADOR,habitacion.getIdentificador()).append(PRECIO,habitacion.getPrecio()).append(TIPO,TIPO_TRIPLE).append(CAMAS_INDIVIDUALES,((Triple) habitacion).getNumCamasIndividuales()).append(CAMAS_DOBLE,((Triple) habitacion).getNumCamasDobles()).append(BANOS,((Triple) habitacion).getNumBanos());
        }
        if (habitacion instanceof Suite){
            return new Document().append(PLANTA,habitacion.getPlanta()).append(PUERTA,habitacion.getPuerta()).append(IDENTIFICADOR,habitacion.getIdentificador()).append(PRECIO,habitacion.getPrecio()).append(TIPO,TIPO_SUITE).append(BANOS,((Suite) habitacion).getNumBanos()).append(JACUZZI,((Suite) habitacion).isTieneJacuzzi());
        }

        return null;

    }

    public static Habitacion gethabitacion(Document documentoHabitacion){
        switch (documentoHabitacion.getString(TIPO)){
            case "SIMPLE": return new Simple(documentoHabitacion.getInteger(PLANTA),(documentoHabitacion.getInteger(PUERTA)),documentoHabitacion.getDouble(PRECIO));
            case "DOBLE": return new Doble (documentoHabitacion.getInteger(PLANTA),documentoHabitacion.getInteger(PUERTA),documentoHabitacion.getDouble(PRECIO),documentoHabitacion.getInteger(CAMAS_INDIVIDUALES),documentoHabitacion.getInteger(CAMAS_DOBLE));
            case "TRIPLE": return new Triple (documentoHabitacion.getInteger(PLANTA),documentoHabitacion.getInteger(PUERTA),documentoHabitacion.getDouble(PRECIO),documentoHabitacion.getInteger(CAMAS_INDIVIDUALES),documentoHabitacion.getInteger(CAMAS_DOBLE),documentoHabitacion.getInteger(BANOS));
            case "SUITE": return new Suite (documentoHabitacion.getInteger(PLANTA),documentoHabitacion.getInteger(PUERTA),documentoHabitacion.getDouble(PRECIO),documentoHabitacion.getInteger(BANOS),documentoHabitacion.getBoolean(JACUZZI));
        }
        return null;
    }
    public static Reserva getReserva(Document documentoReserva){

        Huesped huesped=getHuesped((Document) documentoReserva.get(HUESPED));
        Habitacion habitacion=gethabitacion((Document) documentoReserva.get(HABITACION));

        String fechaInicioReserva=documentoReserva.getString(FECHA_INICIO_RESERVA);
        LocalDate fechaInicio=LocalDate.parse(fechaInicioReserva,FORMATO_DIA);
        String fechaFinReserva=documentoReserva.getString(FECHA_FIN_RESERVA);
        LocalDate fechaFin=LocalDate.parse(fechaFinReserva,FORMATO_DIA);


        Regimen regimen=null;
        for (Regimen opcion:Regimen.values()){
            if (opcion.toString().equals(documentoReserva.getString(REGIMEN))){
                regimen=opcion;
            }
        }

        Reserva reserva=new Reserva(huesped,habitacion,regimen,fechaInicio,fechaFin,documentoReserva.getInteger(NUMERO_PERSONAS)) ;


        return reserva;
    }
    public static  Document getDocumento(Reserva reserva){
        if (reserva==null){
            throw new NullPointerException("ERROR:La reserva no puede ser nula");
        }
        String checkinNulo="";
        String checkoutNulo="";
        if (reserva.getCheckIn()==null){
            checkinNulo="no registrado";
        }else checkinNulo= reserva.getCheckIn().format(FORMATO_DIA_HORA);
        if (reserva.getCheckOut()==null){
            checkoutNulo="no registrado";
        }else checkoutNulo= reserva.getCheckOut().format(FORMATO_DIA_HORA);
        return new Document().append(HUESPED,getDocumento(reserva.getHuesped())).append(HABITACION,getDocumento(reserva.getHabitacion())).append(REGIMEN,reserva.getRegimen().toString()).append(FECHA_INICIO_RESERVA,reserva.getFechaInicioReserva().format(FORMATO_DIA)).append(FECHA_FIN_RESERVA,reserva.getFechaFinReserva().format(FORMATO_DIA)).append(CHECKIN,checkinNulo).append(CHECKOUT,checkoutNulo).append(NUMERO_PERSONAS,reserva.getNumeroPersonas());
    }


}
