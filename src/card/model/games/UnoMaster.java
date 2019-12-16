package card.model.games;

import card.controller.CardController;
import card.model.cards.Card;
import card.model.cards.UnoCard;
import card.model.dealers.UnoDealer;
import card.model.players.Player;
import card.model.players.UnoPlayer;
import card.model.players.UnoPlayer.Type;
import card.model.bots.UnoBot;

public class UnoMaster extends GameMaster
{

	private CardController app;
	private boolean reverseOrder;
	private UnoDealer deck;

	public UnoMaster(CardController app)
	{
		super();
		this.app = app;
		reverseOrder = true;
		deck = new UnoDealer(app);

	}

	@Override
	public void startGame()
	{
		setupGame();
		
		boolean gameOver = false;
		app.out("Game start!");
		while (!gameOver)
		{
			if(deck.isDrawDeckEmpty())
			{
				app.out("Out of cards. Reshuffling....");
				deck.reshuffleDiscardPile();
			}
			app.out("it's now " + currentPlayer());
			
			app.out("Discard Pile: " + deck.discardPeek());

			if (!hasPlayableCard(currentPlayer()))
			{
				app.out(currentPlayer() + " can't play anything, they must draw a card");
				deck.draw(currentPlayer());
			}
			else if (!currentPlayer().isBot())
			{
				printHand();

				boolean acceptable = false;
				while (!acceptable)
				{
					app.out("Type the slot of the card you want to play, or type \"draw\" to take a card");
					String input = consoleIn.next();

					if (input.equalsIgnoreCase("draw"))
					{
						app.out("You drew a card");
						acceptable = true;
					}
					else if (this.validInt(input, 0, currentPlayer().getHandSize()))
					{
						int num = Integer.parseInt(input);

						if (cardIsPlayable(currentPlayer().pickCard(num)))
						{
							app.out("You played the " + deck.discard(currentPlayer().playCard(num)));
							acceptable = true;
						}
						else
							app.out("You can't play that card! Try again.");

					}
				}
			}
			else
			{
				int response = ((UnoBot)currentPlayer()).processTurn(deck.discardPeek());
				
				if(response == UnoBot.DRAW_A_CARD)
				{
					app.out(currentPlayer() + " decides to draw a card.");
				}
				else
				{
					app.out(currentPlayer() + " played the " + deck.discard(currentPlayer().playCard(response)));
				}
			}
			
			this.next();
		}

	}

	@Override
	protected void setupGame()
	{
		deck.buildDeck();

		app.out("Enter name:");
		UnoPlayer playerOne = new UnoPlayer(consoleIn.nextLine());
		this.addToGame(playerOne);
		this.addBotPlayers(3, Game.UNO);
		
		deck.shuffleCards();
		deck.dealCards(getPlayers(), 7);
		playerOne.organizeHand(Type.COLOR);
		
		deck.discard(deck.draw());

	}

	/**
	 * switches to the next player <br>
	 * <i> uses a "modulus" effect where the number will cycle back to zero once it
	 * hits max number. useful for readability</i>
	 */
	@Override
	public void next()
	{

		if(reverseOrder)
			currentTurn--;
		else
			currentTurn++;
		
		
		if (currentTurn >= this.numberOfPlayers())
		{
			currentTurn = 0;
		}
		else if (currentTurn < 0)
		{
			currentTurn = numberOfPlayers() - 1;
		}
		turnCount++;
	}

	private boolean hasPlayableCard(Player playerToCheck)
	{
		boolean canPlay = false;

		for (int index = 0; index < playerToCheck.getHandSize(); index++)
		{
			Card current = playerToCheck.pickCard(index);

			canPlay = (cardIsPlayable(current) == true);
		}

		return canPlay;
	}

	private boolean cardIsPlayable(Card cardToPlay)
	{
		boolean canPlay = false;
		UnoCard cardInPlay = (UnoCard) cardToPlay;
		UnoCard lastPlayed = (UnoCard) deck.discardPeek();
		if (cardInPlay.isWild())
		{
			canPlay = true;
		}
		else
		{
			canPlay = (cardInPlay.getType() == lastPlayed.getType() || cardInPlay.getColor() == lastPlayed.getColor());
		}

		return canPlay;

	}

	private void printHand()
	{
		app.out("Hand:");
		for (int index = 0; index < currentPlayer().getHandSize(); index++)
		{
			app.out(index + ": " + currentPlayer().pickCard(index));
		}
	}

}
