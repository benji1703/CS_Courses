

/**
 * 
 * A game between several players.
 * 
 * @author talm
 *
 */

public interface Game {
	/**
	 * A new player has joined the game.
	 * @param player the player who has joined the game
	 * @param time the time at which the player joined.
	 * @return A status update (can be aimed at specific players or at all of them).
	 */
	public StatusUpdate playerJoin(Player player, long time);
	
	/**
	 * A move by one of the players. 
	 * @param player The player who made the move
	 * @param word The word played
	 * @param time The time at which the move was made (in milliseconds from Jan 1, 1970).
	 * @return A status update (can be aimed at specific players or at all of them).
	 */
	public StatusUpdate playerMove(Player player, String word, long time);
	
	/**
	 * A player has aborted.  
	 * This can happen due to network failure, client crashes, etc.
	 * @param player the aborting player. null means all players aborted simultaneously.
	 * @param time the time at which the player aborted.
	 * @return A status update (can be aimed at specific players or at all of them).
	 */
	public StatusUpdate playerAbort(Player player, long time);
	
	/**
	 * Has the game ended?
	 * @return
	 */
	public boolean hasEnded();
}
