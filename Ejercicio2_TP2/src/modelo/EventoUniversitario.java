package modelo;

import modelo.actividades.Actividad;
import modelo.actividades.Charla;
import modelo.actividades.Taller;
import modelo.actividades.Curso;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.NotSerializableException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import java.io.FileInputStream;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.StreamCorruptedException;

public class EventoUniversitario implements Serializable {
    private final String Id;
    private String titulo;
    private double costoBase;
    private boolean gratuito;
    private static int cantidadEventos;
    private Sala sala;
    private List<Actividad> actividadesEvento = new ArrayList<>();

    public String getId() {
        return Id;
    }

    public String getTitulo() {
        return titulo;
    }

    public double getCostoBase() {
        return costoBase;
    }

    public boolean isGratuito() {
        return gratuito;
    }

    public Sala getSala() {
        return sala;
    }

    public List<Actividad> getActividadesEvento() {
        return actividadesEvento;
    }

    public EventoUniversitario(String id, String titulo, double costoBase, boolean gratuito) {
        this.Id = id;
        this.titulo = titulo;
        this.costoBase = costoBase;
        this.gratuito = gratuito;
        cantidadEventos++;
    }
    public EventoUniversitario(EventoUniversitario otro) {
        this.Id = otro.Id + " - copia ";
        this.titulo = otro.titulo;
        this.costoBase = otro.costoBase;
        this.gratuito = otro.gratuito;
        cantidadEventos++;
    }
    public double calcularCostoEstimado(){
        if (gratuito) {
            return 0;
        }
        double costoActividades = 0;
        for (Actividad act : actividadesEvento) {
            costoActividades = costoActividades + act.calcularCostoMateriales();
        }
        return (costoBase + costoActividades) * 1.21;
    }
    public void asignarSala(Sala sala){ this.sala = sala; }
     public Actividad crearActividad(int id, String titulo, int cupo, String tipo) {
        Actividad nuevaActividad;

        if (tipo.equals("Charla")) {
            nuevaActividad = new Charla(id, titulo, cupo, "Sin disertante asignado");
        } else if (tipo.equals("Taller")) {
            nuevaActividad = new Taller(id, titulo, cupo, false);
        } else if (tipo.equals("Curso")) {
            nuevaActividad = new Curso(id, titulo, cupo, 1);
        } else {
            System.out.println("Tipo de actividad no reconocido: " + tipo);
            return null;
        }

        actividadesEvento.add(nuevaActividad);
        return nuevaActividad;
     }
    public void mostrarDatos(){
        System.out.println("========================================");
        System.out.println("Evento codigo=" + Id);
        System.out.println("TÍtulo=" + titulo);
        System.out.println("Costo=" + this.calcularCostoEstimado());
        System.out.println("Gratuito :" + gratuito);

        if (sala != null) {
            System.out.println("Sala asignada: " + sala.getNombre() + " (id=" + sala.getId() + ")");
        } else {
            System.out.println("Sala asignada: ninguna");
        }

        if (actividadesEvento.isEmpty()) {
            System.out.println("Actividades: sin actividades cargadas");
        } else {
            for (Actividad act : actividadesEvento) {
                act.mostrarIdentificacion();
                act.mostrarInscripciones();
            }
        }
    }

    private static String nombreArchivo(String id) {
        return "evento_" + id + ".dat";
    }

    public boolean persistirEvento() {
        try (FileOutputStream fos = new FileOutputStream(nombreArchivo(this.Id));
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(this);
            return true;
        } catch (FileNotFoundException e) {
            System.out.println("Error de archivo: no se pudo crear o abrir '" + nombreArchivo(this.Id) + "'");
        } catch (NotSerializableException e) {
            System.out.println("Error de serialización: la clase " + e.getMessage() + " no implementa Serializable");
        } catch (IOException e) {
            System.out.println("Error de entrada/salida al guardar el evento: " + e.getMessage());
        }
        return false;

    }

    public EventoUniversitario recuperarEvento(String id) {
        try (FileInputStream fis = new FileInputStream(nombreArchivo(id));
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            return (EventoUniversitario) ois.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("Error de archivo: no existe '" + nombreArchivo(id) + "'");
        } catch (InvalidClassException e) {
            System.out.println("Error de versión: las clases cambiaron desde que se guardó el evento");
        } catch (StreamCorruptedException e) {
            System.out.println("Error de formato: el archivo está dañado o no contiene un evento");
        } catch (ClassNotFoundException e) {
            System.out.println("Error de clase: no se encontró la clase del objeto guardado (" + e.getMessage() + ")");
        } catch (IOException e) {
            System.out.println("Error de entrada/salida al leer el evento: " + e.getMessage());
        }
        return null;
    }

    public static int getCantidadEventos(){
        return cantidadEventos;
    }

}
