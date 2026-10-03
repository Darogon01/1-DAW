import javax.swing.JOptionPane;

public class ValidadorDni {

    public static char calcularLetraDni(int numeroDni) {
        // 11. Guardar en la constante LETRAS las 23 letras en el orden oficial (la posición de cada letra es su resto)
        final String LETRAS = "TRWAGMYFPDXBNJZSQVHLCKE";

        // 12. Calcular el resto de dividir entre 23 y guardarlo en la variable posicion (siempre da 0..22, una posición válida)
        int posicion = numeroDni % 23;

        // 13. Retornar la letra de esa posición con método charAt
        return LETRAS.charAt(posicion);
    }

    public static boolean tieneFormatoDni(String dni) {
        // 4. Si no mide 9 caracteres, retornar false
        if (dni.length() != 9) {
            return false;
        }

        // 5. Recorrer los 8 primeros caracteres y retornar false en cuanto uno no sea dígito (Character.isDigit)
        for (int i = 0; i < 8; i++) {
            if (!Character.isDigit(dni.charAt(i))) {
                return false;
            }
        }

        // 6. Retornar si el último carácter es una letra (Character.isLetter ya devuelve true o false)
        return Character.isLetter(dni.charAt(8));
    }

    public static void main(String[] args) {
        // 1. Pedir al usuario el DNI en una ventana y guardarlo en la variable dniTexto
        String dniTexto = JOptionPane.showInputDialog("DNI (8 números + letra):");

        // 2. Quitar espacios y guiones y pasar a mayúsculas (por si el usuario escribe " 12345678-z")
        String dniLimpio = dniTexto.trim().replace("-", "").replace(" ", "").toUpperCase();

        // 3. Llamar a la función tieneFormatoDni para comprobar el formato antes de calcular nada
        if (!tieneFormatoDni(dniLimpio)) {
            // 7. Si el formato no es correcto, mostrarlo en una ventana de diálogo tipo mensaje de error
            JOptionPane.showMessageDialog(null, "Formato incorrecto: deben ser 8 números y 1 letra",
                    "DNI", JOptionPane.ERROR_MESSAGE);
        } else {
            // 8. Separar la parte numérica con substring(0, 8) y convertirla a int con parseInt; guardarla en numeroDni
            int numeroDni = Integer.parseInt(dniLimpio.substring(0, 8));

            // 9. Guardar la letra escrita por el usuario en la variable letraEscrita con charAt(8)
            char letraEscrita = dniLimpio.charAt(8);

            // 10. Llamar a la función calcularLetraDni y guardar lo que retorna en la variable letraCorrecta
            char letraCorrecta = calcularLetraDni(numeroDni);

            // 14. Comparar las dos letras con == (los char se comparan con ==, los String con equals) y mostrar el resultado en una ventana de diálogo tipo mensaje
            if (letraEscrita == letraCorrecta) {
                JOptionPane.showMessageDialog(null, "DNI válido: " + dniLimpio);
            } else {
                JOptionPane.showMessageDialog(null, "Letra incorrecta. Para " + numeroDni
                        + " la letra es " + letraCorrecta, "DNI", JOptionPane.WARNING_MESSAGE);
            }
        }
    }
}
