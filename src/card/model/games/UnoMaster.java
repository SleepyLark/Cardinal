package card.model.games;

import card.controller.CardController;
import card.model.dealers.UnoDealer;

public class UnoMaster extends GameMaster
{

	private CardController app;
	private boolean reverseOrder;
	private UnoDealer deck;

	public UnoMaster(CardController app)
	{
		super();
		this.app = app;
		reverseOrder = false;
		deck = new UnoDealer(app);
		

	}

	@Override
	public void startGame()
	{
		setupGame();
		deck.drawDebug(-1);

	}

	@Override
	protected void setupGame()
	{
		deck.buildDeck();

	}

	/**
	 * switches to the next player <br>
	 * <i> uses a "modulus" effect where the number will cycle back to zero once it
	 * hits max number. useful for readability</i>
	 */
	@Override
	public void next()
	{
		
		currentTurn = reverseOrder ? currentTurn-- : currentTurn++;
	
		if (currentTurn >= this.numberOfPlayers())
		{
			currentTurn = 0;
		}
		else if(currentTurn < 0)
		{
			currentTurn = numberOfPlayers()-1;
		}
		turnCount++;
	}
}
