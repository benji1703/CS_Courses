/* Assignment number : 5.3
File Name : Editor2.java
Name (First Last) : Benjamin Arbibe
Student ID : 323795633
Email : benjamin.arbibe@post.idc.ac.il */

public class Editor2 {

	public static void main(String[] args) {

		String filename = args[0];
		int width = Integer.parseInt(args[1]);
		int height = Integer.parseInt(args[2]);
		
		// Reads image data from the argument, into an array pic
		int[][][] pic = ImageEditing.read(filename + ".ppm"); 			
	
		System.out.println("Scaled pic " + (args[1]) + " x " 
				+ (args[2]) +" \n ");						
		// Renders the image on the screen
		ImageEditing.show(ImageEditing.scale(pic, width, height)); 	
		
		
	}

}
