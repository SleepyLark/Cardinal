package card.model;

import java.util.ArrayList;
import card.controller.CardController;

public class GoFishMaster extends GameMaster
{
	private CardController app;
	private int[] scoreSheet;
	
	public GoFishMaster(CardController app)
	{
		super();
		this.app = app;
	}
	
	public void startGame()
	{
		scoreSheet = new int[this.numberOfPlayers()];
	}
	
	
	public ArrayList<PlayingCard> askForCard(StandardPlayer personToPick, PlayingCard cardYouWant)
	{
		int numberType = cardYouWant.getNumber();
		ArrayList<PlayingCard> cardsToTake = new ArrayList<PlayingCard>();
		
		for(int index = 0; index < personToPick.getSizeOfHand(); index++)
		{
			if(((PlayingCard) personToPick.getCurrentHand().get(index)).getNumber() == numberType)
			{
				cardsToTake.add((PlayingCard) personToPick.discardCard(index));
			}
		}
		
		return cardsToTake;
	}
	
	public boolean hasASet(StandardPlayer personToCheck)
	{
		int playerPosition = this.getPlayers().indexOf(personToCheck);
		return false;
	}
	
}
