

public class Temp {

	public static void main(String[] args) {

		double[] inputArr = {45,23,11,89,77,98,4,28,65,43};

		Sorting.mergeSort(inputArr);

		for (int i = 0; i < inputArr.length; i++) {
			System.out.println(inputArr[i]);
		}
		
		double[] inputArr2 = {45,23,11,89,77,98,4,28,65,43};

		Sorting.quickSort(inputArr2);

		for (int i = 0; i < inputArr2.length; i++) {
			System.out.println(inputArr2[i]);
		}
	}

}
