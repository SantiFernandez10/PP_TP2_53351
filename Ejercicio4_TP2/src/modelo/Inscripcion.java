package modelo;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

public class Inscripcion implements Serializable {
    private LocalDate fecha;
    private String estado;
    private Estudiante estudiante;
    private TicketDeAcceso ticket;

    public Inscripcion(LocalDate fecha, String estado, Estudiante estudiante) {
        this.fecha = fecha;
        this.estado = estado;
        this.estudiante = estudiante;
    }

    public LocalDate getFecha() {return fecha;}

    public String getEstado() {return estado;}

    public Estudiante getEstudiante() {return estudiante;}

    public TicketDeAcceso getTicket() {return ticket;}

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public TicketDeAcceso emitirTicket() {
        if (!"Confirmado".equals(this.estado)) {
            System.out.println("No se puede emitir ticket: inscripción de "
                    + estudiante.getNombre() + " no está confirmada.");
            return null;
        }
        if (this.ticket == null) {
            this.ticket = new TicketDeAcceso();
        }
        return this.ticket;
    }

    public class TicketDeAcceso implements Serializable {
        private String idTicket;
        private LocalDate fechaEmision;

        public TicketDeAcceso() {
            this.idTicket = "TCK-" + UUID.randomUUID().toString().substring(0, 8);
            this.fechaEmision = LocalDate.now();
        }

        public String getIdTicket() {return idTicket;}

        public LocalDate getFechaEmision() {return fechaEmision;}

        public void enviarTicket() {
            System.out.println("[" + Thread.currentThread().getName() + "] Enviando ticket " + idTicket
                    + " a " + estudiante.getNombre() + " (legajo " + estudiante.getLegajo() + ")");
        }
    }
}