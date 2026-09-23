import java.util.ArrayList;
import java.util.List;

public class EjemploArrayList {
    public static void main(String[] args) {
        List<String> tareas = new ArrayList<>();

        tareas.add("Preparar presentación");
        tareas.add("Revisar código");
        tareas.add("Actualizar documentación");
        tareas.add("Ejecutar pruebas");
        tareas.add("Publicar versión");

        System.out.println(tareas);
        System.out.println("Primera: " + tareas.get(0));
        System.out.println("Tercera: " + tareas.get(2));

        tareas.set(1, "Revisar código Java");
        tareas.add(0, "Revisar correo");
        tareas.add(3, "Reunión de seguimiento");

        System.out.println(tareas);
    }
}