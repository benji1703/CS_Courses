/**
 * 
 */
package hangman;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import hangman.GuessManagerContract.GuessResponse;

/**
 * @author benji1703
 *
 */
public class GuessManagerTest {

	GuessManager guessTest;

	@Before
	public void setUp() throws Exception {
		this.guessTest = new GuessManager("testing", 7);
	}


	/**
	 * Test method for {@link hangman.GuessManager#GuessManager(java.lang.String, int)}.
	 */
	@Test
	public void testGuessManager() {

		assertNotEquals(null, this.guessTest);
		assertEquals(7, this.guessTest.numOfGuessesLeft);
		assertEquals("_______", this.guessTest.hint);
		assertEquals("testing", this.guessTest.word);

		GuessManager otherGuessTest = new GuessManager("benji" , 4);
		assertNotEquals(this.guessTest.numOfGuessesLeft, otherGuessTest.numOfGuessesLeft);
		assertNotEquals(this.guessTest.hint, otherGuessTest.hint);
		assertNotEquals(this.guessTest.word, otherGuessTest.word);


	}

	@Test
	public void testGuessManagerEmptyInput(){

		GuessManager emptyGuess = new GuessManager("", 0);
		assertEquals("", emptyGuess.hint);
		assertEquals("", emptyGuess.word);
		assertEquals(0, emptyGuess.numOfGuessesLeft);
	}
	
	

	@Test
	public void testGuessManagerNullInput(){
		boolean catching = false;
		try {
			@SuppressWarnings("unused")
			GuessManager nullGuess = new GuessManager(null, 0);
		} catch (NullPointerException e) {
			catching = true;

		}
		assertTrue(catching);
	}

	@Test
	public void testGuessManagerNegativeGuessInput(){
		boolean catching = false;
		try {
			@SuppressWarnings("unused")
			GuessManager negativeGuess = new GuessManager("", -1); 
		} catch (IllegalArgumentException  e) {
			catching = true;
		}
		assertTrue(catching);
	}

	/**
	 * Test method for {@link hangman.Gues,hesManager#getBadGuessesLeft()}.
	 */
	@Test
	public void testGetBadGuessesLeft() {

		assertEquals(7, this.guessTest.getBadGuessesLeft());
		this.guessTest.getGuessResponse('t');
		assertEquals(7, this.guessTest.getBadGuessesLeft());
		this.guessTest.getGuessResponse('a');
		assertEquals(6, this.guessTest.getBadGuessesLeft());
		this.guessTest.getGuessResponse('t');
		assertEquals(6, this.guessTest.getBadGuessesLeft());		
		this.guessTest.getGuessResponse('i');
		assertEquals(6, this.guessTest.getBadGuessesLeft());
	}

	@Test
	public void testGetBadGuessesLeftLosing() {

		assertEquals(7, this.guessTest.getBadGuessesLeft());
		this.guessTest.getGuessResponse('a');
		assertEquals(6, this.guessTest.getBadGuessesLeft());
		this.guessTest.getGuessResponse('b');
		assertEquals(5, this.guessTest.getBadGuessesLeft());
		this.guessTest.getGuessResponse('c');
		assertEquals(4, this.guessTest.getBadGuessesLeft());		
		this.guessTest.getGuessResponse('d');
		assertEquals(3, this.guessTest.getBadGuessesLeft());
		this.guessTest.getGuessResponse('f');
		assertEquals(2, this.guessTest.getBadGuessesLeft());
		this.guessTest.getGuessResponse('g'); // g exist in testing
		assertEquals(2, this.guessTest.getBadGuessesLeft());
		this.guessTest.getGuessResponse('j');
		assertEquals(1, this.guessTest.getBadGuessesLeft());
		this.guessTest.getGuessResponse('j');
		assertEquals(0, this.guessTest.getBadGuessesLeft());
		assertEquals(GuessResponse.GUESS_LOSE, this.guessTest.getGuessResponse('a'));
	}

