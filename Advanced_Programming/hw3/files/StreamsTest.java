package files;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class StreamsTest {

	@Test
	public void testGetQuoted()  throws IOException {
		ByteArrayInputStream in = new ByteArrayInputStream("this is irrelevant \"return this substring\" this is irrelevant".getBytes());
		byte[] rawExpected = "return this substring".getBytes();
		List<Byte> expected = new ArrayList<Byte>(rawExpected.length);
		for (byte b : rawExpected)
			expected.add(b);
		
		assertEquals(expected, Streams.getQuoted(in));
	}

	@Test
	public void testReadUntil()  throws IOException {
		StringReader in = new StringReader("This is a test<end|nope<endMark> some extra text");
		String expected = "This is a test<end|nope";
		String actual = Streams.readUntil(in, "<endMark>");
		assertEquals(expected, actual);
	}
	
	@Test
	public void testFilterOut()  throws IOException {
		byte[] bytes = "aabbccddeeaabbccddeeabcde".getBytes();
		ByteArrayInputStream in = new ByteArrayInputStream(bytes);
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		
		byte[] expectedBytes = "aaccddeeaaccddeeacde".getBytes();
		Streams.filterOut(in, out, (byte) 'b');
		byte[] actualBytes = out.toByteArray();
		assertArrayEquals(expectedBytes, actualBytes);
	}
	
	@Test
	public void testReadNumber()  throws IOException {
		byte[] bytes = {0x12, 0x34, 0x56, 0x78, 0x0a};
		ByteArrayInputStream in = new ByteArrayInputStream(bytes);
		long num = Streams.readNumber(in);
		assertEquals(0x123456780aL, num);
	}
}
