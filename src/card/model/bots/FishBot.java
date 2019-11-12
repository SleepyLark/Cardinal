package card.model.bots;

import card.model.cards.PlayingCard;
import card.model.dealers.Dealer;
import card.model.players.StandardPlayer;

public class FishBot extends StandardBot
{
	private int lastTypeAsked;

	public FishBot(String name)
	{
		super(name);

		lastTypeAsked = -1;
	}

	public PlayingCard takeTurn()
	{
		PlayingCard cardToAsk = null;
		boolean pass = false;
		while (pass == false)
		{
			int cardIndex = Dealer.randomInt(0, this.getHandSize() - 1);
			if (((PlayingCard) this.pickCard(cardIndex)).getNumber() != lastTypeAsked)
			{
				pass = true;
				cardToAsk = (PlayingCard) this.pickCard(cardIndex);
			}
		}
		
		lastTypeAsked = cardToAsk.getNumber();
		return cardToAsk;
	}

}
