package hangman;

public interface GuessManagerContract {

	public static final char NON_MATCH = '_';
	
	public enum GuessResponse {
		/**
		 * The guess was correct, but not yet a winning guess.
		 */
		GUESS_GOOD, 
		
		/**
		 * The guess was incorrect, but not yet a losing guess.
		 */
		GUESS_BAD,
		
		/**
		 *  The guess was incorrect and the game was lost.
		 */
		GUESS_LOSE, 
		
		/**
		 * The guess was correct and the game was won.
		 */
		GUESS_WIN,  
	}
	
	/** 
	 * Return the number of guesses left before the player loses.
	 */
	public int getBadGuessesLeft();
	
	/**
	 * Return a string that has the letters guessed so far in their correct positions and
	 * {@link #NON_MATCH} in the other positions.
	 */
	public String getCurrentHint();
	
	/**
	 * This method checks whether a letter is part of the word. If it was,
	 * it updates the current hint, otherwise it decrement the number of guesses left.
	 * 
	 * @param letter The letter that was guessed.
	 * @return One of the {@link GuessResponse} responses, as specified in their documentation.
	 */
	public GuessResponse getGuessResponse(char letter);
	
	
}
