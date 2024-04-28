package org.iesalandalus.programacion.reservashotel.modelo.dominio;

public enum Regimen {
    SOLO_ALOJAMIENTO("alojamiento",0.0),
    ALOJAMIENTO_DESAYUNO("alojamiento y deyuno",15.0),
    MEDIA_PENSION("media pension",30.0),
    PENSION_COMPLETA("pension completa",50.0);

    private String descripcion;
    private Double incrementoPrecio;
    //Este metodo no se llega a usar
    private Regimen(String descripcion, double incrementoPrecio){
        this.descripcion=descripcion;
        this.incrementoPrecio=incrementoPrecio;
    }
    public double getIncrementoPrecio(){
        return incrementoPrecio;
    }

    @Override
    public String toString() {

        return String.format("regimen=%s,incremento=%.2f ?",descripcion,incrementoPrecio);
    }

}
