package excepciones;

public class ReservaNoEncontradaException extends Exception {
    public ReservaNoEncontradaException(String mensaje) {
        super(mensaje);
    }
}