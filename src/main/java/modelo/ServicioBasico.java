package modelo;

public class ServicioBasico implements Servicio {
    private String descripcion;
    private double costo;

    public ServicioBasico(String descripcion, double costo) {
        this.descripcion = descripcion;
        this.costo = costo;
    }

    @Override
    public String getDescripcion() { return descripcion; }

    @Override
    public double getCosto() { return costo; }
}