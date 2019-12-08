package card.model.games;

import java.util.Scanner;

import card.controller.CardController;
import card.model.bots.TrashBot;
import card.model.cards.Card;
import card.model.cards.PlayingCard;
import card.model.dealers.StandardDealer;
import card.model.players.Player;
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
	private StandardPlayer playerOne;
	/**
	 * for debugging, subjected to change
	 */
	private Scanner consoleIn;

	private boolean gameOver;
	private boolean roundOver;

	public GarbageMaster(CardController app)
	{
		super();
		this.app = app;
		this.maxHandSize = 10;
		deck = new StandardDealer(app);
		playerOne = null;
		consoleIn = new Scanner(System.in);
		gameOver = false;
		roundOver = false;
	}

	@Override
	public void startGame()
	{

		setupGame();

		int winner = -999;
		while (!gameOver)
		{
			if(getCurrentTurn() == winner)
			{
				roundOver = false;
				winner = -999;
				app.out("Round over!");
				discardEveryonesCards();
				deck.reshuffleDiscardPile();
				dealCards();
			}
			// app.out(debug());
			app.out("It is now " + currentPlayer() + "'s turn.");
			printHand();
			app.out("Discard Pile: " + deck.discardPeek());
			
			if(deck.isDrawDeckEmpty())
			{
				Card temp = deck.drawFromDiscardPile();
				deck.reshuffleDiscardPile();
				app.out("Draw pile empty. Reshuffling...");
				deck.discard(temp);
			}
			
			if (!(currentPlayer().isBot()))
			{
				app.out("Which deck do you want to draw?");
				String response = consoleIn.next();
				if (response.equals("discard"))
				{

					this.evaluteCard(deck.drawFromDiscardPile());
				}
				else
				{
					this.evaluteCard(deck.draw());
				}
			}
			else
			{
				if (((TrashBot) currentPlayer()).turn(deck.discardPeek(), playerHandStatus(getCurrentTurn())) == TrashBot.TAKE_FROM_DISCARD)
				{
					this.evaluteCard(deck.drawFromDiscardPile());
				}
				else 
				{
					this.evaluteCard(deck.draw());
				}
			}
			
			if(roundOver)
			{
				this.flipAllCardsLeft(this.getCurrentTurn());
				if(wonRound(getCurrentTurn()))
				{
					currentPlayer().winner();
					app.out(currentPlayer() + " is also a winner!");
					playerHandSize[getCurrentTurn()]--;
				}
			}

			if (wonRound(getCurrentTurn()) && !roundOver)
			{
				app.out(currentPlayer() + " has won the round!");
				currentPlayer().winner();
				winner = this.getCurrentTurn();
				playerHandSize[getCurrentTurn()]--;
				roundOver = true; 
			}

			this.next();
		}

	}
	

	/**
	 * adds players and deals out cards
	 */
	protected void setupGame()
	{
		 app.out("Enter name:");
		 playerOne = new StandardPlayer(consoleIn.nextLine());
		 this.addToGame(playerOne);
		 
		 boolean error = true;
			int cpuPlayers = 0;

			while (error)
			{
				app.out("How many CPU players?");
				cpuPlayers = consoleIn.nextInt();

				if (cpuPlayers < 1)
				{
					app.out("Invalid number of players.");
				}
				else
					error = false;
			}


		addBotPlayers(cpuPlayers, Game.GARBAGE);

		playerHandSize = new int[this.numberOfPlayers()];

		for (int index = 0; index < playerHandSize.length; index++)
		{
			playerHandSize[index] = maxHandSize;
		}

		playerHandStatus = new boolean[this.numberOfPlayers()][maxHandSize];
		
		
		//if there's more than 4 players, add in another deck (may need to adjust exact what should be the limit)
				for(int cycles = 0; cycles <= (cpuPlayers + 1)/4; cycles++)
				{
					deck.buildDeck();
				}
				
				app.out(deck.deckSize());
		
		dealCards();
	}
	

	private void addBotPlayers()
	{
		boolean error = true;
		int cpuPlayers = 0;

		while (error)
		{
			app.out("How many CPU players?");
			cpuPlayers = consoleIn.nextInt();

			if (cpuPlayers < 1)
			{
				app.out("Invalid number of players.");
			}
			else
				error = false;
		}

		for (int times = 0; times < cpuPlayers; times++)
		{
			this.addToGame(new TrashBot(null));
		}
		
		
	}
	

	private void evaluteCard(Card card)
	{

		printHand();
		// this.debug();
		app.out(card);

		PlayingCard cardToCheck = (PlayingCard) card;
		Player currentPlayer = this.currentPlayer();
		int playerIndex = this.getCurrentTurn();

		if (!wonRound(playerIndex) && ((cardToCheck.getNumber() <= playerHandSize[playerIndex]) || (cardToCheck.getNumber() == PlayingCard.JACK)))
		{
			int cardSlot = cardToCheck.getNumber() - 1;

			// Jacks can be placed anywhere
			if (cardSlot +1 == PlayingCard.JACK)
			{
				if (!currentPlayer.isBot())
				{
					app.out("Lucky you! You got a Jack! Where do you want to put it?");
					cardSlot = consoleIn.nextInt();
				}
				else
				{
					app.out(currentPlayer + " got a Jack!");
					cardSlot = ((TrashBot) currentPlayer).processJack(playerHandStatus[playerIndex],playerHandSize[playerIndex]);
				}
				
				// if the card has already been flipped, try again
				if (playerHandStatus[playerIndex][cardSlot] == true)
				{
					app.out("There's no point to switch it there! Try again!");
					evaluteCard(cardToCheck);
				}
			}

			// if the card flipped is a Jack and can be replace, swap it and reevaluate
			// TODO: add new rule to lock cards in
			if (((PlayingCard) currentPlayer.pickCard(cardSlot)).getNumber() == PlayingCard.JACK)
			{
				currentPlayer.addToHand(cardSlot, cardToCheck);
				app.out("The Jack got swapped with a(n) " + cardToCheck);

				playerHandStatus[playerIndex][cardSlot] = true;
				
				if(lastCard(playerIndex))
				{
					deck.discard(currentPlayer.discardCard(cardSlot + 1));
				}
				else
				{
					evaluteCard(currentPlayer.discardCard(cardSlot + 1));
				}

			}
			else
			{
				// if the card hasn't been flip, flip it and see what the other card is.
				if (playerHandStatus[playerIndex][cardSlot] == false)
				{
					currentPlayer.addToHand(cardSlot, cardToCheck);
					app.out(currentPlayer + " got a(n) " + cardToCheck);
					playerHandStatus[playerIndex][cardSlot] = true;
					app.out("Flipping card...");
					evaluteCard(currentPlayer.discardCard(cardSlot + 1));
				}
				else
				{
					app.out(this.currentPlayer() + " got a " + cardToCheck + ", but that card has already been flipped! too bad");
					deck.discard(card);

				}

			}
		}
		else
		{
			app.out("It was a(n) " + cardToCheck + "\nGarbage. Too bad.");
			deck.discard(cardToCheck);
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
			if(!playerHandStatus[playerIndex][cardSlot])
			evaluteCard((PlayingCard) this.getPlayer(playerIndex).pickCard(cardSlot));
		}
	}
	
	private boolean lastCard(int playerIndex)
	{
		int counter = 0;
		for (int cardSlot = 0; cardSlot < playerHandSize[playerIndex]; cardSlot++)
		{
			if(!playerHandStatus[playerIndex][cardSlot])
				counter++;
		}
		
		return counter == 1;
	}

	private void dealCards()
	{
		deck.shuffleCards();
		
		for (int player = 0; player < playerHandStatus.length; player++)
		{
			for (int index = 0; index < playerHandStatus[0].length; index++)
			{
				playerHandStatus[player][index] = false;
			}
			
			deck.dealCards(this.getPlayer(player), playerHandSize[player]);
		}
		
		deck.discard(deck.draw());
	}
	
	private void discardEveryonesCards()
	{
		for (int player = 0; player < this.getPlayers().size(); player++)
		{
			Player currentPlayer = this.getPlayer(player);
			for(int card = currentPlayer.getHandSize()-1; card >= 0 ; card--)
			{
				deck.discard(currentPlayer.discardCard(card));
			}
		}
	}


	private void debug()
	{
		String stats = "==[DEBUG]==\n";
		stats += "Turn Count: " + this.getTurnCount();
		stats += "\nCurrent Turn position: " + this.getCurrentTurn();
		stats += "\nPlayer: " + this.currentPlayer();
		stats += "\nHand:\n";
		for (int index = 0; index < playerHandSize[this.getCurrentTurn()]; index++)
		{
			stats += "[" + index + "]: " + currentPlayer().pickCard(index) + " \tFlipped?: " + playerHandStatus[this.getCurrentTurn()][index] + "\n";

		}
		stats += "Size: " + playerHandSize[this.getCurrentTurn()];
		stats += "\n==========";

		app.out(stats);

	}

	private void printHand()
	{
		String stats = "Hand:\n";
		for (int index = 0; index < this.currentPlayer().getHandSize(); index++)
		{
			stats += "[" + index + "]: ";
			if (playerHandStatus[this.getCurrentTurn()][index])
				stats += this.currentPlayer().pickCard(index) + "\n";
			else
				stats += "????\n";
		}

		app.out(stats);
	}
}
