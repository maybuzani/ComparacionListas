import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class BenchmarkListas {
    private static final int N = 100_000;

    public static void main(String[] args) {
        List<Integer> arrayList = new ArrayList<>();
        List<Integer> linkedList = new LinkedList<>();

        llenar(arrayList);
        llenar(linkedList);

        medirAcceso("ArrayList", arrayList);
        medirAcceso("LinkedList", linkedList);

        medirInsercionInicio("ArrayList", new ArrayList<>());
        medirInsercionInicio("LinkedList", new LinkedList<>());

        medirInsercionFinal("ArrayList", new ArrayList<>());
        medirInsercionFinal("LinkedList", new LinkedList<>());
    }

    private static void llenar(List<Integer> lista) {
        for (int i = 0; i < N; i++) {
            lista.add(i);
        }
    }

    private static void medirAcceso(String nombre, List<Integer> lista) {
        long inicio = System.nanoTime();
        long suma = 0;

        for (Integer valor : lista) {
            suma += valor;
        }

        long fin = System.nanoTime();

        System.out.printf("%s: %.3f ms%n",
                nombre, (fin - inicio) / 1_000_000.0);
        System.out.println("Suma: " + suma);
    }

    private static void medirInsercionInicio(
            String nombre, List<Integer> lista) {

        long inicio = System.nanoTime();

        for (int i = 0; i < 50_000; i++) {
            lista.add(0, i);
        }

        long fin = System.nanoTime();

        System.out.printf("%s: %.3f ms%n",
                nombre, (fin - inicio) / 1_000_000.0);
    }

    private static void medirInsercionFinal(
            String nombre, List<Integer> lista) {

        long inicio = System.nanoTime();

        for (int i = 0; i < 100_000; i++) {
            lista.add(i);
        }

        long fin = System.nanoTime();

        System.out.printf("%s: %.3f ms%n",
                nombre, (fin - inicio) / 1_000_000.0);
    }
}