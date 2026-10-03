import javax.swing.JOptionPane;

public class GeneradorEmail {

    public static String quitarTildes(String texto) {
        // 5. Cambiar cada vocal con tilde, la ü y la ñ por su letra simple con el método replace (un email no admite tildes)
        return texto.replace('á', 'a').replace('é', 'e').replace('í', 'i')
                .replace('ó', 'o').replace('ú', 'u').replace('ü', 'u').replace('ñ', 'n');
    }

    public static String generarEmail(String nombre, String apellidos) {
        final String DOMINIO = "@empresa.es";

        // 4. Quitar espacios de los extremos con trim, pasar a minúsculas con toLowerCase y llamar a quitarTildes; guardarlo en nombreLimpio y apellidosLimpios
        String nombreLimpio = quitarTildes(nombre.trim().toLowerCase());
        String apellidosLimpios = quitarTildes(apellidos.trim().toLowerCase());

        // 6. Separar los apellidos por uno o más espacios con split("\\s+") y guardarlos en el array arrayApellidos
        String[] arrayApellidos = apellidosLimpios.split("\\s+");

        // 7. Coger la inicial del nombre con método charAt(0) y guardarla en la variable inicialNombre
        char inicialNombre = nombreLimpio.charAt(0);

        // 8. Montar el usuario con la inicial + el primer apellido completo (arrayApellidos[0])
        String usuario = inicialNombre + arrayApellidos[0];

        // 9. Si hay segundo apellido (el array tiene más de 1 posición), añadir su inicial
        if (arrayApellidos.length > 1) {
            usuario += arrayApellidos[1].charAt(0);
        }

        // 10. Retornar el usuario unido al dominio
        return usuario + DOMINIO;
    }

    public static void main(String[] args) {
        // 1. Pedir al usuario el nombre en una ventana y guardarlo en la variable nombre
        String nombre = JOptionPane.showInputDialog("Nombre:");

        // 2. Pedir al usuario los apellidos en una ventana y guardarlos en la variable apellidos
        String apellidos = JOptionPane.showInputDialog("Apellidos:");

        // 3. Llamar a la función generarEmail y guardar lo que retorna en la variable emailCorporativo
        String emailCorporativo = generarEmail(nombre, apellidos);

        // 11. Mostrar el email generado en una ventana de diálogo tipo mensaje
        JOptionPane.showMessageDialog(null, "Email asignado: " + emailCorporativo);
    }
}
