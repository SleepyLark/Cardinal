package card.model.bots;

import card.model.cards.Card;
import card.model.players.StandardPlayer;

public class TrashBot extends StandardBot
{
	public static int DRAW_A_CARD = 0;
	public static int TAKE_FROM_DISCARD = 1;

	public TrashBot(String username)
	{
		super(username);

	}
	
	public int turn(Card lastCardDiscarded, boolean[] currentHand)
	{
		return DRAW_A_CARD;
	}
	
	public Card processJack(boolean[] currentHand)
	{
		for(int index = 0; index < currentHand.length; index++)
		{
			if(!currentHand[index])
			return this.discardCard(index);
		}
		
		return null;
		
	}
	
}
