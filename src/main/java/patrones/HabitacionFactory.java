package patrones;

import modelo.Habitacion;
import modelo.TipoHabitacion;

public class HabitacionFactory {
    public static Habitacion crearHabitacion(String numero, TipoHabitacion tipo) {
        switch (tipo) {
            case TipoHabitacion.INDIVIDUAL:
                return new Habitacion(numero, tipo, 100.0);
            case TipoHabitacion.DOBLE:
                return new Habitacion(numero, tipo, 150.0);
            case TipoHabitacion.SUITE:
                return new Habitacion(numero, tipo, 300.0);
            default:
                throw new IllegalArgumentException("Tipo de habitación no válido");
        }
    }
}