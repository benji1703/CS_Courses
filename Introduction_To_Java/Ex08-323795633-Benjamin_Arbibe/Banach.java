/* Assignment number : 8.4
File Name : Banach.java
Name (First Last) : Benjamin Arbibe
Student ID : 323795633
Email : benjamin.arbibe@post.idc.ac.il */

public class Banach {

	public static void main(String[] args) {
		// Number of recursion from command line
		int n = Integer.parseInt(args[0]);
		// As said at the piazza "A triangle subdivided into 4 smaller ones."
		// (if n is one.) - so here we get one circle if n is zero etc.
		banach(n);
	}

	/**
	 * The function draw a circle, then we draw 9 new circles scaled down in
	 * size by a factor of 3. One of these circles will have the same center as
	 * the original circle. The centers of the remaining 8 circles need to be
	 * equally spaced along the circumference of the original circle. The
	 * function repeat the second step with each of the new circles.
	 * 
	 * @param n
	 *            - number of recursion
	 */
	public static void banach(int n) {
		banach(n, 0.5, 0.5, 0.3);
	}

	private static void banach(int n, double x, double y, double circleSize) {

		// Base case of the the recursion
		if (n == -1)
			return;

		// Find the point of Banach circle, using the formula.
		double x1 = x;
		double y1 = y;
		double x2 = (x + circleSize * Math.cos(Math.PI * 0.25));
		double y2 = (y + circleSize * Math.sin(Math.PI * 0.25));
		double x3 = (x + circleSize * Math.cos(Math.PI * 0.75));
		double y3 = (y + circleSize * Math.sin(Math.PI * 0.75));
		double x4 = (x + circleSize * Math.cos(Math.PI * 1.25));
		double y4 = (y + circleSize * Math.sin(Math.PI * 1.25));
		double x5 = (x + circleSize * Math.cos(Math.PI * 1.75));
		double y5 = (y + circleSize * Math.sin(Math.PI * 1.75));

		// Drawing the circle using StdDraw.circle
		StdDraw.circle(x, y, circleSize);

		// Recursion "drill down"
		banach(n - 1, x1, y1, circleSize / 3);
		banach(n - 1, x1, y1 - circleSize, circleSize / 3);
		banach(n - 1, x1, y1 + circleSize, circleSize / 3);
		banach(n - 1, x1 - circleSize, y1, circleSize / 3);
		banach(n - 1, x1 + circleSize, y1, circleSize / 3);
		banach(n - 1, x2, y2, circleSize / 3);
		banach(n - 1, x3, y3, circleSize / 3);
		banach(n - 1, x4, y4, circleSize / 3);
		banach(n - 1, x5, y5, circleSize / 3);

	}
}
