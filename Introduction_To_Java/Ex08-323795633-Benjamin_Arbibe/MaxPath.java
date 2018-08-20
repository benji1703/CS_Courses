/* Assignment number : 8.2
File Name : MaxPath.java
Name (First Last) : Benjamin Arbibe
Student ID : 323795633
Email : benjamin.arbibe@post.idc.ac.il */

public class MaxPath {

	public static void main(String[] args) {

		// Testing code
		// The function must get the argument like this:
		// First is the number of row, then the number of column, then all the
		// number of the matrix by the command line arguments.
		// We fill the matrix like this: first all the number from left to right
		// from the first row, then all the number from the second line from
		// left to right etc.

		// Number of row
		int n = Integer.parseInt(args[0]);
		// Number of column
		int m = Integer.parseInt(args[1]);

		int[][] givenMatrix = new int[n][m];

		int argPlace = 2;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				givenMatrix[i][j] = Integer.parseInt(args[argPlace]);
				argPlace++;
			}
		}

		// Using the non effective version of maxPath
		System.out.println(maxPath(givenMatrix));

		// Using the effective version of maxPath, using the memo
		System.out.println(effMaxPath(givenMatrix));

	}

	/**
	 * The function computes the cost of the maximal path in the given array.
	 * The function use a private method (maxPath) that make the math.
	 * 
	 * @param arr
	 *            - the given array
	 * @return the "maximal cost path"
	 */
	public static int maxPath(int[][] arr) {
		return maxPath(arr, 0, 0);
	}

	private static int maxPath(int[][] arr, int i, int j) {
		// The function is the auxiliary function that does the math for the
		// public method. It computes the cost of the maximal path in the given
		// array. The function take a position in the array and drill down to
		// every possible option to find the "Maximum cost path". We say that a
		// path is the maximal if it has the largest cost among all possible
		// paths.
		// Base case
		if ((i == (arr.length - 1)) && (j == (arr[0].length - 1))) {
			return arr[i][j];
		}
		// Impossible to come from the right if we're already at the right
		else if (i == arr.length - 1) {
			return arr[i][j] + maxPath(arr, i, j + 1);
		}
		// Impossible to come from the floor if we're already at the floor
		else if (j == arr[0].length - 1) {
			return arr[i][j] + maxPath(arr, i + 1, j);
		}
		// Take whichever gives you more and return..
		else {
			return arr[i][j]
					+ Math.max(maxPath(arr, i + 1, j), maxPath(arr, i, j + 1));
		}
	}

	/**
	 * This function is the main function for the effMaxPath. It get as an input
	 * only an array and compute with memoisation array (using a private
	 * function as an auxiliary that computes the max path cost) more
	 * efficiently.
	 * 
	 * @param arr
	 *            - the given array
	 * @return the "maximal cost path"
	 */
	public static int effMaxPath(int[][] arr) {
		return effMaxPath(arr, 0, 0, createMemo(arr));
	}

	private static int[][] createMemo(int[][] arr) {
		// This function create the memo int 2d array. The function is using a
		// function (fillWithMinus) to fill the array with minus 1.
		int memo[][] = new int[arr.length][arr[0].length];
		fillWithMinus(memo, 0, 0);
		// The memo is now filled with -1
		return memo;
	}

	private static void fillWithMinus(int[][] array, int row, int col) {
		// This function use only recursion to fill all the 2d array with only
		// -1.
		// This is extremely helpful for the memoisation process.
		// row and col has to be 0 when called
		if (row < array.length) {
			if (col < array[row].length) {
				array[row][col] = 0;
				fillWithMinus(array, row, col + 1);
			} else {
				fillWithMinus(array, row + 1, 0);
			}
		}
	}

	private static int effMaxPath(int[][] arr, int i, int j, int[][] memo) {

		// This is the auxiliary function that does the math behind the
		// effective version of the max path. The fuction take an array, and
		// using a "memo" 2d array the function (using memoisation) computes
		// efficiently the cost of the maximal path in the given array.

		// If not filled with -1, the number was already computed, so we dont
		// need to compute it again.
		if (memo[i][j] != 0)
			return memo[i][j];

		int down = 0;
		int right = 0;

		// Recursion
		if (i != (arr.length - 1))
			down = effMaxPath(arr, i + 1, j, memo);

		if (j != (arr[0].length - 1))
			right = effMaxPath(arr, i, j + 1, memo);

		// Commputing the memo, by using the max of down or right.
		return memo[i][j] = (arr[i][j] + Math.max(down, right));
	}

}