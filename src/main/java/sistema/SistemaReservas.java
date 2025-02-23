package sistema;

import excepciones.ClienteDuplicadoException;
import excepciones.ClienteNoEncontradoException;
import excepciones.HabitacionNoDisponibleException;
import excepciones.ReservaNoEncontradaException;
import modelo.Cliente;
import modelo.Habitacion;
import modelo.Reserva;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class SistemaReservas {
    private static SistemaReservas instance;
    private List<Habitacion> habitaciones;
    private List<Reserva> reservas;
    private List<Cliente> clientes;

    private SistemaReservas() {
        habitaciones = new ArrayList<>();
        reservas = new ArrayList<>();
        clientes = new ArrayList<>();
    }

    public static SistemaReservas getInstance() {
        if (instance == null) {
            instance = new SistemaReservas();
        }
        return instance;
    }

    public void agregarHabitacion(Habitacion habitacion) {
        habitaciones.add(habitacion);
    }

    public void agregarCliente(Cliente cliente) throws ClienteDuplicadoException {
        if (clientes.stream().anyMatch(c -> c.getId().equals(cliente.getId()))) {
            throw new ClienteDuplicadoException("Cliente ya existe con ID: " + cliente.getId());
        }
        clientes.add(cliente);
    }

    public Reserva crearReserva(String idCliente, String numeroHabitacion,
                                LocalDate fechaEntrada, LocalDate fechaSalida)
            throws HabitacionNoDisponibleException, ClienteNoEncontradoException {
        Cliente cliente = clientes.stream()
                .filter(c -> c.getId().equals(idCliente))
                .findFirst()
                .orElseThrow(() -> new ClienteNoEncontradoException("Cliente no encontrado"));

        Habitacion habitacion = habitaciones.stream()
                .filter(h -> h.getNumero().equals(numeroHabitacion) && h.isDisponible())
                .findFirst()
                .orElseThrow(() -> new HabitacionNoDisponibleException("Habitación no disponible"));

        String idReserva = UUID.randomUUID().toString();
        Reserva reserva = new Reserva(idReserva, cliente, habitacion, fechaEntrada, fechaSalida);
        habitacion.setDisponible(false);
        reservas.add(reserva);
        return reserva;
    }

    public List<Habitacion> getHabitacionesDisponibles() {
        return habitaciones.stream()
                .filter(Habitacion::isDisponible)
                .collect(Collectors.toList());
    }

    public Cliente buscarCliente(String idCliente) {
        return clientes.stream()
                .filter(c -> c.getId().equals(idCliente))
                .findFirst()
                .orElse(null);
    }

    public Reserva buscarReserva(String idReserva) {
        return reservas.stream()
                .filter(r -> r.getId().equals(idReserva))
                .findFirst()
                .orElse(null);
    }

    public List<Reserva> getTodasLasReservas() {
        return new ArrayList<>(reservas);
    }

    public List<Reserva> getReservasPorCliente(String idCliente) {
        return reservas.stream()
                .filter(r -> r.getCliente().getId().equals(idCliente))
                .collect(Collectors.toList());
    }

    // Método para cancelar una reserva
    public void cancelarReserva(String idReserva) throws ReservaNoEncontradaException {
        Reserva reserva = buscarReserva(idReserva);
        if (reserva == null) {
            throw new ReservaNoEncontradaException("Reserva no encontrada con ID: " + idReserva);
        }

        reserva.getHabitacion().setDisponible(true);
        reservas.remove(reserva);
    }

    // Método para verificar disponibilidad de habitación en fechas específicas
    public boolean verificarDisponibilidad(String numeroHabitacion,
                                           LocalDate fechaEntrada,
                                           LocalDate fechaSalida) {
        return reservas.stream()
                .filter(r -> r.getHabitacion().getNumero().equals(numeroHabitacion))
                .noneMatch(r -> fechaEntrada.isBefore(r.getFechaSalida()) &&
                        fechaSalida.isAfter(r.getFechaEntrada()));
    }

    // Método para obtener estadísticas básicas
    public Map<String, Object> obtenerEstadisticas() {
        Map<String, Object> estadisticas = new HashMap<>();

        estadisticas.put("totalReservas", reservas.size());
        estadisticas.put("totalClientes", clientes.size());
        estadisticas.put("habitacionesDisponibles", getHabitacionesDisponibles().size());

        double ingresoTotal = reservas.stream()
                .mapToDouble(Reserva::calcularCostoTotal)
                .sum();
        estadisticas.put("ingresoTotal", ingresoTotal);

        return estadisticas;
    }
}

