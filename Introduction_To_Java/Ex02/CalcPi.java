/* Assignment number : 2.5
File Name : CalcPi.java
Name (First Last) : Benjamin Arbibe
Student ID : 323795633
Email : benjamin.arbibe@post.idc.ac.il */

/* The idea behind this program is the Monte Carlo theory, and using the Pytagoras theory.
This program pick points at random inside a given square. It checks to see if the point is 
inside the circle (if x^2 + y^2 < r^2 - Pytagoras) - we need to multiply it by four at the
end, because it's only the quarter of the circle (minus are lost in the square process) */ 

 public class CalcPi {
 
	public static void main(String[] args) {

		double n = Double.parseDouble(args[0]) ;
		double randomx, randomy, sqrx, sqry;	
    	double sum = 0.0;
          
			for (int i = 0 ; i <= n ; i++ ) {
        
        		randomx = 2.0 * Math.random() - 1.0;
        		randomy = 2.0 * Math.random() - 1.0;
        
        		sqrx = randomx * randomx;
        		sqry = randomy * randomy;
        
       			double pyta = sqrx + sqry;			// Pytagoras Theory - checking the "r" of the formula 
        
        			if  (pyta < 1) {  				// Counting how many point are in the circle
        			sum++; }
        	
       		 }
     
           System.out.println(4*(sum/n));       	// Only the (+),(+) got in, so this is only a quarter
    }

 }