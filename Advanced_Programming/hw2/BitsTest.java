import static org.junit.Assert.*;

import org.junit.Test;

/**
 * This class only tests simple examples (to give a sanity-check).
 * Careful: Correct code will always pass the tests, but not all code that
 * passes these tests is correct.
 * @author talm
 *
 */
public class BitsTest {
	@Test
	public void testReverse() {
		assertEquals(0x0807060504030201L, Bits.byteReverse(0x0102030405060708L));
	}
	
	@Test
	public void testRol() {
		assertEquals(0xe1eaaf31, Bits.rol(0x8f0f5579, 5));
	}
	
	@Test
	public void testInterleave() {
		assertEquals(0xaaaaaaaaaaaaaaaaL, Bits.interleave(0xffffffff, 0));
	}
	
	@Test
	public void testPackStruct() {
		assertEquals(0x4ac080ad, Bits.packStruct((byte) 0x95, false, true, (char) 0x0202));
	}
	
	@Test
	public void testUpdateStruct() {
		assertEquals(0x514080ad, Bits.updateStruct(0x4ac080ad, (byte) 0xa2));
	}
}
