package card.model.bots;

import card.model.cards.Card;
import card.model.dealers.Dealer;
import card.model.players.StandardPlayer;

public class TrashBot extends StandardBot
{
	public static int DRAW_A_CARD = 0;
	public static int TAKE_FROM_DISCARD = 1;

	public TrashBot(String username)
	{
		super(username);

	}
	
	public int turn(Card lastCardDiscarded, boolean[] currentHand )
	{
		return DRAW_A_CARD;
	}
	
	public int processJack(boolean[] currentHand, int maxSize)
	{
		int cardSlot = 0;
		
		while(currentHand[cardSlot])
			cardSlot = Dealer.randomInt(0, maxSize);
		
		return cardSlot;
		
	}
	
}
