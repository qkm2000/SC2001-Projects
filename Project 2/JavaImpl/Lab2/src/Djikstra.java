import java.util.*;

public class Djikstra {

    private static final int NO_PARENT = -1;
    public static int OuterComparisonCount = 0;
    public static int PQInnerCount = 0;
    public static int[] originalParent;

    public void NormalAlgo(int[][] adjMatrix, int startVertex) {
        int nVertices = adjMatrix[0].length;

        int[] shortestDistances = new int[nVertices];
        boolean[] added = new boolean[nVertices];
        int[] parents = new int[nVertices];

        for (int i = 0; i < nVertices; i++) {
            shortestDistances[i] = Integer.MAX_VALUE/2;
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
        //originalParent = shortestDistances;

        printSolution(startVertex, shortestDistances, parents);
    }

    public long PriorityArrAlgo(int[][] adjMatrix, int startVertex) {
        OuterComparisonCount = 0;
        PQInnerCount = 0;
        int nVertices = adjMatrix[0].length;

        int[] shortestDistances = new int[nVertices];
        int[] visited = new int[nVertices];
        int[] parents = new int[nVertices];

        //Array based priority queue implementation
        PriorityQueueImpl queue = new PriorityQueueImpl(nVertices*nVertices);
        shortestDistances[startVertex] = 0;

        for (int i = 0; i < nVertices; i++) {
            if(i != startVertex) {
                shortestDistances[i] = Integer.MAX_VALUE/2;
            }
            parents[i] = NO_PARENT;
            visited[i] = 0;
            queue.add(new QNode(i, shortestDistances[i]));
        }

        PQInnerCount = 0;
        while (!queue.isEmpty()) {//V
            QNode currNode = ((QNode)queue.remove()); //1
            int u = currNode.node;
            int edgeDistance;
            int newDistance;

            // All the neighbors of v
            for (int i = 0; i < adjMatrix[u].length; i++) { //V
                QNode v = new QNode(i, adjMatrix[u][i]);
                OuterComparisonCount++;
                //  If it's an edge & If current node hasn't already been processed
                if (v.cost > 0) {
                    edgeDistance = v.cost;
                    newDistance = shortestDistances[u] + edgeDistance;

                    // If new distance is cheaper in cost
                    if (newDistance < shortestDistances[v.node]) {
                        parents[v.node] = u;
                        shortestDistances[v.node] = newDistance;

                        // Remove all old instances of nodes
                        // Add the current node to the queue with updated distance
                        if (!queue.isEmpty())
                            queue.remove(v); //2V
                        queue.add(new QNode(v.node, shortestDistances[v.node])); //2V
                    }
                }
            }
        }
        printSolution(startVertex, shortestDistances, parents);
        System.out.println("Total Comparison Count " + (OuterComparisonCount + PQInnerCount));
        //System.out.println("count " + OuterComparisonCount);
        //System.out.println("PQcount " + PQInnerCount);
        //CheckResult(originalParent, shortestDistances);
        //originalParent = parents;
        return OuterComparisonCount + PQInnerCount;
    }

//    https://stackoverflow.com/questions/26547816/understanding-time-complexity-calculation-for-dijkstra-algorithm

    public long PriorityMinHeapAlgo(ArrayList<ArrayList<QNode>> adjList, int startVertex) {
        OuterComparisonCount = 0;
        PQInnerCount = 0;
        int nVertices = adjList.size();

        int[] shortestDistances = new int[nVertices];
        int[] visited = new int[nVertices];
        int[] parents = new int[nVertices];

        //Default Java priority queue implementation (minimizing heap tree)
//        PriorityQueue<QNode> queue = new PriorityQueue<>((o1, o2) -> {
//            Djikstra.PQInnerCount++;
//            if (o1.cost < o2.cost)
//                return -1;
//            else if (o1.cost > o2.cost)
//                return 1;
//
//            return 0;
//        });

        UpdatableHeap<Integer> queue = new UpdatableHeap<>();
        shortestDistances[startVertex] = 0;

        for (int i = 0; i < nVertices; i++) {
            if(i != startVertex) {
                shortestDistances[i] = Integer.MAX_VALUE/2;
            }
            parents[i] = NO_PARENT;
            visited[i] = 0;
            //queue.add(new QNode(i, shortestDistances[i]));
            queue.addOrUpdate(i, shortestDistances[i]);
        }

        PQInnerCount = 0;
        int edges = 0;
        for(int i = 0 ; i < nVertices; i++)
                edges += adjList.get(i).size();


        while (!queue.isEmpty()) {
            //int u = ((QNode)queue.remove()).node;
            int u = queue.pop();
            int edgeDistance;
            int newDistance;

            // All the neighbors of v
            for (int i = 0; i < adjList.get(u).size(); i++) {
                OuterComparisonCount++;
                QNode v = adjList.get(u).get(i);
                //  It is always an edge for a adjList so there is no need to check.
                //  Check if current node hasn't already been processed
                    edgeDistance = v.cost;
                    newDistance = shortestDistances[u] + edgeDistance;

                    // If new distance is cheaper in cost
                    if (newDistance < shortestDistances[v.node]) {
                        // Remove all old instances of nodes
//                        if(!queue.isEmpty()) {
//                            queue.removeIf(x -> {
//                                //PQInnerCount++;
//                                return x.node == v.node;
//                            }); // To change to manual?
//                        }
                        parents[v.node] = u;
                        shortestDistances[v.node] = newDistance;
                        //queue.add(new QNode(v.node, shortestDistances[v.node]));
                        queue.addOrUpdate(v.node,shortestDistances[v.node]);
                    }

                //}

            }
        }
        printSolution(startVertex, shortestDistances, parents);
        System.out.println("No of edges = " + edges);
        //System.out.println("Total Comparison Count " +(OuterComparisonCount + PQInnerCount));
        //CheckResult(originalParent, shortestDistances);
        return OuterComparisonCount + PQInnerCount;
    }


    private void CheckResult(int[] pp, int[] parents) {
        int sumA = Arrays.stream(pp).sum();
        int sumB = Arrays.stream(parents).sum();

        System.out.println(sumB - sumA == 0? "===SAME SAME===" : "*****FAILED****** diff: " + (sumB-sumA));
    }

    private void printSolution(int startVertex, int[] distances, int[] parents) {
        int nVertices = distances.length;
        System.out.printf("%-11s %8s %7s","Vertex", "Distance","Path");

        for (int vertexIndex = 0; vertexIndex < nVertices; vertexIndex++) {
            //Print All
            //if (vertexIndex != startVertex) {
            //Print Start->End
            if (vertexIndex == nVertices -1) {
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
