package es.ieslavereda.model;

public class Alumno extends Persona {
    private final String NIA;
    private final Titulo titulo;

    public Alumno(String nombre, String apellidos, int edad, String email, String NIA, Titulo titulo) {
        super(nombre, apellidos, edad, email);
        this.NIA = NIA;
        this.titulo = titulo;
    }

    public Titulo getTitulo() {
        return titulo;
    }

    public String getNombre(){
        return nombre;
    }

    public String getNIA() {
        return NIA;
    }

    @Override
    public String getTipo() {
        return "Alumno";
    }

    @Override
    public String getID() {
        return NIA;
    }


    public enum Curso{
        PRIMERO(1),SEGUNDO(2);

        private final int value;
        Curso(int value) {
            this.value=value;
        }

        public static Curso getCursoFromInt(int value) throws Exception {
            if(value!=1 && value!=2) throw new Exception("Valor de curso incorrecto: " + value);
            return (value==1) ? PRIMERO : SEGUNDO;
        }

        @Override
        public String toString() {
            return String.valueOf(value);
        }
    }
    public enum Ciclo{
        DAM,
        DAW,
        ASIR,
        SMR
    }
    public enum Titulo {
        DAM_1(Curso.PRIMERO,Ciclo.DAM),
        DAM_2(Curso.SEGUNDO,Ciclo.DAM),
        DAW_1(Curso.PRIMERO,Ciclo.DAW),
        DAW_2(Curso.SEGUNDO,Ciclo.DAW),
        ASIR_1(Curso.PRIMERO,Ciclo.ASIR),
        ASIR_2(Curso.SEGUNDO,Ciclo.ASIR),
        SMR_1(Curso.PRIMERO,Ciclo.SMR),
        SMR_2(Curso.SEGUNDO,Ciclo.SMR);

        private final Curso curso;
        private final Ciclo ciclo;

        Titulo(Curso curso, Ciclo ciclo) {
            this.curso = curso;
            this.ciclo = ciclo;
        }

        public static Titulo getTitulo(Curso curso, Ciclo ciclo){

            Titulo t;
            int i=0;

            do {
                t = Titulo.values()[i++];
            }while(t.ciclo!=ciclo || t.curso!=curso);

            return t;
        }

        @Override
        public String toString() {
            return curso.toString()+","+ciclo ;
        }
    }
}
