package org.iesalandalus.programacion.reservashotel.vista;

import org.iesalandalus.programacion.reservashotel.modelo.dominio.*;
import org.iesalandalus.programacion.utilidades.Entrada;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.regex.Pattern;



public class Consola {
    private Consola() {

    }
    public static void mostrarMenu(){
        System.out.println("!!!!!!!!!!!! MENU !!!!!!!!!!!!");
        for (Opcion opcion : Opcion.values()) {
            System.out.println(opcion+".");
        }
    }

    public static Opcion elegirOpcion(){

        int opcionUsuario=0;

        do {
            System.out.println("Eligir Opcion:");
            opcionUsuario= Entrada.entero();
            if (opcionUsuario<1||opcionUsuario>17){
                System.out.println("NUMERO INCORRECTO-->Introduce un numero correcto(1-15).");
            }
        }while (opcionUsuario<1||opcionUsuario>17);

        Opcion[] opciones=Opcion.values();
        System.out.println(opciones[opcionUsuario-1]);
        return opciones[opcionUsuario-1];

    }
    public static Huesped leerHuespedes()  {

        String telefono;
        String correo;
        String nombre;
        String dni;
        LocalDate fecNacimiento;

        System.out.println("!!!!!DATOS CLIENTE!!!!!");

        System.out.print("NOMBRE:");
        nombre = Entrada.cadena();

        System.out.print("DNI:");
        dni = Entrada.cadena();

        System.out.print("CORREO:");
        correo = Entrada.cadena();

        System.out.print("TELEFONO:");
        telefono = Entrada.cadena();

        System.out.print("FECHA NACIMIENTO (dd-MM-yyyy):");
        String nacimientoCad = Entrada.cadena();
        fecNacimiento=LocalDate.parse(nacimientoCad, DateTimeFormatter.ofPattern("dd-MM-yyyy"));

        System.out.println("Creado correctamente");

        return new Huesped(nombre,dni,correo,telefono,fecNacimiento);

    }
    public static Huesped getHuespedPorDni(){

        String nombre="-";
        String dni;
        String telefono="666666666";
        String correo="correoFalso@gmail.com";
        LocalDate fechaNac=LocalDate.of(2000,01,01);

        System.out.println("Introduce el dni del cliente que quiere buscar");
        dni=Entrada.cadena();

        return new Huesped(nombre,dni,correo,telefono,fechaNac);

    }

    public static LocalDate leerFecha(String mensaje){
        String formatoFecha = "\\d{2}-\\d{2}-\\d{4}";
        String fecha;
        System.out.println(" ");
        System.out.println("!!!!!!RESERVA DIAS!!!!!!");

        do {
            System.out.println(mensaje);
            fecha=Entrada.cadena();
            if (!Pattern.matches(formatoFecha,fecha)){
                System.out.println("La fecha tiene que tener este formato-->dd-MM-yyyy");
            }
        }while (!Pattern.matches(formatoFecha,fecha));

        LocalDate fecNacimiento=LocalDate.parse(fecha,DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        return fecNacimiento;

        //return LocalDate.parse(fecha);
    }
    public static LocalDateTime leerFechaHora(String mensaje){
        // String formatoFecha = "\\d{2}-\\d{2}-\\d{4} \\d{2}:\\d{2}:\\d{2}";
        DateTimeFormatter formater=DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        LocalDateTime prueba=null;
        String fecha;

        do {
            try {
                System.out.println(mensaje);
                fecha=Entrada.cadena();
                prueba=LocalDateTime.parse(fecha,formater);
            }catch (DateTimeParseException e){
                System.out.println("La fecha introducida tiene un formato incorrecto, usa formato dd-MM-yyy HH:mm:ss");
            }

        }while (prueba==null);

        return prueba;

    }


    public static Habitacion leerHabitacion(){
        int planta;
        int puerta;
        double precio;


        System.out.println(" ");
        System.out.println("!!!!!DATOS HABITACION!!!!!!");

        System.out.print("Introduce la planta:");
        planta=Entrada.entero();

        System.out.print("Introduce la puerta:");
        puerta=Entrada.entero();
        System.out.print("Introduce el precio:");
        precio=Entrada.realDoble();
        switch (leerTipoHabitacion()){
            case SIMPLE:
                return new Simple(planta,puerta,precio);
            case DOBLE:
                System.out.println("Introduce numero camas individiales 1-2");
                int individualesDoble=Entrada.entero();
                System.out.println("Introduce numero camas Dobles 0-1");
                int doblesDobles=Entrada.entero();
                return new Doble(planta,puerta,precio,individualesDoble,doblesDobles);
            case TRIPLE:
                System.out.println("Introduce numero de baños:");
                int banos=Entrada.entero();
                System.out.println("Introduce numero camas individiales 1-2");
                int individualesTriple=Entrada.entero();
                System.out.println("Introduce numero camas Dobles 0-1");
                int doblesTriple=Entrada.entero();
                return new Triple(planta,puerta,precio,banos,individualesTriple,doblesTriple);
            case SUITE:

                Boolean tieneJacuzzi = false;
                System.out.println("Introduce numero de baños:");
                int banosSuite=Entrada.entero();
                char siONo;
                do {
                    System.out.println("Con jacuzzi? S o N");
                    siONo=Entrada.caracter();
                }while (siONo!='s' && siONo !='n');
                if (siONo=='S'|| siONo=='s'){
                    tieneJacuzzi=true;
                }
                if (siONo=='N' || siONo=='n'){
                    tieneJacuzzi=false;
                }
                return new Suite(planta,puerta,precio,banosSuite,tieneJacuzzi);

            default:
                System.out.println("Problemas con el tipo de Habitacion");
        }

        return null;
    }
    public static Habitacion leerHabitacionPorIdentificador(){
        int planta;
        int puerta;
        double precio= 50;

        System.out.print("Introduce la planta:");
        planta=Entrada.entero();
        System.out.print("Introduce la puerta:");
        puerta=Entrada.entero();

        return new Simple(planta,puerta,precio);


    }

    public static TipoHabitacion leerTipoHabitacion(){

        System.out.println(" ");
        System.out.println("!!!!!!!!!!TIPOS DE HABITACIONES!!!!!!!!!!!");
        for (TipoHabitacion tipos : TipoHabitacion.values()) {
            System.out.println((tipos.ordinal()+1) +"-."+tipos);
        }

        int opcion;
        System.out.print("Elige un tipo de Habitacion(1-4)-->");
        opcion=Entrada.entero();

        TipoHabitacion[] tipos=TipoHabitacion.values();
        return tipos[opcion-1];

    }

    public static Regimen leerRegimen(){
        System.out.println(" ");
        System.out.println("!!!!!!!!!!TIPOS DE REGIMEN!!!!!!!!!!!");

        for (Regimen tipos : Regimen.values()) {
            System.out.println((tipos.ordinal()+1) +"-."+tipos);
        }

        int opcion;
        System.out.print("Elige un tipo de Regimen(1-4)-->");
        opcion=Entrada.entero();

        return Regimen.values()[opcion-1];
    }

}
