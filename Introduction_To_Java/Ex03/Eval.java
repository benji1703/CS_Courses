/* Assignment number : 3.3
File Name : Eval.java
Name (First Last) : Benjamin Arbibe
Student ID : 323795633
Email : benjamin.arbibe@post.idc.ac.il */

public class Eval {   	
	
	public static void main(String[] args)  {
		
		Parser.init(args[0]);
		int resultLine = Parser.nextInt();

		// Assumes that the input consists of a sequence of (char,n) pairs,
		// where n is 1 or more digits.
		
		while (Parser.hasMoreChars()) {  			// Enter the loop until there is no more Chars	
			
			char a = Parser.nextChar();				// Getting the Operator
			int count = Parser.nextInt();			// Getting the Operand

			if (a == '+') {							// If the operator is +  do
				resultLine = resultLine + count;	// Counting the number with the right operator
			}
			
	
			else if (a == '-') {					// If the operator is - do
				resultLine = resultLine - count;	// Counting the number with the right operator
			}			

		}		
	
	System.out.println(args[0] + " = " + resultLine); // Printing the result
	
	}
}