/* Assignment number : 1.0
File Name : RandomDistance.java
Name (First Last) : Benjamin Arbibe
Student ID : 323795633
Email : benjamin.arbibe@post.idc.ac.il */


public class RandomDistance {

	public static void main(String[] args) {
		
	// Enter the two numbers from the Command Argument to INT

		    int a = Integer.parseInt(args[0]) ;	 
		    int b = Integer.parseInt(args[1]) ;
	
	// Create the Randoms number x1, x2, y1, y2 with b and a to start from
	
			int x1 = a + (int)(Math.random() * ((b - a) + 1)) ; 
			int x2 = a + (int)(Math.random() * ((b - a) + 1)) ;
			int y1 = a + (int)(Math.random() * ((b - a) + 1)) ;
			int y2 = a + (int)(Math.random() * ((b - a) + 1)) ;
			
	// Calculate the Distance between the X's and Y's

			int dx = x1 - x2 ;
			int dy = y1 - y2 ;

	// Square root of the 2 distance's square

       		double c = Math.sqrt((dx*dx)+(dy*dy));

	    System.out.println ("The distance between (" + x1 + ", " + y1 + ") and (" + x2 + 
	    ", " + y2 + ") is " +c ); 

	}

}
