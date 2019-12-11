package card.model.players;

import java.util.ArrayList;
import java.util.Collection;

import card.model.cards.Card;
import card.model.cards.UnoCard;
import card.model.cards.UnoCard.ColorSuit;

public class UnoPlayer extends Player
{

	public UnoPlayer(String username)
	{
		super(username);
	}

	public enum Type
	{
		COLOR, NUMBER;
	}

	public void organizeHand(Type sortBy)
	{

		if (sortBy == Type.NUMBER)
		{
			currentHand.sort(this);
		}
		else if (sortBy == Type.COLOR)
		{
			ArrayList<ArrayList<Card>> colorTemp = new ArrayList<ArrayList<Card>>();
			for (ColorSuit currentColor : ColorSuit.values())
			{
				ArrayList<Card> temp = new ArrayList<Card>();

				for (int index = currentHand.size() - 1; index >= 0; index--)
				{
					UnoCard current = (UnoCard) currentHand.get(index);
					if (current.getColor() == currentColor)
						temp.add(currentHand.remove(index));
				}
				temp.sort(this);
				colorTemp.add(temp);
			}

			for (int index = colorTemp.size() - 1; index >= 0; index--)
			{
				currentHand.addAll(colorTemp.remove(index));
			}
		}
	}

	@Override
	public int compare(Card arg0, Card arg1)
	{
		return (((UnoCard) arg0).getType() - ((UnoCard) arg1).getType());
	}
}
