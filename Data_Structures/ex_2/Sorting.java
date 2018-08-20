import java.lang.reflect.Array;
import java.util.Random;

import Plotter.Plotter;


public class Sorting {

	final static int SELECTION_VS_QUICK_LENGTH = 12;
	final static int MERGE_VS_QUICK_LENGTH = 15;
	final static int MERGE_VS_QUICK_SORTED_LENGTH = 12;
	final static int SELECT_VS_MERGE_LENGTH = 16;
	final static double T = 600.0;

	// Will help for almost all the function

	private static void swap(double arr[] ,int i ,int j) {
		double temp=arr[i];
		arr[i]=arr[j];
		arr[j]=temp;
	}

	/**
	 * Sorts a given array using the quick sort algorithm.
	 * At each stage the pivot is chosen to be the leftmost element of the subarray.
	 * 
	 * Should run in average complexity of O(nlog(n)), and worst case complexity of O(n^2)
	 * 
	 * @param arr - the array to be sorted
	 */
	public static void quickSort(double[] arr){

		quickSort(arr, 0, arr.length-1);	

	}

	private static void quickSort(double arr[], int left, int right) {
		if( left < right)
		{
			int index = partition(arr, left, right);
			quickSort(arr, left, index - 1);
			quickSort(arr, index, right);
		}
	}

	private static int partition(double arr[], int left, int right) {
		double pivot = arr[right];
		int high = right;

		while (left < high) {
			while (left < high && arr[left] < pivot) {
				left++;
			}
			while (left < high && arr[high] >= pivot) {
				high--;
			}
			swap(arr,left, high);
		}
		swap(arr, left, right);
		return left;
	}

	/**
	 * Sorts a given array using the merge sort algorithm.
	 * 
	 * Should run in complexity O(nlog(n)) in the worst case.
	 * 
	 * @param arr - the array to be sorted
	 */

	public static void mergeSort(double[] arr){

		mergeSort(arr, 0, arr.length-1);

	}

	private static void mergeSort(double[] arr, int l, int r){

		if (l < r)
		{
			int m = (l+r)/2;

			mergeSort(arr, l, m);
			mergeSort(arr , m+1, r);

			merge(arr, l, m, r);
		}
	}
	// Using the pseudo-code that we saw in class
	// Merges two subarrays of arr[].
	// First subarray is arr[l..m]
	// Second subarray is arr[m+1..r]

	private static void merge(double[] arr, int l, int m, int r){

		int n1 = m - l + 1;
		int n2 = r - m;

		/* Create temp arrays */
		double L[] = new double [n1];
		double R[] = new double [n2];

		/*Copy data to temp arrays*/
		for (int i = 0; i < n1; i++)
			L[i] = arr[l+i];
		for (int j = 0; j<  n2; j++)
			R[j] = arr[m+1+j];

		/* Merge the temp arrays */
		int i = 0, j = 0;
		int k = l;
		while (i < n1 && j < n2)
		{
			if (L[i] <= R[j]){
				arr[k] = L[i];
				i++;
			}
			else{
				arr[k] = R[j];
				j++;
			}
			k++;
		}
		while (i < n1)
		{
			arr[k] = L[i];
			i++;
			k++;
		}
		while (j < n2)
		{
			arr[k] = R[j];
			j++;
			k++;
		}
	}



	/**
	 * finds the i'th order statistic of a given array.
	 * 
	 * Should run in complexity O(n) in the worst case.
	 * 
	 * @param arr - the array.
	 * @param i - a number between 0 and arr.length - 1.
	 * @return the number which would be at index i, if the array was to be sorted
	 */
	public static double select (double[] arr, int i){
		return select(arr, 0, arr.length - 1, i);
	}

	public static double select (double[] list, int left, int right, int n){

		if (left == right) {
			return list[left];
		}
		else {
			partition2(list,)
		}

	
	}

	public static int partition(double[] list, int left, int right, int pivotIndex){
		double pivotValue = list[pivotIndex];
		swap(list, pivotIndex, right);  // Move pivot to end
		int storeIndex = left;
		for (int i = left; i < right; i++) {
			if (list[i] < pivotValue) {
				swap(list, storeIndex, i);
				storeIndex++;
			}
		}
		swap(list, right, storeIndex);  // Move pivot to its final place
		return storeIndex;	
	}



	public static double insertionSort3(double array[]) {  
		int n = array.length;  
		for (int j = 1; j < n; j++) {  
			double key = array[j];  
			int i = j-1;  
			while ( (i > -1) && ( array [i] > key ) ) {  
				array [i+1] = array [i];  
				i--;  
			}  
			array[i+1] = key; 
		}
		if (array.length == 5){
			return array[3];
		}
		else return array[array.length/2];
	}  



	/**
	 * Sorts a given array using the selection sort algorithm.
	 * 
	 * Should run in complexity O(n^2) in the worst case.
	 * 
	 * @param arr - the array to be sorted
	 */
	public static void selectionSort(double[] arr){
		
		int length=(arr.length-1);
		while (length>0)
		{
			int maxi=0;
			for(int i=1;i<=length;i++){
				if (arr[i]>arr[maxi]){
					maxi=i;
				}
			}
			swap(arr,maxi,length); 
			length--; 
		}
		return;
	}



	public static void main(String[] args) {
		//selectionVsQuick();
		mergeVsQuick();
		//mergeVsQuickOnSortedArray();
		//selectVsMerge();
	}

