package card.controller;

import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JOptionPane;

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
		String[] option = {"Go Fish", "Garbage", "UNO"};
		int result = JOptionPane.showOptionDialog(null,"Pick a Game","Cardinal",JOptionPane.YES_OPTION, JOptionPane.QUESTION_MESSAGE, null,option, option[0]);
		
		if(result == 1)
		waluigi.startGame();
		else if (result == 2)
			yoshi.startGame();
		else
			toad.startGame();
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
