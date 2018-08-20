package hangman;

import static hangman.GuessManagerContract.GuessResponse.*;
import hangman.GuessManagerContract.GuessResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
	final static char[][] hangman = { 
		   "  _________     ".toCharArray(),
		   " |         |    ".toCharArray(),
		   " |         0    ".toCharArray(),
		   " |        /|\\   ".toCharArray(),
		   " |        / \\   ".toCharArray(),
		   " |              ".toCharArray(),
		   " |              ".toCharArray() 
		   };
	
	final static AsciiPicture head = new AsciiPicture(1, 1, 11, 2, hangman);
	final static AsciiPicture leftArm = new AsciiPicture(1, 1, 10, 3, hangman);
	final static AsciiPicture body = new AsciiPicture(1, 1, 11, 3, hangman);
	final static AsciiPicture rightArm = new AsciiPicture(1, 1, 12, 3, hangman);
	final static AsciiPicture leftLeg = new AsciiPicture(1, 1, 10, 4, hangman);
	final static AsciiPicture rightLeg = new AsciiPicture(1, 1, 12, 4, hangman);
	
	final static AsciiPicture gallowsVertical = new AsciiPicture(1, hangman.length - 1, 1, 1, hangman);
	final static AsciiPicture gallowsHorizontal = new AsciiPicture(10, 2, 2, 0, hangman);
	
	final static AsciiPicture[] badGuesses = {
		head, body, leftArm, rightArm, leftLeg, rightLeg 
	};
	
	
	public static void main(String[] args) {
		// This is the "canvas" on which the hangman picture will be slowly drawn
		AsciiPicture hangman = new AsciiPicture(Main.hangman[0].length, Main.hangman.length, ' ');
		
		// The gallows are pre-drawn
		hangman.overlay(gallowsVertical, 1, 1, ' ');
		hangman.overlay(gallowsHorizontal, 2, 0, ' ');
		
		// The secret word is given on the command line, or a default is used.
		String word = args.length > 0 ? args[0] : "antidisestablishmentarianism";
		GuessManager gm = new GuessManager(word, badGuesses.length - 1);
		
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		
		try	{
			
			GuessResponse resp = GUESS_GOOD; 
			while (resp != GUESS_WIN && resp != GUESS_LOSE) {
				// print the current state of the hangman picture and the current hint
				hangman.print(System.out);
				System.out.println(gm.getCurrentHint());
				
				// read a letter from the user (we only use the first letter of the line)
				System.out.print("Guess a letter ->");
				String input = in.readLine();
				char inChar = input.charAt(0);
				
				// Run the code to check the guess
				resp = gm.getGuessResponse(inChar);
				
				// Compute the message that will be output to the user.
				String message = "";
				switch(resp) {
				case GUESS_BAD:
					message = "Sorry, try again";
					break;
				case GUESS_GOOD:
					message = "Good guess!";
					break;
				case GUESS_LOSE:
					message = "Sorry, you lost. Better luck next time.";
					break;
				case GUESS_WIN:
					message = "Congratulations! You won!!";
					break;
				}
				System.out.println(message);
				
				// Update the hangman picture by overlaying the latest part of the hangman
				// (we don't care if we overlay the same picture multiple times, it won't change
				// the output).
				int bg = badGuesses.length - gm.getBadGuessesLeft() - 2;
				if (bg >= 0)
					hangman.overlay(badGuesses[bg], badGuesses[bg].leftX, badGuesses[bg].topY, ' ');
			}

			// Output the final state of the hangman and hint.
			System.out.println();
			hangman.print(System.out);
			System.out.println(gm.getCurrentHint());
		} catch (IOException e) {
			e.printStackTrace(System.err);
		}
	}
}
