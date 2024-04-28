package org.iesalandalus.programacion.reservashotel.modelo.dominio;

import java.util.Objects;
import java.util.regex.Pattern;

public abstract class Habitacion implements Comparable<Habitacion> {
    public static double MIN_PRECIO_HABITACION=40;
    public static double MAX_PRECIO_HABITACION=150;
    public static int MIN_NUMERO_PUERTA=0;
    public static int MAX_NUMERO_PUERTA=15;
    public static int MIN_NUMERO_PLANTA=1;
    public static int MAX_NUMERO_PLANTA=3;



    protected String identificador;
    protected int planta;
    protected int puerta;
    protected double precio;



    public Habitacion(int planta,int puerta,double precio){
        setPlanta(planta);
        setPuerta(puerta);
        setPrecio(precio);
        setIdentificador();


    }

    public Habitacion(Habitacion habitacion){
        if (habitacion==null){
            throw new NullPointerException("ERROR: No es posible copiar una habitación nula.");
        }
        setPlanta(habitacion.getPlanta());
        setPuerta(habitacion.getPuerta());
        setPrecio(habitacion.getPrecio());
        setIdentificador();

    }
    public abstract int getNumeroMaximoPersonas();


    public String getIdentificador() {
        return identificador;
    }
    protected void setIdentificador(){
        this.identificador= String.valueOf(planta)+String.valueOf(puerta);

    }

    protected void setIdentificador(String identificador) {

        if (!Pattern.matches("[1-3][1-9][1-5]" , identificador)){
            throw new IllegalArgumentException("ERROR:El identificador es incorrecto");
        }else {
            this.identificador=identificador;
        }
    }

    public int getPlanta() {
        return planta;
    }

    protected void setPlanta(int planta) {
        if (planta<MIN_NUMERO_PLANTA||planta>MAX_NUMERO_PLANTA){
            throw new IllegalArgumentException("ERROR: No se puede establecer como planta de una habitación un valor menor que 1 ni mayor que 3.");
        }else{
            this.planta = planta;
        }

    }

    public int getPuerta() {
        return puerta;
    }

    protected void setPuerta(int puerta) {
        if (puerta<MIN_NUMERO_PUERTA || puerta>=MAX_NUMERO_PUERTA){
            throw new IllegalArgumentException("ERROR: No se puede establecer como puerta de una habitación un valor menor que 0 ni mayor que 15.");
        }
        this.puerta = puerta;
    }

    public double getPrecio() {
        return precio;
    }

    protected void setPrecio(double precio) {
        if (precio<MIN_PRECIO_HABITACION||precio>MAX_PRECIO_HABITACION){
            throw new IllegalArgumentException("ERROR: No se puede establecer como precio de una habitación un valor menor que 40.0 ni mayor que 150.0.");
        }else {
            this.precio = precio;
        }
    }


    @Override
    public int compareTo(Habitacion o) {
        int comparacionPlanta=Integer.compare(this.planta, o.planta);

        if (comparacionPlanta==0){
            return Integer.compare(this.puerta, o.puerta);
        }
        return comparacionPlanta;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Habitacion that = (Habitacion) o;
        return Objects.equals(identificador, that.identificador);
    }

    @Override
    public int hashCode() {
        return Objects.hash(identificador);
    }

    @Override
    public String toString() {

        return String.format("identificador=%s (%s-%s), precio habitación=%s",identificador,planta,puerta,precio);
    }



}
