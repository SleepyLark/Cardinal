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
		ArrayList<PlayingCard> set = new ArrayList<PlayingCard>();
		boolean hasSet = false;
		
		for(int index = personToCheck.getSizeOfHand()-1; index >=0; index--)
		{
			int currentNumber = ((PlayingCard) personToCheck.pickCard(index)).getNumber();
			int count = 0;
			for(int nextIndex = index-1; nextIndex >=0; nextIndex--)
			{
				int nextNumber = ((PlayingCard) personToCheck.pickCard(nextIndex)).getNumber();
				if(nextNumber == currentNumber)
				{
					set.add((PlayingCard) personToCheck.pickCard(nextIndex));
					count++;
				}
			}
			if(count == 3)
			{
				set.add((PlayingCard) personToCheck.pickCard(index));
				index-=4;
				hasSet = true;
				this.addPoint(personToCheck);
				for(Card current :  set)
				{
					personToCheck.discardCard(current);
				}
			}
			count = 0;
		}
		
		return hasSet;
	}
	
	private void addPoint(int indexOfPlayer)
	{
		scoreSheet[indexOfPlayer]++;
	}
	
	private void addPoint(StandardPlayer personToCheck)
	{
		addPoint(this.getPlayers().indexOf(personToCheck));
	}
	
	public int getScore(int indexOfPlayer)
	{
		return scoreSheet[indexOfPlayer];
	}
	
	public int getScore(StandardPlayer personToGet)
	{
		return getScore(this.getPlayers().indexOf(personToGet));
	}
	public StandardPlayer determineWinner()
	{
		StandardPlayer winner = null;
		int highScore = 0;
		for(int index = 0; index < scoreSheet.length; index++)
		{
			if(scoreSheet[index] > highScore)
			{
				winner = (StandardPlayer) this.getPlayers().get(index);
			}
		}
		
		return winner;
	}
	
}
