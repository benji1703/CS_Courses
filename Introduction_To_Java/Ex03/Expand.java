/* Assignment number : 3.2
File Name : Expand.java
Name (First Last) : Benjamin Arbibe
Student ID : 323795633
Email : benjamin.arbibe@post.idc.ac.il */

public class Expand {   	
	
	public static void main(String[] args)  {
		
		Parser.init(args[0]); 						// Using the parser function init
		String resultLine = "";
		// Assumes that the input consists of a sequence of (char,n) pairs,
		// where n is 1 or more digits.
		
		while (Parser.hasMoreChars()) {				// Enter the loop until there is no more Chars	
			char a = Parser.nextChar();				// Getting the letter Char to a
			int count = Parser.nextInt();			// Getting the number of integer Char			
			for (int i = 1; i <= count; i++) {   	// Creating a loop for N time, the number of Char			
				resultLine = resultLine + a;		// Making a string with the results
				}	
		}			
	System.out.println(resultLine);
	}
}