package card.controller;

import java.util.ArrayList;
import java.util.Scanner;

import card.model.*;
import card.model.StandardPlayer.Type;

public class CardController
{

	private StandardDealer luigi;
	private GoFishMaster toad;
	private WarMaster bowser;
	private StandardPlayer playerOne;
	private StandardPlayer playerTwo;

	public CardController()
	{
		luigi = new StandardDealer(this);
		toad = new GoFishMaster(this);
		bowser = new WarMaster(this);
		playerTwo = new FishBot("Yoshi");

		luigi.buildDeck(false);

	}

	public void start()
	{
		luigi.buildDeck();
		out(luigi.getDrawDeckSize());
		// consoleWar();
		//consoleGoFishTest();
		// dummyScenario();
	}

	public void out(Object message)
	{
		System.out.println(message);
	}

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
			playerOne.addToHand(luigi.drawACard());
			playerTwo.addToHand(luigi.drawACard());
		}

		out(toad.getCurrentPlayer() + " starts first");
		while (!luigi.getDrawDeck().isEmpty())
		{
			out("---------------------");
			if (toad.getCurrentPlayer().getSizeOfHand() >= 4)
			{
				if (toad.hasASet((StandardPlayer) toad.getCurrentPlayer()))
					out(toad.getCurrentPlayer() + " got a point!");
			}

			out(toad.getCurrentPlayer() + "'s score: " + toad.getScore((StandardPlayer) toad.getCurrentPlayer()));
			if (toad.getCurrentPlayer() == playerOne)
			{

				out("Your hand:");
				out("Last card drawn: " + playerOne.getLastDrawnCard());
				playerOne.organizeHand(Type.NUMBER);
				out(playerOne.getCurrentHand());
				out("Size: " + playerOne.getSizeOfHand());

				if (playerOne.getSizeOfHand() > 0)
				{
					
					boolean valid = false;
					int cardIndex = 0;
					while (!valid)
					{
						out("Ask for card:");
						cardIndex = consoleIn.nextInt();
						if(cardIndex > playerOne.getSizeOfHand() || cardIndex < 0)
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
						if (luigi.getDrawDeckSize() > 0)
						{
							playerOne.addToHand(luigi.drawACard());
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
				else if (luigi.getDrawDeckSize() != 0)
				{
					out("You're out of card! Draw another one");
					playerOne.addToHand(luigi.drawACard());

				}
				else
				{
					out("There's no more cards left.");
				}

			}
			else
			{
				if (playerTwo.getSizeOfHand() > 0)
				{
					PlayingCard cardWanted = ((FishBot) playerTwo).takeTurn();
					out("got any " + cardWanted.getNumber() + "'s?");

					ArrayList<PlayingCard> cards = toad.askForCard((StandardPlayer) playerOne, cardWanted);

					if (cards.size() == 0)
					{
						out("Go fish");
						if (luigi.getDrawDeckSize() > 0)
							playerTwo.addToHand(luigi.drawACard());

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
					playerTwo.addToHand(luigi.drawACard());
				}

			}

			toad.next();
			out("It's now " + toad.getCurrentPlayer() + "'s turn");
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
			playerOne.addToHand(luigi.drawACard());
			playerTwo.addToHand(luigi.drawACard());
		}

		while (playerOne.getSizeOfHand() > 0 && playerTwo.getSizeOfHand() > 0)
		{
			out(bowser.battle(playerOne, playerTwo) + " wins the battle!");
			turnCount++;
		}

		if (playerOne.getSizeOfHand() > playerTwo.getSizeOfHand())
		{
			out(playerOne + " is the victor!");

		}
		else
		{
			out(playerTwo + " is the victor");
		}

		out("Turns taken: " + turnCount);
	}

	public void dummyScenario()
	{
		out("Current Players:");
		out(toad.getPlayers() + "");
		out(playerOne.getUsername() + "'s hand:");
		out(playerOne.getCurrentHand());
		out(playerTwo.getUsername() + "'s hand:");
		out(playerTwo.getCurrentHand());
		out(toad.getCurrentPlayer() + " starts.");
		playerOne.addToHand(luigi.drawACard());
		out(toad.getCurrentPlayer() + " draws a card.");
		out(toad.getCurrentPlayer() + " drew a(n) " + toad.getCurrentPlayer().getCurrentHand().get(toad.getCurrentPlayer().getSizeOfHand() - 1));
		out(toad.getCurrentPlayer().getCurrentHand());
		out(toad.getCurrentPlayer() + " plays a(n) " + luigi.discardACard(toad.getCurrentPlayer().discardCard(luigi.getRandomInt(0, toad.getCurrentPlayer().getSizeOfHand()))));
		out(luigi.getDiscardPile());
		toad.next();
		out("It's now " + toad.getCurrentPlayer() + "'s turn");
		out(toad.getCurrentPlayer() + " draws a card.");
		out(toad.getCurrentPlayer() + " drew a(n) " + toad.getCurrentPlayer().getCurrentHand().get(toad.getCurrentPlayer().getSizeOfHand() - 1));
		out(toad.getCurrentPlayer().getCurrentHand());
		out(toad.getCurrentPlayer() + " ends their turn.");
		toad.next();
		out("It is now " + toad.getCurrentPlayer() + "'s turn.");
		out(toad.getCurrentPlayer() + " flips the table in rage.");
		toad.next();
		out("It looks like " + toad.getCurrentPlayer() + " is the winner");
		toad.getCurrentPlayer().winner();
		out(playerOne + ": " + playerOne.getWinCount());
		out(playerTwo + ": " + playerTwo.getWinCount());

	}

}
