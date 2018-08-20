/* Assignment number : 5.2
File Name : Editor1.java
Name (First Last) : Benjamin Arbibe
Student ID : 323795633
Email : benjamin.arbibe@post.idc.ac.il */

public class Editor1 {

	public static void main(String[] args) {

		String filename = args[0];
		String action = args[1];
		
		// Reads image data from the argument, into an array pic
		int[][][] pic = ImageEditing.read(filename); 

		if (action.equals("fh")) {

			System.out.println("flipHorizontally pic \n");
			// Renders the image on the screen
			ImageEditing.show(ImageEditing.flipHorizontally(pic)); 

		}

		else if (action.equals("fv")) {

			System.out.println("flipVertically pic \n");
			// Renders the image on the screen
			ImageEditing.show(ImageEditing.flipVertically(pic)); 

		}

		else if (action.equals("gr")) {

			System.out.println("greyScale pic \n");
			// Renders the image on the screen
			ImageEditing.show(ImageEditing.greyScale(pic)); 

		}

		else {
			System.out.println("The function is not valid. Insert fh,"
					+ " fv or gr only \n");
		}
	}

}
