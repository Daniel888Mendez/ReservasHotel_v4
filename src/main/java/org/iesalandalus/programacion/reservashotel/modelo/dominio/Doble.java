package org.iesalandalus.programacion.reservashotel.modelo.dominio;

public class Doble extends Habitacion {
    private static final int NUM_MAXIMO_PERSONAS=2;
    static final int  MIN_NUM_CAMAS_INDIVIDUALES = 1;
    static final int MAX_NUM_CAMAS_INDIVIDUALES = 2;
    static final int MIN_NUM_CAMAS_DOBLES = 0;
    static final int MAX_NUM_CAMAS_DOBLES = 1;
    private int numCamasIndividuales;
    private int numCamasDobles;

    public Doble(int planta, int puerta, double precio, int numCamasIndividuales,int numCamasDobles) {
        super(planta, puerta, precio);
        setNumCamasDobles(numCamasDobles);
        setNumCamasIndividuales(numCamasIndividuales);
        validaNumCamas();

    }

    public Doble(Habitacion habitacionDoble) {
        super(habitacionDoble);
        setNumCamasDobles(getNumCamasDobles());
        setNumCamasIndividuales(getNumCamasIndividuales());

    }

    public int getNumCamasIndividuales() {
        return numCamasIndividuales;
    }

    public void setNumCamasIndividuales(int numCamasIndividuales) {
        this.numCamasIndividuales = numCamasIndividuales;
    }

    public int getNumCamasDobles() {
        return numCamasDobles;
    }

    public void setNumCamasDobles(int numCamasDobles) {
        this.numCamasDobles = numCamasDobles;
    }
    private void validaNumCamas(){

        if (getNumCamasIndividuales()<MIN_NUM_CAMAS_INDIVIDUALES || getNumCamasIndividuales()>MAX_NUM_CAMAS_INDIVIDUALES){
            throw new IllegalArgumentException("ERROR: El número de camas individuales de una habitación doble no puede ser inferior a 1 ni mayor que 2");
        }


        if (getNumCamasDobles()<MIN_NUM_CAMAS_DOBLES || getNumCamasDobles()>MAX_NUM_CAMAS_DOBLES){
            throw new IllegalArgumentException("ERROR: El número de camas dobles de una habitación doble no puede ser inferior a 0 ni mayor que 1");
        }

        if (getNumCamasIndividuales() == 2 && getNumCamasDobles() == 1 || getNumCamasIndividuales()== 1 && getNumCamasDobles() == 0 || getNumCamasIndividuales() == 1 && getNumCamasDobles() == 1){
            throw new IllegalArgumentException("ERROR: La distribución de camas en una habitación doble tiene que ser 2 camas individuales y 0 doble o 1 camas individuales y 1 doble");
        }



    }



    @Override
    public String toString() {
        return  super.toString() + String.format( ", habitación doble, capacidad=%d personas, camas individuales=%d, camas dobles=%d" ,getNumeroMaximoPersonas(),numCamasIndividuales,numCamasDobles);


    }

    @Override
    public int getNumeroMaximoPersonas() {
        return NUM_MAXIMO_PERSONAS;
    }


}
