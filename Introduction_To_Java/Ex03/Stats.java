/* Assignment number : 3.4
File Name : Stats.java
Name (First Last) : Benjamin Arbibe
Student ID : 323795633
Email : benjamin.arbibe@post.idc.ac.il */

public class Stats {   	
	
	public static void main(String[] args)  {
		
        int[] anArray;  				// Creating an array
        double sum = 0;					// Creating the int for sum
        double devsum = 0;				// Creating the int for the deviation count
        
        anArray = new int[args.length]; // Making the array int with args.length length

		for (int i = 0; i < args.length; i++) {			// Create the loop for counting the sum
		
        	anArray[i] = Integer.parseInt(args[i]);		// Putting the number in the correct array
   		 	sum = Double.parseDouble(args[i]) + sum;

		}
		
		double average = sum / args.length;				// Create the double and calculate the average
		
		for (int i = 0; i < args.length; i++) { 		// Create the loop to count the difference between every number
			
        	devsum = devsum + Math.abs(anArray[i] - average);
        	
        	} 
        	
		double deviation = devsum / args.length;		// Create the double and calculate the absolute deviation	 
		
		System.out.println("The average is " + average + 
		". The absolute deviation is " + deviation + "."); 					// Printing

	}
}
