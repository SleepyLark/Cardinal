package card.model;

import card.controller.CardController;
import java.util.ArrayList;

public class GameMaster
{
	private CardController mainApp;
	private ArrayList<Player> playerOrder;
	private int currentTurn;
	private int turnCount;
	
	public GameMaster(CardController app)
	{
		mainApp = app;
		playerOrder = new ArrayList<Player>();
		currentTurn = 0;
		turnCount = 0;
	}
	
	public void addToGame(Player personPlaying)
	{
		playerOrder.add(personPlaying);
	}
	
	public void addToGame(int order, Player personPlaying)
	{
		playerOrder.add(order, personPlaying);
	}
	
	public Player getCurrentPlayer()
	{
		return playerOrder.get(currentTurn);
	}
	
	public void setCurrentPlayer(Player personsTurn)
	{
		currentTurn = playerOrder.indexOf(personsTurn);
	}
	
	public ArrayList<Player> getPlayers()
	{
		return playerOrder;
	}
	
	public void next()
	{
		currentTurn++;
		if(currentTurn >= playerOrder.size())
		{
			currentTurn = 0;
		}
		turnCount++;
	}
	
	public int getTurnCount()
	{
		return turnCount;
	}
	
	public void reset()
	{
		currentTurn = 0;
		turnCount = 0;
	}
}
