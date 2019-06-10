package card.controller;

import card.model.*;
import card.model.PlayingCard.Suit;


public class CardController
{

	private Dealer luigi;
	private GameMaster toad;
	private StandardPlayer playerOne;
	private StandardPlayer playerTwo;
	
	public CardController()
	{
		luigi = new Dealer(this);
		toad = new GameMaster(this);
		playerOne = new StandardPlayer("Waluigi");
		playerTwo = new StandardPlayer("Yoshi");
		
		luigi.buildStandardDeck(false);
		luigi.shuffleCards();
		
		for(int times = 0; times < 7; times++)
		{
			playerOne.addToHand(luigi.drawACard());
			playerTwo.addToHand(luigi.drawACard());
		}
		
		toad.addToGame(playerOne);
		toad.addToGame(playerTwo);
				
	}
	
	public void start()
	{
		out("Current Players:");
		out(toad.getPlayers() + "");
		out(playerOne.getUsername()+"'s hand:");
		out(playerOne.getCurrentHand());
		out(playerTwo.getUsername()+"'s hand:");
		out(playerTwo.getCurrentHand());
		
	}
	
	public void out(Object message)
	{
		System.out.println(message);
	}
}
