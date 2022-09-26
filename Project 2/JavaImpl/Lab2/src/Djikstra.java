import org.w3c.dom.Node;

import java.util.*;
import java.util.function.Predicate;

public class Djikstra {

    private static final int NO_PARENT = -1;

    public void NormalAlgo(int[][] adjMatrix, int startVertex) {
        int nVertices = adjMatrix[0].length;

        int[] shortestDistances = new int[nVertices];
        boolean[] added = new boolean[nVertices];
        int[] parents = new int[nVertices];

        for (int i = 0; i < nVertices; i++) {
            shortestDistances[i] = Integer.MAX_VALUE;
            added[i] = false;
        }

        shortestDistances[startVertex] = 0;
        parents[startVertex] = NO_PARENT;

        for (int i = 1; i < nVertices; i++) {
            int nearestVertex = -1;
            int shortestDistance = Integer.MAX_VALUE;
            for (int index = 0; index < nVertices; index++) {
                if (!added[index] && shortestDistances[index] < shortestDistance) {
                    nearestVertex = index;
                    shortestDistance = shortestDistances[index];
                }
            }

            added[nearestVertex] = true;

            for (int index = 0; index < nVertices; index++) {
                int edgeDistance = adjMatrix[nearestVertex][index];

                if (edgeDistance > 0 && ((shortestDistance + edgeDistance) < shortestDistances[index])) {
                    parents[index] = nearestVertex;
                    shortestDistances[index] = shortestDistance + edgeDistance;
                }
            }
        }
        printSolution(startVertex, shortestDistances, parents);
    }

    public void PriorityArrAlgo(int[][] adjMatrix, int startVertex) {
        int nVertices = adjMatrix[0].length;

        int[] shortestDistances = new int[nVertices];
        Set<Integer> visited = new HashSet<Integer>();
        int[] parents = new int[nVertices];

        //Array based priority queue implementation
        PriorityQueueImpl queue = new PriorityQueueImpl(nVertices);

        for (int i = 0; i < nVertices; i++) {
            shortestDistances[i] = Integer.MAX_VALUE;
        }

        shortestDistances[startVertex] = 0;
        parents[startVertex] = NO_PARENT;
        queue.add(new QNode(startVertex, 0));


        while (!queue.isEmpty()) {
            int u = ((QNode)queue.remove()).node;

            visited.add(u);

            int edgeDistance;
            int newDistance;

            // All the neighbors of v
            for (int i = 0; i < adjMatrix[u].length; i++) {
                QNode v = new QNode(i, adjMatrix[u][i]);
                //  If it's an edge & If current node hasn't already been processed
                if (v.cost > 0 && !visited.contains(v.node)) {
                    edgeDistance = v.cost;
                    newDistance = shortestDistances[u] + edgeDistance;

                    // If new distance is cheaper in cost
                    if (newDistance < shortestDistances[v.node]) {
                        // Remove all old instances of nodes
                        if(!queue.isEmpty())
                            queue.remove(v);

                        parents[v.node] = u;
                        shortestDistances[v.node] = newDistance;
                    }

                    // Add the current node to the queue
                    queue.add(new QNode(v.node, shortestDistances[v.node]));
                }

            }
        }
        printSolution(startVertex, shortestDistances, parents);
    }

    public void PriorityMinHeapAlgo(ArrayList<ArrayList<QNode>> adjList, int startVertex) {
        int nVertices = adjList.size();

        int[] shortestDistances = new int[nVertices];
        Set<Integer> visited = new HashSet<Integer>();
        int[] parents = new int[nVertices];

        //Default Java priority queue implementation (minimizing heap tree)
        PriorityQueue<QNode> queue = new PriorityQueue<>();

        for (int i = 0; i < nVertices; i++) {
            shortestDistances[i] = Integer.MAX_VALUE;
        }

        shortestDistances[startVertex] = 0;
        parents[startVertex] = NO_PARENT;
        queue.add(new QNode(startVertex, 0));


        while (!queue.isEmpty()) {
            int u = ((QNode)queue.remove()).node;

            visited.add(u);

            int edgeDistance;
            int newDistance;

            // All the neighbors of v
            for (int i = 0; i < adjList.get(u).size(); i++) {
                QNode v = adjList.get(u).get(i);
                //  It is always an edge for a adjList so there is no need to check.
                //  Check if current node hasn't already been processed
                if (!visited.contains(v.node)) {
                    edgeDistance = v.cost;
                    newDistance = shortestDistances[u] + edgeDistance;

                    // If new distance is cheaper in cost
                    if (newDistance < shortestDistances[v.node]) {
                        // Remove all old instances of nodes
                        if(!queue.isEmpty())
                            queue.removeIf(x->x.node == v.node); // To change to manual?

                        parents[v.node] = u;
                        shortestDistances[v.node] = newDistance;
                    }

                    // Add the current node to the queue
                    queue.add(new QNode(v.node, shortestDistances[v.node]));
                }

            }
        }
        printSolution(startVertex, shortestDistances, parents);
    }

    private void printSolution(int startVertex, int[] distances, int[] parents) {
        int nVertices = distances.length;
        System.out.printf("%-11s %8s %7s","Vertex", "Distance","Path");

        for (int vertexIndex = 0; vertexIndex < nVertices; vertexIndex++) {
            if (vertexIndex != startVertex) {
                System.out.printf("\n%2d -> ", startVertex);
                System.out.printf("%-2d \t",vertexIndex);
                System.out.printf("%-3d \t\t",distances[vertexIndex]);
                printPath(vertexIndex, parents);
            }
        }
        System.out.println();
    }

    private static void printPath(int currentVertex, int[] parents) {

        // Base case : Source node has
        // been processed
        if (currentVertex == NO_PARENT) {
            return;
        }
        printPath(parents[currentVertex], parents);
        System.out.print(currentVertex + " ");
    }
}

class QNode implements Comparable<QNode>{

    public int node;
    public int cost;

    public QNode(int node, int cost) {
        this.node = node;
        this.cost = cost;
    }

    @Override
    public int compareTo(QNode o) {
        if (this.cost < o.cost)
            return -1;
            //return o.node;
        else if (this.cost > o.cost)
            return 1;
        //return this.node;

        return 0;
    }
}
