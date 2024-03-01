package es.ieslavereda;

import java.io.Serializable;

public class Docente extends Persona implements Serializable {
    private String DNI;

    public Docente(String nombre, String apellidos, int edad, String email, String DNI) {
        super(nombre, apellidos, edad, email);
        this.DNI = DNI;
    }

    @Override
    public String getTipo() {
        return "Docente";
    }

    @Override
    public String getID() {
        return DNI;
    }
}
