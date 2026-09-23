import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class ColaTrabajos {
    public static void main(String[] args) {
        List<String> trabajos = new ArrayList<>();

        Scanner scanner = new Scanner(System.in);
        int opcion = 0;

        do {
            System.out.println("\nSistema de cola de trabajos");
            System.out.println("1. Agregar trabajo normal ");
            System.out.println("2. Agregar trabajo urgente ");
            System.out.println("3. Procesar siguiente trabajo ");
            System.out.println("4. Consultar siguiente trabajo ");
            System.out.println("5. Mostrar trabajos pendientes ");
            System.out.println("6. Mostrar numero de trabajos ");
            System.out.println("7. Salir ");
            System.out.print("Seleccione una opcion: ");

            try {
                opcion = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                opcion = 0;
            }
            switch (opcion) {
                case 1:
                    System.out.print("Ingrese el nombre del trabajo normal: ");
                    String trabajoNormal = scanner.nextLine();
                    trabajos.add(trabajoNormal);
                    System.out.println("Trabajo normal agregado.");
                    break;
                case 2:
                    System.out.print("Ingrese el nombre del trabajo urgente: ");
                    String trabajoUrgente = scanner.nextLine();
                    trabajos.add(0, trabajoUrgente);
                    System.out.println("Trabajo urgente agregado al inicio.");
                    break;
                case 3:
                    if (trabajos.isEmpty()) {
                        System.out.println("No hay trabajos pendientes.");
                    } else {
                        String procesado = trabajos.remove(0);
                        System.out.println("Procesando trabajo: " + procesado);
                    }
                    break;
                case 4:
                    if (trabajos.isEmpty()) {
                        System.out.println("No hay trabajos pendientes.");
                    } else {
                        String siguiente = trabajos.get(0);
                        System.out.println("Siguiente trabajo en cola: " + siguiente);
                    }
                    break;
                case 5:
                    if (trabajos.isEmpty()) {
                        System.out.println("La cola está vacía.");
                    } else {
                        System.out.println("Trabajos pendientes: ");
                        for (int i = 0; i < trabajos.size(); i++) {
                            System.out.printf("  [%d] %s%n", i + 1, trabajos.get(i));
                        }
                    }
                    break;
                case 6:
                    System.out.println("Numero de trabajos pendientes: " + trabajos.size());
                    break;
                case 7:
                    System.out.println("Saliendo del sistema");
                    break;
                default:
                    System.out.println("Opcion no valida, intente de nuevo.");
            }
        } while (opcion != 7);
        scanner.close();
    }
}