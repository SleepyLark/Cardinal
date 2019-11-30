package card.view;

import card.controller.CardController;

import java.awt.Dimension;

import javax.swing.JFrame;

public class CardFrame extends JFrame
{
	private CardController app;
	private CardPanel appPanel;
	
	public CardFrame(CardController app)
	{
		super();
		this.app = app;
		appPanel = new CardPanel(app);
		
	}
	
	private void setupFrame()
	{	
		this.setContentPane(appPanel);
		this.setSize(500, 500);
		this.setVisible(false);
		this.setResizable(true);
		this.setLocationRelativeTo(null);
		this.setMinimumSize(new Dimension(500,500));
		this.setTitle("Calendar");
	}
}
