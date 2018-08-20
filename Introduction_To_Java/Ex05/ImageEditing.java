/* Assignment number : 5.1
File Name : ImageEditing.java
Name (First Last) : Benjamin Arbibe
Student ID : 323795633
Email : benjamin.arbibe@post.idc.ac.il */

public class ImageEditing {

	public static void main(String args[]) {

		// Reads image data from a file, into an array
		int[][][] pic = read("tinypic.ppm"); 
		System.out.println("Standart pic \n"); // For a clean code
		// Displays the array's data on standard output
		print(pic);

		System.out.println("flipHorizontally pic \n");
		print(flipHorizontally(pic));

		System.out.println("flipVertically pic  \n");
		print(flipVertically(pic));

		System.out.println("greyScale pic  \n");
		print(greyScale(pic));

	}

	/**
	 * Renders an image using StdDraw. The input array is assumed to contain 
	 * integers in the range [0,255]. With the third dimension being of size 3.
	 * 
	 * @param pic
	 *            - the image to show.
	 */

	public static void show(int[][][] pic) {
		StdDraw.setCanvasSize(pic[0].length, pic.length);
		int height = pic.length;
		int width = pic[0].length;
		StdDraw.setXscale(0, width);
		StdDraw.setYscale(0, height);
		StdDraw.show(30);
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				StdDraw.setPenColor(pic[i][j][0], pic[i][j][1], pic[i][j][2]);
				StdDraw.filledRectangle(j + 0.5, height - i - 0.5, 0.5, 0.5);
			}
		}
		StdDraw.show();
	}

	/**
	 * Receives the name of a PPM file and returns an array containing the 
	 * image's data. The file must be located in the assignment's folder.
	 * 
	 * @param filename
	 *            - the filename with the extension.
	 * @return read - the matrix of the picture.
	 */

	public static int[][][] read(String filename) {

		// Calling the right file
		StdIn.setInput(filename); 
		// This is for ״P3״
		StdIn.readString(); 
		// The number of columns from file
		int columns = StdIn.readInt(); 
		// The number of row from file
		int rows = StdIn.readInt(); 
		 // Creating the 3 dim matrix
		int[][][] readMatrix = new int[rows][columns][3];
		StdIn.readInt(); // This is the number of colors

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				for (int k = 0; k < 3; k++) {
					// Getting the value in a 3d matrix
					readMatrix[i][j][k] = StdIn.readInt(); 
				}
			}
		}
		return readMatrix;
	}

	/**
	 * The function writes to standard output the contents of the given 
	 * 3 dimensional array.
	 * 
	 * @param source
	 *            - the matrix to print
	 */

	public static void print(int[][][] source) {

		// Creating a loop to print
		for (int i = 0; i < source.length; i++) { 
			// Getting to every single place
			for (int j = 0; j < source[0].length; j++) { 
				for (int k = 0; k < 3; k++) {
					// Printing every number correctly
					System.out.printf("%5s", source[i][j][k]); 
				}
			}
			System.out.println(); 
		}
		System.out.println(); // For a clean code
		return;
	}

	/**
	 * The function flips a picture vertically.
	 * 
	 * @param source
	 *            - a 3D matrix.
	 * @return the flipped picture as a 3D matrix.
	 */

	public static int[][][] flipVertically(int[][][] source) {

		int[][][] verticalTemp = source;
		int[][][] vertical = new int[source.length][source[0].length][3]; 
		int rows = (vertical.length) - 1; 

		// Using one loop to swift the content
		for (int i = 0; i < (rows + 1); i++) { 
			vertical[i] = verticalTemp[rows - i];
		}
		return vertical;
	}

	/**
	 * The function flips a picture horizontally
	 * 
	 * @param source
	 *            - as a 3D matrix.
	 * @return the flipped picture
	 */

	public static int[][][] flipHorizontally(int[][][] source) {

		// Getting the source in a temp Matrix
		int[][][] horizontalTemp = source; 
		// Creating the new matrix
		int[][][] horizontal = new int[source.length][source[0].length][3];
		int columns = (horizontal[0].length); 
		int rows = (horizontal.length);

		for (int j = 0; j < rows; j++) { // Creating two loop to flip
			for (int i = 0; i < columns; i++) { // To flip horizontally
				horizontal[j][i] = horizontalTemp[j][(columns - i) - 1];
			}
		}

		return horizontal;
	}

	/**
	 * The function takes an RGB pixel as input and returns the corresponding 
	 * greyscale pixel, using the formula to make it grey
	 * 
	 * @param pixel
	 * @return the grey pixel
	 */
	public static int[] luminance(int[] pixel) {

		// Creating it in double to have the right numbers
		double[] templum = new double[3]; 

		templum[0] = (double) (0.299 * pixel[0]);
		templum[1] = (double) (0.587 * pixel[1]);
		templum[2] = (double) (0.114 * pixel[2]);

		// Summing the grey color of every pixel and parsing
		int sumlum = (int) (templum[0] + templum[1] + templum[2]); 

		int[] lum = new int[3]; // Creating a lum array

		lum[0] = sumlum; // Putting the sumlum in every cell
		lum[1] = sumlum;
		lum[2] = sumlum;

		return lum; 
	}

	/**
	 * This function takes an RGB image as input and returns a greyscaled 
	 * version of this image.
	 * 
	 * @param source
	 *            of a 3D matrix
	 * @return the greyscale 3D matrix
	 */

	public static int[][][] greyScale(int[][][] source) {

		// Creating the new matrix
		int[][][] gray = new int[source.length][source[0].length][3]; 

		// Creating a loop to print
		for (int i = 0; i < source.length; i++) { 
			for (int j = 0; j < source[0].length; j++) { 
				int[] klum = new int[3];
				for (int k = 0; k < 3; k++) {
					klum[k] = source[i][j][k]; // Creating a pixel
				}
				// Put the grey pixel in the new Matrix
				gray[i][j] = luminance(klum); 
			}
		}
		return gray;
	}

	/**
	 * The function takes as input a digital image and two dimensions and 
	 * returns a scaled version of the digital image according to the specified
	 * dimensions.
	 * 
	 * @param source
	 *            - source of the 3D matrix
	 * @param width
	 *            - the desired widht
	 * @param height
	 *            - the desired height
	 * @return - the scaled matrix
	 */
	public static int[][][] scale(int[][][] source, int width, int height) {

		int h = height;
		int w = width;
		// Creating the new matrix with the right size
		int[][][] scale = new int[h][w][3]; 
		double h0 = source.length;
		double w0 = source[0].length;
		double ratioH = h0 / h;
		double ratioW = w0 / w;

		for (int i = 0; i < h; i++) { // Creating a loop
			for (int j = 0; j < w; j++) { // Getting to every single place
				for (int k = 0; k < 3; k++) { // To get to every 3D cell
					scale[i][j][k] = source[(int) (i * ratioH)][(int) (j
							* ratioW)][k]; // Applying the right function
				}
			}
		}

		return scale;
	}

	/**
	 * The function returns a pixel blended according to the the average of the 
	 * two given pixel Note that the resulting pixel is an integer values.
	 * 
	 * @param pixel1
	 *            - the first pixel
	 * @param pixel2
	 *            - the second pixel
	 * @param alpha
	 *            - how much to blend the pixel 1 to pixel 2. The number must be
	 *             between 0 and 1 inclusive (double)
	 * @return the pixel after the blending was applied.
	 */
	public static int[] blend(int[] pixel1, int[] pixel2, double alpha) {

		int[] blend = new int[3]; // Creating a new array to blend each pixel

		blend[0] = (int) ((alpha * pixel1[0]) + ((1 - alpha) * pixel2[0]));
		blend[1] = (int) ((alpha * pixel1[1]) + ((1 - alpha) * pixel2[1]));
		blend[2] = (int) ((alpha * pixel1[2]) + ((1 - alpha) * pixel2[2]));

		return blend; // return the new grey pixel

	}

	/**
	 * The function returns the alpha blending of the two given source images.
	 *  The function computes each new pixel using the blend function.
	 * 
	 * @param source1
	 *            - the given matrix
	 * @param source2
	 *            - the matrix to blend to
	 * @param alpha
	 *            - how much to blend every pixel from source 1 to source 2.
	 *            The number must be between 0 and 1 inclusive (double)
	 * @return
	 */
	public static int[][][] combine(int[][][] source1, int[][][] source2,
			double alpha) {

		int[][][] combine = new int[source1.length][source1[0].length][3]; 

		for (int i = 0; i < source1.length; i++) { 
			for (int j = 0; j < source1[0].length; j++) { 
				int[] combined1 = new int[3];
				int[] combined2 = new int[3];
				for (int k = 0; k < 3; k++) {
					combined1[k] = source1[i][j][k]; // Creating a pixel
					combined2[k] = source2[i][j][k]; // Creating a pixel
				}
				// Put the grey pixel in the new Matrix
				combine[i][j] = blend(combined1, combined2, alpha); 
			}
		}
		return combine;

	}

	/**
	 * The function morphs the source image into the target image in steps.
	 *  If the images don't have the same dimensions, the function starts by
	 * rescaling them as necessary. At the end of each blending step, the 
	 * function uses the show function to display the intermediate result.
	 * 
	 * @param source
	 *            - the given matrix
	 * @param target
	 *            - the matrix to morph to
	 * @param n
	 *            - the number of steps.
	 */
	public static void morph(int[][][] source, int[][][] target, int n) {

		// Scaling it to morph it
		int[][][] targetSc = scale(target, source[0].length, source.length);

		for (int j = 0; j <= n; j++) {

			// Making it in double to have the right alpha
			double alpha = ((n - j) * 1.00) / n; 
			ImageEditing.show(ImageEditing.combine(source, targetSc, alpha)); 
		}

		return;
	}
}
