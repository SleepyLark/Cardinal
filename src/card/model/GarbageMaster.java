package card.model;

import card.controller.CardController;

public class GarbageMaster extends GameMaster
{

	private CardController app;
	private int[] playerHandSize;
	private int maxHandSize;
	private StandardDealer deck;

	public GarbageMaster(CardController app, int maxHandSize)
	{
		super();
		this.app = app;
		this.maxHandSize = maxHandSize;
		deck = new StandardDealer(app);
	}

	public void gameReady()
	{

	}

	@Override
	public void startGame()
	{
		if (this.numberOfPlayers() < 2)
		{
			app.out("Invalid number of players.  Please add players before starting the game");
		}
		else
		{
			playerHandSize = new int[this.numberOfPlayers()];
			for (int index : playerHandSize)
			{
				playerHandSize[index] = maxHandSize;
			}
			
			deck.shuffleCards();
			
		}
	}
}
