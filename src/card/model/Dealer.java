package card.model;

import java.util.ArrayList;

import card.controller.CardController;
import card.model.PlayingCard.Suit;

/**
 * Source of the main deck of cards
 * @author Skyler
 *
 */
public abstract class Dealer
{
	protected ArrayList<Card> drawDeck;
	protected ArrayList<Card> discardPile;
	private CardController app;

	public Dealer(CardController app)
	{
		this.app = app;

		drawDeck = new ArrayList<Card>();
		discardPile = new ArrayList<Card>();

	}
	
	/**
	 * builds the deck of cards needed for the game;
	 */
	public abstract void buildDeck();

	/**
	 * "Shuffles" the deck <br><i>maybe do some more complex shuffling?</i>
	 */
	private void shuffle()
	{
		ArrayList<Card> temp = new ArrayList<Card>();

		while (!drawDeck.isEmpty())
		{
			temp.add(drawDeck.remove(getRandomInt(0, drawDeck.size())));
		}
		
		drawDeck = temp;
	}
	
	/**
	 * puts the Discard pile back into the draw deck and shuffles it
	 */
	public void reshuffleDiscardPile()
	{
		while(!discardPile.isEmpty())
		{
			drawDeck.add(discardPile.remove(0));
		}
		shuffleCards();
	}

	/**
	 * "shuffles" the cards a random amount of times
	 */
	public void shuffleCards()
	{
		int shuffleAmount = getRandomInt(1,5);
		
		for(int times = 0; times < shuffleAmount; times++)
		{
			shuffle();
		}
	}
	
	public void dealCardsToPlayers(ArrayList<Player> players, int numberOfCardsToDeal)
	{
		
	}

	/**
	 * get a random integer (faster than doing Math.random() and casting it).
	 * @param min the lowest possible value
	 * @param max the highest possible value
	 * @return a random integer
	 */
	public static int getRandomInt(int min, int max)
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
	
	public Card discardACard(Card discard)
	{
		discardPile.add(discard);
		return discard;
	}
	
	public int getDrawDeckSize()
	{
		return drawDeck.size();
	}
	
	public int getDiscardPileSize()
	{
		return discardPile.size();
	}

}
