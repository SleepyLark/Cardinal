package card.controller;

import card.model.*;
import card.model.PlayingCard.Suit;


public class CardController
{

	private PlayingCard test;
	
	public CardController()
	{
		test = new PlayingCard(Suit.CLUBS, PlayingCard.ACE);
				
	}
	
	public void start()
	{
		System.out.println(test);
	}
}
