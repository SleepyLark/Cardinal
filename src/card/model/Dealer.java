package card.model;

import java.util.ArrayList;

import card.controller.CardController;
import card.model.PlayingCard.Suit;

public class Dealer
{
	private ArrayList<Card> drawDeck;
	private ArrayList<Card> discardPile;
	private CardController app;

	public Dealer(CardController app)
	{
		this.app = app;

		drawDeck = new ArrayList<Card>();
		discardPile = new ArrayList<Card>();

	}

	public void buildStandardDeck(Boolean includeJokers)
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

	private void shuffle()
	{
		ArrayList<Card> temp = new ArrayList<Card>();

		while (!drawDeck.isEmpty())
		{
			temp.add(drawDeck.remove(getRandomInt(0, drawDeck.size())));
		}
		
		drawDeck = temp;
	}
	
	public void reshuffleDiscardPile()
	{
		drawDeck = discardPile;
		discardPile.clear();
		shuffleCards();
	}
	
	public void shuffleCards()
	{
		int shuffleAmount = getRandomInt(1,5);
		
		for(int times = 0; times < shuffleAmount; times++)
		{
			shuffle();
		}
	}

	private int getRandomInt(int min, int max)
	{
		return (int) (Math.random() * max) + min;
	}

	public ArrayList<Card> getDrawDeck()
	{
		return drawDeck;
	}
	
	public ArrayList<Card> getDiscardPile()
	{
		return discardPile;
	}
	
	public Card drawACard()
	{
		return drawDeck.remove(0);
	}
	
	public void discardACard(Card discard)
	{
		discardPile.add(discard);
	}

}
