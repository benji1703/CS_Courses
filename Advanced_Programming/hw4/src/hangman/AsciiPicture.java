package hangman;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;

/**
 * An ASCII picture, consisting of a grid of ASCII characters.
 * (the unicode value of ASCII characters are all in the range 0-127).
 * 
 * @author talm
 *
 */
public class AsciiPicture {
	/** 
	 * The width of the picture in characters.
	 */
	public int width;
	
	/**
	 * The height of the picture in characters.
	 */
	public int height;
	
	/**
	 * The left-most column of the picture (in the {@link #picture} array). 
	 */
	public int leftX;
	
	/**
	 * The top-most row of the picture (in the {@link #picture} array)
	 */
	public int topY;
	
	/**
	 * The characters of a picture, as a 2-dimensional array.
	 * The picture itself is a sub-rectangle in the this array (it can be 
	 * part of the array or the entire array). 
	 * 
	 * picture[i][j] represents the i'th row and j'th column (counting from the top-left corner).
	 * picture[topY][leftX] is the top-left corner of the picture.
	 * picture[topY][leftX + width - 1] is the top-right corner of the picture.
	 * picture[topY + height - 1][leftX] is the bottom-left corner of the picture.
	 * picture[topY + height - 1][leftX + width - 1] is the bottom-right corner of the picture.
	 */
	public char[][] picture;
	
	/**
	 * Return the character at column x and row y of the picture part, relative to the top-left corner.
	 */
	public char get(int x, int y) {
		return picture[topY + y][leftX + x];
	}
	
	/**
	 * Set the character at column x and row y of the picture part, relative to the top-left corner.
	 */
	public void set(int x, int y, char c) {
		picture[topY + y][leftX + x] = c;
	}

	/**
	 *  Create a picture part from an existing picture array.
	 */
	public AsciiPicture(int width, int height, int x, int y,
			char[][] picture) {
		this.width = width;
		this.height = height;
		this.leftX = x;
		this.topY = y;
		this.picture = picture;
	}
	
	/**
	 * Create a new empty picture (with a new array) with a given width and height.
	 * The picture will be filled with a given character as the background.
	 */
	public AsciiPicture(int width, int height, char bgChar) {
		this.width = width;
		this.height = height;
		
		picture = new char[height][];
		
		for (int i = 0; i < height; ++i) {
			picture[i] = new char[width];
			for (int j = 0; j < width; ++j)
				picture[i][j] = bgChar;
		}
	}
	
	
	/**
	 * "Paint" another picture over this one.
	 * The overlaid picture is placed inside this picture at given coordinates 
	 * relative to to the top-left corner of this picture.
	 * 
	 * The overlaid picture is "cropped" to the size of this picture (i.e., characters that
	 * fall outside the sub-rectangle defined by this picture should not be written).
	 * 
	 * @param top The picture part that will be overlaid.
	 * @param topX The position of the left-most column of the overlaid picture, relative to 
	 * 	the left-most column of this picture. This number can be negative.
	 * @param topY The position of the top-most row of the overlaid picture, relative 
	 *  to the top-most row of this picture. This number can be negative.
	 * @param bgChar This character is considered transparent in the top picture (and will not
	 *  overwrite what's "underneath" it).
	 */
	public void overlay(AsciiPicture top, int topX, int topY, char bgChar) {
		
		// Compute the max Y coordinate (in top-space) for the part of the top picture that will be overlaid.
		int croppedMaxY = top.height;
		if (topY + top.height > height)
			croppedMaxY = height - topY; // FIXING!

		// Compute the max X coordinate (in top space) for the part of the top picture that will be overlaid.
		int croppedMaxX = top.width;
		if (topX + top.width > width)
			croppedMaxX = width - topX; // FIXING!

		// Compute the min Y coordinate (in top space) for the part of the top picture that will be overlaid.
		int croppedMinY = 0;
		if (topY < 0)
			croppedMinY = -topY;
		
		// Compute the min X coordinate (in top space) for the part of the top picture that will be overlaid.
		int croppedMinX = 0;
		if (topX < 0)
			croppedMinX = -topX;
		
		for (int y = croppedMinY; y < croppedMaxY; ++y) {
			for (int x = croppedMinX; x < croppedMaxX; ++x) {
				if (top.get(x, y) != bgChar) // FIXING!
					set(x + topX, y + topY, top.get(x, y));
			}
		}
	}
	
	/**
	 * Print the picture to a {@link PrintWriter}.
	 * Each row of the picture should be a line of text output to the stream, separated by newlines.  
	 * @param out The stream to write to.
	 * @throws IOException
	 */
	public void print(PrintStream out) throws IOException {
		for (int y = 0; y < height; ++y) {
			for (int x = 0; x < width; ++x) {
				out.print(get(x, y));
			}
			out.println();
		}
	}
	
	
}
