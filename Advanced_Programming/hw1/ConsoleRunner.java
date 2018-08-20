import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;





/**
 * 
 * A class that runs a game with input/output on the system console.
 * 
 * @author talm
 *
 */

public class ConsoleRunner {
	Game game;
	
	public ConsoleRunner(Game game) {
		this.game = game;
	} 
	
	public void runGame() {
		// Create a buffered reader from standard input stream so we can read entire lines.
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		
		System.out.println("How many players? ");
		String numString;
		try {
			numString = in.readLine();
		} catch (IOException e) {
			// can't do anything!
			System.err.println(e);
			return;
		}
		
		final int NUM_PLAYERS = Integer.parseInt(numString);
		
		// Use an anonymous class for a "each" player.
		Player players[] = new Player[NUM_PLAYERS];
		
		StatusUpdate update;
		
		for (int i = 0; i < NUM_PLAYERS; ++i) {
			try {
				System.out.println("Hi player " + i + ", what's your name? ");

				final String name = in.readLine();

				// Anonymous inner class can access final variables in enclosing scope.
				players[i] = new Player() {
					@Override
					public String getName() {
						return name;
					}
				};

				update = game.playerJoin(players[i], System.currentTimeMillis());
				// Print the status message.
				outputStatusMessages(update);
			} catch (IOException e) {
				// This should never happen...
				System.err.println(e);
				return;
			}
		}
		
		while (!game.hasEnded()) {
			for (int i = 0; i < NUM_PLAYERS; ++i) {
				System.out.println("Hi " + players[i].getName() +", enter your guess->");
				try { 
					String word = in.readLine();
					update = game.playerMove(players[i], word, System.currentTimeMillis());

					outputStatusMessages(update);
				} catch (IOException e) {
					System.err.println(e);
				}
			}
		}
		
		System.out.println("Game Over!");

	}
	

	/**
	 * Output a status message 
	 * @param status
	 */
	private void outputStatusMessages(StatusUpdate status) {
		Map<Player,List<String>> specifics = status.getSpecificMessages();
		if (specifics != null) {
			for (Player player : specifics.keySet()) {
				List<String> messages = specifics.get(player);
				if (messages != null) {
					System.out.println("<--- Messages for: " + player.getName() + " --->");
				
					for (String msg : messages) {
						System.out.println(msg);
					}
					System.out.println();
				}
			}
		}
		List<String> defaultMessages = status.getMessages(); 
		if (defaultMessages != null) {
			System.out.println("<--- Messages for everyone else --->");
			for (String msg : defaultMessages) {
				System.out.println(msg);
			}
			System.out.println();
		}
	}
	
}
