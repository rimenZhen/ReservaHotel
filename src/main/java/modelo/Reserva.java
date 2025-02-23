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
    private List<Observer> observers;

    public Reserva(String id, Cliente cliente, Habitacion habitacion,
                   LocalDate fechaEntrada, LocalDate fechaSalida) {
        this.id = id;
        this.cliente = cliente;
        this.habitacion = habitacion;
        this.fechaEntrada = fechaEntrada;
        this.fechaSalida = fechaSalida;
        this.servicios = new ArrayList<>();
        this.observers = new ArrayList<>();
    }

    public void addServicio(Servicio servicio) {
        servicios.add(servicio);
        notifyObservers("Se agregó un nuevo servicio a la reserva");
    }

    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    private void notifyObservers(String mensaje) {
        setChanged(); // Marca que ha habido un cambio
        notifyObservers(mensaje); // Notifica a los observadores
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