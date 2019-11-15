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

	private GoFishMaster toad;
	private WarMaster bowser;
	private GarbageMaster waluigi;

	public CardController()
	{
		toad = new GoFishMaster(this);
		bowser = new WarMaster(this);
		waluigi = new GarbageMaster(this);
		

	}

	public void start()
	{
	
		waluigi.startGame();
	}
	
	/**
	 * A quick way to print to the console.  Useful for quick debugging
	 * @param message The thing you want to send to the console.
	 */
	public void out(Object message)
	{
		System.out.println(message);
	}
	
}
