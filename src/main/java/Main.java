import interfaz.InterfazConsola;
import modelo.TipoHabitacion;
import patrones.HabitacionFactory;
import sistema.SistemaReservas;

public class Main {
    public static void main(String[] args) {
        // Inicializar el sistema con algunas habitaciones
        SistemaReservas sistema = SistemaReservas.getInstance();

        // Crear algunas habitaciones usando el Factory
        sistema.agregarHabitacion(HabitacionFactory.crearHabitacion("101", TipoHabitacion.INDIVIDUAL));
        sistema.agregarHabitacion(HabitacionFactory.crearHabitacion("102", TipoHabitacion.DOBLE));
        sistema.agregarHabitacion(HabitacionFactory.crearHabitacion("103", TipoHabitacion.SUITE));

        // Iniciar la interfaz de usuario
        InterfazConsola interfaz = new InterfazConsola();
        interfaz.mostrarMenu();
    }
}