import javax.swing.JOptionPane;

public class ValidadorPassword {

    public static String validarPassword(String password) {
        // 4. Crear la variable errores vacía para ir añadiendo cada regla que no se cumple (para luego mostrarlo en la ventana mensaje)
        String errores = "";

        // 5. Comprobar la longitud mínima con método length
        if (password.length() < 8) {
            errores += "- Mínimo 8 caracteres\n";
        }

        // 6. Crear las variables indicador (flags) a false (aún no se ha encontrado nada)
        boolean tieneMayuscula = false;
        boolean tieneMinuscula = false;
        boolean tieneDigito = false;
        boolean tieneEspacio = false;

        // 7. Recorrer la contraseña carácter a carácter con un for y el método charAt, y poner a true el indicador que toque
        for (int i = 0; i < password.length(); i++) {
            char caracter = password.charAt(i);
            if (Character.isUpperCase(caracter)) {
                tieneMayuscula = true;
            } else if (Character.isLowerCase(caracter)) {
                tieneMinuscula = true;
            } else if (Character.isDigit(caracter)) {
                tieneDigito = true;
            } else if (Character.isWhitespace(caracter)) {
                tieneEspacio = true;
            }
        }

        // 8. Añadir a errores cada regla que no se haya cumplido (! significa "no")
        if (!tieneMayuscula) {
            errores += "- Al menos una mayúscula\n";
        }
        if (!tieneMinuscula) {
            errores += "- Al menos una minúscula\n";
        }
        if (!tieneDigito) {
            errores += "- Al menos un número\n";
        }
        if (tieneEspacio) {
            errores += "- Sin espacios\n";
        }

        // 9. Retornar errores (si está vacío, la contraseña es válida)
        return errores;
    }

    public static void main(String[] args) {
        String password;
        String errores;

        // 1. Repetir la petición de la contraseña mientras tenga errores (do-while porque hay que pedirla al menos una vez)
        do {
            // 2. Pedir al usuario la contraseña en una ventana y guardarla en la variable password
            password = JOptionPane.showInputDialog("Crea tu contraseña:");

            // 3. Llamar a la función validarPassword y guardar lo que retorna en la variable errores
            errores = validarPassword(password);

            // 10. Si hay errores, mostrarlos en una ventana de diálogo tipo mensaje de error
            if (!errores.isEmpty()) {
                JOptionPane.showMessageDialog(null, "La contraseña no cumple:\n" + errores,
                        "Contraseña débil", JOptionPane.ERROR_MESSAGE);
            }
        } while (!errores.isEmpty());

        // 11. Mostrar la confirmación en una ventana de diálogo tipo mensaje
        JOptionPane.showMessageDialog(null, "Contraseña aceptada");
    }
}
