package card.model;

import java.util.ArrayList;

public interface Player
{
	public String getUsername();
	public int getWinCount();
	public void winner();
	public void loser();
	public int getLoseCount();
	public ArrayList<Card> getCurrentHand();
	public void addToHand(Card cardToAdd);
	public void discardCard(int index);
	
}
