

public class NumberGuessingGame implements Game {
	int number;
	
	boolean wasGuessed;
	
	public NumberGuessingGame() {
		// Choose a new random number to guess between 1 and 100
		number = (int) (Math.random() * 100);
		wasGuessed = false;
	}
	

	@Override
	public StatusUpdate playerJoin(Player player, long time) {
		StatusUpdate update = new StatusUpdate();
		update.addMessage(player, "Hello " + player.getName() + ", guess a number between 1 and 100!");
		return update;
	}

	@Override
	public StatusUpdate playerMove(Player player, String word, long time) {
		StatusUpdate update = new StatusUpdate();

		int guess;
		
		// ** benjamin.arbibe **
		// if NaN - update message for user (Try and Catch)
		
		try {
			guess = Integer.parseInt(word);
		} catch (NumberFormatException e) {
			update.addMessage(word + " - this input is not valid, TRY AGAIN"); 
			return update;
		}
		
		// ** benjamin.arbibe **
		
		if (guess == number) {
			update.addMessage("Yay! " + player.getName() + " has guessed the number!");
			wasGuessed = true;
		} else if (guess > number) {
			update.addMessage(player, "Your guess is too high");
		} else {
			update.addMessage(player, "Your guess is too low");
		}
		return update;
	}

	@Override
	public StatusUpdate playerAbort(Player player, long time) {
		// Do nothing, we don't really care.
		return null;
	}


	@Override
	public boolean hasEnded() {
		return wasGuessed;
	}

}
