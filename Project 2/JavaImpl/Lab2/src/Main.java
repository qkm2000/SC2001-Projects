import java.io.*;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    private static final int GRAPH_SIZE = 100;
    public static void main(String[] args) throws IOException {
//        int graph1[][] = new int[][]{
//                {0, 4, 0, 0, 0, 0, 0, 8, 0},
//                {4, 0, 8, 0, 0, 0, 0,11, 0},
//                {0, 8, 0, 7, 0, 4, 0, 0, 2},
//                {0, 0, 7, 0, 9,14, 0, 0, 0},
//                {0, 0, 0, 9, 0,10, 0, 0, 0},
//                {0, 0, 4,14,10, 0, 2, 0, 0},
//                {0, 0, 0, 0, 0, 2, 0, 1, 6},
//                {8,11, 0, 0, 0, 0, 1, 0, 7},
//                {0, 0, 2, 0, 0, 0, 6, 7, 0}
//        };
//
//        int graph2[][] = {
//                {0, 0, 6, 3, 7, 5, 0, 7, 4, 2},
//                {0, 3, 8, 0, 3, 7, 3, 6, 7, 2},
//                {2, 0, 3, 6, 5, 1, 1, 3, 2, 8},
//                {3, 6, 9, 4, 9, 3, 1, 5, 6, 9},
//                {7, 4, 6, 3, 4, 1, 7, 1, 1, 1},
//                {0, 3, 1, 9, 1, 2, 8, 1, 3, 7},
//                {0, 3, 5, 9, 9, 1, 2, 4, 2, 1},
//                {3, 0, 2, 5, 4, 2, 1, 6, 3, 9},
//                {0, 2, 9, 3, 1, 3, 8, 5, 2, 1},
//                {2, 3, 0, 0, 9, 4, 4, 5, 8, 1}};
//
//        int graph3[][] = new int[GRAPH_SIZE][GRAPH_SIZE];
//        Random rand = new Random(0);
//        for(int i = 0 ; i < graph3.length; i++){
//            for(int j = 0; j < graph3[i].length; j++){
//                graph3[i][j] = rand.nextInt(0,16);
//            }
//        }
        Djikstra djikstra = new Djikstra();

        String filePath = "test cases and results/";

        List<String[]> Result = new ArrayList<>();

        List<Path> files;
        try(Stream<Path> paths = Files.walk(Paths.get(filePath))){
            files = paths.filter(Files::isRegularFile).toList();
        }

        for(Path file : files) {
            ArrayList<ArrayList<Integer>> arr = new ArrayList<>();

            try (BufferedReader reader = new BufferedReader(new FileReader(file.toAbsolutePath().toString()))) {
                String line;

                // checking end of file
                while ((line = reader.readLine()) != null) {
                    // adding each string to arraylist
                    arr.add(new ArrayList<>());

                    for (String digit : line.split(",")) {

                        if (digit.trim().matches("\\[\\d" + "|" + "\\d]")) {
                            digit = digit.replace("[", "");
                            digit = digit.replace("]", "");
                        }

                        digit = digit.trim();
                        arr.get(arr.size() - 1).add(Integer.parseInt(digit));
                    }
                }
            }
            int[][] graphToTry = arr.stream().map(u -> u.stream().mapToInt(i -> i).toArray()).toArray(int[][]::new);

            long Start, End;
            long res;
            System.out.println("======="+file.getFileName()+"=======");
            //djikstra.NormalAlgo(graphToTry, 0);
            System.out.println("=====Array=====");
            {
                Start = System.nanoTime();
                res = djikstra.PriorityArrAlgo(graphToTry, 0);
                End = System.nanoTime() - Start;
                System.out.println(End);
                Result.add(new String[]{file.getFileName().toString().split("Graph")[0],
                        file.getFileName().toString().split(" ")[1].split("\\.")[0],
                        "Array",
                        Long.toString(res), Long.toString(End)});
            }
            System.out.println("=====MHeap=====");
            {
                Start = System.nanoTime();
                res = djikstra.PriorityMinHeapAlgo(AdjMatrixToAdjList(graphToTry), 0);
                End = System.nanoTime() - Start;
                System.out.println(End);
                Result.add(new String[]{file.getFileName().toString().split("Graph")[0],
                        file.getFileName().toString().split(" ")[1].split("\\.")[0],
                        "Heap",
                        Long.toString(res), Long.toString(End)});
            }
        }

        /* Print to CSV files */
        try {
            ArrayListToCSV(Result, "Result.csv");
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    public static void ArrayListToCSV(List<String[]> conv, String CSV_FILE_NAME) throws IOException {
        File csvOutputFile = new File(CSV_FILE_NAME);
        try (PrintWriter pw = new PrintWriter(csvOutputFile)) {
            conv.stream()
                    .map(Main::convertToCSV)
                    .forEach(pw::println);
        }
    }

    public static String convertToCSV(String[] data) {
        return Stream.of(data)
                .map(Main::escapeSpecialCharacters)
                .collect(Collectors.joining(","));
    }

    public static String escapeSpecialCharacters(String data) {
        String escapedData = data.replaceAll("\\R", " ");
        if (data.contains(",") || data.contains("\"") || data.contains("'")) {
            data = data.replace("\"", "\"\"");
            escapedData = "\"" + data + "\"";
        }
        return escapedData;
    }
}
