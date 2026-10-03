import javax.swing.JOptionPane;

public class ReservaCine {

    public static int contarLibres(char[][] sala, char libre) {
        // 4. Recorrer la matriz con dos for anidados (filas fuera, asientos dentro) y contar los libres
        int asientosLibres = 0;
        for (int fila = 0; fila < sala.length; fila++) {
            for (int asiento = 0; asiento < sala[fila].length; asiento++) {
                if (sala[fila][asiento] == libre) {
                    asientosLibres++;
                }
            }
        }
        // 5. Retornar el total de libres
        return asientosLibres;
    }

    public static String dibujarSala(char[][] sala) {
        // 6. Montar el dibujo con StringBuilder: primero la cabecera con los números de asiento (asiento + 1 porque el usuario cuenta desde 1)
        StringBuilder dibujo = new StringBuilder("      PANTALLA\n    ");
        for (int asiento = 0; asiento < sala[0].length; asiento++) {
            dibujo.append(asiento + 1).append(' ');
        }
        dibujo.append('\n');

        // 7. Añadir cada fila con su número y sus asientos (dos for anidados, igual que al contar)
        for (int fila = 0; fila < sala.length; fila++) {
            dibujo.append("F").append(fila + 1).append("  ");
            for (int asiento = 0; asiento < sala[fila].length; asiento++) {
                dibujo.append(sala[fila][asiento]).append(' ');
            }
            dibujo.append('\n');
        }

        // 8. Retornar el dibujo convertido a String con toString
        return dibujo.toString();
    }

    public static void main(String[] args) {
        final char LIBRE = '·';
        final char OCUPADO = 'X';

        // 1. Crear la matriz sala de 5 filas x 8 asientos y rellenarla con LIBRE
        char[][] sala = new char[5][8];
        for (int fila = 0; fila < sala.length; fila++) {
            for (int asiento = 0; asiento < sala[fila].length; asiento++) {
                sala[fila][asiento] = LIBRE;
            }
        }

        // 2. Marcar algunos asientos ya vendidos (simula reservas anteriores)
        sala[2][3] = OCUPADO;
        sala[2][4] = OCUPADO;
        sala[4][0] = OCUPADO;

        // 3. Repetir mientras el usuario quiera seguir y queden asientos libres (llamando a contarLibres en la condición)
        boolean seguirReservando = true;
        while (seguirReservando && contarLibres(sala, LIBRE) > 0) {

            // 9. Pedir al usuario fila y asiento en una ventana, enseñando el dibujo de dibujarSala dentro de <html><pre> (así la ventana usa letra monoespaciada y las columnas cuadran)
            String entrada = JOptionPane.showInputDialog("<html><pre>" + dibujarSala(sala) + "</pre>"
                    + "Fila y asiento separados por espacio (ej: 3 6)<br>Cancelar para terminar</html>");

            // 10. Si pulsa Cancelar, poner seguirReservando a false (el while termina en la siguiente comprobación)
            if (entrada == null) {
                seguirReservando = false;
            } else {
                // 11. Separar los dos números con split y restar 1 (el usuario cuenta desde 1, el array desde 0)
                String[] partes = entrada.trim().split("\\s+");
                int fila = Integer.parseInt(partes[0]) - 1;
                int asiento = Integer.parseInt(partes[1]) - 1;

                // 12. Comprobar el rango ANTES de acceder a la matriz (si no, ArrayIndexOutOfBoundsException) y luego si está ocupado
                if (fila < 0 || fila >= sala.length || asiento < 0 || asiento >= sala[0].length) {
                    JOptionPane.showMessageDialog(null, "Ese asiento no existe", "Error", JOptionPane.ERROR_MESSAGE);
                } else if (sala[fila][asiento] == OCUPADO) {
                    JOptionPane.showMessageDialog(null, "Asiento ocupado, elige otro");
                } else {
                    sala[fila][asiento] = OCUPADO;
                    JOptionPane.showMessageDialog(null, "Reservado: fila " + (fila + 1) + ", asiento " + (asiento + 1));
                }
            }
        }

        // 13. Mostrar los asientos que quedan libres en una ventana de diálogo tipo mensaje
        JOptionPane.showMessageDialog(null, "Quedan " + contarLibres(sala, LIBRE) + " asientos libres");
    }
}
