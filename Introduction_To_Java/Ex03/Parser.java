/* Assignment number : 3.1
File Name : Parser.java
Name (First Last) : Benjamin Arbibe
Student ID : 323795633
Email : benjamin.arbibe@post.idc.ac.il */

public class Parser {    
    static String str;   // the input text
	static int N;        // input size
	static int current;  // current position in the text
	
	/** Initializes the given string for parsing. */
	public static void init(String s) {
		str = s;
		N = str.length();
		current = 0;
	}
	
	/** Returns true if there are more characters to process, false otherwise. */
	public static boolean hasMoreChars() {
		return (current < N) ? true : false;
	}

	/** Returns the next character in the text.
	/*  Should be called only if hasMoreChars() is true. */
	// Side effect: advances current just beyond the character.
	public static char nextChar() {
		char currenttemp = str.charAt(current);
		current++;
		return currenttemp;
	}
	
	/** Returns the next integer in the text.
	 *  Should be called only if hasMoreChars() is true.
	 *  It is assumed that this function is called only if the caller knows that
	 *  the next char in the string is the beginning of an integer. */
	// Side effect: advances current just beyond the integer.
	public static int nextInt() {
		 String tempResult = "";
		 int resultOfInt = 0;
		 while (hasMoreChars() && str.charAt(current) >= 48 && 
		 	str.charAt(current) <= 57) {
		 		tempResult += str.charAt(current);
		 		current++;
		 }
		 resultOfInt = Integer.parseInt(tempResult);
		 return resultOfInt;
	}
}