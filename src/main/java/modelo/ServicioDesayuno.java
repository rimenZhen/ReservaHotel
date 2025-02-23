package modelo;

public class ServicioDesayuno extends ServicioDecorator {
    public ServicioDesayuno(Servicio servicio) {
        super(servicio);
    }

    @Override
    public String getDescripcion() {
        return servicioDecorado.getDescripcion() + " + Desayuno";
    }

    @Override
    public double getCosto() {
        return servicioDecorado.getCosto() + 15.0;
    }
}