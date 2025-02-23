package interfaz;

import excepciones.ClienteDuplicadoException;
import modelo.*;
import patrones.ClienteObserver;
import sistema.SistemaReservas;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class InterfazConsola {
    private Scanner scanner;
    private SistemaReservas sistema;

    public InterfazConsola() {
        scanner = new Scanner(System.in);
        sistema = SistemaReservas.getInstance();
    }

    public void mostrarMenu() {
        while (true) {
            System.out.println("\n=== Sistema de Reservas de Hotel ===");
            System.out.println("1. Registrar Cliente");
            System.out.println("2. Crear Reserva");
            System.out.println("3. Agregar Servicio a Reserva");
            System.out.println("4. Ver Reservas");
            System.out.println("5. Salir");
            System.out.print("Seleccione una opción: ");

            int opcion = scanner.nextInt();
            scanner.nextLine(); // Consumir nueva línea

            switch (opcion) {
                case 1:
                    registrarCliente();
                    break;
                case 2:
                    crearReserva();
                    break;
                case 3:
                    agregarServicio();
                    break;
                case 4:
                    verReservas();
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Opción no válida");
            }
        }
    }

    private void registrarCliente() {
        System.out.println("\n=== Registro de Cliente ===");
        System.out.print("ID: ");
        String id = scanner.nextLine();
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Teléfono: ");
        String telefono = scanner.nextLine();

        Cliente cliente = new Cliente(id, nombre, email, telefono);
        try {
            sistema.agregarCliente(cliente);
            System.out.println("Cliente registrado exitosamente");
        } catch (ClienteDuplicadoException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void crearReserva() {
        System.out.println("\n=== Crear Nueva Reserva ===");

        // Solicitar ID del cliente
        System.out.print("ID del cliente: ");
        String idCliente = scanner.nextLine();

        // Mostrar habitaciones disponibles
        System.out.println("\nHabitaciones disponibles:");
        for (Habitacion habitacion : sistema.getHabitacionesDisponibles()) {
            System.out.printf("Número: %s, Tipo: %s, Precio por noche: %.2f€\n",
                    habitacion.getNumero(),
                    habitacion.getTipo(),
                    habitacion.getPrecioPorNoche());
        }

        // Solicitar número de habitación
        System.out.print("\nNúmero de habitación deseada: ");
        String numeroHabitacion = scanner.nextLine();

        // Solicitar fechas
        System.out.println("\nFecha de entrada (formato YYYY-MM-DD): ");
        LocalDate fechaEntrada = LocalDate.parse(scanner.nextLine());

        System.out.println("Fecha de salida (formato YYYY-MM-DD): ");
        LocalDate fechaSalida = LocalDate.parse(scanner.nextLine());

        try {
            Reserva reserva = sistema.crearReserva(idCliente, numeroHabitacion, fechaEntrada, fechaSalida);

            // Crear y agregar observer para notificaciones
            Cliente cliente = sistema.buscarCliente(idCliente);
            ClienteObserver observer = new ClienteObserver(cliente);
            reserva.addObserver(observer);

            System.out.println("\nReserva creada exitosamente");
            System.out.printf("ID de reserva: %s\n", reserva.getId());
            System.out.printf("Costo total: %.2f€\n", reserva.calcularCostoTotal());

        } catch (Exception e) {
            System.out.println("Error al crear la reserva: " + e.getMessage());
        }
    }

    private void agregarServicio() {
        System.out.println("\n=== Agregar Servicio a Reserva ===");

        // Solicitar ID de reserva
        System.out.print("ID de la reserva: ");
        String idReserva = scanner.nextLine();

        // Buscar la reserva
        Reserva reserva = sistema.buscarReserva(idReserva);
        if (reserva == null) {
            System.out.println("Reserva no encontrada");
            return;
        }

        // Mostrar servicios disponibles
        System.out.println("\nServicios disponibles:");
        System.out.println("1. Servicio de Limpieza (20€)");
        System.out.println("2. Desayuno (15€)");
        System.out.println("3. Parking (10€)");
        System.out.println("4. Servicio de Habitación (25€)");
        System.out.print("\nSeleccione un servicio: ");

        int opcionServicio = scanner.nextInt();
        scanner.nextLine(); // Consumir nueva línea

        // Crear y agregar el servicio seleccionado
        Servicio servicio = null;
        switch (opcionServicio) {
            case 1:
                servicio = new ServicioBasico("Servicio de Limpieza", 20.0);
                break;
            case 2:
                servicio = new ServicioDesayuno(new ServicioBasico("Servicio Base", 0.0));
                break;
            case 3:
                servicio = new ServicioBasico("Parking", 10.0);
                break;
            case 4:
                servicio = new ServicioBasico("Servicio de Habitación", 25.0);
                break;
            default:
                System.out.println("Opción no válida");
                return;
        }

        if (servicio != null) {
            reserva.addServicio(servicio);
            System.out.println("Servicio agregado exitosamente");
            System.out.printf("Nuevo costo total de la reserva: %.2f€\n",
                    reserva.calcularCostoTotal());
        }
    }

    private void verReservas() {
        System.out.println("\n=== Ver Reservas ===");
        System.out.println("1. Ver todas las reservas");
        System.out.println("2. Buscar reservas por cliente");
        System.out.print("Seleccione una opción: ");

        int opcion = scanner.nextInt();
        scanner.nextLine(); // Consumir nueva línea

        List<Reserva> reservasAMostrar = new ArrayList<>();

        switch (opcion) {
            case 1:
                reservasAMostrar = sistema.getTodasLasReservas();
                break;
            case 2:
                System.out.print("Ingrese el ID del cliente: ");
                String idCliente = scanner.nextLine();
                reservasAMostrar = sistema.getReservasPorCliente(idCliente);
                break;
            default:
                System.out.println("Opción no válida");
                return;
        }

        if (reservasAMostrar.isEmpty()) {
            System.out.println("No se encontraron reservas");
            return;
        }

        System.out.println("\nReservas encontradas:");
        for (Reserva reserva : reservasAMostrar) {
            System.out.println("\n----------------------------------------");
            System.out.printf("ID Reserva: %s\n", reserva.getId());
            System.out.printf("Cliente: %s\n", reserva.getCliente().getNombre());
            System.out.printf("Habitación: %s (%s)\n",
                    reserva.getHabitacion().getNumero(),
                    reserva.getHabitacion().getTipo());
            System.out.printf("Fecha entrada: %s\n", reserva.getFechaEntrada());
            System.out.printf("Fecha salida: %s\n", reserva.getFechaSalida());

            System.out.println("Servicios contratados:");
            for (Servicio servicio : reserva.getServicios()) {
                System.out.printf("- %s (%.2f€)\n",
                        servicio.getDescripcion(),
                        servicio.getCosto());
            }

            System.out.printf("Costo total: %.2f€\n", reserva.calcularCostoTotal());
        }
    }
}