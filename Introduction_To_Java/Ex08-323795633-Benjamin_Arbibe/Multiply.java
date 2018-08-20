/* Assignment number : 8.0
File Name : Multiply.java
Name (First Last) : Benjamin Arbibe
Student ID : 323795633
Email : benjamin.arbibe@post.idc.ac.il */

public class Multiply {

	public static void main(String[] args) {

		// Get two relevant inputs (positive integer) as command line arguments.
		int a = Integer.parseInt(args[0]);
		int b = Integer.parseInt(args[1]);

		// Multiply the positive integers.
		System.out.println(multiply(a, b));

	}

	/**
	 * The function use recursion and returns the product of two integers. We
	 * assume that both of them are non-negative. This function computes the max
	 * of the two integers and then send it to the auxiliary function that
	 * computes the multiplication of the two numbers to do it more efficiently.
	 * 
	 * @param a
	 *            - the first Integer
	 * @param b
	 *            - the second Integer
	 * @return the multiply of the two number
	 */
	public static int multiply(int a, int b) {

		int max = Math.max(a, b);
		int min = Math.min(a, b);

		return multiplyAuxiliary(min, max);

	}

	/**
	 * The function use recursion and returns the product of two integers. We
	 * assume that both of them are non-negative.
	 * 
	 * @param a
	 *            - the first Integer
	 * @param b
	 *            - the second Integer
	 * @return - the multiplication of 'a' by 'b'. The product
	 */
	private static int multiplyAuxiliary(int a, int b) {

		// Base case to stop the recursion. Added the B to stop it before.
		if (a == 0 || b == 0) {
			return 0;
		} else
			return (multiply(a - 1, b) + b);
	}

}
