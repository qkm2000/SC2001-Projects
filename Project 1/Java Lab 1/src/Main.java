import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) {
        //https://www.geeksforgeeks.org/sorting-by-combining-insertion-sort-and-merge-sort-algorithms/
        //https://stackoverflow.com/questions/69015899/key-comparisons-in-a-merge-insertion-hybrid-sort
        //https://stackoverflow.com/questions/66612198/how-to-explain-the-result-of-my-experiment-aimed-to-find-the-threshold-between-i
        //https://stackoverflow.com/questions/68448208/best-way-to-combine-insertionsort-and-mergesort

        List<int[]> list = new ArrayList<>();
        list.add(new int[1000]);
        list.add(new int[5000]);
        list.add(new int[10_000]);
        list.add(new int[100_000]);
        list.add(new int[1_000_000]);
        list.add(new int[2 * 1_000_000]);
        list.add(new int[8 * 1_000_000]);
        list.add(new int[16 * 1_000_000]);
        Random r = new Random(0);
        for (int[] ints : list)
            for (int j = 0; j < ints.length; j++)
                ints[j] = r.nextInt(1,1_000_001);

        Sorter ob = new Sorter();
        //Sorter.printArray(arr);

        /*Result list for printing to csv */
        List<String[]> mergeResult = new ArrayList<>();
        List<String[]> hybridResult = new ArrayList<>();


        for (int[] ints : list) {
            System.out.printf("========================= Array Size: %d =========================%n", ints.length);

            System.out.println("============Starting Merge Sort============");
            long avgTimeMerge = 0;
            int setSize = 3;
            for (int i = 0; i < setSize; i++) {
                ob.resetKeyComparisonCounter();
                // First run will always be slower
                int[] MergeSortArr = Arrays.copyOf(ints, ints.length);
                avgTimeMerge += Timer("Merge", () -> ob.MergeSort(MergeSortArr, 0, MergeSortArr.length - 1), () -> ArrayCheck(MergeSortArr));
            }
            System.out.println(ob.getKeyComparisonCounter() + " Key Comparisons" + "\tAverage Time: " + avgTimeMerge / setSize + " ms");
            mergeResult.add(new String[]{Integer.toString(ints.length), Integer.toString(ob.getKeyComparisonCounter()), Long.toString(avgTimeMerge / setSize)});


            /* Insertion sort is too slow */
//            System.out.println(String.format("============Starting Insertion Sort============"));
//            avgTimeMerge = 0;
//            setSize = 3;
//            for(int i = 0; i < setSize; i++){
//                ob.resetKeyComparisonCounter();
//                int[] InsertionArr = Arrays.copyOf(ints, ints.length);
//                Timer("Insertion", ()-> ob.InsertionSort(InsertionArr, 0, InsertionArr.length), ()->ArrayCheck(InsertionArr));
//            }
//            System.out.println(ob.getKeyComparisonCounter() + " Key Comparisons" + "\tAverage Time: " + avgTimeMerge / setSize + " ms");


            System.out.println("============Starting Hybrid Sort============");
            int[] S_Array = {4, 8, 16, 32, 64};
            for (int s : S_Array) {
                ob.setThreshold(s);
                System.out.println("S= " + ob.getThreshold());
                avgTimeMerge = 0;
                setSize = 3;
                for (int i = 0; i < setSize; i++) {
                    ob.resetKeyComparisonCounter();
                    int[] IMSortArr = Arrays.copyOf(ints, ints.length);
                    avgTimeMerge += Timer("Hybrid", () -> ob.IMSort(IMSortArr, 0, IMSortArr.length - 1), () -> ArrayCheck(IMSortArr));
                }
                System.out.println(ob.getKeyComparisonCounter() + " Key Comparisons" + "\tAverage Time: " + avgTimeMerge / setSize + " ms");
                hybridResult.add(new String[]{Integer.toString(ints.length), Integer.toString(ob.getThreshold()), Integer.toString(ob.getKeyComparisonCounter()), Long.toString(avgTimeMerge / setSize)});
            }

            /* Print to CSV files */
            try {
                ArrayListToCSV(hybridResult, "HybridResult.csv");
                ArrayListToCSV(mergeResult, "MergeResult.csv");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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

    public static long Timer(String sortName, Runnable before, Runnable after) {
        long bgn, end;
        bgn = System.currentTimeMillis();
        before.run();
        end = System.currentTimeMillis();
        after.run();
        //System.out.println((end - bgn) + " ms");
        return (end - bgn);
    }

    public static void ArrayCheck(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            if (arr[i - 1] > arr[i]) {
                System.out.println("index " + i + " " + arr[i - 1] + " " + arr[i] + " failed");
                break;
            }
        }
    }
}
