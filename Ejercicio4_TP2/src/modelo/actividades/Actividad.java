package modelo.actividades;

import exepciones.CupoExcedidoExcepcion;
import modelo.Estudiante;
import modelo.Inscripcion;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public abstract class Actividad implements Serializable {
    private int id;
    private String titulo;
    private int cupoMaximo;
    public static final int cupoMinimo = 5;
    private List<Inscripcion> inscripcionesActividad = new ArrayList<>();

    public Actividad(int id, String titulo, int cupoMaximo) {
        this.id = id;
        this.titulo = titulo;
        this.cupoMaximo = cupoMaximo;
    }

    public int getId() { return id; }
    public String getTitulo() { return titulo; }
    public int getCupoMaximo() { return cupoMaximo; }
    public List<Inscripcion> getInscripcionesActividad() { return inscripcionesActividad; }

    public Inscripcion inscribir(Estudiante estudiante) throws CupoExcedidoExcepcion {
        if (this.inscripcionesActividad.size() >= this.cupoMaximo) {
            throw new CupoExcedidoExcepcion("No hay cupo disponible en '" + this.titulo
                    + "' (cupo máximo: " + this.cupoMaximo + ")");
        }
        Inscripcion nuevaInscripcion = new Inscripcion(LocalDate.now(), "Pendiente", estudiante);
        this.inscripcionesActividad.add(nuevaInscripcion);
        return nuevaInscripcion;
    }

    public void mostrarInscripciones() {
        System.out.println(" Se inscribió a:" + this.titulo + " Cuyo Id es:" + this.id);
        if (this.inscripcionesActividad.isEmpty()) {
            System.out.println(" Nadie se inscribió a nada ");
        } else {
            for (Inscripcion ins : this.inscripcionesActividad) {
                System.out.println(" -" + ins.getEstudiante().getNombre() + " Legajo: " + ins.getEstudiante().getLegajo());
            }
        }
    }

    public final void mostrarIdentificacion() {
        System.out.println("Actividad: " + getTipo() + ", id=" + id + " - " + titulo);
    }

    public abstract double calcularCostoMateriales();
    public abstract String getTipo();
}