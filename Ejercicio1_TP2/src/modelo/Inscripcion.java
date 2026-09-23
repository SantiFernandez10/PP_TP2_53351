package modelo;

import java.time.LocalDate;
import java.io.Serializable;

public class Inscripcion implements Serializable {
    private LocalDate fecha;
    private String estado;
    private Estudiante estudiante;

    public Inscripcion(LocalDate fecha, String estado, Estudiante estudiante) {
        this.fecha = fecha;
        this.estado = estado;
        this.estudiante = estudiante;
    }

    public LocalDate getFecha() {return fecha;}

    public String getEstado() {return estado;}

    public Estudiante getEstudiante() {return estudiante;}
}
