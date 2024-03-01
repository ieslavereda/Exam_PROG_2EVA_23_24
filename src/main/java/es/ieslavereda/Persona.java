package es.ieslavereda;

public abstract class Persona implements Imprimible {
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
}
