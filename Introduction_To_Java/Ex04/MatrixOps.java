/* Assignment number : 4.0
File Name : MatrixOps.java
Name (First Last) : Benjamin Arbibe
Student ID : 323795633
Email : benjamin.arbibe@post.idc.ac.il */

public class MatrixOps {
	
	public static void main(String args[]) {
		
		
		// Creates two random matrices, A and B for testing
		
		int[][] a = random(4, 4, 9);
		int[][] b = random(4, 4, 9);

		// Printing the two matrix
		
		System.out.println("Printing the matrix A \n"); 
		print(a);          System.out.println();
		System.out.println("Printing the matrix B \n"); 
		print(b);          System.out.println();
		
		//Printing the sum of the two random matrixes
		
		System.out.println("Printing the sum of the matrix A+B \n"); 
		print(add(a,b));  	System.out.println();
		
		//Printing the product of the two random matrixes

		System.out.println("Printing the product of the matrix AxB \n"); 
		print(mult(a,b));  	System.out.println();
				
		//Printing a new matrix - C = A * (A + B)
		
		int[][] c = mult(a, add(a,b));
		System.out.println("Creating a new matrix C = A * (A + B) \n"); 
		print(c);          	System.out.println();
		
		//Creating a new random matrix - D (5x5)

		int[][] d = random(5, 5, 9);
		System.out.println("Creating a new matrix D (5x5) \n"); 
		print(d);			System.out.println();

		//Creating an identity matrix 5x5
		
		int[][] identity5 = identity(5);
		System.out.println("Creating a identity matrix I (5x5) \n"); 
		print(identity5);			System.out.println();

		//Multiply D x I

		int[][] dxidentity5 = mult(d, identity5);
		System.out.println("The product of D x I \n"); 
		print(dxidentity5);  System.out.println();
		
		
		//Checking if D x I = D
		
		if (equals(dxidentity5, d)){
			System.out.println("This is an equal matrix   D x I = D \n");
		}

		//Creating a new random matrix - E (6x6)

		int[][] e = random(6, 6, 9);
		System.out.println("Creating a new matrix E (6x6) \n"); 
		print(e);        		  System.out.println();
		
		//Creating an identity matrix 6x6

		int[][] identity6 = identity(6);
		System.out.println("Creating a identity matrix I (6x6) \n"); 
		print(identity6);			System.out.println();

		//Multiply E x I

		int[][] exidentity6 = mult(identity6, e);
		System.out.println("The product of I x E is: \n"); 
		print(exidentity6);  System.out.println();
		
		//Checking if E x I = E
		
		if (equals(exidentity6, e)){
			System.out.println("This is an equal matrix E x I = E \n");
		}
		
		//Checking if (A x I = I) and (I x A = A)
		
		if (equals(exidentity6, e) && equals(dxidentity5, d)){
			System.out.println("We proved that I x E = E and D x I = D \n");
		}
		
		//Creating a random matrix F (6x5)
		
		int[][] f = random(6, 5, 9);
		System.out.println("Creating a new matrix F (6x5) \n"); 
		print(f);        		  System.out.println();
		
		//Creating a random matrix G (5x3)
		
		int[][] g = random(5, 3, 9);
		System.out.println("Creating a new matrix G (5x3) \n"); 
		print(g);        		  System.out.println();
		
		//Multiply F x G = H

		int[][] h = mult(f, g);
		System.out.println("The product of F x G (6x3) \n"); 
		print(h);  System.out.println();
		
		//Create the transpose of G and calling it T
		System.out.println("Printing the matrix G (5x3) \n"); 
		print(g);        		  System.out.println();
		int[][] t = transpose(g);
		System.out.println("The transpose of G is: \n"); 
		print(t);  System.out.println();
				
		//Taking the matrix T and creating a subset from (1,2) to (3,4)
		
		System.out.println("Creating a subset of the matrix T"
			+ "from (1,2) to (3,4) \n"); 
		print(subMatrix(t, 1, 2, 3, 4)); 
		System.out.println();
		
		//Create a Random Matrix - make the transpose of it, and multiply it to get Symetric Matrix

		int[][] u = random(4, 5, 9);
		System.out.println("Creating a Random matrix (4x5) \n"); 
		print(u);  System.out.println();

		int[][] ut = transpose(u);
		System.out.println("Creating a the transpose of the Matrix \n"); 
		print(ut);  System.out.println();
		
		int[][] utu = mult(ut, u);
		System.out.println("Multiplying the two Matrix \n"); 
		print(utu);  System.out.println();

		if (isSymmetric(utu)){
			System.out.println("We showed that a random matrix mult its "
					+ "transpose is a symetric matrix.");

		}
	}	

	/**
	 *  Prints the given matrix.
	 *  
	 *  @param the matrix to be printed.
	 */
	public static int[][] print(int matrix[][]) {
		
		for (int row = 0; row < matrix.length; row++) 						//Creating a loop to print
		{
			for (int column = 0; column < matrix[row].length;column++) 		//Getting to every single place
		        {
		        System.out.printf("%4s", matrix[row][column]);				//Printing every number correctly
		        }
			System.out.println();
		}
		return null;
	}

	/**
	 * Adds the two given matrices. Assumes that they have the same dimensions.
	 * 
	 * @param m1 - first summand
	 * @param m2 - second summand
	 * @return the sum of the two matrices
	 */
	public static int[][] add(int[][] m1, int[][] m2) {
		int[][] sum = new int[m1.length][m1[0].length];					// Creating a new matrix for the sum
		for (int i = 0; i < m1.length; i++) {					
		   for (int j = 0; j < m1[0].length; j++) {
		      sum[i][j] = m1[i][j] + m2[i][j];			// The loop get to every place and sum it
		   }
		}
		return sum;										// Return the matrix that we created
	}
	
