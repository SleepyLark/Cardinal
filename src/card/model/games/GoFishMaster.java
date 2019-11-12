package card.model.games;

import java.util.ArrayList;
import java.util.Scanner;

import card.controller.CardController;
import card.model.bots.FishBot;
import card.model.bots.TrashBot;
import card.model.cards.Card;
import card.model.cards.PlayingCard;
import card.model.dealers.StandardDealer;
import card.model.players.Player;
import card.model.players.StandardPlayer;
import card.model.players.StandardPlayer.Type;

public class GoFishMaster extends GameMaster
{
	private CardController app;
	private int[] scoreSheet;
	private StandardDealer luigi;
	private StandardPlayer playerOne;
	private Scanner consoleIn;
	
	public GoFishMaster(CardController app)
	{
		super();
		this.app = app;
		consoleIn = new Scanner(System.in);
	}
	
	
	public void startGame()
	{
		setupGame();

		app.out(this.currentPlayer() + " starts first");
		while (!luigi.isDrawDeckEmpty())
		{
			app.out("---------------------");
			if (this.currentPlayer().getHandSize() >= 4)
			{
				if (this.hasASet(this.currentPlayer()))
					app.out(this.currentPlayer() + " got a point!");
			}

			app.out(this.currentPlayer() + "'s score: " + this.getScore(getCurrentTurn()));
			if (!(this.currentPlayer().isBot()))
			{

				app.out("Your hand:");
				app.out("Last card drawn: " + playerOne.getLastDrawnCard());
				playerOne.organizeHand(Type.NUMBER);
				app.out(currentPlayer().getCurrentHand());
				app.out("Size: " + currentPlayer().getHandSize());

				if (currentPlayer().getHandSize() > 0)
				{
					
					boolean valid = false;
					int cardIndex = 0;
					
					//TODO: add method to pick who to choose from
					//app.out("Who do you want to pick from?");
					int personToAsk = 1;
					
					while (!valid)
					{
						app.out("Ask for card:");
						cardIndex = consoleIn.nextInt();
						if(cardIndex > currentPlayer().getHandSize() || cardIndex < 0)
						{
							app.out("Card doesn't exist! Try again.");
						}
						else
						{
							valid = true;
						}
					}
					
					app.out("got any " + ((PlayingCard) playerOne.pickCard(cardIndex)).getNumber() + "'s?");

					ArrayList<PlayingCard> cards = this.askForCard(getPlayer(personToAsk), playerOne.pickCard(cardIndex));

					if (cards.size() == 0)
					{
						app.out("Go fish");
						if (luigi.deckSize() > 0)
						{
							playerOne.addToHand(luigi.draw());
						}
						else
						{
							app.out("Looks like there's no more cards left to draw!");
						}
					}
					else
					{
						app.out(getPlayer(personToAsk)+ " handed you over " + cards);
						playerOne.addToHand(cards);
					}
				}
				else if (luigi.deckSize() != 0)
				{
					app.out("You're app.out of card! Draw another one");
					playerOne.addToHand(luigi.draw());

				}
				else
				{
					app.out("There's no more cards left.");
				}

			}
			else
			{
				if (currentPlayer().getHandSize() > 0)
				{
					PlayingCard cardWanted = ((FishBot) currentPlayer()).takeTurn();
					app.out("got any " + cardWanted.getNumber() + "'s?");

					ArrayList<PlayingCard> cards = this.askForCard((StandardPlayer) playerOne, cardWanted);

					if (cards.size() == 0)
					{
						app.out("Go fish");
						if (luigi.deckSize() > 0)
							currentPlayer().addToHand(luigi.draw());

					}
					else
					{
						app.out("You handed over " + cards);
						currentPlayer().addToHand((Card) cards);
					}
				}
				else
				{
					app.out(currentPlayer() + " ran app.out of cards! They must draw one.");
					currentPlayer().addToHand(luigi.draw());
				}

			}

			this.next();
			app.out("It's now " + this.currentPlayer() + "'s turn");
		}

		app.out("Game over.");

		if (this.determineWinner() == playerOne)
		{
			app.out("You Won!");
		}
		else
		{
			app.out("You lost");
		}

		consoleIn.close();
	}
	
