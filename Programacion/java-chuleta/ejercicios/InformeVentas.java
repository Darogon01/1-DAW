import javax.swing.JOptionPane;

public class InformeVentas {

    public static double calcularTotal(double[] importes) {
        // 4. Crear el acumulador sumaImportes a 0 (sumar empieza desde 0)
        double sumaImportes = 0;

        // 5. Recorrer el array con for-each (solo hay que leer, no hace falta el índice) y sumar cada importe con +=
        for (double importe : importes) {
            sumaImportes += importe;
        }

        // 6. Retornar la suma
        return sumaImportes;
    }

    public static int buscarPosicionMaximo(double[] importes) {
        // 9. Suponer que el mayor está en la posición 0 y guardarlo en la variable posicionMaximo
        int posicionMaximo = 0;

        // 10. Recorrer desde la posición 1 con for clásico (aquí sí hace falta el índice) y quedarse con la posición si el valor es mayor
        for (int i = 1; i < importes.length; i++) {
            if (importes[i] > importes[posicionMaximo]) {
                posicionMaximo = i;
            }
        }

        // 11. Retornar la posición (no el valor: con la posición se saca el valor Y el nombre del día)
        return posicionMaximo;
    }

    public static void main(String[] args) {
        // 1. Guardar los nombres de los días en el array dias
        String[] dias = {"Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado", "Domingo"};

        // 2. Guardar las ventas de cada día en el array ventasDiarias (arrays paralelos: la misma posición es el mismo día)
        double[] ventasDiarias = {1250.50, 980.00, 1430.75, 1100.00, 2150.30, 3200.00, 1875.45};

        // 3. Llamar a la función calcularTotal y guardar lo que retorna en la variable totalSemana
        double totalSemana = calcularTotal(ventasDiarias);

        // 7. Calcular la media dividiendo entre la propiedad length del array (sin paréntesis: es propiedad, no método)
        double mediaDiaria = totalSemana / ventasDiarias.length;

        // 8. Llamar a la función buscarPosicionMaximo y guardar lo que retorna en la variable posicionMejorDia
        int posicionMejorDia = buscarPosicionMaximo(ventasDiarias);

        // 12. Crear un StringBuilder informe para ir montando el texto (más eficiente que += dentro de un bucle)
        StringBuilder informe = new StringBuilder();
        informe.append(String.format("Total semana: %.2f €%n", totalSemana));
        informe.append(String.format("Media diaria: %.2f €%n", mediaDiaria));
        informe.append(String.format("Mejor día: %s (%.2f €)%n%n", dias[posicionMejorDia], ventasDiarias[posicionMejorDia]));
        informe.append("Días por encima de la media:\n");

        // 13. Recorrer con for clásico (hace falta i para leer los dos arrays a la vez) y añadir los días que superan la media
        int diasPorEncima = 0;
        for (int i = 0; i < ventasDiarias.length; i++) {
            if (ventasDiarias[i] > mediaDiaria) {
                informe.append(String.format("  - %s: %.2f €%n", dias[i], ventasDiarias[i]));
                diasPorEncima++;
            }
        }
        informe.append("Total: ").append(diasPorEncima).append(" días");

        // 14. Mostrar el informe en una ventana de diálogo tipo mensaje (toString convierte el StringBuilder en String)
        JOptionPane.showMessageDialog(null, informe.toString(), "Informe semanal", JOptionPane.INFORMATION_MESSAGE);
    }
}
