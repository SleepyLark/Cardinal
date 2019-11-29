package card.model.players;

import java.util.ArrayList;

import card.model.cards.Card;
import card.model.cards.PlayingCard;
import card.model.cards.PlayingCard.Suit;
import card.model.players.StandardPlayer.Type;

/**
 * Skills need for any card game
 * @author Skyler
 *
 */
public abstract class Player
{
	private String username;
	private int winCount;
	private int loseCount;
	protected ArrayList<Card> currentHand;
	
	public Player(String username)
	{
		this.username = username;
		currentHand = new ArrayList<Card>();
		winCount = 0;
		loseCount = 0;
	}
	
	public void setUsername(String username)
	{
		this.username = username;
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
	
	public int getHandSize()
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
		return currentHand.get(getHandSize()-1);
	}
	
	public boolean isBot()
	{
		return false;
	}
	
	public String toString()
	{
		return username;
	}
	
}
