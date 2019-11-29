package card.model.cards;

public class UnoCard implements Card
{
	public enum Color
	{
		RED, BLUE, YELLOW, GREEN, BLACK;
	}
	
	public static int REVERSE = 11;
	public static int SKIP = 12;
	public static int DRAW_TWO = 13;
	
	private Color color;
	private int type;
	
	public UnoCard(Color color, int type)
	{
		this.color = color;
		this.type = type;
	}
	
	private String colorToString()
	{
		String desc = color+"";
		
		desc = desc.substring(0,1).toUpperCase() + desc.substring(1).toLowerCase(); //makes the enum name readable
		
		return desc;
	}
	
	private String specialToString()
	{
		String desc = null;
		if(type == REVERSE)
		{
			desc = "Reverse";
		}
		else if(type == SKIP)
		{
			desc = "Skip";
		}
		else if(type == DRAW_TWO)
		{
			desc = "+2";
		}
		
		return desc;
	}
	
	public String toString()
	{
		if(type > 10)
		{
			return colorToString() + " " + specialToString();
		}
		else
		{
			return colorToString() + " " + type;
		}
	}
}