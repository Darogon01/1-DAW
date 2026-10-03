import javax.swing.JOptionPane;
import java.util.ArrayList;

public class GestorTurnos {

    public static void main(String[] args) {
        // 1. Crear la lista colaClientes vacía (ArrayList porque la cola crece y encoge todo el rato)
        ArrayList<String> colaClientes = new ArrayList<>();

        // 2. Crear el contador numeroTicket a 0 y el indicador abierto a true (controla el bucle sin while(true))
        int numeroTicket = 0;
        boolean abierto = true;
        String[] botones = {"Nuevo turno", "Atender", "Mi posición", "Ver cola", "Cerrar"};

        // 3. Repetir mientras el puesto esté abierto
        while (abierto) {
            // 4. Mostrar los botones con showOptionDialog y guardar el índice del botón pulsado en la variable eleccion (-1 si se cierra con la X)
            int eleccion = JOptionPane.showOptionDialog(null,
                    "Clientes en espera: " + colaClientes.size(), "Turnos",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
                    null, botones, botones[0]);

            switch (eleccion) {
                case 0 -> {
                    // 5. Sumar 1 al contador, formatear el ticket con 3 cifras (%03d → A007) y añadirlo al final con add
                    numeroTicket++;
                    String ticket = String.format("A%03d", numeroTicket);
                    colaClientes.add(ticket);
                    JOptionPane.showMessageDialog(null, "Su turno: " + ticket);
                }
                case 1 -> {
                    // 6. Si la cola no está vacía, sacar el primero con remove(0) (devuelve el elemento quitado y los demás avanzan)
                    if (colaClientes.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "No hay nadie esperando");
                    } else {
                        String atendido = colaClientes.remove(0);
                        JOptionPane.showMessageDialog(null, "Pase " + atendido);
                    }
                }
                case 2 -> {
                    // 7. Pedir al usuario su ticket en una ventana y buscar su posición con indexOf (-1 si no está)
                    String ticketBuscado = JOptionPane.showInputDialog("Su ticket (ej: A003):");
                    int posicion = colaClientes.indexOf(ticketBuscado.trim().toUpperCase());
                    if (posicion == -1) {
                        JOptionPane.showMessageDialog(null, "Ese ticket no está en la cola");
                    } else {
                        JOptionPane.showMessageDialog(null, "Personas delante de usted: " + posicion);
                    }
                }
                case 3 -> {
                    // 8. Mostrar la cola completa (al concatenar un ArrayList se imprime como [A001, A002])
                    JOptionPane.showMessageDialog(null,
                            colaClientes.isEmpty() ? "Cola vacía" : "En cola: " + colaClientes);
                }
                default -> {
                    // 9. Botón Cerrar (4) o la X (-1): poner abierto a false para que el while termine
                    abierto = false;
                }
            }
        }
    }
}
