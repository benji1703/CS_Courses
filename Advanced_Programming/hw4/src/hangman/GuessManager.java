package hangman;

import hangman.GuessManagerContract.GuessResponse;

public class GuessManager implements GuessManagerContract{


	String word;
	String hint;
	int numOfGuessesLeft;

	/** 
	 * Constructor
	 * @param word - the word to guess
	 * @param i - number of Guesses Left
	 */
	public GuessManager(String word, int i) {

		if (i < 0) {
			throw new IllegalArgumentException("Incorect guess number (must be positive)");
		}
		
		this.word = word;
		this.hint = "";
		this.numOfGuessesLeft = i;
		int counter = 0;
		while (counter < word.length()) { // Create empty hint line
			this.hint += NON_MATCH;
			counter++;
		}
					
	}

	/** 
	 * Return the number of guesses left before the player loses.
	 */
	@Override
	public int getBadGuessesLeft() {

		return this.numOfGuessesLeft;

	}

	/**
	 * Return a string that has the letters guessed so far in their correct positions and
	 * {@link #NON_MATCH} in the other positions.
	 * Note that even if the guess was case-insensitive, the 
	 */
	@Override
	public String getCurrentHint() {

		return this.hint;

	}

	/**
	 * This method checks whether a letter is part of the word. If it was,
	 * it updates the current hint, otherwise it decrement the number of guesses left.
	 * 
	 * @param letter The letter that was guessed.
	 * @return One of the {@link GuessResponse} responses, as specified in their documentation.
	 */
	@Override
	public GuessResponse getGuessResponse(char letter) {

		StringBuilder tempHint = new StringBuilder(); // for the creation and update of HINT
		tempHint.setLength(this.word.length()); // Create the StringBuilder length based of the word
		
		if (word.indexOf(letter) == -1) { 		// If not found in word
			this.numOfGuessesLeft--;			// Decrement if not found in word

			if (this.numOfGuessesLeft < 0) { 	// Check if we have, after this try, any Guesses Left
				return GuessResponse.GUESS_LOSE;// If not - WE LOSE :(
			}

			else return GuessResponse.GUESS_BAD;// If we have another try(s), this is a BAD GUESS
		}

		else { 									// We need to deal with the "hint" word
			for (int i = 0; i < word.length(); i++) {
				if (word.charAt(i) == letter) { // If this is in the right place, show hint
					tempHint.setCharAt(i, letter);
				}
				else {							// Continue looking but fill the SB
					tempHint.setCharAt(i, this.hint.charAt(i));
				}

			}

			this.hint = tempHint.toString();
			
			if (this.hint.indexOf(NON_MATCH) == -1) { // If no "Empty _" meaning all is revealed
				return GuessResponse.GUESS_WIN;
			}
			else return GuessResponse.GUESS_GOOD;	// This is a GOOD GUESS - IF FOUND
		}

	}

}
