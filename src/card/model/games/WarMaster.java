package card.model.games;

import java.util.ArrayList;
import java.util.Scanner;

import card.controller.CardController;
import card.model.cards.PlayingCard;
import card.model.dealers.StandardDealer;
import card.model.players.StandardPlayer;

public class WarMaster extends GameMaster
{
	private CardController app;
	private StandardPlayer playerOne;
	private StandardPlayer playerTwo;
	private StandardDealer luigi;
	private ArrayList<PlayingCard> pot;
	private boolean isTie;
	private Scanner consoleIn;

	public WarMaster(CardController app)
	{
		super();
		this.app = app;
		playerOne = new StandardPlayer(null);
		playerTwo = new StandardPlayer("Donkey Kong");
		luigi = new StandardDealer(null);
		pot = new ArrayList<PlayingCard>();
		consoleIn = new Scanner(System.in);
		isTie = false;
	}

	public StandardPlayer battle(StandardPlayer playerOne, StandardPlayer playerTwo)
	{
		int playerOnesPoint = ((PlayingCard) playerOne.pickCard(0)).getNumber();
		int playerTwosPoint = ((PlayingCard) playerTwo.pickCard(0)).getNumber();

		pot.add((PlayingCard) playerOne.discardCard(0));
		pot.add((PlayingCard) playerTwo.discardCard(0));

		if (playerOnesPoint == PlayingCard.ACE)
		{
			playerOnesPoint += 13;
		}

		if (playerTwosPoint == PlayingCard.ACE)
		{
			playerTwosPoint += 13;
		}

		if (playerOnesPoint == playerTwosPoint)
		{
			battle(playerOne, playerTwo);
		}

		if (playerOnesPoint > playerTwosPoint)
		{
			victor(playerOne);
			return playerOne;
		}
		else
		{
			victor(playerTwo);
			return playerTwo;
		}
	}

	private void victor(StandardPlayer personWhoWon)
	{
		for (int index = pot.size() - 1; index >= 0; index--)
		{
			personWhoWon.addToHand(pot.remove(index));
		}
	}

	@Override
	public void startGame()
	{

		setupGame();
		int turnCount = 0;
		app.out(playerOne.getHandSize() + "\t" + playerTwo.getHandSize());

		while (playerOne.getHandSize() > 0 && playerTwo.getHandSize() > 0)
		{
			app.out(this.battle(playerOne, playerTwo) + " wins the battle!");
			turnCount++;
		}

		if (playerOne.getHandSize() > playerTwo.getHandSize())
		{
			app.out(playerOne + " is the victor!");

		}
		else
		{
			app.out(playerTwo + " is the victor");
		}

		app.out("Turns taken: " + turnCount);

	}

	@Override
	protected void setupGame()
	{
		app.out("Enter name:");
		playerOne.setUsername(consoleIn.nextLine());
		this.addToGame(playerOne);
		this.addToGame(playerTwo);
		luigi.shuffleCards();
		luigi.shuffleCards();
		luigi.dealCards(this.getPlayers(), 26);
	
	}

}
