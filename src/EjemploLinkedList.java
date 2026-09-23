import java.util.LinkedList;
import java.util.List;

public class EjemploLinkedList {
    public static void main(String[] args) {
        List<String> tareas = new LinkedList<>();

        tareas.add("Preparar presentación");
        tareas.add("Revisar código");
        tareas.add("Actualizar documentación");
        tareas.add("Ejecutar pruebas");
        tareas.add("Publicar versión");

        System.out.println(tareas);
    }
    private static void medirEliminacionInicio(String nombre, List<Integer> lista) {
        long inicio = System.nanoTime();

        while (!lista.isEmpty()) {
            lista.remove(0);
        }

        long fin = System.nanoTime();

        System.out.printf("%s: %.3f ms%n",
                nombre, (fin - inicio) / 1_000_000.0);
    }
}