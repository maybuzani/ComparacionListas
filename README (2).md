# Práctica: ArrayList vs. LinkedList en Java Collections Framework



## 1. Propósito

`ArrayList` y `LinkedList` implementan la interfaz `List`, pero utilizan
estructuras internas diferentes. La práctica busca que compruebes experimentalmente cómo estas diferencias afectan el acceso,
inserción, eliminación y recorrido.

Al finalizar, serás capaz de utilizar `List`, `ArrayList` y
`LinkedList`; explicar sus diferencias estructurales; relacionar
operaciones con complejidad temporal; medir empíricamente su desempeño;
y justificar la elección de una implementación.

## 2. Situación problema

Una aplicación mantiene una lista de tareas pendientes y realiza
operaciones de inserción al inicio y al final, acceso por posición,
eliminación y recorrido. Se investigará cuándo resulta más apropiado
utilizar `ArrayList` o `LinkedList`.

## 3. Preparación en IntelliJ IDEA

Crear el proyecto y su respectibo repositorio:

``` text
ComparacionListas/
└── src/
    ├── EjemploArrayList.java
    ├── EjemploLinkedList.java
    ├── ComparacionListas.java
    ├── BenchmarkListas.java
    ├── EjemploDeque.java
    └── ColaTrabajos.java
```

## 4. Experimento con ArrayList

``` java
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
```

`ArrayList` utiliza conceptualmente un arreglo dinámico:

``` text
Índice       0       1       2       3       4
             ▼       ▼       ▼       ▼       ▼
          ┌───────┬───────┬───────┬───────┬───────┐
ArrayList │   A   │   B   │   C   │   D   │   E   │
          └───────┴───────┴───────┴───────┴───────┘
```

**Pregunta:** ¿Qué ocurre internamente al insertar en la posición 0?
¿Por qué el acceso por índice puede realizarse eficientemente? R.

## 5. Experimento con LinkedList

``` java
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
}
```

`LinkedList` es una lista doblemente enlazada:

``` text
┌─────┐     ┌─────┐     ┌─────┐     ┌─────┐
│  A  │ ⇄   │  B  │ ⇄   │  C  │ ⇄   │  D  │
└─────┘     └─────┘     └─────┘     └─────┘
```

## 6. Comparación funcional

``` java
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
```

Analice por qué `probarLista(List<String> lista)` puede trabajar con
ambas implementaciones.

## 7. Complejidad temporal

|Operación|ArrayList|LinkedList|
|-----------|---------|----------|
|`get(i)`|O(1)|O(n)|
|`set(i,x)`|                        O(1) |        O(n)|
| `add(x)` al final|      O(1) amortizado  |       O(1)|
| `add(0,x)` |                       O(n) |        O(1)|
| `remove(0)`|                      O(n) |        O(1)|
| búsqueda por valor|               O(n) |        O(n)|
| recorrido completo |               O(n) |        O(n)

La complejidad asintótica no implica que una implementación sea siempre
más rápida. En `LinkedList`, una inserción en una posición intermedia
requiere primero localizar el nodo, lo que puede costar O(n).

## 8. Benchmark de acceso

``` java
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
    }

    private static void llenar(List<Integer> lista) {
        for (int i = 0; i < N; i++) {
            lista.add(i);
        }
    }

    private static void medirAcceso(String nombre, List<Integer> lista) {
        long inicio = System.nanoTime();
        long suma = 0;

        for (int i = 0; i < lista.size(); i++) {
            suma += lista.get(i);
        }

        long fin = System.nanoTime();

        System.out.printf("%s: %.3f ms%n",
                nombre, (fin - inicio) / 1_000_000.0);
        System.out.println("Suma: " + suma);
    }
}
```

Ejecute al menos tres veces y registre:

  |Ejecución | ArrayList | LinkedList |
  |-----------|-----------|------------|
  |1   | 48.838    | 4900.865   |     
  |2   | 17.152    | 4938.992   |                      
 | 3   | 14.867    | 4946.817   |                     
 | Promedio| 27.285    | 4928.8913  |              

Después sustituya el recorrido mediante `get(i)` por:

``` java
for (Integer valor : lista) {
    suma += valor;
}
```

Compare nuevamente y registre resultados
|Ejecucion  | ArrayList | LinkedList |

|-----------|-----------|------------|

