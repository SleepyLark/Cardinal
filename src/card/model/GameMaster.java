package card.model;

import card.controller.CardController;
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
	public Player getCurrentPlayer()
	{
		return playerOrder.get(currentTurn);
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
}
