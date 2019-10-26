package card.model.games;

import java.util.Scanner;

import card.controller.CardController;
import card.model.bots.TrashBot;
import card.model.cards.PlayingCard;
import card.model.dealers.StandardDealer;
import card.model.players.StandardPlayer;

public class GarbageMaster extends GameMaster
{

	private CardController app;
	private int[] playerHandSize;
	private int maxHandSize;
	private StandardDealer deck;
	/**
	 * for debugging, subjected to change
	 */
	private Scanner consoleIn;

	public GarbageMaster(CardController app)
	{
		super();
		this.app = app;
		this.maxHandSize = 7;
		deck = new StandardDealer(app);
		consoleIn = new Scanner(System.in);
	}

	@Override
	public void startGame()
	{
		
		app.out("Enter name:");
		StandardPlayer playerOne = new StandardPlayer(consoleIn.nextLine());
		this.addToGame(playerOne);
		
		app.out("How many players?");
		int cpuPlayers = consoleIn.nextInt();
		for(int times = 0; times < cpuPlayers; times++)
		{
			this.addToGame(new TrashBot(null));
		}
	
		if (this.numberOfPlayers() < 2)
		{
			app.out("Invalid number of players.  Please add players before starting the game");
		}
		else
		{
			playerHandSize = new int[this.numberOfPlayers()];
			for (int index : playerHandSize)
			{
				playerHandSize[index] = maxHandSize;
			}
			
			deck.buildDeck();
			deck.dealCards(this.getPlayers(), maxHandSize);
			deck.discardACard(deck.drawACard());
			
			app.out("Discard Pile: " + deck.getDiscardPile().get(0));
			app.out("It is now "+this.getCurrentPlayer()+"'s turn.");
			if(this.getCurrentPlayer() == playerOne)
			{
				app.out("Which deck do you want to draw?");
				String response = consoleIn.next();
				if(response.equals("discard"))
				{
				
					this.evaluteCard((PlayingCard)deck.getDiscardPile().get(0));
				}
			}
			
			
			
		}
	}
	
	private boolean evaluteCard(PlayingCard cardToCheck)
	{
		boolean works = false;
		
		if(cardToCheck.getNumber() > playerHandSize[this.getCurrentTurn()] || cardToCheck.getNumber() == PlayingCard.JACK)
		{
		
		}
		
		return works;
	}
}
