package files;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

import org.junit.Test;

public class RandomAccessTest {

	@Test
	public void testSortBytes() throws IOException {
		File temp = File.createTempFile("sortBytes", "byt");
		RandomAccessFile file = new RandomAccessFile(temp, "rw");
		
		byte[] origBytes = {0,1,5,4,3,2,10,20,-1,1,2,3}; 
		file.write(origBytes);
		RandomAccess.sortBytes(file);
		
		file.seek(0);
		byte[] sortedBytes = {0,1,1,2,2,3,3,4,5,10,20,-1};
		byte[] actualBytes = new byte[sortedBytes.length];
		
		file.readFully(actualBytes);
		assertArrayEquals(sortedBytes, actualBytes);
	}
	
	@Test
	public void testSortTriBytes() throws IOException {
		File temp = File.createTempFile("sortTriBytes", "byt");
		RandomAccessFile file = new RandomAccessFile(temp, "rw");
		
		byte[] origBytes = {0,1,5, 4,3,2, 10,20,30, 1,2,3}; 
		file.write(origBytes);
		RandomAccess.sortTriBytes(file);
		
		file.seek(0);
		byte[] sortedBytes = {0,1,5, 1,2,3, 4,3,2, 10,20,30};
		byte[] actualBytes = new byte[sortedBytes.length];
		
		file.readFully(actualBytes);
		assertArrayEquals(sortedBytes, actualBytes);
	}
	
	@Test
	public void testSortTriBytesQuick() throws IOException {
		File temp = File.createTempFile("sortTriBytesQuick", "byt");
		RandomAccessFile file = new RandomAccessFile(temp, "rw");
		
		byte[] origBytes = {0,1,5, 4,3,2, 10,20,30, 1,2,3}; 
		file.write(origBytes);
		RandomAccess.sortTriBytesQuick(file);
		
		file.seek(0);
		byte[] sortedBytes = {0,1,5, 1,2,3, 4,3,2, 10,20,30};
		byte[] actualBytes = new byte[sortedBytes.length];
		
		file.readFully(actualBytes);
		assertArrayEquals(sortedBytes, actualBytes);
	}

}