	@Test
	public void testGetBadGuessesLeftWinning() {

		assertEquals(7, this.guessTest.getBadGuessesLeft());
		this.guessTest.getGuessResponse('t');
		assertEquals(7 , this.guessTest.getBadGuessesLeft());
		this.guessTest.getGuessResponse('e');
		assertEquals(7, this.guessTest.getBadGuessesLeft());
		this.guessTest.getGuessResponse('s');
		assertEquals(7, this.guessTest.getBadGuessesLeft());		
		this.guessTest.getGuessResponse('i');
		assertEquals(7, this.guessTest.getBadGuessesLeft());
		this.guessTest.getGuessResponse('n');
		assertEquals(GuessResponse.GUESS_WIN, this.guessTest.getGuessResponse('g'));
	}
	/**
	 * Test method for {@link hangman.GuessManager#getCurrentHint()}.
	 */
	@Test
	public void testGetCurrentHintWinning() {
		assertEquals("_______", this.guessTest.getCurrentHint());
		this.guessTest.getGuessResponse('t');
		assertEquals("t__t___", this.guessTest.getCurrentHint());
		this.guessTest.getGuessResponse('e');
		assertEquals("te_t___", this.guessTest.getCurrentHint());
		this.guessTest.getGuessResponse('s');
		assertEquals("test___", this.guessTest.getCurrentHint());
		this.guessTest.getGuessResponse('i');
		assertEquals("testi__", this.guessTest.getCurrentHint());
		this.guessTest.getGuessResponse('n');
		assertEquals("testin_", this.guessTest.getCurrentHint());
		assertEquals(GuessResponse.GUESS_WIN, this.guessTest.getGuessResponse('g'));
	}

	@Test
	public void testGetCurrentHintLosing() {
		assertEquals("_______", this.guessTest.getCurrentHint());
		this.guessTest.getGuessResponse('z');
		assertEquals("_______", this.guessTest.getCurrentHint());
		this.guessTest.getGuessResponse('y');
		assertEquals("_______", this.guessTest.getCurrentHint());
		this.guessTest.getGuessResponse('x');
		assertEquals("_______", this.guessTest.getCurrentHint());
		this.guessTest.getGuessResponse('w');
		assertEquals("_______", this.guessTest.getCurrentHint());
		this.guessTest.getGuessResponse('j');
		assertEquals("_______", this.guessTest.getCurrentHint());
		this.guessTest.getGuessResponse('f');
		assertEquals("_______", this.guessTest.getCurrentHint());
		this.guessTest.getGuessResponse('r');
		assertEquals("_______", this.guessTest.getCurrentHint());
		assertEquals(GuessResponse.GUESS_LOSE, this.guessTest.getGuessResponse('k'));
	}

	@Test
	public void testGetCurrentHintWorking() {
		assertEquals("_______", this.guessTest.getCurrentHint());
		this.guessTest.getGuessResponse('t');
		assertEquals("t__t___", this.guessTest.getCurrentHint());
		this.guessTest.getGuessResponse('e');
		assertEquals("te_t___", this.guessTest.getCurrentHint());
		this.guessTest.getGuessResponse('s');
		assertEquals("test___", this.guessTest.getCurrentHint());
		this.guessTest.getGuessResponse('i');
		assertEquals("testi__", this.guessTest.getCurrentHint());
		this.guessTest.getGuessResponse('n');
		assertEquals("testin_", this.guessTest.getCurrentHint());
		assertEquals(GuessResponse.GUESS_WIN, this.guessTest.getGuessResponse('g'));
	}

	/**
	 * Test method for {@link hangman.GuessManager#getGuessResponse(char)}.
	 */
	@Test
	public void testGetGuessResponse() {

		assertEquals(GuessResponse.GUESS_GOOD, this.guessTest.getGuessResponse('t'));
		assertEquals(GuessResponse.GUESS_GOOD, this.guessTest.getGuessResponse('e'));
		assertEquals(GuessResponse.GUESS_BAD, this.guessTest.getGuessResponse('y'));
		assertEquals(GuessResponse.GUESS_BAD, this.guessTest.getGuessResponse('y'));
		assertEquals(GuessResponse.GUESS_BAD, this.guessTest.getGuessResponse('x'));
		assertEquals(GuessResponse.GUESS_GOOD, this.guessTest.getGuessResponse('s'));
		assertEquals(GuessResponse.GUESS_GOOD, this.guessTest.getGuessResponse('e'));

	}

}
