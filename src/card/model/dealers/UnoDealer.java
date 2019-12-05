package card.model.dealers;

import card.model.cards.UnoCard;
import card.model.cards.UnoCard.ColorSuit;
import card.controller.CardController;

public class UnoDealer extends Dealer
{

	public UnoDealer(CardController app)
	{
		super(app);
		// TODO Auto-generated constructor stub
	}

	
	public void buildDeck()
	{
		ColorSuit[] suit = {ColorSuit.RED, ColorSuit.GREEN, ColorSuit.BLUE, ColorSuit.YELLOW};
		
		for(ColorSuit currentColor : suit)
		{
			drawDeck.add(new UnoCard(currentColor, 0));
			for(int number = 1; number <= UnoCard.DRAW_TWO; number++)
			{
				drawDeck.add(new UnoCard(currentColor, number));
				drawDeck.add(new UnoCard(currentColor, number));
			}
			
			drawDeck.add(new UnoCard(ColorSuit.BLACK, UnoCard.WILD));
			drawDeck.add(new UnoCard(ColorSuit.BLACK, UnoCard.DRAW_FOUR));
		}
		
	}

}
