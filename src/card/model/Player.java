package card.model;

import java.util.ArrayList;

/**
 * Skills need for any card game
 * @author Skyler
 *
 */
public interface Player
{
	public String getUsername();
	public int getWinCount();
	public void winner();
	public void loser();
	public int getLoseCount();
	public ArrayList<Card> getCurrentHand();
	public int getSizeOfHand();
	public void addToHand(Card cardToAdd);
	public Card getLastDrawnCard();
	public Card discardCard(int index);
	public Card discardCard(Card cardToDiscard);
	public Card playCard(int index);
	public Card playCard(Card cardToPlay);
	public Card pickCard(Card cardToPick);
	public Card pickCard(int index);
	
}
