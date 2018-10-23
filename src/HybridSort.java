import java.util.ArrayList;

public class HybridSort implements SortingAlgorithm{

	/**
	 * Method calls findRuns which finds all the runs and separate them in different arrays. 
	 * It calls the method merge which merges all the runs with the merge sort algorithm.
	 * It copies all the elements in the result array into the original array.
	 */
	@Override
	public void sort(int[] a) {
		var runs = findRuns(3, a);
		var result = merge(runs);
		
		for(int i = 0; i < result.length; i++) {
			a[i] = result[i];
		}
	}
	/**
	 * This method finds all the runs within an array with a minimum array size of @param min.
	 * @param min is the minimum array size to look for a sequence of numbers that is bigger than previous number on the list.
	 * @param arr is the array to find the runs from
	 * @return returns an ArrayList containing all the runs.
	 */
	public ArrayList<int[]> findRuns(int min, int[] arr) {
		
		var runs = new ArrayList<int[]>();
		
		int first = 0;
		int previous;
		int pointer = 0;
		
		for(int current = 1; current < arr.length; current++) {
			
			previous = current - 1;
			
			if(arr[previous] > arr[current]) {
				if(longEnough(first, previous, min)) {
					runs.add(subarray(first, previous, arr));
					splitArray(pointer, first, min, arr, runs);
					pointer = current;
				}
				first = current;
			}
			
			if(current == arr.length - 1){
				if(longEnough(first, current, min)) {
					runs.add(subarray(first, current, arr));
					splitArray(pointer, first, min, arr, runs);
					pointer = current;
				}else {
					splitArray(pointer, current + 1, min, arr, runs);
				}
			}
			
		}
		
		return runs;
		
	}
	
	/**
	 * Merge merges all the arrays found in @param runs
	 * 
	 * @param runs ArrayList of runs
	 * @return return merged ArrayList.
	 */
	public static int[] merge(ArrayList<int[]> runs) {
		
		
		if(runs.size() == 1) {
			return runs.get(0);
		}
		
		ArrayList<int[]> temp = new ArrayList<int[]>();
		
		int previous;
		for(int current = 1; current < runs.size(); current = current + 2) {
			previous = current - 1;
			temp.add(merge(runs.get(current), runs.get(previous)));
		}
		
		if(runs.size() % 2 != 0) {
			temp.add(runs.get(runs.size() - 1));
		}
		
		return merge(temp);
		
	}
	/**
	 * This method merges an array to the other with a a MergeSort algorithm.
	 * 
	 * @param L the left array
	 * @param R the right array
	 * @return returns merged array.
	 */
	public static int[] merge(int[] L, int[] R) {

			int n1 = L.length;
			int n2 = R.length;
			
			int[] arr = new int[n1 + n2];

			
	        int i = 0, j = 0;

	        int k = 0;
	        while (i < n1 && j < n2) {
	            if (L[i] <= R[j]) {
	                arr[k] = L[i];
	                i++;
	            }
	            else {
	                arr[k] = R[j];
	                j++;
	            }
	            k++;
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
	        return arr;
	}
	
	/**
	 * Checks if the left pointer (@param l) and the right pointer (@param h) are far enough apart.
	 * @param l left pointer
	 * @param h right pointer
	 * @param min how far they should be apart
	 * @return true if they are far enough.
	 */
	public static boolean longEnough(int l, int h, int min) {
		return (h-l) + 1 >= min;
	}
	
	/**
	 * This method finds runs that are not ordered from pointer ( @param start ) to the other pointer ( @param limit )
	 * from an array ( @param arr ). It adds it to the ArrayList of runs that was found
	 * @param start starting pointer
	 * @param limit ending pointer
	 * @param size the limited size the runs should be
	 * @param arr the array from which to look for
	 * @param runs the result of the runs found
	 */
	private static void splitArray(int start , int limit, int size, int[] arr, ArrayList<int[]> runs) {
		int current = start;
		int length;
		while(current != limit) {
			length = current - start + 1;
			if(length == size || current == limit - 1) {
				runs.add(subarray(start, current, arr));
				start = current + 1;
			}
			current++;
		}
	}
	
	/**
	 * This method returns a sub array of an array according to the lowest and highest elements
	 * passed in as a parameter.
	 * 
	 * @param l is the pointer that represents the first element of the array.
	 * @param h is the pointer that represents the last element of the array.
	 * @param arr is the array we are getting the sub array from.
	 * @return sub array of @param arr according to l and h values.
	 */
	public static int[] subarray(int l, int h, int[] arr) {
		
		int size = h - l + 1;
		
		int[] copy = new int[size];
		
		int current = l;
		for(int i = 0; i < copy.length; i++) {
			copy[i] = arr[current];
			current++;
		}
		
		insertionSort(copy);
		return copy;
	}
	
	/**
	 * Insertion Sort algorithm
	 * @param a is the array from which we should sort.
	 */
	public static void insertionSort(int[] a) {
        for(int i = 1; i < a.length; i++) {
            int current = a[i];
            int comp = i - 1;
            while(comp >= 0 && current < a[comp]) {
                int temp = a[comp];
                a[comp] = a[comp + 1];
                a[comp + 1] = temp;
                comp--;
            }
        }
    }
	
}
