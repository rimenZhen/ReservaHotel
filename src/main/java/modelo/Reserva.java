package modelo;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class Reserva extends Observable {
    private String id;
    private Cliente cliente;
    private Habitacion habitacion;
    private LocalDate fechaEntrada;
    private LocalDate fechaSalida;
    private List<Servicio> servicios;

    public Reserva(String id, Cliente cliente, Habitacion habitacion,
                   LocalDate fechaEntrada, LocalDate fechaSalida) {
        this.id = id;
        this.cliente = cliente;
        this.habitacion = habitacion;
        this.fechaEntrada = fechaEntrada;
        this.fechaSalida = fechaSalida;
        this.servicios = new ArrayList<>();
    }


    public void addServicio(Servicio servicio) {
        servicios.add(servicio);
        setChanged(); // Marca cambio
        super.notifyObservers("Se agregó un nuevo servicio a la reserva");
    }


    public double calcularCostoTotal() {
        long dias = ChronoUnit.DAYS.between(fechaEntrada, fechaSalida);
        double costoHabitacion = habitacion.getPrecioPorNoche() * dias;
        double costoServicios = servicios.stream()
                .mapToDouble(Servicio::getCosto)
                .sum();
        return costoHabitacion + costoServicios;
    }

    public String getId() { return id; }
    public Cliente getCliente() { return cliente; }
    public Habitacion getHabitacion() { return habitacion; }
    public LocalDate getFechaEntrada() { return fechaEntrada; }
    public LocalDate getFechaSalida() { return fechaSalida; }
    public List<Servicio> getServicios() { return new ArrayList<>(servicios); }
}