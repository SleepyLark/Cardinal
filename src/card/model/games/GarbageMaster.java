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
	/**
	 * tells if the card has been flipped over or not
	 */
	private boolean[][] playerHandStatus;
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
			
			playerHandStatus = new boolean[this.numberOfPlayers()][maxHandSize];
			
			for (int player = 0; player < playerHandStatus.length; player++)
			{
				for(int index = 0; index < playerHandStatus[0].length; index++)
				{
					playerHandStatus[player][index] = false;
				}
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
				
					this.evaluteCard((PlayingCard)deck.drawFromDiscard());
				}
				else
				{
					this.evaluteCard((PlayingCard)deck.drawACard());
				}
			}
			
			if(wonRound(this.getCurrentTurn()))
			{
				app.out(this.getCurrentPlayer() +" has won the round!");
			}
			
			this.next();
			
			
			
		}
	}
	
	private boolean evaluteCard(PlayingCard cardToCheck)
	{
		boolean works = false;
		
		if(cardToCheck.getNumber() <= playerHandSize[this.getCurrentTurn()] || cardToCheck.getNumber() == PlayingCard.JACK)
		{
			
			int cardSlot = cardToCheck.getNumber()-1;
			if(cardToCheck.getNumber() == PlayingCard.JACK)
			{
				app.out("Lucky you! You got a Jack! Where do you want to put it?");
				cardSlot = consoleIn.nextInt();
				if(playerHandStatus[this.getCurrentTurn()][cardSlot] == true)
				{
					this.getCurrentPlayer().addToHand(cardSlot, cardToCheck);
					evaluteCard((PlayingCard)this.getCurrentPlayer().discardCard(cardSlot+1));
				}
			}
			
			if(playerHandStatus[this.getCurrentTurn()][cardSlot] == false)
			{
				this.getCurrentPlayer().addToHand(cardSlot, cardToCheck);
				app.out("You got a " + cardToCheck);
				playerHandStatus[this.getCurrentTurn()][cardSlot] = true;
				
				evaluteCard((PlayingCard)this.getCurrentPlayer().discardCard(cardSlot+1));
			}
			else
			{
				app.out("That card has already been flipped! too bad");
				deck.discardACard(cardToCheck);
			}
		}
		
		return works;
	}
	
	private boolean wonRound(int playerIndex)
	{
		boolean winner = false;
		int counter = 0;
		
		for(int index = 0; index < playerHandSize[playerIndex]; index++)
		{
			if(playerHandStatus[playerIndex][index] == true)
			{
				counter++;
			}
		}
		
		if(counter == playerHandSize[playerIndex])
			winner = true;
		
		return winner;
	}
	
	private void flipAllCardsLeft(int playerIndex)
	{
		for(int cardSlot = 0; cardSlot < this.getPlayer(playerIndex).getHandSize(); cardSlot++)
		{
			evaluteCard((PlayingCard)this.getPlayer(playerIndex).pickCard(cardSlot));
		}
	}
}
