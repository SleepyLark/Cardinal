package card.model;

import card.controller.CardController;
import card.model.PlayingCard.Suit;

public class StandardDealer extends Dealer
{

	public StandardDealer(CardController app)
	{
		super(app);
	
	}
	
	/**
	 * Builds a new deck with all 52 cards found in a standard card deck
	 * @param includeJokers  Adds two joker cards
	 */
	public void buildDeck(Boolean includeJokers)
	{
		Suit[] suits = { Suit.CLUBS, Suit.DIAMONDS, Suit.HEARTS, Suit.SPADES };

		if (includeJokers)
		{
			drawDeck.add(new PlayingCard(Suit.JOKER, PlayingCard.JOKER));
			drawDeck.add(new PlayingCard(Suit.JOKER, PlayingCard.JOKER));
		}
		for (Suit currentSuit : suits)
		{
			for (int number = 1; number <= PlayingCard.KING; number++)
			{
				drawDeck.add(new PlayingCard(currentSuit, number));
			}
		}
	}

	@Override
	public void buildDeck()
	{
		buildDeck(false);
	}
	
}
