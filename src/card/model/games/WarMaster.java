package card.model.games;

import java.util.ArrayList;

import card.controller.CardController;
import card.model.cards.PlayingCard;
import card.model.players.StandardPlayer;

public class WarMaster extends GameMaster
{
	private CardController app;
	private ArrayList<PlayingCard> pot;
	private boolean isTie;
	
	public WarMaster(CardController app)
	{
		super();
		this.app = app;
		pot = new ArrayList<PlayingCard>();
		isTie = false;
	}
	
	public StandardPlayer battle(StandardPlayer playerOne, StandardPlayer playerTwo)
	{
		int playerOnesPoint = ((PlayingCard)playerOne.pickCard(0)).getNumber();
		int playerTwosPoint = ((PlayingCard)playerTwo.pickCard(0)).getNumber();
		
		pot.add((PlayingCard) playerOne.discardCard(0));
		pot.add((PlayingCard) playerTwo.discardCard(0));
		
		if(playerOnesPoint == PlayingCard.ACE)
		{
			playerOnesPoint+=13;
		}
		
		if(playerTwosPoint == PlayingCard.ACE)
		{
			playerTwosPoint += 13;
		}
		
		if(playerOnesPoint == playerTwosPoint)
		{
			battle(playerOne, playerTwo);
		}
	
		if(playerOnesPoint > playerTwosPoint)
		{
			victor(playerOne);
			return playerOne;
		}
		else
		{
			victor(playerTwo);
			return playerTwo;
		}
	}
	
	private void victor(StandardPlayer personWhoWon)
	{
		for(int index = pot.size()-1; index >=0; index--)
		{
			personWhoWon.addToHand(pot.remove(index));
		}
	}

	@Override
	public void startGame()
	{
		// TODO Auto-generated method stub
		
	}
	
}
