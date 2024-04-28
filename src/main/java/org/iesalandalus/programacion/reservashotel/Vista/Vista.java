package org.iesalandalus.programacion.reservashotel.vista;

import org.iesalandalus.programacion.reservashotel.Controlador.Controlador;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.*;
import org.iesalandalus.programacion.utilidades.Entrada;

import javax.naming.OperationNotSupportedException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;

import static org.iesalandalus.programacion.reservashotel.vista.Consola.*;


public class Vista {
    private  Controlador controlador;

    public Vista() {

    }

    public void comenzar() throws OperationNotSupportedException {
        Opcion opcion=null;
        do {
            try {
                Consola.mostrarMenu();
                opcion=Consola.elegirOpcion();
                opcion.ejecutar();

            }catch (NullPointerException| DateTimeParseException|IllegalArgumentException e){
                System.out.println("-" +e.getMessage());
            }


        }while (opcion!=Opcion.SALIR);
        terminar();

    }
    public void terminar() throws OperationNotSupportedException {
        controlador.terminar();

    }

    public void setControlador(Controlador controlador) {
        if (controlador == null) {
            throw new NullPointerException("ERROR: El controlador no puede ser nulo.");
        }
        this.controlador = controlador;
        Opcion.setVista(this);
    }

    public  void insertarHuesped() throws OperationNotSupportedException {
        Huesped huesped=new Huesped(Consola.leerHuespedes());
        controlador.insertar(huesped);

        System.out.println("huesped creado");

    }
    public  void buscarHuesped() throws OperationNotSupportedException {

        Huesped huesped=Consola.getHuespedPorDni();
        Iterator <Huesped> huespedIterator =controlador.getHuespedes().iterator();
        if (controlador.getHuespedes().isEmpty()){
            System.out.println("No huesped");
        }

        while (huespedIterator.hasNext()){
            Huesped huesped1=huespedIterator.next();
            if (huesped != null){
                if (huesped1.getDni().equals(huesped.getDni())){
                    System.out.println(controlador.buscar(huesped1));
                }else {
                    System.out.println("no se encontro huesped");
                }
            }
        }
    }




    public  void borrarHuesped() throws OperationNotSupportedException {
        controlador.borrar(getHuespedPorDni());
        System.out.println("Huesped BORRADO");
    }

    public  void mostrarHuespedes()  {

        ArrayList<Huesped> huespedArrayList=controlador.getHuespedes();
        System.out.println(" ");
        System.out.println("Resultrados de la busqueda ordenado Alfabeticamente.");

        Collections.sort(huespedArrayList);
        for (Huesped huesped:huespedArrayList){
            System.out.println("-->"+huesped);
        }
        System.out.println(" ");

    }

    public  void insertarHabitacion() throws OperationNotSupportedException {
        controlador.insertar(leerHabitacion());
        System.out.println("Habitacion insertada");
    }

    public  void buscarHabitacion() throws OperationNotSupportedException {

        Habitacion habitacion=Consola.leerHabitacionPorIdentificador();
        if (habitacion!=null){
            if (controlador.getHabitaciones().isEmpty()){
                System.out.println("No hay habitaciones");
            }else{
                Iterator<Habitacion> habitacionIterator=controlador.getHabitaciones().iterator();
                while (habitacionIterator.hasNext()){
                    Habitacion habitacion1=habitacionIterator.next();
                    if (habitacion1.getIdentificador().equals(habitacion.getIdentificador())){
                        System.out.println(controlador.buscar(habitacion1));
                    }
                }

            }

        }

    }

    public  void borrarHabitacion() throws OperationNotSupportedException {

        controlador.borrar(Consola.leerHabitacionPorIdentificador());
        System.out.println("Habitacion borrada");

    }

    public  void mostrarHabitaciones(){

        ArrayList<Habitacion> habitacionesArrayList=controlador.getHabitaciones();
        System.out.println(" ");
        System.out.println("Resultrados de la busqueda orenada por planta/puerta");

        Collections.sort(habitacionesArrayList);

        for (Habitacion habitacion:habitacionesArrayList){
            System.out.println("-->"+habitacion);
        }
        System.out.println(" ");

    }



    public  void insertarReservas() throws OperationNotSupportedException {
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        System.out.println("!!!!!!!!!CREANDO RESERVA!!!!!!!!!");
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        System.out.println(" ");

        System.out.println("Numero de pesonas alojadas:");
        int numPerson = Entrada.entero();


        Huesped huesped=controlador.buscar(Consola.getHuespedPorDni());

        Habitacion habitacion=Consola.leerHabitacionPorIdentificador();

        Iterator<Habitacion> habitacionIterator=controlador.getHabitaciones().iterator();
        while (habitacionIterator.hasNext()){
            Habitacion habitacion1=habitacionIterator.next();
            if (habitacion1.getIdentificador().equals(habitacion.getIdentificador())){
                Reserva reserva= new Reserva(huesped, habitacion1, leerRegimen(), leerFecha("Entrada"), leerFecha("Salida"), numPerson);
                controlador.insertar(reserva);
                System.out.println("La reserva esta creada");
                System.out.println(reserva);
            }else {
                System.out.println("dsdaad");
            }
        }

    }

    public  void listarReservas(Huesped huesped){
        if (huesped==null){
            throw new NullPointerException("ERROR: El huesped no puede ser nulo.");
        }
        System.out.println(" ");
        System.out.println("Estas son las reservas del huesped  con DNI--> "+ huesped.getDni() + " Ordenado por fecha de inicio");
        ArrayList<Reserva> reservaArrayList=controlador.getReservas(huesped);
        Collections.sort(reservaArrayList);
        if (reservaArrayList.isEmpty()){
            System.out.println("No hay ninguna reserva que mostrar");
        }else {
            for (Reserva reserva:reservaArrayList){
                System.out.println("-->" + reserva);
            }
        }
        System.out.println(" ");

    }

