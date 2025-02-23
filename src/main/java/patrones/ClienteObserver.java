package patrones;

import modelo.Cliente;

import java.util.Observable;
import java.util.Observer;

public class ClienteObserver implements Observer {
    private Cliente cliente;

    public ClienteObserver(Cliente cliente) {
        this.cliente = cliente;
    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof String) {
            String mensaje = (String) arg;
            System.out.printf("Notificación para el cliente %s: %s\n",
                    cliente.getNombre(), mensaje);
        }
    }
}