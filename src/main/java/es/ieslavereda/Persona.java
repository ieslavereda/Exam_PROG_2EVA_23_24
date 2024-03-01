package es.ieslavereda;

import java.io.Serializable;

public abstract class Persona implements Imprimible, Comparable<Persona>, Serializable {
    protected String nombre;
    private String apellidos;
    private int edad;
    private String email;

    public Persona(String nombre, String apellidos, int edad, String email) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.edad = edad;
        this.email = email;
    }

    public abstract String getID();

    @Override
    public String getFullName() {
        return nombre+" "+getApellidos();
    }

    public String getApellidos() {
        return apellidos;
    }

    public int getEdad() {
        return edad;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public int hashCode() {
        return getID().toLowerCase().hashCode();
    }

    @Override
    public int compareTo(Persona o) {
        return (apellidos.compareToIgnoreCase(o.apellidos)==0)?
                nombre.compareToIgnoreCase(o.nombre):
                apellidos.compareToIgnoreCase(o.apellidos);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj==null) return false;
        if(!(obj instanceof Persona)) return false;
        Persona p = (Persona) obj;

        return p.getID().equalsIgnoreCase(getID());
    }
}
