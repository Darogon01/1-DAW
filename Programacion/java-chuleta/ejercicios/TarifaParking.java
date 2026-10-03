import javax.swing.JOptionPane;

public class TarifaParking {

    public static double calcularImporte(int minutosEstancia) {
        final int MINUTOS_GRATIS = 15;
        final double PRECIO_HORA = 2.50;
        final double TOPE_DIARIO = 20.00;

        // 4. Si la estancia no supera los minutos gratis, retornar 0 (return sale de la función aquí mismo)
        if (minutosEstancia <= MINUTOS_GRATIS) {
            return 0;
        }

        // 5. Calcular las horas completas con división entera y guardarlas en la variable horasCobradas (135 / 60 = 2)
        int horasCobradas = minutosEstancia / 60;

        // 6. Si sobran minutos (resto distinto de 0), cobrar la fracción como una hora más con incremento horasCobradas++
        if (minutosEstancia % 60 != 0) {
            horasCobradas++;
        }

        // 7. Calcular el importe multiplicando horasCobradas por PRECIO_HORA
        double importe = horasCobradas * PRECIO_HORA;

        // 8. Si el importe supera el tope diario, cobrar solo el tope
        if (importe > TOPE_DIARIO) {
            importe = TOPE_DIARIO;
        }

        // 9. Retornar el importe
        return importe;
    }

    public static void main(String[] args) {
        // 1. Pedir al usuario los minutos de estancia en una ventana y guardarlos en la variable minutosEstancia
        int minutosEstancia = Integer.parseInt(JOptionPane.showInputDialog("Minutos de estancia:"));

        // 2. Separar horas y minutos con división entera y módulo (para luego mostrarlo en la ventana mensaje)
        int horas = minutosEstancia / 60;
        int minutosSueltos = minutosEstancia % 60;

        // 3. Llamar a la función calcularImporte y guardar lo que retorna en la variable importe
        double importe = calcularImporte(minutosEstancia);

        // 10. Mostrar la estancia y el importe en una ventana de diálogo tipo mensaje
        JOptionPane.showMessageDialog(null,
                String.format("Estancia: %d h %d min%nImporte: %.2f €", horas, minutosSueltos, importe));
    }
}
