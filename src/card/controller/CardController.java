package card.controller;

import card.model.*;
import card.model.PlayingCard.Suit;


public class CardController
{

	private Dealer luigi;
	
	public CardController()
	{
		luigi = new Dealer(this);
		
		luigi.buildStandardDeck(false);
		
		luigi.shuffleCards();
				
	}
	
	public void start()
	{
		System.out.println(luigi.getDrawDeck());
		luigi.discardACard(luigi.drawACard());
		System.out.println(luigi.getDrawDeck());
		
	}
}
