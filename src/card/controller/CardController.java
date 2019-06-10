package card.controller;

import java.util.ArrayList;
import java.util.Scanner;

import card.model.*;
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
		playerTwo = new StandardPlayer("Yoshi");

		luigi.buildStandardDeck(false);

	}

	public void start()
	{
		consoleGoFishTest();
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
				out("Last card drawn: "+ playerOne.getLastDrawnCard());
				playerOne.organizeHand(Type.NUMBER);
				out(playerOne.getCurrentHand());
				out("Size: "+playerOne.getSizeOfHand());

				if (playerOne.getSizeOfHand() > 0)
				{
					out("Ask for card:");
					int cardIndex = consoleIn.nextInt();
					out("got any " + ((PlayingCard) playerOne.pickCard(cardIndex)).getNumber() + "'s?");

					ArrayList<PlayingCard> cards = toad.askForCard((StandardPlayer) playerTwo, (PlayingCard) playerOne.pickCard(cardIndex));

					if (cards.size() == 0)
					{
						out("Go fish");
						playerOne.addToHand(luigi.drawACard());
						toad.next();
						out("It's now " + toad.getCurrentPlayer() + "'s turn");
					}
					else
					{
						out(playerTwo + " handed you over " + cards);
						playerOne.addToHand(cards);
					}
				}
				else
				{
					out("You're out of card! Draw another one");
					playerOne.addToHand(luigi.drawACard());
					toad.next();
					out("It's now " + toad.getCurrentPlayer() + "'s turn");

				}

			}
			else
			{
				if (playerTwo.getSizeOfHand() > 0)
				{
					int cardIndex = luigi.getRandomInt(0, playerTwo.getSizeOfHand() - 1);
					out("got any " + ((PlayingCard) playerTwo.pickCard(cardIndex)).getNumber() + "'s?");

					ArrayList<PlayingCard> cards = toad.askForCard((StandardPlayer) playerOne, (PlayingCard) playerTwo.pickCard(cardIndex));

					if (cards.size() == 0)
					{
						out("Go fish");
						playerTwo.addToHand(luigi.drawACard());
						toad.next();
						out("It's now " + toad.getCurrentPlayer() + "'s turn");
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
					toad.next();
					out("It's now " + toad.getCurrentPlayer() + "'s turn");
				}

			}

		}

		if (toad.determineWinner() == playerOne)
		{
			out("You Won!");
		}
		else
		{
			out("You lost");
		}
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
