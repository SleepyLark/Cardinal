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
	
	private boolean gameOver;

	public GarbageMaster(CardController app)
	{
		super();
		this.app = app;
		this.maxHandSize = 10;
		deck = new StandardDealer(app);
		consoleIn = new Scanner(System.in);
		gameOver = false;
	}

	@Override
	public void startGame()
	{
		boolean error = true;
		int cpuPlayers = 0;

		app.out("Enter name:");
		StandardPlayer playerOne = new StandardPlayer(consoleIn.nextLine());
		this.addToGame(playerOne);

		while (error)
		{
			app.out("How many CPU players?");
			cpuPlayers = consoleIn.nextInt();

			if (this.numberOfPlayers() < 1)
			{
				app.out("Invalid number of players.  Please add players before starting the game");
			}
			else
				error = false;
		}

		for (int times = 0; times < cpuPlayers; times++)
		{
			this.addToGame(new TrashBot(null));
		}

		playerHandSize = new int[this.numberOfPlayers()];

		for (int index = 0; index < playerHandSize.length; index++)
		{
			playerHandSize[index] = maxHandSize;
		}

		playerHandStatus = new boolean[this.numberOfPlayers()][maxHandSize];

		for (int player = 0; player < playerHandStatus.length; player++)
		{
			for (int index = 0; index < playerHandStatus[0].length; index++)
			{
				playerHandStatus[player][index] = false;
			}
		}

		deck.buildDeck();
		deck.shuffleCards();
		deck.shuffleCards();
		app.out("deck size " + deck.getDrawDeckSize());
		deck.dealCards(this.getPlayers(), maxHandSize);
		deck.discardACard(deck.drawACard());

		while (!gameOver)
		{
			//app.out(debug());
			app.out("It is now " + this.getCurrentPlayer() + "'s turn.");
			app.out(printHand());
			app.out("Discard Pile: " + deck.peekFromDiscard());
			if (this.getCurrentPlayer() == playerOne)
			{
				app.out("Which deck do you want to draw?");
				String response = consoleIn.next();
				if (response.equals("discard"))
				{
					
					this.evaluteCard((PlayingCard) deck.drawFromDiscard());
				}
				else
				{
					this.evaluteCard((PlayingCard) deck.drawACard());
				}
			}
			else
			{
				if (((TrashBot) this.getCurrentPlayer()).turn(deck.peekFromDiscard(), playerHandStatus(this.getCurrentTurn())) == TrashBot.TAKE_FROM_DISCARD)
				{
					this.evaluteCard((PlayingCard) deck.drawFromDiscard());
				}
				else
				{
					this.evaluteCard((PlayingCard) deck.drawACard());
				}
			}

			if (wonRound(this.getCurrentTurn()))
			{
				app.out(this.getCurrentPlayer() + " has won the round!");
				this.getCurrentPlayer().winner();
				int winner = this.getCurrentTurn();
				this.next();
				while (this.getCurrentTurn() != winner)
				{

					this.flipAllCardsLeft(this.getCurrentTurn());
					if (wonRound(this.getCurrentTurn()))
						app.out(this.getCurrentPlayer() + " also won!");
					this.next();
				}

			}

			this.next();
		}

	}

	private void evaluteCard(PlayingCard cardToCheck)
	{
	
		app.out(printHand());
		StandardPlayer currentPlayer = (StandardPlayer)this.getCurrentPlayer();
		int playerIndex = this.getCurrentTurn();

		if (!wonRound(playerIndex) && (cardToCheck.getNumber() <= playerHandSize[playerIndex] || cardToCheck.getNumber() == PlayingCard.JACK))
		{
			int cardSlot = cardToCheck.getNumber() - 1;
			if (cardToCheck.getNumber() == PlayingCard.JACK)
			{
				app.out("Lucky you! You got a Jack! Where do you want to put it?");
				cardSlot = consoleIn.nextInt();
				if (playerHandStatus[playerIndex][cardSlot] == true)
				{
					currentPlayer.addToHand(cardSlot, cardToCheck);
					app.out("You put the Jack in the " +cardSlot+"'s place.\nFlipping card...");
					evaluteCard((PlayingCard) currentPlayer.discardCard(cardSlot + 1));
				}
			}

			if (playerHandStatus[playerIndex][cardSlot] == false)
			{
				currentPlayer.addToHand(cardSlot, cardToCheck);
				app.out(currentPlayer + " got a(n) " + cardToCheck);
				playerHandStatus[playerIndex][cardSlot] = true;
				app.out("Flipping card...");
				evaluteCard((PlayingCard) currentPlayer.discardCard(cardSlot + 1));
			}
			else
			{
				if(((PlayingCard) currentPlayer.pickCard(cardSlot)).getNumber() == PlayingCard.JACK)
				{
					currentPlayer.addToHand(cardSlot, cardToCheck);
					app.out("The Jack got swapped with a(n) "+cardToCheck);
					evaluteCard((PlayingCard) currentPlayer.discardCard(cardSlot + 1));
				}
					
				app.out(this.getCurrentPlayer() + " got a "+ cardToCheck+ ", but that card has already been flipped! too bad");
				deck.discardACard(cardToCheck);
			}
		}
		else
		{
			app.out("It was a(n) "+ cardToCheck +"\nGarbage. Too bad.");
			deck.discardACard(cardToCheck);
		}
		
	}

	private boolean wonRound(int playerIndex)
	{
		boolean winner = false;
		int counter = 0;

		for (int index = 0; index < playerHandSize[playerIndex]; index++)
		{
			if (playerHandStatus[playerIndex][index] == true)
			{
				counter++;
			}
		}

		if (counter == playerHandSize[playerIndex])
			winner = true;

		return winner;
	}

	public boolean[] playerHandStatus(int playerIndex)
	{
		return playerHandStatus[playerIndex];
	}

	private void flipAllCardsLeft(int playerIndex)
	{
		for (int cardSlot = 0; cardSlot < this.getPlayer(playerIndex).getHandSize(); cardSlot++)
		{
			evaluteCard((PlayingCard) this.getPlayer(playerIndex).pickCard(cardSlot));
		}
	}
	
	private String debug()
	{
		String stats = "==[DEBUG]==\n";
		stats += "Turn Count: " + this.getTurnCount();
		stats += "\nCurrent Turn position: " + this.getCurrentTurn();
		stats += "\nPlayer: "+this.getCurrentPlayer();
		stats += "\nHand:\n";
		for(int index = 0; index < playerHandSize[this.getCurrentTurn()]; index++)
		{
			stats += "["+index+"]: " + getCurrentPlayer().pickCard(index) + " \tFlipped?: " + playerHandStatus[this.getCurrentTurn()][index] +"\n";
			
		}
		stats += "Size: " + playerHandSize[this.getCurrentTurn()];
		stats += "\n==========";
		
		return stats;
		
	}
	
	private String printHand()
	{
		String stats = "Hand:\n";
		for(int index = 0; index < playerHandSize[this.getCurrentTurn()]; index++)
		{
			stats += "["+index+"]: ";
			if(playerHandStatus[this.getCurrentTurn()][index])
				stats += this.getCurrentPlayer().pickCard(index) + "\n";
			else
				stats += "????\n";
		}
		
		return stats;
	}
}