|1          | 13.340    | 2.750   |     
|2          | 13.096    | 3.128   |                      
|3          | 15.717    | 2.832   |                     
| Promedio  | 14.051    | 2.903   |

## 9. Inserciones al inicio

``` java
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
```

Ejecute con:

``` java
medirInsercionInicio("ArrayList", new ArrayList<>());
medirInsercionInicio("LinkedList", new LinkedList<>());
```

Formule una hipótesis antes de ejecutar y compare con los resultados.

## 10. Inserciones al final

``` java
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
```

Registre los resultados:

 | Operación   | ArrayList | LinkedList |
 | ------------|-----------|------------|
 | Insertar al inicio | 281.140   | 5.049      |
 | Insertar al final | 8.181     | 9.551      |

## 11. Eliminaciones

Prepare dos listas con el mismo número de elementos y mida:

``` java
while (!lista.isEmpty()) {
    lista.remove(0);
}
```

Analice por qué `ArrayList` debe desplazar elementos y `LinkedList`
puede modificar los enlaces del primer nodo.

## 12. LinkedList como Deque

`LinkedList` también implementa `Deque`.

``` java
import java.util.LinkedList;

public class EjemploDeque {
    public static void main(String[] args) {
        LinkedList<String> tareas = new LinkedList<>();

        tareas.addFirst("Primera");
        tareas.addLast("Última");
        tareas.addFirst("Urgente");

        System.out.println(tareas);
        System.out.println("Primera: " + tareas.getFirst());
        System.out.println("Última: " + tareas.getLast());

        tareas.removeFirst();
        System.out.println(tareas);
    }
}
```

## 13. Actividad integradora: Sistema de cola de trabajos

Desarrolle `ColaTrabajos.java` para administrar trabajos enviados a un
servidor.

El sistema deberá permitir:

``` text
1. Agregar trabajo normal
2. Agregar trabajo urgente
3. Procesar siguiente trabajo
4. Consultar siguiente trabajo
5. Mostrar trabajos pendientes
6. Mostrar número de trabajos
7. Salir
```

### Primera implementación

``` java
List<String> trabajos = new ArrayList<>();
```

Operaciones:

``` java
trabajos.add(trabajo);       // normal
trabajos.add(0, trabajo);    // urgente
trabajos.get(0);             // consultar siguiente
trabajos.remove(0);          // procesar
```

### Segunda implementación

Cambie únicamente la implementación:

``` java
List<String> trabajos = new LinkedList<>();
```

Compruebe que el comportamiento funcional permanece y analice las
diferencias de desempeño.

## 14. Mejora del diseño

Analice si realmente se necesita `List`. El problema requiere
principalmente insertar al inicio y al final, consultar el inicio y
eliminar el inicio.

Refactorice utilizando:

``` java
Deque<String> trabajos = new LinkedList<>();
```

y las operaciones:

``` java
addFirst()
addLast()
peekFirst()
pollFirst()
```

Ejemplo:

``` java
trabajos.addLast("Ejecutar pruebas");
trabajos.addFirst("Corregir servidor");

String siguiente = trabajos.peekFirst();
String procesado = trabajos.pollFirst();
```

La elección no consiste únicamente en decidir entre `ArrayList` y
`LinkedList`; primero debe seleccionarse la abstracción adecuada para
las operaciones requeridas.

## 15. Tabla comparativa final

  
| Característica|`ArrayList` |`LinkedList`|
|------------------|-----------------|---------------------------------|
| Implementa `List` |      Sí    |                 Sí|
|Estructura      |        Arreglo dinámico  |      Lista doblemente enlazada|
|Acceso `get(i)`|         O(1)  |                  O(n)|
|  Modificación `set(i)`|   O(1) |                   O(n)|
|  Inserción al final|      O(1) amortizado |        O(1)|
|  Inserción al inicio|     O(n) |                   O(1)|
|  Eliminación al inicio|   O(n) |                   O(1)|
|  Búsqueda por valor|      O(n) |                   O(n)|
 | Recorrido completo |     O(n) |                   O(n)|
 | Implementa `Deque`|      No   |                   Sí|
|  Memoria adicional por elemento|   Menor en general  |      Mayor por los enlaces|
|Acceso aleatorio frecuente|        Adecuado|                Poco adecuado|
|Operaciones frecuentes en extremos|  No es su principal fortaleza|     Adecuado|
                              


## 16. Preguntas de análisis

