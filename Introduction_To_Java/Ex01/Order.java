/* Assignment number : 1.0
File Name : Order.java
Name (First Last) : Benjamin Arbibe
Student ID : 323795633
Email : benjamin.arbibe@post.idc.ac.il */

public class Order {
	
	public static void main(String[] args) {

    // Enter the three numbers from the Command Argument to INT
    
    	int a = Integer.parseInt(args[0]) ;	
    	int b = Integer.parseInt(args[1]) ;		
    	int c = Integer.parseInt(args[2]) ;		

		int minimum = Math.min (Math.min (a,b), c); 		// Calculate the Min
	    
		int maximum = Math.max (Math.max (a,b), c); 		// Calculate the Max
	   		
		int median = (a + b + c) - (minimum + maximum);		// Calculate the Median
  
        System.out.println (minimum + " " + median + " " + maximum); 
        	
  	}

}		

