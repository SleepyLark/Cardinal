package card.model.bots;

import java.util.ArrayList;

import card.model.cards.Card;
import card.model.players.StandardPlayer;

public class TrashBot extends StandardPlayer
{
	
	private ArrayList<String> usernames;
	public static int DRAW_A_CARD = 0;
	public static int TAKE_FROM_DISCARD = 1;

	public TrashBot(String username)
	{
		super(username);
		usernames = new ArrayList<String>();
		makeUsernames();
		if (username == null)
			randomUsername();
		
	}
	
	private void makeUsernames()
	{
		usernames.add("Toad");
		usernames.add("Yoshi");
		usernames.add("Wario");
		usernames.add("Luigi");
		usernames.add("Mario");
		usernames.add("Waluigi");
		
	}
	
	public void randomUsername()
	{
		super.setUsername(usernames.get((int)(Math.random() * usernames.size())));
	}
	
	public int turn(Card lastCardDiscarded, boolean[] currentHand)
	{
		return DRAW_A_CARD;
	}
	
}
