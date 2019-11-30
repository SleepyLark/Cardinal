package card.controller;

import java.util.ArrayList;
import java.util.Scanner;

import card.model.games.*;
public class CardController
{

	private GoFishMaster toad;
	private WarMaster bowser;
	private GarbageMaster waluigi;
	private UnoMaster yoshi;

	public CardController()
	{
		toad = new GoFishMaster(this);
		bowser = new WarMaster(this);
		waluigi = new GarbageMaster(this);
		yoshi = new UnoMaster(this);
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
