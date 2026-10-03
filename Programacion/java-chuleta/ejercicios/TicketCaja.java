import javax.swing.JOptionPane;

public class TicketCaja {

    public static void main(String[] args) {
        final double IVA = 0.21;
        final double DESCUENTO_VOLUMEN = 0.10;
        final int UNIDADES_PARA_DESCUENTO = 10;

        // 1. Pedir al usuario el nombre del producto en una ventana y guardarlo en la variable nombreProducto
        String nombreProducto = JOptionPane.showInputDialog("Nombre del producto:");

        // 2. Pedir al usuario el precio unitario en una ventana y guardarlo en la variable precioUnitario (replace por si el usuario usa comas)
        String precioTexto = JOptionPane.showInputDialog("Precio unitario (€):");
        double precioUnitario = Double.parseDouble(precioTexto.replace(",", "."));

        // 3. Pedir al usuario la cantidad en una ventana y guardarla en la variable cantidad (int porque no se venden medias unidades)
        int cantidad = Integer.parseInt(JOptionPane.showInputDialog("Cantidad:"));

        // 4. Calcular el subtotal multiplicando precioUnitario por cantidad y guardarlo en la variable subtotal
        double subtotal = precioUnitario * cantidad;

        // 5. Calcular el descuento con operador ternario (solo hay descuento si se llega a UNIDADES_PARA_DESCUENTO)
        double descuento = (cantidad >= UNIDADES_PARA_DESCUENTO) ? subtotal * DESCUENTO_VOLUMEN : 0;

        // 6. Restar el descuento al subtotal y guardarlo en la variable baseImponible (el IVA se calcula sobre la base, no sobre el subtotal)
        double baseImponible = subtotal - descuento;

        // 7. Calcular el IVA y el total y guardarlos en las variables importeIva y total
        double importeIva = baseImponible * IVA;
        double total = baseImponible + importeIva;

        // 8. Montar el texto del ticket con String.format (%.2f = 2 decimales, %% = símbolo %) y mostrarlo en una ventana de diálogo tipo mensaje
        String ticket = String.format(
                "%s  x %d%n"
                + "Subtotal:   %.2f €%n"
                + "Descuento: -%.2f €%n"
                + "Base:       %.2f €%n"
                + "IVA 21%%:    %.2f €%n"
                + "TOTAL:      %.2f €",
                nombreProducto, cantidad, subtotal, descuento, baseImponible, importeIva, total);
        JOptionPane.showMessageDialog(null, ticket, "Ticket", JOptionPane.INFORMATION_MESSAGE);
    }
}
