package modelo;

public abstract class ServicioDecorator implements Servicio {
    protected Servicio servicioDecorado;

    public ServicioDecorator(Servicio servicio) {
        this.servicioDecorado = servicio;
    }

    @Override
    public String getDescripcion() {
        return servicioDecorado.getDescripcion();
    }

    @Override
    public double getCosto() {
        return servicioDecorado.getCosto();
    }
}