	/**
	 * Compares the selection sort algorithm against quick sort on random arrays
	 */
	public static void selectionVsQuick(){
		double[] quickTimes = new double[SELECTION_VS_QUICK_LENGTH];
		double[] selectionTimes = new double[SELECTION_VS_QUICK_LENGTH];
		long startTime, endTime;
		Random r = new Random();
		for (int i = 0; i < SELECTION_VS_QUICK_LENGTH; i++) {
			long sumQuick = 0;
			long sumSelection = 0;
			for(int k = 0; k < T; k++){
				int size = (int)Math.pow(2, i);
				double[] a = new double[size];
				double[] b = new double[size];
				for (int j = 0; j < a.length; j++) {
					a[j] = r.nextGaussian() * 5000;
					b[j] = a[j];
				}
				startTime = System.currentTimeMillis();
				quickSort(a);
				endTime = System.currentTimeMillis();
				sumQuick += endTime - startTime;
				startTime = System.currentTimeMillis();
				selectionSort(b);
				endTime = System.currentTimeMillis();
				sumSelection += endTime - startTime;
			}
			quickTimes[i] = sumQuick/T;
			selectionTimes[i] = sumSelection/T;
		}
		Plotter.plot("quick sort", quickTimes, "selection sort", selectionTimes);
	}

	/**
	 * Compares the merge sort algorithm against quick sort on random arrays
	 */
	public static void mergeVsQuick(){
		double[] quickTimes = new double[MERGE_VS_QUICK_LENGTH];
		double[] mergeTimes = new double[MERGE_VS_QUICK_LENGTH];
		long startTime, endTime;
		Random r = new Random();
		for (int i = 0; i < MERGE_VS_QUICK_LENGTH; i++) {
			long sumQuick = 0;
			long sumMerge = 0;
			for (int k = 0; k < T; k++) {
				int size = (int)Math.pow(2, i);
				double[] a = new double[size];
				double[] b = new double[size];
				for (int j = 0; j < a.length; j++) {
					a[j] = r.nextGaussian() * 5000;
					b[j] = a[j];
				}
				startTime = System.currentTimeMillis();
				quickSort(a);
				endTime = System.currentTimeMillis();
				sumQuick += endTime - startTime;
				startTime = System.currentTimeMillis();
				mergeSort(b);
				endTime = System.currentTimeMillis();
				sumMerge += endTime - startTime;
			}
			quickTimes[i] = sumQuick/T;
			mergeTimes[i] = sumMerge/T;
		}
		Plotter.plot("quick sort", quickTimes, "merge sort", mergeTimes);
	}

	/**
	 * Compares the merge sort algorithm against quick sort on pre-sorted arrays
	 */
	public static void mergeVsQuickOnSortedArray(){
		double[] quickTimes = new double[MERGE_VS_QUICK_SORTED_LENGTH];
		double[] mergeTimes = new double[MERGE_VS_QUICK_SORTED_LENGTH];
		long startTime, endTime;
		Random r = new Random();
		for (int i = 0; i < MERGE_VS_QUICK_SORTED_LENGTH; i++) {
			long sumQuick = 0;
			long sumMerge = 0;
			for (int k = 0; k < T; k++) {
				int size = (int)Math.pow(2, i);
				double[] a = new double[size];
				double[] b = new double[size];
				for (int j = 0; j < a.length; j++) {
					a[j] = j;
					b[j] = j;
				}
				startTime = System.currentTimeMillis();
				quickSort(a);
				endTime = System.currentTimeMillis();
				sumQuick += endTime - startTime;
				startTime = System.currentTimeMillis();
				mergeSort(b);
				endTime = System.currentTimeMillis();
				sumMerge += endTime - startTime;
			}
			quickTimes[i] = sumQuick/T;
			mergeTimes[i] = sumMerge/T;
		}
		Plotter.plot("quick sort on sorted array", quickTimes, "merge sort on sorted array", mergeTimes);
	}

	/**
	 * Compares the select algorithm against sorting an array.
	 */
	public static void selectVsMerge(){
		double[] mergeTimes = new double[MERGE_VS_QUICK_LENGTH];
		double[] selectTimes = new double[MERGE_VS_QUICK_LENGTH];
		long startTime, endTime;
		double x;
		Random r = new Random();
		for (int i = 0; i < MERGE_VS_QUICK_LENGTH; i++) {
			long sumMerge = 0;
			long sumSelect = 0;
			for (int k = 0; k < T; k++) {
				int size = (int)Math.pow(2, i);
				double[] a = new double[size];
				double[] b = new double[size];
				for (int j = 0; j < a.length; j++) {
					a[j] = r.nextGaussian() * 5000;
					b[j] = a[j];
				}
				int index = (int)(Math.random() * size);
				startTime = System.currentTimeMillis();
				mergeSort(a);
				x = a[index];
				endTime = System.currentTimeMillis();
				sumMerge += endTime - startTime;
				startTime = System.currentTimeMillis();
				x = select(b, index);
				endTime = System.currentTimeMillis();
				sumSelect += endTime - startTime;
			}
			mergeTimes[i] = sumMerge/T;
			selectTimes[i] = sumSelect/T;
		}
		Plotter.plot("merge sort and select", mergeTimes, "select", selectTimes);
	}
}
