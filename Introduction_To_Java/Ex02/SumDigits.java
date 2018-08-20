/* Assignment number : 2.2
File Name : SumDigits.java
Name (First Last) : Benjamin Arbibe
Student ID : 323795633
Email : benjamin.arbibe@post.idc.ac.il */

public class SumDigits {
  
  public static void main(String[] args) {
	
		int n = Integer.parseInt(args[0]) ;	
		int sum = 0;
		
        while (n != 0) {   							//Do this until n=0
                        sum = sum + (n % 10);		//Add last digit to the sum
                        n = n / 10;                 //Remove the last digit
               		   }   
                  System.out.println("Sum of digits: " + sum);
		}	
}      