package hangman;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import org.junit.Before;
import org.junit.Test;

public class AsciiPictureTest {

	AsciiPicture hangmanTest;

	@Before
	public void setUp() throws Exception {
		char[][] hangman = { 
				"  _________     ".toCharArray(),
				" |         |    ".toCharArray(),
				" |         0    ".toCharArray(),
				" |        /|\\   ".toCharArray(),
				" |        / \\   ".toCharArray(),
				" |              ".toCharArray(),
				" |              ".toCharArray() 
		};

		this.hangmanTest = new AsciiPicture(hangman[0].length, hangman.length, 0, 0, hangman);
	}

	@Test
	public void testGet() {

		// Get the right char from position

		assertEquals(' ', this.hangmanTest.get(0, 0));
		assertEquals('/', this.hangmanTest.get(10, 3));
		assertEquals('/', this.hangmanTest.get(10, 4));
		assertEquals('\\', this.hangmanTest.get(12, 3));
		assertEquals('\\', this.hangmanTest.get(12, 4));
		assertEquals('|', this.hangmanTest.get(11, 3));
		assertEquals('|', this.hangmanTest.get(1, 1));
		assertEquals(' ', this.hangmanTest.get(6, 6));

	}

	@Test
	public void testSet() {

		this.hangmanTest.set(1, 1,'$');
		assertEquals('$', this.hangmanTest.get(1, 1));
		assertNotEquals('|', this.hangmanTest.get(1, 1)); // Testing the Replacement (line 38 tested)
		this.hangmanTest.set(1, 1,'|'); // Putting it back
		assertEquals('|', this.hangmanTest.get(1, 1));


		this.hangmanTest.set(10, 4,'$');
		assertEquals('$', this.hangmanTest.get(10, 4));
		assertNotEquals('/', this.hangmanTest.get(10, 4)); // Testing the Replacement (line 34 tested)
		this.hangmanTest.set(10, 4,'/'); // Putting it back
		assertEquals('/', this.hangmanTest.get(10, 4));

	}

	@Test
	public void testAsciiPictureIntIntIntIntCharArrayArray() {

		// Testing w h x y

		assertEquals(16, hangmanTest.width);
		assertEquals(7, hangmanTest.height);
		assertEquals(0, hangmanTest.leftX);
		assertEquals(0, hangmanTest.topY);

		char[][] hangmanCurrent = { 
				"  _________     ".toCharArray(),
				" |         |    ".toCharArray(),
				" |         0    ".toCharArray(),
				" |        /|\\   ".toCharArray(),
				" |        / \\   ".toCharArray(),
				" |              ".toCharArray(),
				" |              ".toCharArray() };


		// For every line, test the same picture

		for(int i = 0; i < hangmanCurrent.length ; i++) {
			assertArrayEquals(hangmanCurrent[i], this.hangmanTest.picture[i]);	
		}
	}

	@Test
	public void testAsciiPictureIntIntChar() {

		// Testing full $ picture creation

		AsciiPicture hangmanCurrent = new AsciiPicture(10, 15, '$');

		assertEquals(10 , hangmanCurrent.width); 
		assertEquals(15 , hangmanCurrent.height); 

		for(int i = 0; i < hangmanCurrent.height ; i++) {

			for (int j = 0; j < hangmanCurrent.width; j++) {

				assertEquals('$', hangmanCurrent.picture[i][j]);

			}	
		}
	}

	@Test
	public void testOverlay1() {

		AsciiPicture dollarPatern = new AsciiPicture(4, 4, '$');

		char [][] first = { 
				"1**1".toCharArray(), 
				"0**0".toCharArray(),
				"1**1".toCharArray(),
				"0**0".toCharArray()
		};

		AsciiPicture firstPic = new AsciiPicture(4, 4, 0, 0, first);

		// Testing the Overlay

		dollarPatern.overlay(firstPic, 0, 0, '*');

		char [][] expectedResult = {
				"1$$1".toCharArray(), 
				"0$$0".toCharArray(),
				"1$$1".toCharArray(),
				"0$$0".toCharArray()
		};

		AsciiPicture expectedAscii = new AsciiPicture(4, 4, 0, 0, expectedResult);

		for (int i = 0; i < expectedAscii.height; i++) {
			assertArrayEquals(expectedAscii.picture[i], dollarPatern.picture[i]);
		}
	}


