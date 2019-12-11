package card.model.bots;

import java.util.ArrayList;

import card.model.cards.Card;
import card.model.players.Player;

public abstract class Bot extends Player
{
	private ArrayList<String> usernames;
	
	public Bot(String username)
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
	
	
	@Override
	public boolean isBot()
	{
		return true;
	}
	
	@Override
	public int compare(Card arg0, Card arg1)
	{
		return 0;
	}
}
