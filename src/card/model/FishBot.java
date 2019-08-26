package card.model;

public class FishBot extends StandardPlayer
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
			int cardIndex = Dealer.getRandomInt(0, this.getSizeOfHand() - 1);
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
