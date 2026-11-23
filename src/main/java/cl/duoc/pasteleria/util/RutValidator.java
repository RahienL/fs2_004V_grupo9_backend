package cl.duoc.pasteleria.util;

public class RutValidator {

    public static boolean validarRut(String rut) {
        if (rut == null || rut.isEmpty()) {
            return false;
        }

        rut = rut.replace(".", "").replace("-", "").toUpperCase().trim();

        if (!rut.matches("^[0-9]{7,8}[0-9K]$")) {
            return false;
        }

        String rutNumeros = rut.substring(0, rut.length() - 1);
        String dv = rut.substring(rut.length() - 1);

        int suma = 0;
        int multiplicador = 2;

        for (int i = rutNumeros.length() - 1; i >= 0; i--) {
            suma += Integer.parseInt(String.valueOf(rutNumeros.charAt(i))) * multiplicador;
            multiplicador++;
            if (multiplicador > 7) {
                multiplicador = 2;
            }
        }

        int dvCalculado = 11 - (suma % 11);
        String dvEsperado;

        if (dvCalculado == 11) {
            dvEsperado = "0";
        } else if (dvCalculado == 10) {
            dvEsperado = "K";
        } else {
            dvEsperado = String.valueOf(dvCalculado);
        }

        return dv.equals(dvEsperado);
    }

    public static String formatearRut(String rut) {
        if (rut == null || rut.isEmpty()) {
            return "";
        }

        rut = rut.replace(".", "").replace("-", "").toUpperCase().trim();

        if (rut.length() < 8) {
            return rut;
        }

        String rutNumeros = rut.substring(0, rut.length() - 1);
        String dv = rut.substring(rut.length() - 1);

        StringBuilder sb = new StringBuilder();
        int contador = 0;
        for (int i = rutNumeros.length() - 1; i >= 0; i--) {
            if (contador == 3) {
                sb.insert(0, ".");
                contador = 0;
            }
            sb.insert(0, rutNumeros.charAt(i));
            contador++;
        }

        sb.append("-").append(dv);
        return sb.toString();
    }
}
