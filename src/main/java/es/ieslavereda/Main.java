package es.ieslavereda;

import es.ieslavereda.model.Alumno;
import es.ieslavereda.model.Alumno.Ciclo;
import es.ieslavereda.model.Alumno.Curso;
import es.ieslavereda.model.Alumno.Titulo;
import es.ieslavereda.model.Docente;
import es.ieslavereda.model.Imprimible;
import es.ieslavereda.model.Persona;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {

        // cargar datos desde csv
        Set<Persona> personas = cargarDatos("documento.csv");

        // pasar de Set<Persona> a Map<Titulo,List<Alumno>>
        Map<Titulo,List<Alumno>> alumnosTitulo = getAlumnosTitulo(personas);

        // imprimir carnets de alumnos ordenados edad
        imprimirCarnets(getAlumnosSortedByAge(personas));

        // imprimir carnets de todos ordenados alfabeticamente
        imprimirCarnets(getPersonsSorted(personas));

        // guardar listado de alumnos de 1DAW
        save(alumnosTitulo.get(Titulo.ASIR_1));

        // guardar personas como objeto
        saveAsObject(personas);

        // cargar personas
        //loadObjectFile();

    }

    private static Set<Persona> loadObjectFile() {
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream("personas"))){

            return (Set<Persona>) ois.readObject();

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private static void saveAsObject(Set<Persona> personas) {

        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("personas"))) {

            oos.writeObject(personas);

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private static Collection<Imprimible> getPersonsSorted(Set<Persona> personas) {
        return personas.stream()
                .sorted()
                .collect(Collectors.toList());
    }

    private static List<Imprimible> getAlumnosSortedByAge(Set<Persona> personas){

        return personas.stream()
                .filter(p -> p instanceof Alumno)
                .map(p-> (Alumno)p)
                .sorted(Comparator.comparingInt(Persona::getEdad))
                .collect(Collectors.toList());

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

    private static void save(Collection<Alumno> alumnos){

        try(PrintWriter pw = new PrintWriter(new FileWriter("alumnos.csv"))){
            String linea="Nombre,Apellidos,NIA,Edad,Mail,Curso,Ciclo";
            pw.println(linea);

            for (Alumno alumno:alumnos)
                pw.println(alumno.getNombre()+","+alumno.getApellidos()+","+alumno.getNIA()+","+alumno.getEdad()+","+alumno.getEmail()+","+alumno.getTitulo());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}