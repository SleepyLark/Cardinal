package card.model.bots;

import card.model.cards.Card;
import card.model.cards.PlayingCard;
import card.model.dealers.Dealer;

public class TrashBot extends Bot
{
	public static int DRAW_A_CARD = 0;
	public static int TAKE_FROM_DISCARD = 1;

	public TrashBot(String username)
	{
		super(username);

	}

	public int turn(Card lastCardDiscarded, boolean[] currentHand)
	{
		int cardNum = ((PlayingCard) lastCardDiscarded).getNumber();
		if ((cardNum < this.getHandSize() && !currentHand[cardNum - 1]) 
				|| cardNum == PlayingCard.JACK)
			return TAKE_FROM_DISCARD;
		else
			return DRAW_A_CARD;
	}

	public int processJack(boolean[] currentHand, int maxSize)
	{
		int cardSlot = 0;

		while (currentHand[cardSlot])
			cardSlot = Dealer.randomInt(0, maxSize);

		return cardSlot;

	}

}