1.  ¿Qué interfaz implementan tanto `ArrayList` como `LinkedList`?
    la interfaz List<E>
2.  ¿Cuál es la principal diferencia en su estructura interna?}
    ArrayList utiliza un arreglo dinámico contiguo en memoria, mientras que LinkedList utiliza una estructura de nodos dobles enlazados donde cada nodo guarda una referencia al elemento
3.  ¿Por qué `ArrayList.get(i)` tiene complejidad O(1)? Porque calcula directamente la dirección de memoria usando un índice del arreglo
4.  ¿Por qué `LinkedList.get(i)` tiene complejidad O(n)?Porque no tiene acceso directo por índice. Para llegar al elemento i, debe comenzar en la cabeza (o cola) de la lista e ir saltando nodo por nodo 
5.  ¿Qué ocurre internamente cuando se ejecuta
    `ArrayList.add(0, elemento)`?
    Java se ve obligado a desplazar todos los elementos existentes una posición a la derecha en memoria
6.  ¿Por qué `LinkedList.add(0, elemento)` no necesita desplazar los
    demás elementos?
    Porque únicamente crea un nuevo nodo en memoria
7.  ¿Por qué afirmar que "`LinkedList` es mejor para inserciones" puede
    ser incorrecto?
    Porque solo es más eficiente si la inserción se realiza en los extremos (inicio o final).
8.  ¿Qué diferencia observó entre recorrer `LinkedList` mediante
    `get(i)` y mediante `for-each`?
    Con get(i), el tiempo fue altísimo debido a la complejidad cuadrática $O(N^2)$ al reiniciar la búsqueda desde el primer nodo en cada iteración. Con for-each, el tiempo bajó drásticamente a milisegundos ($O(N)$)
9.  ¿Qué resultados obtuvo para inserciones al inicio?
    LinkedList fue enormemente superior (~5.049 ms) comparado con ArrayList (~281.140 ms), confirmando la ventaja de evitar desplazamientos de memoria
10. ¿Qué resultados obtuvo para inserciones al final?
    Ambas obtuvieron tiempos muy bajos y similares (ArrayList ~8.181 ms vs. LinkedList ~9.551 ms), aunque ArrayList resultó ligeramente más rápido por no tener la sobrecarga de instanciar un nuevo objeto Node en cada paso
11. ¿Los tiempos medidos coinciden exactamente con lo esperado a partir
    de Big-O? Explique.
    Sí, la tendencia teórica se cumple. Las operaciones $O(1)$ mostraron tiempos en milisegundos muy bajos, mientras que las operaciones $O(N)$ repetidas o $O(N^2)$ mostraron incrementos drásticos en tiempo
12. ¿Qué costo de memoria adicional tiene conceptualmente una lista
    enlazada?
    Además del objeto almacenado, cada nodo de LinkedList requiere overhead de memoria para almacenar dos punteros/referencias adicionales
13. ¿Qué ventajas proporciona programar contra `List`?
    Aplica el principio de polimorfismo, lo que permite cambiar la implementación concreta
14. ¿Por qué `Deque` representa mejor el problema de la cola de
    trabajos?
    Porque la interfaz Deque (Double Ended Queue) define explícitamente métodos de inserción y extracción en ambos extremos
15. ¿En qué escenario seleccionaría `ArrayList`?
    Cuando el caso de uso requiera lecturas frecuentes o accesos aleatorios por índice, recorridos rápidos y baja sobrecarga de memoria, sin realizar inserciones o eliminaciones continuas en el inicio o medio de la lista
16. ¿En qué escenario tendría sentido utilizar `LinkedList`?
    Cuando se implementen estructuras tipo pila (stack) o cola/cola doble (queue/deque) donde las inserciones y eliminaciones se realicen únicamente en los extremos (inicio o fin) y no se requiera acceder por índice
## 17. Entregables

``` text
ComparacionListas/
├── README.md
└── src/
    ├── EjemploArrayList.java
    ├── EjemploLinkedList.java
    ├── ComparacionListas.java
    ├── BenchmarkListas.java
    ├── EjemploDeque.java
    └── ColaTrabajos.java
```

En `README.md` incluir:
-   tabla con los tiempos obtenidos;
-   comparación entre acceso mediante `get(i)` y `for-each`;
-   resultados de inserciones y eliminaciones;
-   tabla comparativa final;
-   respuestas a las preguntas de análisis;
-   conclusión técnica.
