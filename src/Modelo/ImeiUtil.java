package Modelo;


import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class ImeiUtil {
    /**
     * Genera N IMEIs únicos y válidos (15 dígitos con Luhn).
     */
    public static List<String> generarImeis(int cantidad) {
        Set<String> set = new LinkedHashSet<>();
        Random rnd = new Random();
        while (set.size() < cantidad) {
            int[] imei = new int[15];
            for (int i = 0; i < 14; i++) imei[i] = rnd.nextInt(10);
            int sum = 0;
            for (int i = 0; i < 14; i++) {
                int d = imei[i];
                if (i % 2 == 1) {
                    d *= 2;
                    if (d > 9) d -= 9;
                }
                sum += d;
            }
            imei[14] = (10 - (sum % 10)) % 10;
            StringBuilder sb = new StringBuilder();
            for (int d : imei) sb.append(d);
            set.add(sb.toString());
        }
        return new ArrayList<>(set);
    }
}