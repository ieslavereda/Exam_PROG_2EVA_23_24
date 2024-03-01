package es.ieslavereda;

import es.ieslavereda.Alumno.Ciclo;
import es.ieslavereda.Alumno.Curso;
import es.ieslavereda.Alumno.Titulo;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Main {
    public static void main(String[] args) {

        Set<Persona> personas = cargarDatos("documento.csv");

        Map<Titulo,List<Alumno>> alumnosTitulo = getAlumnosTitulo(personas);


       // imprimirCarnets(new HashSet<>(personas));
        imprimirCarnets(new HashSet<>(alumnosTitulo.get(Titulo.ASIR_1)));

    }

    private static Map<Titulo, List<Alumno>> getAlumnosTitulo(Set<Persona> personas) {

        Map<Titulo, List<Alumno>> titulos= new HashMap<>();

        for(Persona persona : personas){
            if(persona instanceof Alumno){
                Alumno alumno = (Alumno) persona;
                if(titulos.containsKey(alumno.getTitulo()))
                    titulos.get(alumno.getTitulo()).add(alumno);
                else {
                    titulos.put(alumno.getTitulo(),new ArrayList<>(List.of(alumno)));
                }
            }
        }
        return titulos;
    }


    private static Set<Persona> cargarDatos(String file) {

        Set<Persona> personas = new HashSet<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {

            String l;
            br.readLine(); // Eliminamos la primera linea
            while ((l = br.readLine()) != null) {
                String a[] = l.split(",");
                String nombre = a[1];
                String apellidos = a[2];
                int edad = Integer.parseInt(a[5]);
                String mail = a[6];

                if (a[0].equalsIgnoreCase("Alumno")) {
                    try {
                        String NIA = a[3];
                        Curso curso = Curso.getCursoFromInt(Integer.parseInt(a[7]));
                        Ciclo ciclo = Ciclo.valueOf(a[8]);
                        Titulo titulo = Titulo.getTitulo(curso, ciclo);
                        personas.add(new Alumno(nombre, apellidos, edad, mail, NIA, titulo));
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                } else {
                    String DNI = a[4];
                    personas.add(new Docente(nombre, apellidos, edad, mail, DNI));
                }
            }


        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return personas;
    }

    private static void imprimirCarnets(Collection<Imprimible> imprimibles){
        for(Imprimible i : imprimibles){
            System.out.println("-----------------------");
            System.out.println(i.getTipo());
            System.out.println(i.getFullName());
            System.out.println("-----------------------");
        }
    }
}