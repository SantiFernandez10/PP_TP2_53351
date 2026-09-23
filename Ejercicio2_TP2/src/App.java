import exepciones.CupoExcedidoExcepcion;
import modelo.Estudiante;
import modelo.EventoUniversitario;
import modelo.Inscripcion;
import modelo.Sala;
import modelo.actividades.Actividad;
import modelo.certificacion.Certificable;

import java.util.ArrayList;
import java.util.List;

public class App {
    public static void main(String[] args) {

        List<Estudiante> estudiantes = new ArrayList<>();
        estudiantes.add(new Estudiante("53351", "Ana Gómez"));
        estudiantes.add(new Estudiante("53324", "Bruno Díaz"));
        estudiantes.add(new Estudiante("52154", "Carla Ruiz"));

        EventoUniversitario evento = new EventoUniversitario("EV001", "Jornada de IA", 10000, false);

        Sala sala = new Sala(1, "Auditorio Principal");
        evento.asignarSala(sala);

        Actividad taller = evento.crearActividad(101, "Taller de Java", 20, "Taller");
        Actividad charla = evento.crearActividad(102, "Charla de IA", 50, "Charla");
        Actividad curso = evento.crearActividad(201, "Curso de POO", 15, "Curso");

        // Caso 1: flujo exitoso
        System.out.println("=== CASO 1: inscribir, persistir y leer (exitoso) ===");
        try {
            charla.inscribir(estudiantes.get(0));
            taller.inscribir(estudiantes.get(1));
            taller.inscribir(estudiantes.get(2));
            curso.inscribir(estudiantes.get(0));
            curso.inscribir(estudiantes.get(2));
            System.out.println("Inscripciones realizadas correctamente.");

            List<String> certificados = new ArrayList<>();
            for (Actividad act : evento.getActividadesEvento()) {
                if (act instanceof Certificable) {
                    Certificable actividadCertificable = (Certificable) act;
                    for (Inscripcion inscripcion : act.getInscripcionesActividad()) {
                        certificados.add(actividadCertificable.generarCertificado(inscripcion.getEstudiante()));
                    }
                }
            }

            System.out.println("=== Certificados emitidos ===");
            for (String certificado : certificados) {
                System.out.println(certificado);
            }


            if (evento.persistirEvento()) {
                System.out.println("Evento guardado correctamente.");
                EventoUniversitario recuperado = evento.recuperarEvento("EV001");
                if (recuperado != null) {
                    System.out.println("Evento recuperado desde archivo:");
                    recuperado.mostrarDatos();
                }
            }
        } catch (CupoExcedidoExcepcion e) {
            System.out.println("Error de inscripción: " + e.getMessage());
        } finally {
            System.out.println(" Fin del caso 1 ");
        }

        // Caso 2: fallo controlado
        System.out.println("=== CASO 2: cupo excedido (fallo controlado) ===");
        Actividad tallerChico = evento.crearActividad(103, "Taller de Git", 1, "Taller");
        try {
            tallerChico.inscribir(estudiantes.get(0));
            tallerChico.inscribir(estudiantes.get(1));
            System.out.println("Inscripciones en Taller de Git realizadas.");
        } catch (CupoExcedidoExcepcion e) {
            System.out.println("Error de inscripción: " + e.getMessage());
            System.out.println("Se cancela el flujo: no se guarda ni se lee el evento.");
        } finally {
            System.out.println(" Fin del caso 2 ");
        }

        evento.mostrarDatos();
    }
}