	protected void setupGame()
	{
		
		app.out("Enter name:");
		playerOne = new StandardPlayer(consoleIn.nextLine());
		this.addToGame(playerOne);

		addBotPlayers();


		scoreSheet = new int[this.numberOfPlayers()];

		luigi.buildDeck();
		luigi.shuffleCards();
		luigi.dealCards(this.getPlayers(), 7);
		
	}
	
	private void addBotPlayers()
	{
		boolean error = true;
		int cpuPlayers = 0;
		
		while (error)
		{
			app.out("How many CPU players?");
			cpuPlayers = consoleIn.nextInt();

			if (this.numberOfPlayers() < 1)
			{
				app.out("Invalid number of players.");
			}
			else
				error = false;
		}

		for (int times = 0; times < cpuPlayers; times++)
		{
			this.addToGame(new FishBot(null));
		}
	}
	
	
	/**
	 * searches a players hand to see if it has the card asked for
	 * @param personToPick player to ask for card
	 * @param cardYouWant the card you want to get
	 * @return a list of all cards that are the same <b>(returns null if no cards were found)</b>
	 */
	private ArrayList<PlayingCard> askForCard(Player personToPick, Card cardYouWant)
	{
		int numberType = ((PlayingCard) cardYouWant).getNumber();
		ArrayList<PlayingCard> cardsToTake = new ArrayList<PlayingCard>();
		
		for(int index = 0; index < personToPick.getHandSize(); index++)
		{
			if(((PlayingCard) personToPick.pickCard(index)).getNumber() == numberType)
			{
				cardsToTake.add((PlayingCard) personToPick.discardCard(index));
			}
		}
		
		return cardsToTake;
	}
	
	/**
	 * checks to see if the player has four of a kind
	 * @param personToCheck player to check
	 * @return true if they have four of a kind
	 */
	private boolean hasASet(Player personToCheck)
	{
		ArrayList<PlayingCard> set = new ArrayList<PlayingCard>();
		boolean hasSet = false;
		
		//backwards for loop of player's hand 
		for(int index = personToCheck.getHandSize()-1; index >=0; index--)
		{
			//starts at the beginning and searches each card after it if it's the same
			//else it will move to the next index
			int currentNumber = ((PlayingCard) personToCheck.pickCard(index)).getNumber();
			int count = 0;
			
			for(int nextIndex = index-1; nextIndex >=0; nextIndex--)
			{
				int nextNumber = ((PlayingCard) personToCheck.pickCard(nextIndex)).getNumber();
				//if they are the same card number 
				if(nextNumber == currentNumber)
				{
					//add it to the reference list and increase counter
					set.add((PlayingCard) personToCheck.pickCard(nextIndex));
					count++;
				}
			}
			//if it finds three cards that matches the original card, then it's a four-of-a-kind
			if(count == 3)
			{
				//add the original card to the reference list
				set.add((PlayingCard) personToCheck.pickCard(index));
				//these cards will be removed from the player's hand, so the index needs to be adjusted as such
				index-=4;
				hasSet = true;
				//add a point to the score sheet for the player
				this.addPoint(personToCheck);
				//removes each card in the reference list from the player's hand
				for(index = set.size(); index >= 0; index--)
				{
					Card current = set.get(index);
					personToCheck.discardCard(current);
				}
			}
			set.clear();
			count = 0;
		}
		
		return hasSet;
	}
	
	private void addPoint(int indexOfPlayer)
	{
		scoreSheet[indexOfPlayer]++;
	}
	
	private void addPoint(Player personToCheck)
	{
		addPoint(this.getPlayers().indexOf(personToCheck));
	}
	
	public int getScore(int indexOfPlayer)
	{
		return scoreSheet[indexOfPlayer];
	}
	
	public int getScore(Player personToGet)
	{
		return getScore(this.getPlayers().indexOf(personToGet));
	}
	private StandardPlayer determineWinner()
	{
		StandardPlayer winner = null;
		int highScore = 0;
		for(int index = 0; index < scoreSheet.length; index++)
		{
			if(scoreSheet[index] > highScore)
			{
				highScore = scoreSheet[index];
				winner = (StandardPlayer) this.getPlayer(index);
			}
		}
		
		return winner;
	}
	
}
