public class Bits {

	/**
	 * Given an 8-byte long composed of the bytes B_1, B_2, ... , B_8, return the long with byte order reversed:
	 * B_8, B_7, ..., B_1
	 * The implementation of this method shouldn't use any function calls.
	 * @param a the number to reverse
	 * @return
	 */

	public static long byteReverse(long a) {

		a = ((a & 0xffffffff00000000L) >> 32) | 
				((a & 0x00000000ffffffffL) << 32); // Step 1
		a = ((a & 0xffff0000ffff0000L) >> 16) | 
				((a & 0x0000ffff0000ffffL) << 16); // Step 2
		a = ((a & 0xff00ff00ff00ff00L) >> 8)  | 
				((a & 0x00ff00ff00ff00ffL) << 8); // Step 3

		return a;

		/*	     
		 * Explaining Algorithm using idea from http://aggregate.org/MAGIC/#Bit%20Reversal
		 * Starting: B1 B2 B3 B4 B5 B6 B7 B8 
		 * Step 1  : B5 B6 B7 B8 B1 B2 B3 B4
		 * Step 2  : B7 B8 B5 B6 B3 B4 B1 B2
		 * Step 3  : B8 B7 B6 B5 B4 B3 B2 B1
		 */

	}

	/**
	 * Given a 32-bit integer composed of 32 bits: b_31,b_30,...b_1,b_0,  return the integer whose bit representation is
	 * b_{31-n},b_{30-n},...,b_1,b_0,b_31,...,b_{31-n+1}. 
	 * The implementation of this method shouldn't use any control structures (if-then, loops) or function calls.
	 * @param a the integer that we are rotating left (ROLing)
	 * @param n the number of bits to rotate.
	 * @return the ROL of a
	 */

	public static int rol(int a, int n) {

		return (a << n) | (a >>> (32 - n));

		/*
		 * Using the "OR" logic. 
		 * Rolling it "N" times left. But not all the number can go left, so I will take a "32-N" time rolling right.
		 * Using >>> to add Zero Padding - not loosing the SignBit. And of course, "OR" between the two OP.
		 */

	}

	/**
	 * Given two 32-bit integers a_31,...,a_0 and b_31,...,b_0, return the 64-bit long that contains their bits interleaved:
	 * a_31,b_31,a_30,b_30,...,a_0,b_0.
	 * The implementation of this method shouldn't use any function calls.
	 * @param a
	 * @param b
	 * @return
	 */
	public static long interleave(int a, int b) {
		
		long result = 0;
		
		int spaceCounter = 32; 	// Creating a counter to count how much time to "shift" left.
		int imask = 1;
		imask = imask << 31; 	// Creating the mask for INT 100....0
		
		for (int i = 1; i <= 32; i++) { // For every bit in INT
			
			long temp = 0L;	
			temp = imask & a;
			temp <<= spaceCounter--; // Counting the space to shift
			result |= temp;		// ORing the result
			
			temp = 0L;
			temp = imask & b;
			temp <<= spaceCounter;
			result |= temp;		// ORing the result
			
			imask >>>= 1;  		// Using the Next mask (01000... 00100.... etc.)
		}
		
		return result;
	}

	/**
	 * Pack several values into a compressed 32-bit representation. 
	 * The packed representation should contain
	 * <table>
	 * <tr><th>bits</th>	<th>value</th></tr>
	 * <tr><td>31</td>		<td>1 if b1 is true, 0 otherwise</td></tr>
	 * <tr><td>30-23</td>	<td>the value of the byte a</td></tr>
	 * <tr><td>22</td>		<td>1 if b2 is true, 0 otherwise</td></tr>
	 * <tr><td>21-6</td>	<td>the value of the char c</td></tr>
	 * <tr><td>5-0</td>		<td>the constant binary value 101101</td></tr>
	 * </table>
	 * The implementation of this method shouldn't use any control structures (if-then, loops) or function calls
	 * (you may use the conditional operator "?:").
	 * @param a
	 * @param b1
	 * @param b2
	 * @param c
	 * @return
	 */
	public static int packStruct(byte a, boolean b1, boolean b2, char c) {
		// TODO: implement
		int  result;
		
		result = (b1 ? 0b1 : 0b0);	// First Step - checking 'b1'
		
		result <<= 8; 			// Creating the place for byte a
		
		result |= ((int)a & 0xff);	   
								// "OR"ing the result to get byte a in place, after solving the Type Casting
		
		result <<= 1; 			// Creating the place for bit 'b2'
		
		result |= (b2 ? 1 : 0);	// If 'b2' is true, then "OR"ing with 1 the zero that we make place for
		
		result <<= 16; 			// Creating the place for char c
		
		result |= ((int)c & 0xffff);			
								// "OR"ing the result to get the char value in place, after solving the Type Casting
		
		result <<= 6; 			// Making place for "101101"
		
		result |= 0b101101;		// "OR"ing the binary "101101" to get the result
		
		return result;
	}

	/**
	 * Given a packed struct (with the same format as {@link #packStruct(byte, boolean, boolean, char)}, update
	 * its byte value (bits 23-30) to the new value a.
	 * The implementation of this method shouldn't use any control structures (if-then, loops) or function calls.
	 * @param struct
	 * @param a
	 * @return
	 */
	public static int updateStruct(int struct, byte a) {

		int mask = 0x807FFFFF; 	// Creating Mask  0b10000000011111111111111111111111;
		
		struct &= mask;			// Removing the byte part
		
		int add = (((int)a & 0xff) << 23); // Moving it to the right place
		
		struct |= add;			// "OR"ing it in the right place
		
		return struct;
	}
}
