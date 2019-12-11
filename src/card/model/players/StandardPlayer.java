package card.model.players;


import card.model.cards.Card;
import card.model.cards.PlayingCard;
import card.model.cards.PlayingCard.Suit;

/**
 * Attributes needed to play standard card games
 * 
 * @author Skyler
 *
 */
public class StandardPlayer extends Player
{

	public enum Type
	{
		SUIT, NUMBER;
	}

	public StandardPlayer(String username)
	{
		super(username);
	}

	/**
	 * Sorts the "cards" by a selected type
	 * 
	 * @param sortBy
	 *            Type.NUMBER = least to greatest <br>
	 *            Type.SUIT = card suit
	 */
	public void organizeHand(Type sortBy)
	{
		if (sortBy == Type.NUMBER)
		{
			currentHand.sort(this);
		}
		else if (sortBy == Type.SUIT)
		{
			for (int index = 0; index < currentHand.size(); index++)
			{
				Suit currentSuit = ((PlayingCard) currentHand.get(index)).getSuit();
				int currentNumber = ((PlayingCard) currentHand.get(index)).getNumber();
				int nextSlot = index + 1;
				for (int nextIndex = index + 1; nextIndex < currentHand.size(); nextIndex++)
				{
					Suit nextSuit = ((PlayingCard) currentHand.get(nextIndex)).getSuit();

					int nextNumber = ((PlayingCard) currentHand.get(nextIndex)).getNumber();
					if (nextSuit == currentSuit)
					{
						Card cardToInsert = currentHand.remove(nextIndex);
						if (nextNumber > currentNumber)
							currentHand.add(nextSlot, cardToInsert);
						else
							currentHand.add(index, cardToInsert);
					}
				}
			}
		}
	}

	@Override
	public int compare(Card arg0, Card arg1)
	{
		return (((PlayingCard)arg0).getNumber() - ((PlayingCard)arg1).getNumber() );
	}

}
