package card.model.cards;

public class UnoCard implements Card
{
	public enum ColorSuit
	{
		RED, BLUE, YELLOW, GREEN, BLACK;
	}
	
	public static int REVERSE = 10;
	public static int SKIP = 11;
	public static int DRAW_TWO = 12;
	public static int WILD = 13;
	public static int DRAW_FOUR = 14;
	
	private ColorSuit color;
	private int type;
	
	public UnoCard(ColorSuit color, int type)
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
	
	public int getType()
	{
		return this.type;
	}
	
	public ColorSuit getColor()
	{
		return this.color;
	}
	
	private String specialToString()
	{
		String desc = null;
		
		if(type == REVERSE)
			desc = "Reverse";
		
		else if(type == SKIP)
			desc = "Skip";
		
		else if(type == DRAW_TWO)
			desc = "+2";
		
		else if(type == WILD)
			desc = "Wild";
		
		else if(type == DRAW_FOUR)
			desc = "+4";
		
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
