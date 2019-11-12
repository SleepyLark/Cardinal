package card.model.bots;

import java.util.ArrayList;

import card.model.players.StandardPlayer;

public class StandardBot extends StandardPlayer
{
	private ArrayList<String> usernames;
	
	public StandardBot(String name)
	{
		super(name);
		usernames = new ArrayList<String>();
		makeUsernames();
		if (name == null)
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
}
