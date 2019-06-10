package card.controller;

import card.model.*;
import card.model.PlayingCard.Suit;
import card.model.StandardPlayer.Type;


public class CardController
{

	private Dealer luigi;
	private GoFishMaster toad;
	private StandardPlayer playerOne;
	private StandardPlayer playerTwo;
	
	public CardController()
	{
		luigi = new Dealer(this);
		toad = new GoFishMaster(this);
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
		
		out(playerOne.getCurrentHand());
		playerOne.organizeHand(Type.NUMBER);
		out(playerOne.getCurrentHand());
				
	}
	
	public void start()
	{
		//dummyScenario();
	}
	
	public void out(Object message)
	{
		System.out.println(message);
	}
	
	public void dummyScenario()
	{
		out("Current Players:");
		out(toad.getPlayers() + "");
		out(playerOne.getUsername()+"'s hand:");
		out(playerOne.getCurrentHand());
		out(playerTwo.getUsername()+"'s hand:");
		out(playerTwo.getCurrentHand());
		out(toad.getCurrentPlayer()+" starts.");
		playerOne.addToHand(luigi.drawACard());
		out(toad.getCurrentPlayer()+" draws a card.");
		out(toad.getCurrentPlayer()+" drew a(n) " + toad.getCurrentPlayer().getCurrentHand().get(toad.getCurrentPlayer().getSizeOfHand()-1));
		out(toad.getCurrentPlayer().getCurrentHand());
		out(toad.getCurrentPlayer() + " plays a(n) "+ luigi.discardACard(toad.getCurrentPlayer().discardCard(luigi.getRandomInt(0,toad.getCurrentPlayer().getSizeOfHand()))));
		out(luigi.getDiscardPile());
		toad.next();
		out("It's now "+toad.getCurrentPlayer()+"'s turn");
		out(toad.getCurrentPlayer()+" draws a card.");
		out(toad.getCurrentPlayer()+" drew a(n) " + toad.getCurrentPlayer().getCurrentHand().get(toad.getCurrentPlayer().getSizeOfHand()-1));
		out(toad.getCurrentPlayer().getCurrentHand());
		out(toad.getCurrentPlayer() + " ends their turn.");
		toad.next();
		out("It is now "+toad.getCurrentPlayer()+"'s turn."); 
		out(toad.getCurrentPlayer()+ " flips the table in rage.");
		toad.next();
		out("It looks like " + toad.getCurrentPlayer() + " is the winner");
		toad.getCurrentPlayer().winner();
		out(playerOne + ": " + playerOne.getWinCount());
		out(playerTwo + ": " + playerTwo.getWinCount());
		
	}
	
}
