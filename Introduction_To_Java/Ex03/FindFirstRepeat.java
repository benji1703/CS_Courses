/* Assignment number : 3.5
File Name : FindFirstRepeat.java
Name (First Last) : Benjamin Arbibe
Student ID : 323795633
Email : benjamin.arbibe@post.idc.ac.il */

public class FindFirstRepeat {   	
	
	public static void main(String[] args)  {
		
        int[] anArray; 								 // Creating an array
        int i = 0, j = 0;
        int firstRepeating = 0;

        boolean repeat = false;						// Creating a boolean - if repeat

        anArray = new int[args.length];  			// Making the array int with args.length length

		for (i = 0; i < args.length; i++) {			// Putting the number in the correct arra		
        	anArray[i] = Integer.parseInt(args[i]);
		}
				
		for (i = 0; i < args.length && repeat == false; i++) {  // This loop is to verify the first against every other different number
		
			for (j = 0; j < args.length && repeat == false; j++) { // This is for moving the number to check against

				if (i != j) { 						// To not verify the number with itself
			
					if (anArray[i] == anArray[j]) { // If this is the same number, 	
					repeat = true;					// Change the boolean to true,
					firstRepeating = anArray[i]; 	// Remember the number,
					break;							// And break the loop.
					
					}					
				}				
			}			
		}
		
	
		if (repeat == true) { 					// If there is a repeating element print
		System.out.println("First repeating element is " + firstRepeating);
		}
	
		else if (repeat == false) { 			// If there is no repeating element print
		System.out.println("No repeating elements");
		}
	}
}