	/**
	 * Checks if the given matrix is symmetric.
	 * 
	 * @param m - the matrix to be tested.
	 * @return true if and only if the matrix is symmetric.
	 */
	public static boolean isSymmetric(int[][] m) {

		boolean isSymmetric = false;					// Creating a false boolean
		for (int i = 0; i < m.length; i++) {
		   for (int j = 0; j < m.length; j++) {
		      if (m[i][j] == m[j][i]) {					// If every Mij = Mji
		    	  isSymmetric = true;					// Change the boolean to true
		    	  break;
		      }
		   }
		}
		return isSymmetric;								// Return the answer of the boolean
	}
	/**
	 * Checks if the two given matrices are equal.
	 * 
	 * @param m1 
	 * @param m2
	 * @return true if and only if m1 equals m2.
	 */
	public static boolean equals(int[][] m1, int[][] m2) {
        
		boolean isEqual = false;							// Creating a boolean as false

		for (int i = 0; i < m1.length; i++) {
		   for (int j = 0; j < m1[0].length; j++) {
		      if (m1[i][j] != m2[i][j]) {					// Check every place between the two
		    	  return false;								// Break if false - no need to check more!
		      }
		   }
		}
		isEqual = true;										// If everything passed OK - this is equal
		return isEqual;										// Return the boolean
	}
	

	/**
	 * Creates and returns an identity matrix of size N.
	 * 
	 * @param N - the size of the identity matrix.
	 * @return the identity matrix of size N
	 */
	public static int[][] identity(int N) {

		int[][] newIndetity = new int[N][N];				// Creating a matrix with the size NxN
		for (int i = 0; i < N; i++) {
				newIndetity[i][i] = 1;						// Creating a in the diagonal 1
		   }
		return newIndetity;
	}	
		
	/**
	 * Creates and returns the transpose of the given matrix.
	 * 
	 * @param m - the given matrix.
	 * @return a new matrix, which is the transpose of m.
	 */
	public static int[][] transpose(int[][] m) {
		int row = m.length; // number of rows
		int column = m[0].length; // number of columns
		
		int[][] transpose = new int[column][row];
		
		for (int i = 0; i < column; i++) {
			for (int j = 0; j < row; j++) {	
				for (int h = 0; h < row; h++) {
						transpose[i][j] = m[j][i];
				}
			}
		}
		return transpose;
	}
	

	/**
	 * Computes and returns the product of the two given matrices.
	 * Assumes that they have compatible dimensions.
	 * 
	 * @param m1
	 * @param m2
	 * @return the product of m1 and m2
	 */
	public static int[][] mult(int[][] m1, int[][] m2)
	{
										// Adding int for understanding - the are not necessary
		int row = m1.length; 			// number of rows in the new matrix
		int column = m2[0].length; 		// number of columns in the new matrix

		int[][] product = new int[row][column];

			for (int i = 0; i < row; i++) {
				for (int j = 0; j < column; j++) {
					int temp = 0;	   // Creating the temp to make the Multiplication
					for (int h = 0; h < m1[0].length; h++) {
							temp = temp + (m1[i][h] * m2[h][j]);
					}
					product[i][j] = temp;
				}
			}
			return product;
	}
	
	/**
	 * The method creates and returns a matrix of N rows and M columns,
	 * in which every element is an integer between 0 and range, inclusive.	 
	 *  
	 * @param n is the number of row of the matrix
	 * @param m is the number of column in the matrix
	 * @param range is the range from zero to the number inclusive for every int of the matrix
	 * @return a random matrix (NxM)
	 */
	
	public static int[][] random (int N, int M, int range){
        

		int[][] randomMatrix = new int[N][M];    // Creating a matrix with the right size
		for (int i = 0; i < N; i++) {
		   for (int j = 0; j < M; j++) {										// Getting to every 'cell'
		      randomMatrix[i][j] = (int) ((range + 1) * Math.random());			// Putting a random number in the place
		   }
		}
		return randomMatrix;					// Return the new matrix
	}
		
	
	/**
	 * The method returns a matrix which is the subset
	 * m. The top-left element of the subset is m(i1,j1), and the bottom-right
	 * element is m(i2,j2). 
	 * 
	 * @param m is a given matrix to create a subset
	 * @param i1 is the line  of the first element
	 * @param j1 is the row  of the first element
	 * @param i2 is the line  of the second element
	 * @param j2 is the row  of the second element
	 * @return a subset of the given matrix (NxM)
	 */
	
	public static int[][] subMatrix(int[][] m, int i1, int j1, int i2, int j2)
	{

		int newRow = ((i2 - i1) + 1);					// Count the size of the new matrix
		int newColumn = ((j2 - j1) + 1);
		
		int[][] subMatrix = new int[newRow][newColumn];	// Creating a new matrix
		
		int countI = i1;								// Creating a count for i
			for (int i = 0; i < newRow; i++) {
				int countJ = j1;  						// Creating a count for i
				for (int j = 0; j < newColumn; j++) {
					subMatrix[i][j] = m[(countI)-1][(countJ)-1];
				    countJ++; 							// To put to the right number - using the count J
				}
				countI++;
			}
		return subMatrix;								// Return the new matrix

	}
}