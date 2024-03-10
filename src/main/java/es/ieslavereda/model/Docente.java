package es.ieslavereda.model;

public class Docente extends Persona {
    private final String DNI;

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
