/* Assignment number : 5.4
File Name : FadeToGrey.java
Name (First Last) : Benjamin Arbibe
Student ID : 323795633
Email : benjamin.arbibe@post.idc.ac.il */

public class FadeToGrey {

	public static void main(String[] args) {
		
		String filename = args[0];
		int steps = Integer.parseInt(args[1]);
		
		// Reads image data from the argument, into an Matrix
		int[][][] firstpic = ImageEditing.read(filename + ".ppm"); 		
		// Take the picture and make it grey
		int[][][] greypic = ImageEditing.greyScale(firstpic);				
		
		// Making the morphing "Animation"
		ImageEditing.morph(firstpic, greypic, steps);						
		
		
	}

}
