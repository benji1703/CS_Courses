/* Assignment number : 2.4
File Name : BinAdd.java
Name (First Last) : Benjamin Arbibe
Student ID : 323795633
Email : benjamin.arbibe@post.idc.ac.il */

public class BinAdd {
  
	public static void main(String[] args) {
	
		String x = args[0];
		String y = args[1];
		String totalcount = "";
		int xlenght = x.length();
		int ylenght = y.length();
		
		// Instructor comment: Local variable declarations should be on separate lines
		// Grade: 0.0
		int carry = 0, result = 0, total = 0;
	
															// Checking first that the Lenght are OK
		while (xlenght != ylenght) {					
		
			int maxi = Math.max (ylenght, xlenght);
			int mini = Math.min (ylenght, xlenght);	
			int diff = maxi - mini;							// Calculate the difference between the two

				if (xlenght < ylenght) {					// Adding to the smallest a right number of '0'
					for (int j = 0; j < diff ; j++){ 
    					x = "0" + x;
    					}
    			xlenght = x.length();
    			}
    			
				else if (xlenght > ylenght) {				// Adding to the smallest a right number of '0'
					for (int j = 0; j < diff ; j++){
    					y = "0" + y;
    					}
    			ylenght = y.length();
    			}	
		}
		
		// Instructor comment: theres really no need for this if - could there be a chance
		// that after the padding of the smallest number with 0s at the begining
		// the length will not be the same for both numbers?
		// and what happens if its not? you did not output any thing to indicate
		// something wrong happened. in short, you could have done without it and
		// nothing would have changed
		// Grade: -3.0
		if (xlenght == ylenght) {							// Doing this only after the lenght are ok and Checked
			
			int tocheck = xlenght - 1;	

			for (int i = 0; i < xlenght; i++) { 
			
				int charx = Character.getNumericValue(x.charAt(tocheck));
				int chary = Character.getNumericValue(y.charAt(tocheck));
				if (charx > 1 || chary > 1) {				// Checking if an illegal imput got in - if yes - print and finish
								System.out.println("illegal input");
								return;
								}
					
				 
				total = charx + chary + carry;
				
			switch (total) {								// Using a switch table for cases - easy to see the option

            	case 0:  result = 0;
            			 carry = 0;
            			 break;
            	case 1:  result = 1;
            			 carry = 0;
            			 break;
           		case 2:  result = 0;
            			 carry = 1;
            			 break;   
            	case 3:  result = 1;
            			 carry = 1;
            			 break;   
				default:									// Verify another time that no error are made
				System.out.println("illegal input");
				return;
                     }	
                     
                totalcount = result + totalcount; 
                // Instructor comment: bad variable name
				tocheck = tocheck - 1;
						
				}

			if (carry == 1) {								// Printing the result if the Carry is bigger than 0
				// Instructor Comment: Rows should not be longer than 80 characters long. (including tabs and spaces).
				// Grade: 0.0
			System.out.println("" + args[0] + " + " + args[1] + " = " +  + carry + totalcount); }
			
			else {											// Printing the result if no need to print Carry
			System.out.println("" + args[0] + " + " + args[1] + " = " + totalcount); 
			}			
		}				
	
	}
	 
}