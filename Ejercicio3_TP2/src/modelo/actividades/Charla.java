package modelo.actividades;

public class Charla extends Actividad {
    private String disertante;

    public Charla(int id, String titulo, int cupoMaximo, String disertante) {
        super(id, titulo, cupoMaximo);
        this.disertante = disertante;
    }

    @Override
    public double calcularCostoMateriales() {
        return 0; // las charlas son siempre gratuitas
    }

    @Override
    public String getTipo() {
        return "Charla";
    }
}
