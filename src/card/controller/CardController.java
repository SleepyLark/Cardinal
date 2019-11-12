package card.controller;

import java.util.ArrayList;
import java.util.Scanner;

import card.model.*;
import card.model.bots.FishBot;
import card.model.cards.PlayingCard;
import card.model.dealers.StandardDealer;
import card.model.games.GarbageMaster;
import card.model.games.GoFishMaster;
import card.model.games.WarMaster;
import card.model.players.StandardPlayer;
import card.model.players.StandardPlayer.Type;

public class CardController
{

	private StandardDealer luigi;
	private GoFishMaster toad;
	private WarMaster bowser;
	private StandardPlayer playerOne;
	private StandardPlayer playerTwo;
	private GarbageMaster waluigi;

	public CardController()
	{
		luigi = new StandardDealer(this);
		toad = new GoFishMaster(this);
		bowser = new WarMaster(this);
		waluigi = new GarbageMaster(this);
		playerTwo = new FishBot("Yoshi");
		
		luigi.buildDeck(false);

	}

	public void start()
	{
		waluigi.startGame();
		// consoleWar();
		//consoleGoFishTest();
		// dummyScenario();
	}
	
	/**
	 * A quick way to print to the console.  Useful for quick debugging
	 * @param message The thing you want to send to the console.
	 */
	public void out(Object message)
	{
		System.out.println(message);
	}

	/*
	 * a test scenario, TODO: re-implement this for GoFishMaster
	 */
	public void consoleGoFishTest()
	{
		Scanner consoleIn = new Scanner(System.in);
		out("Enter name:");
		playerOne = new StandardPlayer(consoleIn.nextLine());
		luigi.shuffleCards();

		toad.addToGame(playerOne);
		toad.addToGame(playerTwo);
		toad.startGame();
		for (int times = 0; times < 7; times++)
		{
			playerOne.addToHand(luigi.draw());
			playerTwo.addToHand(luigi.draw());
		}

		out(toad.currentPlayer() + " starts first");
		while (!luigi.isDrawDeckEmpty())
		{
			out("---------------------");
			if (toad.currentPlayer().getHandSize() >= 4)
			{
				if (toad.hasASet((StandardPlayer) toad.currentPlayer()))
					out(toad.currentPlayer() + " got a point!");
			}

			out(toad.currentPlayer() + "'s score: " + toad.getScore((StandardPlayer) toad.currentPlayer()));
			if (toad.currentPlayer() == playerOne)
			{

				out("Your hand:");
				out("Last card drawn: " + playerOne.getLastDrawnCard());
				playerOne.organizeHand(Type.NUMBER);
				out(playerOne.getCurrentHand());
				out("Size: " + playerOne.getHandSize());

				if (playerOne.getHandSize() > 0)
				{
					
					boolean valid = false;
					int cardIndex = 0;
					while (!valid)
					{
						out("Ask for card:");
						cardIndex = consoleIn.nextInt();
						if(cardIndex > playerOne.getHandSize() || cardIndex < 0)
						{
							out("Card doesn't exist! Try again.");
						}
						else
						{
							valid = true;
						}
					}
					
					out("got any " + ((PlayingCard) playerOne.pickCard(cardIndex)).getNumber() + "'s?");

					ArrayList<PlayingCard> cards = toad.askForCard((StandardPlayer) playerTwo, (PlayingCard) playerOne.pickCard(cardIndex));

					if (cards.size() == 0)
					{
						out("Go fish");
						if (luigi.deckSize() > 0)
						{
							playerOne.addToHand(luigi.draw());
						}
						else
						{
							out("Looks like there's no more cards left to draw!");
						}
					}
					else
					{
						out(playerTwo + " handed you over " + cards);
						playerOne.addToHand(cards);
					}
				}
				else if (luigi.deckSize() != 0)
				{
					out("You're out of card! Draw another one");
					playerOne.addToHand(luigi.draw());

				}
				else
				{
					out("There's no more cards left.");
				}

			}
			else
			{
				if (playerTwo.getHandSize() > 0)
				{
					PlayingCard cardWanted = ((FishBot) playerTwo).takeTurn();
					out("got any " + cardWanted.getNumber() + "'s?");

					ArrayList<PlayingCard> cards = toad.askForCard((StandardPlayer) playerOne, cardWanted);

					if (cards.size() == 0)
					{
						out("Go fish");
						if (luigi.deckSize() > 0)
							playerTwo.addToHand(luigi.draw());

					}
					else
					{
						out("You handed over " + cards);
						playerTwo.addToHand(cards);
					}
				}
				else
				{
					out(playerTwo + " ran out of cards! They must draw one.");
					playerTwo.addToHand(luigi.draw());
				}

			}

			toad.next();
			out("It's now " + toad.currentPlayer() + "'s turn");
		}

		out("Game over.");

		if (toad.determineWinner() == playerOne)
		{
			out("You Won!");
		}
		else
		{
			out("You lost");
		}

		consoleIn.close();
	}

