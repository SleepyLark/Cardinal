package card.model;

public class PlayingCard
{
	public enum Suit
	{
		HEARTS, DIAMONDS, SPADES, CLUBS, JOKER;
	}

	public static final int ACE = 1;
	public static final int JACK = 11;
	public static final int QUEEN = 12;
	public static final int KING = 13;
	public static final int JOKER = 0;

	private Suit cardSuit;
	private int number;
	private int[] faceCards;

	public PlayingCard(Suit type, int number)
	{
		faceCards = new int[] { ACE, JACK, QUEEN, KING };
		cardSuit = type;
		this.number = number;
	}

	public int getNumber()
	{
		return number;
	}

	public Suit getSuit()
	{
		return cardSuit;
	}

	public boolean isFaceCard()
	{
		boolean face = false;
		for (int current : faceCards)
		{
			if (number == current)
			{
				face = true;
			}
		}

		return face;
	}

	public String faceToString()
	{
		String desc = null;
		if(number == ACE)
		{
			desc = "Ace";
		}
		else if (number == JACK)
		{
			desc = "Jack";
		}
		else if (number == QUEEN)
		{
			desc = "Queen";
		}
		else if(number == KING)
		{
			desc = "King";
		}
		
		return desc;
	}
	
	public String suitToString()
	{
		String desc = cardSuit+"";
		
		desc = desc.substring(0,1).toUpperCase() + desc.substring(1).toLowerCase();
		
		return desc;
	}

	public String toString()
	{
		String desc = "";
		
		if(getSuit() == Suit.JOKER)
		{
			desc = "Joker";
		}
		else if(isFaceCard())
		{
			desc = faceToString() + " of " + suitToString();
		}
		else
		{
			desc = number + " of " + suitToString();
		}
		return desc;
	}

}
