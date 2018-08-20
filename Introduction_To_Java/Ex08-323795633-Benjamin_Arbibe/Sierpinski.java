/* Assignment number : 8.3
File Name : Sierpinski.java
Name (First Last) : Benjamin Arbibe
Student ID : 323795633
Email : benjamin.arbibe@post.idc.ac.il */

public class Sierpinski {

	public static void main(String[] args) {
		// Number of recursion from command line
		int n = Integer.parseInt(args[0]);
		// As said at the piazza "A triangle subdivided into 4 smaller ones."
		// (if n is one.)
		sierpinski(n);
	}

	/**
	 * The function draw an equilateral triangle, then subdivide it into four
	 * smaller congruent equilateral triangles. Then, the function repeat the
	 * second step with all the triangles except for the middle one.
	 * 
	 * @param n
	 *            - the number of the recursion of the Sierpinski "steps"
	 */
	public static void sierpinski(int n) {
		sierpinski(n, 0, 0, 1);
	}


	private static void sierpinski(int n, double x, double y, double triSize) {

		// Base case of the the recursion
		if (n == -1)
			return;

		// Find the 3 point of Sierpinski triangle, using the formula.
		double x1 = x;
		double y1 = y;
		double x2 = x1 + triSize;
		double y2 = y1;
		double x3 = x1 + triSize / 2;
		double y3 = y1 + (Math.sqrt(3)) * triSize / 2;

		// Drawing the triangle using StdDraw
		StdDraw.line(x1, y1, x2, y2);
		StdDraw.line(x1, y1, x3, y3);
		StdDraw.line(x2, y2, x3, y3);

		// Recursion "drill down"
		sierpinski(n - 1, x1, y1, triSize / 2);
		sierpinski(n - 1, (x1 + x2) / 2, (y1 + y2) / 2, triSize / 2);
		sierpinski(n - 1, (x1 + x3) / 2, (y1 + y3) / 2, triSize / 2);
	}

}