	/*
	 * Lol this thing doesn't work
	 */
	public void consoleWar()
	{
		Scanner consoleIn = new Scanner(System.in);
		int turnCount = 0;
		out("Enter name:");
		playerOne = new StandardPlayer(consoleIn.nextLine());
		luigi.shuffleCards();
		luigi.shuffleCards();
		bowser.addToGame(playerOne);
		bowser.addToGame(playerTwo);
		for (int times = 0; times < 26; times++)
		{
			playerOne.addToHand(luigi.draw());
			playerTwo.addToHand(luigi.draw());
		}

		while (playerOne.getHandSize() > 0 && playerTwo.getHandSize() > 0)
		{
			out(bowser.battle(playerOne, playerTwo) + " wins the battle!");
			turnCount++;
		}

		if (playerOne.getHandSize() > playerTwo.getHandSize())
		{
			out(playerOne + " is the victor!");

		}
		else
		{
			out(playerTwo + " is the victor");
		}

		out("Turns taken: " + turnCount);
	}

	/*
	 * A method I used to first debug my initial logic, may delete later
	 */
	public void dummyScenario()
	{
		out("Current Players:");
		out(toad.getPlayers() + "");
		out(playerOne.getUsername() + "'s hand:");
		out(playerOne.getCurrentHand());
		out(playerTwo.getUsername() + "'s hand:");
		out(playerTwo.getCurrentHand());
		out(toad.currentPlayer() + " starts.");
		playerOne.addToHand(luigi.draw());
		out(toad.currentPlayer() + " draws a card.");
		out(toad.currentPlayer() + " drew a(n) " + toad.currentPlayer().getCurrentHand().get(toad.currentPlayer().getHandSize() - 1));
		out(toad.currentPlayer().getCurrentHand());
		out(toad.currentPlayer() + " plays a(n) " + luigi.discard(toad.currentPlayer().discardCard(luigi.randomInt(0, toad.currentPlayer().getHandSize()))));
		toad.next();
		out("It's now " + toad.currentPlayer() + "'s turn");
		out(toad.currentPlayer() + " draws a card.");
		out(toad.currentPlayer() + " drew a(n) " + toad.currentPlayer().getCurrentHand().get(toad.currentPlayer().getHandSize() - 1));
		out(toad.currentPlayer().getCurrentHand());
		out(toad.currentPlayer() + " ends their turn.");
		toad.next();
		out("It is now " + toad.currentPlayer() + "'s turn.");
		out(toad.currentPlayer() + " flips the table in rage.");
		toad.next();
		out("It looks like " + toad.currentPlayer() + " is the winner");
		toad.currentPlayer().winner();
		out(playerOne + ": " + playerOne.getWinCount());
		out(playerTwo + ": " + playerTwo.getWinCount());

	}

}
