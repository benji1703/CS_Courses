package files;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class Streams {
	/**
	 * Read from an InputStream until a quote character (") is found, then read
	 * until another quote character is found and return the bytes in between the two quotes. 
	 * If no quote character was found return null, if only one, return the bytes from the quote to the end of the stream.
	 * @param in
	 * @return A list containing the bytes between the first occurrence of a quote character and the second.
	 */
	public static List<Byte> getQuoted(InputStream in) throws IOException {

		List<Byte> resultByte = new ArrayList<Byte>();
		int runner;

		try {
			runner = in.read();								// Get a "Run"
			while (runner != -1) {							// Until end of InputStream

				if (runner == (int)'"') { 						// Compare to "
					runner = in.read();
					while (runner != (int)'"' && runner != -1) {
						resultByte.add((byte) runner); 			// Append
						runner = in.read();
					}
				}
				runner = in.read();
			}
		}

		catch (IOException e) {
			e.printStackTrace();
		}
		return resultByte;
	}


	/**
	 * Read from the input until a specific string is read, return the string read up to (not including) the endMark.
	 * @param in the Reader to read from
	 * @param endMark the string indicating to stop reading. 
	 * @return The string read up to (not including) the endMark (if the endMark is not found, return up to the end of the stream).
	 */
	public static String readUntil(Reader in, String endMark) throws IOException {

		StringBuilder resultSb = new StringBuilder();

		int runner; 

		try {
			runner = in.read();
			while (!resultSb.toString().contains(endMark) && runner != -1) {			// Check if contained
				resultSb.append((char) runner);
				runner = in.read();
			}

			if (resultSb.toString().contains(endMark)) {				// Remover Character - if needed
				resultSb.delete((resultSb.length() - endMark.length()), (resultSb.length())); 
			}
		}

		catch (IOException e) {
			e.printStackTrace();
		}

		finally {
			in.close();
		}

		return resultSb.toString();
	}

	/**
	 * Copy bytes from input to output, ignoring all occurrences of badByte.
	 * @param in
	 * @param out
	 * @param badByte
	 */
	public static void filterOut(InputStream in, OutputStream out, byte badByte) throws IOException {

		int runner;

		try {
			runner = in.read();
			while (runner != -1) {
				if (runner != (int)badByte) {		// If dif. from badByte - write
					out.write((byte) runner);
				}
				runner = in.read();
			}
		}

		catch (IOException e) {
			e.printStackTrace();
		}

		finally {
			in.close();
		}

		return;
	}

	/**
	 * Read a 48-bit <b>(40-bit in PDF)</b> (unsigned) integer from the stream and return it. The number is represented as five bytes, 
	 * with the most-significant byte first. 
	 * If the stream ends before 5 bytes are read, return -1.
	 * @param in
	 * @return the number read from the stream
	 */
	public static long readNumber(InputStream in) throws IOException {

		int runner;
		int counter = 0;
		long resultLong = 0;

		try {
			runner = in.read();

			while (runner != -1) {
				resultLong <<= 8;				// Using bitWise to shift left bytes
				resultLong |= (byte)runner;
				runner = in.read();
				counter++;						// Count the number of byte - if less than 5 - print later '-1'
			}
		}

		catch (IOException e) {
			e.printStackTrace();
		}

		finally {
			in.close();
		}

		if (counter < 5) {
			return -1;
		}

		return resultLong;
	}
}
