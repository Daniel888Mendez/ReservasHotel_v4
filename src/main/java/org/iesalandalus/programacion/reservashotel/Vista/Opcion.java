package org.iesalandalus.programacion.reservashotel.vista;


import org.iesalandalus.programacion.reservashotel.controlador.Controlador;

import javax.naming.OperationNotSupportedException;

public enum Opcion {


    SALIR("Salir"){
        @Override
        public void ejecutar() {

        }
    },
    INSERTAR_HUESPED("Insertar Huesped") {
        @Override
        public void ejecutar() throws OperationNotSupportedException {
            vista.insertarHuesped();

        }
    },
    BUSCAR_HUESPED("Buscar Huesped") {
        @Override
        public void ejecutar() throws OperationNotSupportedException {
            vista.buscarHuesped();

        }
    },
    BORRAR_HUESPED("Borrar Huesped") {
        @Override
        public void ejecutar() throws OperationNotSupportedException {
            vista.borrarHuesped();

        }
    },
    MOSTRAR_HUESPEDES("Mostrar huespedes") {
        @Override
        public void ejecutar() {
            vista.mostrarHuespedes();

        }
    },
    INSERTAR_HABITACION("Insertar habitacion") {
        @Override
        public void ejecutar() throws OperationNotSupportedException {
            vista.insertarHabitacion();

        }
    },
    BUSCAR_HABITACION("Buscar Habitacion") {
        @Override
        public void ejecutar() throws OperationNotSupportedException {
            vista.buscarHabitacion();

        }
    },
    BORRAR_HABITACION("Borrar Habitacion") {
        @Override
        public void ejecutar() throws OperationNotSupportedException {
            vista.borrarHabitacion();

        }
    },
    MOSTRAR_HABITACIONES("Mostrar Habitaciones") {
        @Override
        public void ejecutar() {
            vista.mostrarHabitaciones();

        }
    },
    INSERTAR_RESERVA("Insertar reservas") {
        @Override
        public void ejecutar() throws OperationNotSupportedException {
            vista.insertarReservas();

        }
    },
    ANULAR_RESERVA("Anular reservas") {
        @Override
        public void ejecutar() throws OperationNotSupportedException {
            vista.anularReserva();

        }
    },
    MOSTRAR_RESERVAS("Mostrar reservas") {
        @Override
        public void ejecutar() {
            vista.mostrarReservas();

        }
    },
    LISTAR_RESERVAS_HUESPED("Mostrar Reservas de un Huesped") {
        @Override
        public void ejecutar() throws OperationNotSupportedException {
            vista.mostrarReservasHuesped();
        }
    },
    LISTAR_RESERVAS_TIPO_HABITACION("Mostrar Reservas por tipo de habitacion") {
        @Override
        public void ejecutar() throws OperationNotSupportedException {
            vista.mostrarReservasTipoHabitacion();

        }
    },

    CONSULTAR_DISPONIBILIDAD("Consultar disoinibilidad") {
        @Override
        public void ejecutar() {


        }
    },
    CHECKIN("Realizar checking") {
        @Override
        public void ejecutar() throws OperationNotSupportedException {
            vista.realizarCheckin();
        }
    },
    CHECOUT("Realizar Checkout") {
        @Override
        public void ejecutar() throws OperationNotSupportedException {
            vista.realizarCheckOut();
        }
    };

    private final String mensajeAMostrar;


    private Opcion(String mensajeAMostrar) {
        this.mensajeAMostrar=mensajeAMostrar;

    }

    private static Vista vista;
    public abstract void ejecutar() throws OperationNotSupportedException;

    static void setVista(Vista vista) {
        if (vista==null){
            throw new NullPointerException("ERROR: La vista no puede ser nula.");
        }
        Opcion.vista=vista;

    }


    @Override
    public String toString() {
        return (ordinal()+1)+".-"+mensajeAMostrar;
    }


}
