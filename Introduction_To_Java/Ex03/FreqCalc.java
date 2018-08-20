/* Assignment number : 3.6
File Name : FreqCalc.java
Name (First Last) : Benjamin Arbibe
Student ID : 323795633
Email : benjamin.arbibe@post.idc.ac.il */

public class FreqCalc {   	
	
	public static void main(String[] args)  {
		
        int[] anArray;
		String story = args[0];
		anArray = countingArray(story);					// Putting the result of the function in anArray

			
			for (int i = 0; i < 26; i ++) { 			// Enter to a printing loop with the results
			
			System.out.print((char) (97 + i) + ":");   // Printing the char in small letlers
			
				for (int j=0; j < anArray[i]; j++) {  	// Creating a loop to print the right number
				System.out.print("*");					// of * in every array
				}
				
			System.out.println();						// Adding a line break - to sort the letters
			}
		}
				
	
	/** Returns an array of int with the number of time that the letter appear.
	 *  Counting only the small and big letter.
	 *  Using the ASCII table to sort the chart into "categories". */

	public static int [] countingArray(String story){  // Creating the function
		
			char temp;
			int[] funcArray;
			funcArray = new int[26];
		
		for (int i = 0; i < story.length(); i++) {
			
			temp = story.charAt(i);

			if (temp >= 97 && temp <= 122) {    //Checking for all Small Character
				funcArray[temp - 97]++;			//Reducing 97 to put one more * it in the right array
			}
			
			if (temp >= 65 && temp <= 90) {		//Checking for Big Character
				funcArray[temp - 65]++;			//Reducing 65 to put one more * it in the righ
			}
		}
		return funcArray;
	}

}