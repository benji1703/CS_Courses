
public class ConsoleGameTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		NumberGuessingGame game = new NumberGuessingGame();
		
		ConsoleRunner runner = new ConsoleRunner(game);
		
		runner.runGame();
	}

}