    public  void listarReservas(TipoHabitacion tipoHabitacion) throws OperationNotSupportedException {
        ArrayList<Reserva> reservaArrayList=controlador.getReservas(tipoHabitacion);
        System.out.println(" ");
        System.out.println("Resultados de la busqueda ordenados por fecha de inicio");
        Collections.sort(reservaArrayList);
        if (reservaArrayList.isEmpty()){
            System.out.println("No hay ninguna reserva que mostrar");
        }else {
            for (Reserva reserva:reservaArrayList){
                System.out.println("-->" + reserva);
            }
        }
        System.out.println(" ");

    }

    public  ArrayList<Reserva> getReservasAnulables(ArrayList<Reserva> reservasAAnular){
        ArrayList<Reserva> reservaArrayList=new ArrayList<>();
        Huesped huesped=getHuespedPorDni();
        for (Reserva reserva:reservasAAnular){
            if (Objects.equals(reserva.getHuesped(),huesped)){
                reservaArrayList.add(reserva);
            }
        }
        return reservaArrayList;

    }
    public  void anularReserva() throws OperationNotSupportedException {
        ArrayList<Reserva> reservaArrayList=getReservasAnulables(controlador.getReservas());


        if (reservaArrayList.isEmpty()){
            System.out.println("El huesped introducido no tiene reservas anulables.");
        }else {
            System.out.println(" ");
            System.out.println("ESTAS SON LAS RESERVAS del huesped");

            int contador=1;
            for (Reserva reserva:reservaArrayList){
                System.out.println("-." + contador +"--> "+ reserva);
                contador++;
            }

            int opcion;
            do {
                System.out.println("Indica que reserva desea borrar");
                opcion= Entrada.entero();
                if (opcion<1 | opcion > reservaArrayList.size()){
                    System.out.println(" vuelve a introducir la reserva que desea borrar");
                }

            }while (opcion < 1 || opcion > reservaArrayList.size());

            char confirmacionRespuesta = 0;
            do {
                System.out.println("Esta seguro de que desea eliminar la reserva? (s/n)");
                confirmacionRespuesta=Entrada.caracter();

            }while (confirmacionRespuesta != 's' && confirmacionRespuesta != 'n');

            if (confirmacionRespuesta=='s'){
                controlador.borrar(reservaArrayList.get(opcion - 1));
                System.out.println("La reserva ha sido anulada con exito.");
            }
            if (confirmacionRespuesta=='n'){
                System.out.println("Saliendo sin borrar");
            }

        }

    }

    public  Reserva mostrarReservas()  {
        if (Controlador.getReservas().isEmpty()){
            System.out.println("No hay reservas que mostrar.");
        }else {
            ArrayList<Reserva> reservaArrayList=controlador.getReservas();
            System.out.println("!!!!!!!Resultado ordenado por fecha!!!!!!!");
            Collections.sort(reservaArrayList);
            for (Reserva reserva: reservaArrayList){
                System.out.println("-->" + reserva);
            }

        }
        return null;
    }
    public void mostrarReservasHuesped(){
        listarReservas(Consola.getHuespedPorDni());

    }

    public void mostrarReservasTipoHabitacion() throws OperationNotSupportedException {
        listarReservas(Consola.leerTipoHabitacion());

    }



    public  Habitacion consultarDisponibilidad(TipoHabitacion tipoHabitacion, LocalDate fechaInicioReserva, LocalDate fechaFinReserva) {

        return null;
    }


    public  void realizarCheckin() {
        int numeroReserva=1;
        Huesped huesped = getHuespedPorDni();
        ArrayList<Reserva> reservaArrayList=controlador.getReservas(huesped);
        if (reservaArrayList.isEmpty()){
            System.out.println("No hay reservas para este Huesped.");
        }else {
            Iterator<Reserva> reservaIterator=reservaArrayList.iterator();
            while (reservaIterator.hasNext()){
                System.out.println(numeroReserva + "-." + reservaIterator.next().toString());
                numeroReserva++;
            }
        }
        System.out.println(" ");

        int opcion=0;
        do {
            System.out.println("Introduce el numero de la reserva para realizar el checkin");
            opcion=Entrada.entero();
        }while (opcion<1 || opcion > reservaArrayList.size());


        if (reservaArrayList.get(opcion-1).getCheckIn()==null){
            controlador.realizarCheckin(reservaArrayList.get(opcion-1),Consola.leerFechaHora("Introduzca la fecha de cheking"));

        }else {
            System.out.println("Ya hay un checkin realizado");
        }


    }
    public  void realizarCheckOut() throws OperationNotSupportedException {
        int numeroReserva=1;
        Huesped huesped = getHuespedPorDni();
        ArrayList<Reserva> reservaArrayList=controlador.getReservas(huesped);
        if (reservaArrayList.isEmpty()){
            System.out.println("No hay reservas para este Huesped.");
        }else {
            Iterator<Reserva> reservaIterator=reservaArrayList.iterator();
            while (reservaIterator.hasNext()){
                System.out.println(numeroReserva + "-." + reservaIterator.next().toString());
                numeroReserva++;
            }
        }
        System.out.println(" ");

        int opcion=0;
        do {
            System.out.println("Introduce el numero de la reserva para realizar el checkout");
            opcion=Entrada.entero();
        }while (opcion<1 || opcion > reservaArrayList.size());


        if (reservaArrayList.get(opcion-1).getCheckOut()==null){
            controlador.realizarCheckOut(reservaArrayList.get(opcion-1),Consola.leerFechaHora("Introduzca la fecha de checkOut"));

        }else {
            System.out.println("Ya hay un checkout realizado");
        }

    }

}