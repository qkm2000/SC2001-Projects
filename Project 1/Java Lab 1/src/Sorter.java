import java.util.Arrays;

class Sorter {
    private int keyComparisonCounter = 0;
    private int SORT_THRESHOLD = 64;

    private void merge(int arr[], int l, int m, int r) {
        int n1 = m - l + 1;
        int n2 = r - m;

        int L[] = new int[n1];
        int R[] = new int[n2];

        for (int i = 0; i < n1; ++i)
            L[i] = arr[l + i];
        for (int j = 0; j < n2; ++j)
            R[j] = arr[m + 1 + j];

        int i = 0, j = 0;

        int k = l;
        while (i < n1 && j < n2) {
            if (L[i] <= R[j]) {
                arr[k] = L[i];
                i++;
            } else {
                arr[k] = R[j];
                j++;
            }
            k++;
            keyComparisonCounter++;
        }

        while (i < n1) {
            arr[k] = L[i];
            i++;
            k++;
        }

        while (j < n2) {
            arr[k] = R[j];
            j++;
            k++;
        }
    }

    void IMSort(int arr[], int l, int r) {
        int S = r - l;
        if (S <= SORT_THRESHOLD) {
            InsertionSort(arr, l, r);
            return;
        }
        if (l < r) {
            int m = l + (r - l) / 2;

            IMSort(arr, l, m);
            IMSort(arr, m + 1, r);

            merge(arr, l, m, r);
        }
    }

    void MergeSort(int arr[], int l, int r) {
        if (l < r) {
            int m = l + (r - l) / 2;

            MergeSort(arr, l, m);
            MergeSort(arr, m + 1, r);

            merge(arr, l, m, r);
        }
    }

    void InsertionSort(int arr[], int l, int r) {
        for (int i = l; i < r; i++) {
            int tempVal = arr[i + 1];
            int j = i + 1;
            while (j > l && arr[j - 1] > tempVal) {
                keyComparisonCounter++;
                arr[j] = arr[j - 1];
                j--;
            }
            arr[j] = tempVal;
        }
    }

    int getThreshold() {
        return SORT_THRESHOLD;
    }

    void setThreshold(int newThreshold) {
        SORT_THRESHOLD = newThreshold;
    }

    int getKeyComparisonCounter() {
        return keyComparisonCounter;
    }

    void resetKeyComparisonCounter() {
        keyComparisonCounter = 0;
    }

    /* A utility function to print array of size n */
    static void printArray(int arr[]) {
        int n = arr.length;
        for (int i = 0; i < n; ++i)
            System.out.print(arr[i] + " ");
        System.out.println();
    }
}
