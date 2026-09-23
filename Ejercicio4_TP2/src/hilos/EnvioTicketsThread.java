package hilos;

import modelo.EventoUniversitario;
import modelo.Inscripcion;
import modelo.actividades.Actividad;

public class EnvioTicketsThread extends Thread {
    public EventoUniversitario evento;

    public EnvioTicketsThread(EventoUniversitario evento) {
        this.evento = evento;
    }

    @Override
    public void run() {
        for (Actividad actividad : evento.getActividadesEvento()) {
            for (Inscripcion inscripcion : actividad.getInscripcionesActividad()) {
                if (inscripcion.getTicket() != null) {
                    inscripcion.getTicket().enviarTicket();
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        System.out.println("El envío de tickets fue interrumpido.");
                    }
                }
            }
        }
        System.out.println("[" + Thread.currentThread().getName() + "] Envío de tickets finalizado.");
    }
}