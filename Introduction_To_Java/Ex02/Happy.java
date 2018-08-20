/* Assignment number : 2.3
File Name : Happy.java
Name (First Last) : Benjamin Arbibe
Student ID : 323795633
Email : benjamin.arbibe@post.idc.ac.il */

public class Happy {
  
	public static void main(String[] args) {
	
	String number = args[0];
	boolean isHappy = false;								// Creating a boolean as false
	String alltheresult = number;
	int sqr = 0;

		
		for (int i = 1; ((i <= 100) && (!isHappy)); i++) {    // Creating a loop for 100 time, checking the number if Happy
		
			int numberle = number.length();					// Counting how many time to do the next loop
			int sum = 0;
			
			for (int j = 0; j < numberle; j++) { 			// Take every digit and make the square of it
				
				int digit = Integer.parseInt ("" + number.charAt(j));
				sqr = (digit * digit);
				sum = sum + sqr;
				
				
				if ((sum == 1) && (j == numberle - 1)) {
				isHappy = true;
				break;
				}
			}
			
			number = "" + sum;
			alltheresult = alltheresult + " " + number;     // Printing a list of all the numbers
		}
															// Now checking the boolean and print it if true or false
			if (!isHappy) {
				System.out.println(args[0]+ " is unhappy :(");
				}
		
			else {
				System.out.println(args[0]+ " is happy as follows:");
				System.out.println(alltheresult) ;
				}	
			 }
		
}

	


