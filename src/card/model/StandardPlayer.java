package card.model;

import java.util.ArrayList;

public class StandardPlayer implements Player
{
	
	private String username;
	private int winCount;
	private int loseCount;
	private ArrayList<Card> currentHand;
	
	public StandardPlayer(String username)
	{
		this.username = username;
		currentHand = new ArrayList<Card>();
		winCount = 0;
		loseCount = 0;
	}

	public String getUsername()
	{
	
		return username;
	}


	public int getWinCount()
	{

		return winCount;
	}


	public int getLoseCount()
	{
		return loseCount;
	}
	
	public void winner()
	{
		winCount++;
	}
	
	public void loser()
	{
		loseCount++;
	}
	
	public ArrayList<Card> getCurrentHand()
	{
		return currentHand;
	}
	
	public void addToHand(Card cardToAdd)
	{
		currentHand.add(cardToAdd);
	}
	
	public void discardCard(int index)
	{
		currentHand.remove(index);
	}
	
	public String toString()
	{
		return username;
	}
}
