package patrones;

import modelo.Habitacion;
import modelo.TipoHabitacion;

import java.util.EnumMap;
import java.util.Map;

public class HabitacionFactory {

    private static final Map<TipoHabitacion, Double> PRECIOS_POR_TIPO = new EnumMap<>(TipoHabitacion.class);

    static {
        // Configuración de precios iniciales
        PRECIOS_POR_TIPO.put(TipoHabitacion.INDIVIDUAL, 100.0);
        PRECIOS_POR_TIPO.put(TipoHabitacion.DOBLE, 150.0);
        PRECIOS_POR_TIPO.put(TipoHabitacion.SUITE, 300.0);
    }


    public static Habitacion crearHabitacion(String numero, TipoHabitacion tipo) {
        Double precio = PRECIOS_POR_TIPO.get(tipo);

        if (precio == null) {
            throw new IllegalArgumentException("Tipo de habitación no válido: " + tipo);
        }

        return new Habitacion(numero, tipo, precio);
    }

}