	@Test
	public void testOverlay2() {

		AsciiPicture underPattern = new AsciiPicture(5, 5, '_');

		char [][] first = { 
				"1^^^1".toCharArray(), 
				"0^^^0".toCharArray(),
				"1^^^1".toCharArray(),
				"0^^^0".toCharArray(),
				"1^^^1".toCharArray()
		};

		AsciiPicture firstPic = new AsciiPicture(5, 5, 0, 0, first);

		// Testing the Overlay

		underPattern.overlay(firstPic, 0, 0, '^');

		char [][] expectedResult = {
				"1___1".toCharArray(), 
				"0___0".toCharArray(),
				"1___1".toCharArray(),
				"0___0".toCharArray(),
				"1___1".toCharArray()
		};

		AsciiPicture expectedAscii = new AsciiPicture(5, 5, 0, 0, expectedResult);

		for (int i = 0; i < expectedAscii.height; i++) {
			assertArrayEquals(expectedAscii.picture[i], underPattern.picture[i]);
		}
	}

	@Test
	public void testOverlay3() { // Line 120-128
		char [][] first = {
				"0586".toCharArray(), 
				"8585".toCharArray(),
				"8686".toCharArray() };

		AsciiPicture firstPic = new AsciiPicture(3, 4, 0, 0, first);

		AsciiPicture dollarPatern = new AsciiPicture(3, 4, '$');

		firstPic.overlay(dollarPatern, -1, -1, '^');

		char [][] expectedResult = {
				"$$86".toCharArray(),  
				"$$85".toCharArray(), 
				"$$86".toCharArray() };

		AsciiPicture expectedAscii = new AsciiPicture(3, 3, 0, 0, expectedResult);

		for (int i = 0; i < expectedAscii.height; i++) {
			assertArrayEquals(expectedAscii.picture[i], firstPic.picture[i]);
		}
	}

	@Test
	public void testOverlay4() { // Line 110 - 119

		char [][] first = {

				"#$#".toCharArray(), 
				"&*&".toCharArray(), 
				"#$#".toCharArray(),
				"&*&".toCharArray() };

		AsciiPicture firstPic = new AsciiPicture(4, 3, 0, 0, first);

		char [][] sec = {
				
				"000".toCharArray(), 
				"X0X".toCharArray(),
				"0X0".toCharArray(), 
				"XXX".toCharArray() }; 
		
		AsciiPicture secPic = new AsciiPicture(4, 3, 0, 0, sec);

		firstPic.overlay(secPic, 2, 1, '0');

		char [][] expectedResult = {
				
				"#$#".toCharArray(), 
				"&*&".toCharArray(), 
				"#$X".toCharArray() }; 
		
		AsciiPicture expectedAscii = new AsciiPicture(3, 3, 0, 0, expectedResult);
		
		
		for (int i = 0; i < expectedAscii.height; i++) {
			assertArrayEquals(expectedAscii.picture[i], firstPic.picture[i]);
		}
	}

	@Test
	public void testSmallPrint() throws IOException {

		AsciiPicture dollarPatern = new AsciiPicture(5, 5, '$');

		ByteArrayOutputStream tempPrint = new ByteArrayOutputStream(); // Using the Hint (thanks)
		PrintStream temp = new PrintStream(tempPrint);

		dollarPatern.print(temp);

		String expectedOutput  = 	
				"$$$$$\r\n" +
						"$$$$$\r\n" +
						"$$$$$\r\n" +
						"$$$$$\r\n" +
						"$$$$$\r\n" ;

		assertEquals(expectedOutput, tempPrint.toString());

		temp.close();

	}

	@Test
	public void testPrint() throws IOException {

		ByteArrayOutputStream tempPrint = new ByteArrayOutputStream(); // Using the Hint (thanks)
		PrintStream temp = new PrintStream(tempPrint);

		hangmanTest.print(temp);

		String expectedOutput  = 	
				"  _________     \r\n" +
						" |         |    \r\n" +
						" |         0    \r\n" +
						" |        /|\\   \r\n"+
						" |        / \\   \r\n"+
						" |              \r\n" +
						" |              \r\n" ;


		assertEquals(expectedOutput, tempPrint.toString());

		temp.close();

	}



}
