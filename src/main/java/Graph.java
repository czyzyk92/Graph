import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class Vertex {
    ArrayList<Vertex> neighbors = new ArrayList<Vertex>();
    int value = 0;
}

public class Graph {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        ArrayList<Vertex> vertices = getVertices(input);
        int numberOfGraphs = findNumberOfGraphs(vertices);

        System.out.println(numberOfGraphs);
        input.close();
    }

    private static ArrayList<Vertex> getVertices(Scanner input) {
        int numberOfEdge = input.nextInt();
        ArrayList<Vertex> vertices = new ArrayList<>();
        int v1;
        int v2;
        for (int i = 0; i < numberOfEdge; i++) {
            try {
                v1 = input.nextInt();
                v2 = input.nextInt();
            } catch (NumberFormatException e) {
                throw new NumberFormatException("The value entered must be a number");
            }

            Vertex vertex = getVertex(vertices, v1);
            Vertex neighbor = getVertex(vertices, v2, vertex);

            vertex.neighbors.add(neighbor);
        }
        return vertices;
    }

    private static Vertex getVertex(ArrayList<Vertex> vertices, int valueOfVertex) {
        return getVertex(vertices, valueOfVertex, null);
    }

    /**
     * @param vertices      - Lista wierzchołków, na której szukamy wierzhołka o wartości #valueOfVertex.
     * @param valueOfVertex - Wartość węzła, którego będziemy szukać w #vertices, jeśli nie znajdziemy to tworzymy nowy.
     * @param vertex        - Sąsiad Wierzchołka o wartości #valueOfVertex.
     *                      Jeśli wartość jest różna od null i nie znaleźliśmy wierzchołka #valueOfVertex to dodajemy ją do listy sąsiadów utworzonego wierzchołka.
     * @return - Znaleziony lub utworzony wierzchołek.
     */
    private static Vertex getVertex(ArrayList<Vertex> vertices,
                                    int valueOfVertex,
                                    Vertex vertex) {
        return vertices.stream().filter(n -> n.value == valueOfVertex).findAny().orElseGet(() -> {
            Vertex n = new Vertex();
            n.value = valueOfVertex;
            if (vertex != null) {
                n.neighbors.add(vertex);
            }
            vertices.add(n);
            return n;
        });
    }

    /**
     * Metda:
     * <li>bierze pierwszy wierzchołek z listy, dodaje go na stos, ustawia ilość grafów na 1 i oznacza go jako odwiedzony w tablicy odwiedzin.
     * <li>Nastepnie do momentu, aż stos nie bęzie pusty, ściąga ostatni element ze stosu i odwiedza wszystkich jego nieodwiedzonych sąsiadów.
     * <li>Oznacza sąsiadów jako odwiedzonych i dodaje na stos.
     * Czynności te powtarza dla wszyskich nieodwiedzonych wierzchołków
     *
     * @param vertices - lista wierzchołków
     * @return ilość grafów
     */
    private static int findNumberOfGraphs(ArrayList<Vertex> vertices) {
        ArrayList<Vertex> stos = new ArrayList<>();
        int numberOfGraphs = 0;
        int[] visitArray = new int[vertices.size()];
        for (int i = 0; i < vertices.size(); i++) {
            visitArray[i] = 0;
        }

        for (int i = 0; i < vertices.size(); i++) {
            if (visitArray[i] != 0) {
                continue;
            }
            numberOfGraphs++;
            stos.add(vertices.get(i));
            visitArray[i] = numberOfGraphs;

            while (!stos.isEmpty()) {
                Vertex vertex = stos.get(stos.size() - 1);
                stos.remove(vertex);
                for (Vertex neighbor : vertex.neighbors) {
                    int indexOfNeighbor = vertices.indexOf(neighbor);
                    boolean vertexIsNotVisited = visitArray[indexOfNeighbor] == 0;
                    if (vertexIsNotVisited) {
                        stos.add(neighbor);
                        visitArray[indexOfNeighbor] = numberOfGraphs;
                    }
                }
            }
        }
        return numberOfGraphs;
    }

}
