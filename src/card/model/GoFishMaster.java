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
	
	/** 
	 * creates score sheet. <b>Players must be already added in game beforehand</b>
	 */
	public void startGame()
	{
		scoreSheet = new int[this.numberOfPlayers()];
	}
	
	
	/**
	 * searches a players hand to see if it has the card asked for
	 * @param personToPick player to ask for card
	 * @param cardYouWant the card you want to get
	 * @return a list of all cards that are the same <b>(returns null if no cards were found)</b>
	 */
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
	
	/**
	 * checks to see if the player has four of a kind
	 * @param personToCheck player to check
	 * @return true if they have four of a kind
	 */
	public boolean hasASet(StandardPlayer personToCheck)
	{
		ArrayList<PlayingCard> set = new ArrayList<PlayingCard>();
		boolean hasSet = false;
		
		//backwards for loop of player's hand 
		for(int index = personToCheck.getSizeOfHand()-1; index >=0; index--)
		{
			//starts at the beginning and searches each card after it if it's the same
			//else it will move to the next index
			int currentNumber = ((PlayingCard) personToCheck.pickCard(index)).getNumber();
			int count = 0;
			
			for(int nextIndex = index-1; nextIndex >=0; nextIndex--)
			{
				int nextNumber = ((PlayingCard) personToCheck.pickCard(nextIndex)).getNumber();
				//if they are the same card number 
				if(nextNumber == currentNumber)
				{
					//add it to the reference list and increase counter
					set.add((PlayingCard) personToCheck.pickCard(nextIndex));
					count++;
				}
			}
			//if it finds three cards that matches the original card, then it's a four-of-a-kind
			if(count == 3)
			{
				//add the original card to the reference list
				set.add((PlayingCard) personToCheck.pickCard(index));
				//these cards will be removed from the player's hand, so the index needs to be adjusted as such
				index-=4;
				hasSet = true;
				//add a point to the score sheet for the player
				this.addPoint(personToCheck);
				//removes each card in the reference list from the player's hand
				for(Card current :  set)
				{
					personToCheck.discardCard(current);
				}
			}
			set.clear();
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
				highScore = scoreSheet[index];
				winner = (StandardPlayer) this.getPlayers().get(index);
			}
		}
		
		return winner;
	}
	
}
