package card.model.bots;

import card.model.cards.Card;

public class UnoBot extends Bot
{
	
	public static int DRAW_A_CARD = -1;
	public UnoBot(String username)
	{
		super(username);
	}
	
	/**
	 * determines which card to play
	 * @param cardInDiscard
	 * @return the index of the card it wants to play, '-1' means to draw a card
	 */
	public int processTurn(Card cardInDiscard)
	{
		return DRAW_A_CARD;
	}
}
