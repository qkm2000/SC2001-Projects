import java.util.ArrayList;
import java.util.Random;

public class Main {

    private static final int GRAPH_SIZE = 20;
    public static void main(String[] args) {
        int graph1[][] = new int[][]{
                {0, 4, 0, 0, 0, 0, 0, 8, 0},
                {4, 0, 8, 0, 0, 0, 0, 11, 0},
                {0, 8, 0, 7, 0, 4, 0, 0, 2},
                {0, 0, 7, 0, 9, 14, 0, 0, 0},
                {0, 0, 0, 9, 0, 10, 0, 0, 0},
                {0, 0, 4, 14, 10, 0, 2, 0, 0},
                {0, 0, 0, 0, 0, 2, 0, 1, 6},
                {8, 11, 0, 0, 0, 0, 1, 0, 7},
                {0, 0, 2, 0, 0, 0, 6, 7, 0}};

        int graph2[][] = {
                {0, 0, 6, 3, 7, 5, 0, 7, 4, 2},
                {0, 3, 8, 0, 3, 7, 3, 6, 7, 2},
                {2, 0, 3, 6, 5, 1, 1, 3, 2, 8},
                {3, 6, 9, 4, 9, 3, 1, 5, 6, 9},
                {7, 4, 6, 3, 4, 1, 7, 1, 1, 1},
                {0, 3, 1, 9, 1, 2, 8, 1, 3, 7},
                {0, 3, 5, 9, 9, 1, 2, 4, 2, 1},
                {3, 0, 2, 5, 4, 2, 1, 6, 3, 9},
                {0, 2, 9, 3, 1, 3, 8, 5, 2, 1},
                {2, 3, 0, 0, 9, 4, 4, 5, 8, 1}};

        int graph3[][] = new int[GRAPH_SIZE][GRAPH_SIZE];
        Random rand = new Random(0);
        for(int i = 0 ; i < graph3.length; i++){
            for(int j = 0; j < graph3[i].length; j++){
                graph3[i][j] = rand.nextInt(0,16);
            }
        }
        Djikstra djikstra = new Djikstra();
        var graphToTry = graph3;

        djikstra.NormalAlgo(graphToTry, 0);
        djikstra.PriorityArrAlgo(graphToTry, 0);
        djikstra.PriorityMinHeapAlgo(AdjMatrixToAdjList(graphToTry),0);
    }

    public static ArrayList<ArrayList<QNode>> AdjMatrixToAdjList(int[][] adjMatrix){
        ArrayList<ArrayList<QNode>> adjList = new ArrayList<>();
        int nodeCost;
        for(int i = 0 ; i < adjMatrix.length; i++){
            adjList.add(new ArrayList<>());
            for(int j = 0; j < adjMatrix[i].length; j++){
                nodeCost = adjMatrix[i][j];
                if(nodeCost > 0)
                    adjList.get(i).add(new QNode(j,nodeCost));
            }
        }
        return adjList;
    }
}
