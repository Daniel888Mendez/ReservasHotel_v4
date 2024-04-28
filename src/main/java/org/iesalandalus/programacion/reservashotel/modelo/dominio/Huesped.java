package org.iesalandalus.programacion.reservashotel.modelo.dominio;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Huesped implements Comparable<Huesped> {
    private static final String ER_TELEFONO="[6|7|9][0-9]{8}";
    private static final String ER_CORREO="^([0-9a-zA-Z]+[-._+&])*[0-9a-zA-Z]+@([-0-9a-zA-Z]+[.])+[a-zA-Z]{2,6}$";
    private static final String ER_DNI="([0-9]{8})([A-Z a-z])";

    public static final String FORMATO_FECHA="dd/MM/yyyy";

    private String nombre;
    private String telefono;
    private  String correo;
    private String dni;
    private LocalDate fechaNacimiento;


    //En los constructores uso el metodo set porque es alli donde se contemplan las excepciones.
    public Huesped(String nombre,String dni,String correo,String telefono,LocalDate fechaNacimiento){
        setNombre(nombre);
        setDni(dni);
        setCorreo(correo);
        setTelefono(telefono);
        setFechaNacimiento(fechaNacimiento);
    }
    public Huesped(Huesped huesped) {
        if (huesped==null){
            throw new NullPointerException("ERROR: No es posible copiar un huésped nulo.");
        }
        setNombre(huesped.getNombre());
        setDni(huesped.getDni());
        setCorreo(huesped.getCorreo());
        setTelefono(huesped.getTelefono());
        setFechaNacimiento(huesped.getFechaNacimiento());

    }

    //Formatea una cadena eliminando espacios delante y detras y creando una lista con cada cadena separada por espacio.
    private String formateaNombre(String nombre){
        //El trim elimina espacios.
        nombre=nombre.trim();
        String nombreArray[];
        //Creara una lista cuando encuentre un espacio
        nombreArray=nombre.split("\\s+");

        //Recorer? la lista y pondra en myusculas la primera letra de la cadena.
        for (int i=0;i<= nombreArray.length-1;i++){
            nombreArray[i]=nombreArray[i].toUpperCase().charAt(0)+nombreArray[i].substring(1).toLowerCase();
        }
        //Retorna la lista con las cadenas separadas por un espacio
        return String.join(" ",nombreArray);
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre){
        if (nombre==null){
            throw new NullPointerException("ERROR: El nombre de un huésped no puede ser nulo.");
        }
        if (nombre.isBlank()){
            throw new IllegalArgumentException("ERROR: El nombre de un huésped no puede estar vacío.");
        }


        this.nombre = formateaNombre(nombre);


    }
    private Boolean comprobarLetraDni(String dni){
        boolean validacion;

        Pattern patronDni=Pattern.compile(ER_DNI);
        Matcher coincidencia= patronDni.matcher(dni);
        validacion=coincidencia.matches();
        //Valido que el dni siga el patron ER_DNI y luego compruebo la letra del dni.
        //Aqui se separa la parte numeria de la letra del dni y se forman dos grupo gracias a los parentesis previamente colocados en la ER de dni.
        //Despues se hace el calculo para comprovar la letra usando esos dos grupos.
        if (validacion){
            int numerosDni= Integer.parseInt(coincidencia.group(1));
            char letraDni=coincidencia.group(2).charAt(0);
            char[] letras= "TRWAGMYFPDXBNJZSQVHLCKE".toCharArray();
            int i=numerosDni%23;
            if (letras[i]==letraDni){
                return true;
            }
        }else{
            return false;
        }
        return false;
    }
    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        if (dni==null){
            throw new NullPointerException("ERROR: El dni de un huésped no puede ser nulo.");
        }
        if (!Pattern.matches(ER_DNI ,dni)){
            throw new IllegalArgumentException("ERROR: El dni del huésped no tiene un formato válido.");
        }
        if (!comprobarLetraDni(dni)) {
            throw new IllegalArgumentException("ERROR: La letra del dni del huésped no es correcta.");
        }else {
            //Si no salta ninguna excepcion de las contempladas arriba almacenara el dni (porque es correcto)en el atributo dni de la clase.
            this.dni=dni;
        }


    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        if (correo==null){
            throw new NullPointerException("ERROR: El correo de un huésped no puede ser nulo.");
        }
        if (!Pattern.matches(ER_CORREO,correo)){
            throw new IllegalArgumentException("ERROR: El correo del huésped no tiene un formato válido.");
        }else {
            this.correo=correo;
        }

    }

    public String getTelefono() {

        return telefono;
    }

    public void setTelefono(String telefono) {
        if (telefono==null){
            throw new NullPointerException("ERROR: El teléfono de un huésped no puede ser nulo.");
        }
        if (!Pattern.matches(ER_TELEFONO,telefono)){
            throw new IllegalArgumentException("ERROR: El teléfono del huésped no tiene un formato válido.");
        }
        this.telefono=telefono;

    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento)  {
        if (fechaNacimiento==null){
            throw new NullPointerException("ERROR: La fecha de nacimiento de un huésped no puede ser nula.");
        }

        this.fechaNacimiento=fechaNacimiento;
    }
    private String getIniciales(){
        //Creo Una Array con las cadenas del nombre para quedarme conlas iniciales

        String[] palabras= getNombre().split("\\s+");
        StringBuilder iniciales=new StringBuilder();
        for (String palabra:palabras) {
            if (!palabra.isEmpty()) {
                iniciales.append(palabra.charAt(0));
            }
        }
        //Retorno esas iniciales en mayusculas.
        return iniciales.toString().toUpperCase();
    }

    //para poder comparar Huesped por nombre.
    @Override
    public int compareTo(Huesped o) {
        return this.nombre.compareTo(o.nombre);
    }

    @Override
    //Solo dos huespedes seran iguales si tienen el mismo DNI.
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Huesped huesped = (Huesped) o;
        return Objects.equals(dni, huesped.dni);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dni);
    }

    @Override
    //La fecha se inserta en horario internacional pero se muestra por pantalla en fformato espanol usando el .offPattern
    public String toString() {
        return String.format( "nombre=%s (%s), DNI=%s, correo=%s, teléfono=%s, fecha nacimiento=%s",nombre,getIniciales(),dni, correo,telefono,fechaNacimiento.format( DateTimeFormatter.ofPattern(FORMATO_FECHA)));
    }
}

