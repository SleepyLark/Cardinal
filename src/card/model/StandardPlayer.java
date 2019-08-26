package card.model;

import java.util.ArrayList;

import card.model.PlayingCard.Suit;
/**
 * Attributes needed to play standard card games
 * @author Skyler
 *
 */
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
	
	/**
	 * Adds a card to a specific index
	 * @param index where the card should be added
	 * @param cardToAdd the card to add
	 */
	public void addToHand(int index, Card cardToAdd)
	{
		currentHand.add(index, cardToAdd);
	}
	
	/**
	 * Adds a list of cards to the players hand (uses the ArrayList<>.addAll() method)
	 * @param cardsToAdd list of cards to add
	 */
	public void addToHand(ArrayList<PlayingCard> cardsToAdd)
	{
		currentHand.addAll(cardsToAdd);
	}
	
	/**
	 * removes a card from the players hand and puts it into "play" 
	 * @param index the index of the card you want to play
	 * @return a reference of the card that was removed
	 */
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
	
	public Card pickCard(Card cardToPick)
	{
		if(currentHand.indexOf(cardToPick) < 0)
		cardToPick = null;
		
		return cardToPick;
	}
	
	/**
	 * 
	 */
	public Card pickCard(int index)
	{
		return currentHand.get(index);
	}
	
	/**
	 * gets the last card added to the hand *NOTE: needs to be modified better*
	 */
	public Card getLastDrawnCard()
	{
		return currentHand.get(getSizeOfHand()-1);
	}
	
	/**
	 * Sorts the "cards" by a selected type
	 * @param sortBy Type.NUMBER = least to greatest <br> Type.SUIT = card suit
	 */
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
		else if(sortBy == Type.SUIT)
		{
			for(int index = 0; index < currentHand.size(); index ++)
			{
				Suit currentSuit = ((PlayingCard) currentHand.get(index)).getSuit();
				int currentNumber = ((PlayingCard) currentHand.get(index)).getNumber();
				int nextSlot = index +1;
				for(int nextIndex = index+1; nextIndex < currentHand.size(); nextIndex++)
				{
					Suit nextSuit = ((PlayingCard) currentHand.get(nextIndex)).getSuit();

					int nextNumber = ((PlayingCard) currentHand.get(nextIndex)).getNumber();
					if(nextSuit == currentSuit)
					{
						Card cardToInsert = currentHand.remove(nextIndex);
						if(nextNumber > currentNumber)
						currentHand.add(nextSlot, cardToInsert);
						else
						currentHand.add(index,cardToInsert);
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
