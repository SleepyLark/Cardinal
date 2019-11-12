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
