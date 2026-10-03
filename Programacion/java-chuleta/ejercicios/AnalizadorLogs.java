import javax.swing.JOptionPane;
import java.util.ArrayList;

public class AnalizadorLogs {

    public static String extraerNivel(String linea) {
        // 4. Buscar las posiciones de los corchetes con indexOf y guardarlas en inicio y fin
        int inicio = linea.indexOf('[');
        int fin = linea.indexOf(']');

        // 5. Si falta alguno (indexOf devuelve -1), retornar "DESCONOCIDO"
        if (inicio == -1 || fin == -1) {
            return "DESCONOCIDO";
        }

        // 6. Retornar el texto entre corchetes con substring (inicio + 1 para saltar el '[', fin no se incluye)
        return linea.substring(inicio + 1, fin);
    }

    public static String extraerMensaje(String linea) {
        // 9. Retornar lo que hay después de "] " con substring (+ 2 para saltar el corchete y el espacio)
        return linea.substring(linea.indexOf(']') + 2);
    }

    public static void main(String[] args) {
        // 1. Guardar las líneas del log en el array lineasLog (en un caso real vendrían de un archivo)
        String[] lineasLog = {
            "2026-10-03 09:00:01 [INFO] Servidor iniciado en el puerto 8080",
            "2026-10-03 09:02:15 [INFO] Usuario ana.garcia ha iniciado sesión",
            "2026-10-03 09:05:42 [WARN] Tiempo de respuesta alto en /api/productos (1850 ms)",
            "2026-10-03 09:07:03 [ERROR] Timeout al conectar con la base de datos",
            "2026-10-03 09:07:04 [INFO] Reintentando conexión...",
            "2026-10-03 09:07:09 [ERROR] NullPointerException en CarritoService.java:42",
            "2026-10-03 09:10:30 [WARN] Memoria al 85 %",
            "2026-10-03 09:15:00 [DEBUG] Caché vaciada"
        };

        // 2. Crear los contadores a 0 y la lista mensajesError (ArrayList porque no sé de antemano cuántos errores habrá)
        int contadorInfo = 0;
        int contadorWarn = 0;
        int contadorError = 0;
        int contadorOtros = 0;
        ArrayList<String> mensajesError = new ArrayList<>();

        // 3. Recorrer cada línea con for-each y llamar a extraerNivel; guardar lo que retorna en la variable nivel
        for (String linea : lineasLog) {
            String nivel = extraerNivel(linea);

            // 7. Contar según el nivel con switch sobre String (valores exactos y conocidos)
            switch (nivel) {
                case "INFO" -> contadorInfo++;
                case "WARN" -> contadorWarn++;
                case "ERROR" -> {
                    contadorError++;
                    // 8. Llamar a extraerMensaje y añadir el mensaje a la lista mensajesError con add
                    mensajesError.add(extraerMensaje(linea));
                }
                default -> contadorOtros++;
            }
        }

        // 10. Calcular el porcentaje de errores (100.0 con decimal para que la división no sea entera)
        double porcentajeError = contadorError * 100.0 / lineasLog.length;

        // 11. Montar el resumen y añadir cada mensaje de error recorriendo la lista con for-each
        String resumen = String.format(
                "Líneas: %d%nINFO: %d | WARN: %d | ERROR: %d | Otros: %d%nErrores: %.1f %%%n%nDetalle de errores:%n",
                lineasLog.length, contadorInfo, contadorWarn, contadorError, contadorOtros, porcentajeError);
        for (String mensaje : mensajesError) {
            resumen += "  ✖ " + mensaje + "\n";
        }

        // 12. Mostrar el resumen en una ventana de diálogo tipo mensaje
        JOptionPane.showMessageDialog(null, resumen, "Análisis de log", JOptionPane.INFORMATION_MESSAGE);
    }
}
