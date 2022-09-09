import java.util.Arrays;
import java.util.Random;

public class Main {

    public static void main(String[] args) {
        //https://www.geeksforgeeks.org/sorting-by-combining-insertion-sort-and-merge-sort-algorithms/
        //https://stackoverflow.com/questions/69015899/key-comparisons-in-a-merge-insertion-hybrid-sort
        //https://stackoverflow.com/questions/66612198/how-to-explain-the-result-of-my-experiment-aimed-to-find-the-threshold-between-i
        //https://stackoverflow.com/questions/68448208/best-way-to-combine-insertionsort-and-mergesort

        int[] arr = new int[16 * 1024 * 1024];
        Random r = new Random(0);
        for (int i = 0; i < arr.length; i++)
            arr[i] = r.nextInt();

        Sorter ob = new Sorter();
        //Sorter.printArray(arr);

        System.out.println(String.format("Starting Merge Sort"));
        for (int i = 0; i < 1; i++) {
            ob.resetKeyComparisonCounter();
            // First run will always be slower
            int[] MergeSortArr = Arrays.copyOf(arr, arr.length);
            Timer("Merge", () -> ob.MergeSort(MergeSortArr, 0, MergeSortArr.length - 1), () -> ArrayCheck(MergeSortArr));
            System.out.println(ob.getKeyComparisonCounter() + " Key Comparions");
        }

        /* Too Slow */
//        System.out.println(String.format("Starting Insertion Sort"));
//        for(int i = 0; i < 3; i++){
//            ob.resetKeyComparisonCounter();
//            int[] InsertionArr = Arrays.copyOf(arr, arr.length);
//            Timer("Insertion", ()-> ob.InsertionSort(InsertionArr, 0, arr.length), ()->ArrayCheck(InsertionArr));
//            System.out.println(ob.getKeyComparisonCounter() + " Key Comparions");
//        }

        System.out.println(String.format("Starting Hybrid Sort"));
        int[] S_Array = {8, 16, 32};
        for (int i = 0; i < 3; i++) {
            ob.resetKeyComparisonCounter();
            int[] IMSortArr = Arrays.copyOf(arr, arr.length);
            ob.setThreshold(S_Array[i]);
            System.out.println("S= " + ob.getThreshold());
            Timer("Hybrid", () -> ob.IMSort(IMSortArr, 0, IMSortArr.length - 1), () -> ArrayCheck(IMSortArr));
            System.out.println(ob.getKeyComparisonCounter() + " Key Comparions");
        }

    }


    public static void Timer(String sortName, Runnable before, Runnable after) {
        long bgn, end;
        bgn = System.currentTimeMillis();
        before.run();
        end = System.currentTimeMillis();
        after.run();
        System.out.println((end - bgn) + " ms");
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
