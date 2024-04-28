package org.iesalandalus.programacion.reservashotel;


import org.iesalandalus.programacion.reservashotel.Controlador.Controlador;
import org.iesalandalus.programacion.reservashotel.vista.Vista;
import org.iesalandalus.programacion.reservashotel.modelo.FactoriaFuenteDatos;
import org.iesalandalus.programacion.reservashotel.modelo.Modelo;

import javax.naming.OperationNotSupportedException;

public class MainApp {





    public static void main(String[] args)  {

        System.out.println("Programa para la Gestión de Hoteles IES Al-Ándalus");
        try {

            Modelo modelo = new Modelo(procesarArgumentosFuenteDatos(args));
            Vista vista = new Vista();
            Controlador controlador = new Controlador(modelo,vista);
            controlador.comenzar();

        }catch (NullPointerException | OperationNotSupportedException | IllegalArgumentException e){
            System.out.println("-"+e.getMessage());
        }


    }
    private static FactoriaFuenteDatos procesarArgumentosFuenteDatos(String[] argumentos){
        for (String argumento : argumentos){
            if (argumento.equals("-fdmongodb")){
                return FactoriaFuenteDatos.MONGODB;
            }
            if (argumento.equals("-fdmemoria")){
                return FactoriaFuenteDatos.MEMORIA;
            }
        }
        return FactoriaFuenteDatos.MONGODB;
    }



}
