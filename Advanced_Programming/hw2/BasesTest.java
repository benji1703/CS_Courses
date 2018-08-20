import static org.junit.Assert.*;

import org.junit.Test;

/**
 * This class only tests simple examples (to give a sanity-check).
 * Careful: Correct code will always pass the tests, but not all code that
 * passes these tests is correct.
 * @author talm
 *
 */
public class BasesTest {

	@Test
	public void testConvertFromBase() {
		int digits[] = {6, 10, 14, 13, 11, 8};
		assertEquals(12345678L, Bases.convertFromBase(digits, 17));
	}


	@Test
	public void testConvertToBase() {
		int expectedDigits[] = {6, 10, 14, 13, 11};
		int testDigits[] = new int[5];
		
		Bases.convertToBase(12345678L, 17, testDigits);
		assertArrayEquals(expectedDigits, testDigits);
	}
	
	@Test
	public void testBaseAdd() {
		int aDigits[] = 	   {6, 10, 14, 13, 11, 8};
		int bDigits[] = 	   {15, 0, 10, 16, 13, 1};
		int expectedDigits[] = {4, 11, 7 , 13, 8};
		int testDigits[] = new int[5];
		
		Bases.baseAdd(17, aDigits, bDigits, testDigits);
		assertArrayEquals(expectedDigits, testDigits);
	}
	
	@Test
	public void testBaseNegate() {
		int inDigits[] = 	   {1, 0, 0, 0, 0, 0};
		int expectedDigits[] = {16, 16, 16, 16, 16};
		int testDigits[] = new int[5];
		
		Bases.baseNegate(17, inDigits, testDigits);
		assertArrayEquals(expectedDigits, testDigits);
	}
}
