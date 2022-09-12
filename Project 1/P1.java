import java.util.*;
public class P1 {
	
	static int comps;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		int choice;
		do {
			System.out.println("Select question part.");
			System.out.println("1: Fix size 10000");
			System.out.println("2: Size ranges from 1000 to 10 mil with fix S = 5");
			System.out.println("3: S ranges from 1 to 100 with fix size = 10000");
			System.out.println("4: Comparison btw MergeSort and HybridSort for size of range from 1000 to 10 mil.");
			choice = sc.nextInt();
			int size, S;
			int[] arr;
			switch(choice) {
			case 1: 
				size = 10000;
				arr = new int[size];
				for(int i = 0; i < size; i++) {
					Random r = new Random();
					arr[i] = r.nextInt(100000);
					// System.out.print(arr[i] + " ");
				}
				// System.out.println();
				S = 5;
				arr = mergeSort(S, arr, 0, size-1);
				// for(int i = 0; i < size; i++) {
				// 	System.out.print(arr[i] + " ");
				// }
				// System.out.println();
				System.out.println("Number of comparisons is " + comps);
				break;
				
			case 2: 
				size = 1000;
				while(size <= 10000000) {
					comps = 0;
					arr = new int[size];
					for(int i = 0; i < size; i++) {
						Random r = new Random();
						arr[i] = r.nextInt(1000);
					}
					S = 5;
					arr = mergeSort(S, arr, 0, size-1);
					System.out.format("Number of comparisons is %d for size of %d. \n", comps, size);
					size *= 10;
				}
				break;
			case 3:
				size = 10000;
				arr = new int[size];
				S = 1;
				int[] newarr = new int[size];
				for(int i = 0; i < size; i++) {
					Random r = new Random();
					arr[i] = newarr[i] =  r.nextInt(1000);
				}
				while(S <= 100) {
					comps = 0;
					newarr = mergeSort(S, arr, 0, size-1);
					System.out.format("Number of comparisons is %d for S value of %d and size of %d.\n", comps, S, size);
					S++;
				}
				break;
			
			case 4:
				int choiceimalS = 6;
				size = 1000;
				while(size <= 10000000) {
					comps = 0;
					arr = new int[size];
					int[] newArr = new int[size];
					for(int i = 0; i<size; i++) {
						Random r = new Random();
						arr[i] = newArr[i] = r.nextInt(1000);
					}
					newArr = mergeSort(0, arr, 0, size-1);
					int first = comps;
					comps = 0;
					newArr = mergeSort(choiceimalS, arr, 0, size-1);
					int second = comps;
					System.out.format("Number of comparisons is %d for MergeSort and %d for Hybrid Sort for size of %d.\n", first, second, size);
					size *= 10;
				}
				break;
			} 
		}while(choice < 7);
	}
	
	public static int[] insertionSort(int[] arr, int start, int end) {
		for(int i = start+1; i <= end; i++) {
			if(arr[i] < arr[i-1]) {
				int j = i;
				while (j > start && arr[j] < arr[j-1]) {
					comps++;
					int tmp = arr[j];
					arr[j] = arr[j-1];
					arr[j-1] = tmp;
					j--;
				}
			}
		}
		return arr;
	}
	public static int[] mergeSort(int S, int[] arr, int start, int end) {
		if(end - start <= 0) {
			return arr;
		} else {
			int mid = (start + end) / 2;
			int[] left = new int[mid+1];
			for(int i = start; i <= mid; i++) {
				left[i] = arr[i];
			}
			int[] right = new int[end-mid];
			for(int i = mid+1; i <= end; i++) {
				right[i-mid-1] = arr[i];
			}
			if(mid+1 <= S) {
				left = insertionSort(left, 0, mid);
			} else {
				left = mergeSort(S, left, 0, mid);
			}
			if(end-mid <= S) {
				right = insertionSort(right, 0, end-mid-1);
			} else {
				right = mergeSort(S, right, 0, end-mid-1);
			}
			
			
			arr = merge(left, mid-start+1, right, end-mid);
			return arr;
		}
	}
	
	public static int[] merge(int[] left, int leftLen, int[] right, int rightLen) {
		int[] newArr = new int[leftLen + rightLen];
		int i = 0, j = 0, index = 0; // i is left index, j is right index
		while(i != leftLen && j != rightLen) {
			comps++;
			if(left[i] < right[j]) {
				newArr[index++] = left[i++];
			} else if (right[j] < left[i]) {
				newArr[index++] = right[j++];
			} else {
				newArr[index++] = left[i++];
				newArr[index++] = right[j++];
			}
		}
		
		while(i != leftLen) {
			newArr[index++] = left[i++];
		}
		
		while(j != rightLen) {
			newArr[index++] = right[j++];
		}
		
		return newArr;
		
	}
}
