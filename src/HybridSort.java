import java.util.ArrayList;

public class HybridSort implements SortingAlgorithm{
	
	
	public static ArrayList<int[]> runs = new ArrayList<int[]>();

	@Override
	public void sort(int[] a) {
		runs(3, a);
		
		var array = runs.get(0);
		for(int i=0; i< array.length;i++){
            a[i] = array[i];
        }
		
	}
	
	public void runs(int min, int[] arr) {
		
		int first = 0;
		int previous;
		int pointer = 0;
		
		for(int current = 1; current < arr.length; current++) {
			
			previous = current - 1;
			
			if(arr[previous] > arr[current]) {
				if(longEnough(first, previous, min)) {
					runs.add(subarray(first, previous, arr));
					splitArray(pointer, first, min, arr);
					pointer = current;
				}
				first = current;
			}
			
			if(current == arr.length - 1){
				if(longEnough(first, current, min)) {
					runs.add(subarray(first, current, arr));
					splitArray(pointer, first, min, arr);
					pointer = current;
				}else {
					splitArray(pointer, current + 1, min, arr);
				}
			}
			
		}
		
		merge(new ArrayList<int[]>());
		
		
	}
	
	public static void merge(ArrayList<int[]> temp) {
		
		
		if(runs.size() == 1) {
			return;
		}
		int previous;
		for(int current = 1; current < runs.size(); current = current + 2) {
			previous = current - 1;
			temp.add(merge(runs.get(current), runs.get(previous)));
		}
		
		if(runs.size() % 2 != 0) {
			temp.add(runs.get(runs.size() - 1));
		}
		
		runs = temp;
		
		merge(new ArrayList<int[]>());
		
	}
	
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
	
	public static boolean longEnough(int l, int h, int min) {
		return (h-l) + 1 >= min;
	}
	
	public static void splitArray(int start , int limit, int size, int[] arr) {
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
