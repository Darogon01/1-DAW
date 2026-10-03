import javax.swing.JOptionPane;

public class CajeroAutomatico {

    public static void main(String[] args) {
        // 1. Guardar el saldo inicial en la variable saldo (double porque hay céntimos)
        double saldo = 1500.00;

        // 2. Declarar la variable opcion fuera del bucle (la condición del while la necesita y dentro del do no la vería)
        int opcion;

        // 3. Repetir el menú hasta que el usuario elija salir (do-while porque el menú se muestra al menos una vez)
        do {
            // 4. Pedir al usuario la opción del menú en una ventana y guardarla en la variable opcionTexto
            String opcionTexto = JOptionPane.showInputDialog(
                    "=== CAJERO ===\n1. Consultar saldo\n2. Ingresar\n3. Retirar\n0. Salir");

            // 5. Si pulsa Cancelar (showInputDialog devuelve null), tratarlo como salir; si no, convertirlo a int
            if (opcionTexto == null) {
                opcion = 0;
            } else {
                opcion = Integer.parseInt(opcionTexto.trim());
            }

            // 6. Ejecutar la operación elegida con switch (valor exacto de una lista cerrada de opciones)
            switch (opcion) {
                case 1 -> {
                    // 7. Mostrar el saldo en una ventana de diálogo tipo mensaje
                    JOptionPane.showMessageDialog(null, String.format("Saldo: %.2f €", saldo));
                }
                case 2 -> {
                    // 8. Pedir al usuario la cantidad a ingresar en una ventana; si es positiva, sumarla al saldo con +=
                    double ingreso = Double.parseDouble(
                            JOptionPane.showInputDialog("Cantidad a ingresar:").replace(",", "."));
                    if (ingreso > 0) {
                        saldo += ingreso;
                        JOptionPane.showMessageDialog(null, String.format("Ingresado. Saldo: %.2f €", saldo));
                    } else {
                        JOptionPane.showMessageDialog(null, "La cantidad debe ser positiva");
                    }
                }
                case 3 -> {
                    // 9. Pedir al usuario la cantidad a retirar en una ventana y comprobar las reglas en orden (else if: se para en la primera que falle)
                    double retirada = Double.parseDouble(
                            JOptionPane.showInputDialog("Cantidad a retirar (múltiplos de 10):").replace(",", "."));
                    if (retirada <= 0) {
                        JOptionPane.showMessageDialog(null, "La cantidad debe ser positiva");
                    } else if (retirada > saldo) {
                        JOptionPane.showMessageDialog(null, "Saldo insuficiente");
                    } else if (retirada % 10 != 0) {
                        JOptionPane.showMessageDialog(null, "Solo billetes: múltiplos de 10 €");
                    } else {
                        saldo -= retirada;
                        JOptionPane.showMessageDialog(null, String.format("Retire su dinero. Saldo: %.2f €", saldo));
                    }
                }
                case 0 -> {
                    // 10. Despedirse en una ventana de diálogo tipo mensaje (el while ve opcion 0 y termina)
                    JOptionPane.showMessageDialog(null, "Gracias. Retire su tarjeta.");
                }
                default -> {
                    // 11. Cualquier otro número: avisar en una ventana de diálogo tipo mensaje de aviso
                    JOptionPane.showMessageDialog(null, "Opción no válida", "Error", JOptionPane.WARNING_MESSAGE);
                }
            }
        } while (opcion != 0);
    }
}
