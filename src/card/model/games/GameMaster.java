package card.model.games;

import card.controller.CardController;
import card.model.players.Player;

import java.util.ArrayList;

/**
 * in charge of gameplay
 * @author Skyler
 *
 */
public abstract class GameMaster
{
	private ArrayList<Player> playerOrder;
	private int currentTurn;
	private int turnCount;
	
	public GameMaster()
	{
		playerOrder = new ArrayList<Player>();
		currentTurn = 0;
		turnCount = 0;
	}
	
	/**
	 *  adds a player to the game
	 * @param personPlaying
	 */
	public void addToGame(Player personPlaying)
	{
		playerOrder.add(personPlaying);
	}
	
	/**
	 * adds a player in a specific order
	 * @param order order number it should be in
	 * @param personPlaying
	 */
	public void addToGame(int order, Player personPlaying)
	{
		playerOrder.add(order, personPlaying);
	}
	
	/**
	 * get the current players turn
	 * @return whose turn it is
	 */
	public Player currentPlayer()
	{
		return playerOrder.get(currentTurn);
	}
	
	/**
	 * get's a player from the roaster based on where it's located in the list
	 * @param index position in the turn order
	 * @return the player from that position
	 */
	public Player getPlayer(int index)
	{
		return playerOrder.get(index);
	}
	
	/**
	 * set whose turn it is based off of player
	 * @param personsTurn the player whose turn it should be
	 */
	public void setCurrentPlayer(Player personsTurn)
	{
		currentTurn = playerOrder.indexOf(personsTurn);
	}
	
	/**
	 * returns the list of players
	 * @return all players in game
	 */
	public ArrayList<Player> getPlayers()
	{
		return playerOrder;
	}
	
	public int numberOfPlayers()
	{
		return playerOrder.size();
	}
	
	/**
	 * Additional items needed to play the game
	 */
	public abstract void startGame();
	
	
	/**
	 * switches to the next player
	 * <br><i> uses a "modulus" effect where the number will cycle back to zero once it hits max number. useful for readability</i>
	 */
	public void next()
	{
		currentTurn++;
		if(currentTurn >= playerOrder.size())
		{
			currentTurn = 0;
		}
		turnCount++;
	}
	
	/**
	 * Gets the current turn index
	 * @return the index that references the current player
	 */
	public int getCurrentTurn()
	{
		return currentTurn;
	}
	
	/**
	 * 
	 * @return how many turns have passed
	 */
	public int getTurnCount()
	{
		return turnCount;
	}
	
	/**
	 * reset turn count and current turn. essentially restarts the game with the same players.
	 */
	public void reset()
	{
		currentTurn = 0;
		turnCount = 0;
	}
	
	public boolean validInt(String maybeInt)
	{
		boolean valid = false;
		try
		{
			Integer.parseInt(maybeInt);
			valid = true;
		}
		catch(Exception e)
		{
			
		}
		
		return valid;
	}
}