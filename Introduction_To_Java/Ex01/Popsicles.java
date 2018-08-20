/* Assignment number : 1.0
File Name : Popsicles.java
Name (First Last) : Benjamin Arbibe
Student ID : 323795633
Email : benjamin.arbibe@post.idc.ac.il */

public class Popsicles {
    
        public static void main(String[] args) {
        
				String name = new String(args[0]); 	    
        	
        		int a = Integer.parseInt(args[1]);		//Enterring the Number to int
            
                double b = Double.parseDouble("2.5");   //Creating the Price
			
				double res = a * b;						//Multiply Price and Number
         
                System.out.println (name +", " + a + " popsicles will cost you "  + res
                 + " Shekels. Bon appetit.");
                
        }

}
