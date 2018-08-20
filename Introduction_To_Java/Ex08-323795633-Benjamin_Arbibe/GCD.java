/* Assignment number : 8.1
File Name : GCD.java
Name (First Last) : Benjamin Arbibe
Student ID : 323795633
Email : benjamin.arbibe@post.idc.ac.il */

public class GCD {

	public static void main(String[] args) {
		// Testing code for Positive integers.
		// Get two relevant inputs (positive integer) as command line arguments.
		int a = Integer.parseInt(args[0]);
		int b = Integer.parseInt(args[1]);
		System.out.println(gcd(a, b));

	}

	/**
	 * The greatest common divisor (gcd) of two non-negative integers (at least
	 * one of which is non-zero) is the largest positive integer that divides
	 * both integers with no remainder. We assume that both parameters are
	 * non-negative integers, and that at least one of them is non-zero. This is
	 * based on the Euclidean algorithm.
	 * 
	 * @param p
	 *            - First integer
	 * @param q
	 *            - Second integer
	 * @return - The Greatest Common Divisor as int
	 */
	public static int gcd(int p, int q) {
		if (q == 0) {
			return p;
		} else
			return gcd(q, p % q);
	}

}
