package files;

import java.io.IOException;
import java.io.RandomAccessFile;

public class RandomAccess {

	/**
	 * Treat the file as an array of (unsigned) 8-bit values and sort them 
	 * in-place using a bubble-sort algorithm.
	 * You may not read the whole file into memory! 
	 * @param file
	 */
	public static void sortBytes(RandomAccessFile file) throws IOException {

		file.seek(0);
		//@tutor: non informative variable name "l" .		
		long l = file.length();
		byte byteForJ1;
		byte byteForJ;

		try {
			// Using the Bubble Sort Algorithm

			for (long i = 0; i < l - 1; i++) {

				for (long j = 0; j < l - i - 1; j++) {

					file.seek(j + 1);
					byteForJ1 = file.readByte();
					file.seek(j);
					byteForJ = file.readByte();

					if ((byteForJ & 0xFF) > (byteForJ1 & 0xFF)) { // Comparing the Bytes only

						// Do the switch

						file.seek(j + 1);
						file.write(byteForJ);

						file.seek(j);
						file.write(byteForJ1);

					}
				}
			}
		}

		catch (IOException e) {
			e.printStackTrace();
		}


	}

	/**
	 * Treat the file as an array of unsigned 24-bit values (stored MSB first) and sort
	 * them in-place using a bubble-sort algorithm. 
	 * You may not read the whole file into memory! 
	 * @param file
	 * @throws IOException
	 */
	public static void sortTriBytes(RandomAccessFile file) throws IOException {


		long l = file.length();
		int byte3ForJ1;
		int byte3ForJ;

		try {
			// Using the Bubble Sort Algorithm

			for (long i = 0; i < l; i += 3) {

				for (long j = 3; j < l - i ; j += 3) {

					file.seek(j - 3);

					// Get first 24 bits
					byte3ForJ = file.read();
					byte3ForJ <<= 8;
					byte3ForJ += (byte)file.read();
					byte3ForJ <<= 8;
					byte3ForJ += (byte)file.read();

					// Get the other 24 bits
					byte3ForJ1 = file.read();
					byte3ForJ1 <<= 8;
					byte3ForJ1 += (byte)file.read();
					byte3ForJ1 <<= 8;
					byte3ForJ1 += (byte)file.read();


					if ((byte3ForJ) > (byte3ForJ1)) { // Comparing the Bytes only

						// Do the switch

						file.seek(j - 3);

						file.write(byte3ForJ1 >> 16);
						file.write(byte3ForJ1 >> 8);
						file.write(byte3ForJ1);

						file.write(byte3ForJ >> 16);
						file.write(byte3ForJ >> 8);
						file.write(byte3ForJ);

					}
				}
			}
		}


		catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Treat the file as an array of unsigned 24-bit values (stored MSB first) and sort
	 * them in-place using a quicksort algorithm. 
	 * You may not read the whole file into memory! 
	 * @param file
	 * @throws IOException
	 */
	public static void sortTriBytesQuick(RandomAccessFile file) throws IOException {

		// Using Algorithm from http://www.vogella.com/tutorials/JavaAlgorithmsQuicksort/article.html

		if (file == null || file.length() == 0) {
			return;
		}
		quickSort(0, (int)file.length() - 3 , file);

	}

	// Helping method for quickSort (recursion)

	private static void quickSort(int low, int high, RandomAccessFile file) {

		int i = low;
		int j = high;

		try {

			file.seek((i + (j - i) / 2) + 2);
			
			// Get the pivot number (24 bits - 3 bytes) - from the middle of the list 
			int pivot = file.read();
			pivot <<= 8;
			pivot += (byte)file.read();
			pivot <<= 8;
			pivot += (byte)file.read();
			
			// Divide into two lists
			while (i <= j) {
				
				file.seek(i);
				int l = file.read();
				l <<= 8;
				l += (byte)file.read();
				l <<= 8;
				l += (byte)file.read();
				
				// If the current value from the left list is smaller than the pivot
	            // element then get the next element from the left list
				
				while (l < pivot) {
					i += 3;
					file.seek(i);
					l = file.read();
					l <<= 8;
				
					l += (byte)file.read();
					l <<= 8;
					l += (byte)file.read();
				}
				
				file.seek(j);
				int r = file.read();
				r <<= 8;
				r += (byte)file.read();
				r <<= 8;
				r += (byte)file.read();
				
				// If the current value from the right list is larger than the pivot
	            // element then get the next element from the right list
				
				while (r > pivot) {
					
					j -= 3;
					if (j > i) {
						file.seek(j);
						r = file.read();
						r <<= 8;
						r += (byte)file.read();
						r <<= 8;
						r += (byte)file.read();
					}
				}
				
				// If we have found a value in the left list which is larger than
	            // the pivot element and if we have found a value in the right list
	            // which is smaller than the pivot element then we exchange the
	            // values.
	            // As we are done we can increase i and j
				
				if (i <= j) {
					
					file.seek(i);
					int temp = file.read();
					temp <<= 8;
					temp += (byte)file.read();
					temp <<= 8;
					temp += (byte)file.read();
					file.seek(j);
					int w = file.read();
					w <<= 8;
					w += (byte)file.read();
					w <<= 8;
					w += (byte)file.read();
					file.seek(i);
					file.write(w >>> 16);
					file.write(w >>> 8);
					file.write(w);
					file.seek(j);
					file.write(temp >>> 16);
					file.write(temp >>> 8);
					file.write(temp);
					i += 3;
					j -= 3;
				}
			}
			
	        // Recursion
			if (low < j) {
				quickSort(low, j , file);
			}
			
	        // Recursion
			if (i < high) {
				quickSort(i, high , file);
			}

		}

		catch (IOException e) {
			e.printStackTrace();
		}

	}
}
