package modelo;

public class Habitacion {
    private String numero;
    private TipoHabitacion tipo;
    private double precioPorNoche;
    private boolean disponible;

    public Habitacion(String numero, TipoHabitacion tipo, double precioPorNoche) {
        this.numero = numero;
        this.tipo = tipo;
        this.precioPorNoche = precioPorNoche;
        this.disponible = true;
    }

    // Getters y setters
    public String getNumero() { return numero; }
    public TipoHabitacion getTipo() { return tipo; }
    public double getPrecioPorNoche() { return precioPorNoche; }
    public boolean isDisponible() { return disponible; }
    public void setDisponible(boolean disponible) { this.disponible = disponible; }
}
