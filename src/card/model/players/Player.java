package card.model.players;

import java.util.ArrayList;

import card.model.cards.Card;

/**
 * Skills need for any card game
 * @author Skyler
 *
 */
public interface Player
{
	public void setUsername(String username);
	public String getUsername();
	
	public int getWinCount();
	public void winner();
	public void loser();
	public int getLoseCount();
	
	public ArrayList<Card> getCurrentHand();
	public int getHandSize();
	public void addToHand(int index, Card cardToAdd);
	public void addToHand(Card cardToAdd);
	
	public Card getLastDrawnCard();
	public Card discardCard(int index);
	public Card discardCard(Card cardToDiscard);
	public Card playCard(int index);
	public Card playCard(Card cardToPlay);
	public Card pickCard(Card cardToPick);
	public Card pickCard(int index);
	
	public boolean isBot();
	
}
