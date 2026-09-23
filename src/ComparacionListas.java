import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ComparacionListas {
    public static void main(String[] args) {
        probarLista(new ArrayList<>());
        System.out.println("--------------------");
        probarLista(new LinkedList<>());
    }

    public static void probarLista(List<String> lista) {
        lista.add("A");
        lista.add("B");
        lista.add("C");
        lista.add(0, "INICIO");
        lista.add("FINAL");

        System.out.println(lista);
        System.out.println("Elemento 2: " + lista.get(2));

        lista.remove(1);
        System.out.println(lista);
    }
}