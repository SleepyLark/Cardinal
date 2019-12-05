package card.model.games;

import card.controller.CardController;

public class UnoMaster extends GameMaster
{

	private CardController app;
	private boolean reverseOrder;

	public UnoMaster(CardController app)
	{
		super();
		this.app = app;
		reverseOrder = false;

	}

	@Override
	public void startGame()
	{
		// TODO Auto-generated method stub

	}

	@Override
	protected void setupGame()
	{
		// TODO Auto-generated method stub

	}

	/**
	 * switches to the next player <br>
	 * <i> uses a "modulus" effect where the number will cycle back to zero once it
	 * hits max number. useful for readability</i>
	 */
	@Override
	public void next()
	{
		if (reverseOrder)
			currentTurn--;
		else
			currentTurn++;

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
