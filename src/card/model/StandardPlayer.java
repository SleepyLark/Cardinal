package card.model;

import java.util.ArrayList;

public class StandardPlayer implements Player
{
	
	private String username;
	private int winCount;
	private int loseCount;
	private ArrayList<Card> currentHand;
	
	public enum Type
	{
		SUIT, NUMBER;
	}
	
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
	
	public int getSizeOfHand()
	{
		return currentHand.size();
	}
	public void addToHand(Card cardToAdd)
	{
		currentHand.add(cardToAdd);
	}
	
	public Card playCard(int index)
	{
		return currentHand.remove(index);
	}
	
	public Card playCard(Card cardToPlay)
	{
		currentHand.remove(cardToPlay);
		return cardToPlay;
	}
	
	public Card discardCard(int index)
	{
		return currentHand.remove(index);
	}
	
	public Card discardCard(Card cardToDiscard)
	{
		currentHand.remove(cardToDiscard);
		return cardToDiscard;
	}
	
	public Card getLastDrawnCard()
	{
		return currentHand.get(getSizeOfHand()-1);
	}
	
	public void organizeHand(Type sortBy)
	{
		if(sortBy == Type.NUMBER)
		{
			for(int index = 0; index < currentHand.size(); index ++)
			{
				for(int nextIndex = index+1; nextIndex < currentHand.size(); nextIndex++)
				{
					int currentNumber = ((PlayingCard) currentHand.get(index)).getNumber();
					int nextNumber = ((PlayingCard) currentHand.get(nextIndex)).getNumber();
					if(nextNumber < currentNumber)
					{
						Card cardToSwap = currentHand.remove(nextIndex);
						Card firstCard = currentHand.remove(index);
						currentHand.add(index, cardToSwap);
						currentHand.add(nextIndex, firstCard);
					}
				}
			}
		}
	}
	
	public String toString()
	{
		return username;
	}
}
