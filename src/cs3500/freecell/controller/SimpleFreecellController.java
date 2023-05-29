package cs3500.freecell.controller;

import cs3500.freecell.model.FreecellModel;
import cs3500.freecell.model.PileType;
import cs3500.freecell.model.hw02.ICard;
import cs3500.freecell.view.FreecellTextView;
import cs3500.freecell.view.FreecellView;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

/**
 * The Controller of the freecell game which allows users to interact with and
 * play the game through taking inputs and relaying them to model and view.
 */
public class SimpleFreecellController implements FreecellController<ICard> {
  private final Readable rd;
  private final FreecellModel<ICard> model;
  private final FreecellView view;

  private PileType sourcePile;
  private int pileIndex;
  private int cardIndex;
  private PileType destPile;
  private int destPileIndex;
  private int inputCounter;

  /**
   * Constructs a SimpleFreecellController object.
   *
   * @param model the model of the game
   * @param rd the input of the game
   * @param ap the output of the game
   * @throws IllegalArgumentException if the readable, appendable, or model are null
   */
  public SimpleFreecellController(FreecellModel<ICard> model, Readable rd, Appendable ap)
          throws IllegalArgumentException {
    if (rd == null || ap == null) {
      throw new IllegalArgumentException("cannot have null in or outputs");
    }
    if (model == null) {
      throw new IllegalArgumentException("cannot have null model");
    }
    this.model = model;
    this.rd = rd;
    view = new FreecellTextView(model, ap);
    this.inputCounter = 1;
  }

  @Override
  public void playGame(List<ICard> deck, int numCascades, int numOpens, boolean shuffle)
          throws IllegalStateException, IllegalArgumentException {
    if (deck == null) {
      throw new IllegalArgumentException("Cannot have a null deck");
    }
    try {
      model.startGame(deck, numCascades, numOpens, shuffle);
      updateBoard();
    } catch (IllegalArgumentException e) {
      makeMessage("Could not start game.");
      return;
    }
    Scanner scanner = new Scanner(rd);
    while (scanner.hasNext()) {
      String input = scanner.next().toLowerCase();
      if (input.equals("q")) {
        makeMessage("Game quit prematurely.");
        return;
      }
      if (this.inputCounter % 4 == 3) {
        destPileInput(input);
      }
      if (this.inputCounter % 4 == 2) {
        cardIndexInput(input);
      }
      if (this.inputCounter % 4 == 1) {
        sourcePileInput(input);
      }
      if (this.inputCounter % 4 == 0) {
        makeMove(numCascades, numOpens);
        this.inputCounter++;
      }
      if (model.isGameOver()) {
        makeMessage("Game over.");
        return;
      }
    }
    throw new IllegalStateException("Readable has run out of inputs");
  }

  /**
   * Relays a string message to be displayed in the view class.
   * @param message the string message to be relayed to the view
   * @throws IllegalStateException when writing to the appendable fails
   */
  private void makeMessage(String message) throws  IllegalStateException {
    try {
      view.renderMessage(message);
    } catch (IOException e) {
      throw new IllegalStateException("Could not write to the appendable");
    }
  }

  /**
   * Connects to the view class in order to display the board on which
   * the game of freecell is being played.
   * @throws IllegalStateException when writing to appendable fails
   */
  private void updateBoard() throws IllegalStateException {
    try {
      view.renderBoard();
    } catch (IOException e) {
      throw new IllegalStateException("Writing to the Appendable object fails");
    }
  }

  /**
   * Checks user input to see if there is a valid source pile within
   * their input and saves the corresponding pile type and index from
   * their input. If there is no valid input, prompts the user that
   * they inputted an invalid value and lets them know to try again.
   * @param input the input to be checked
   */
  private void sourcePileInput(String input) {
    switch (input.substring(0, 1)) {
      case "c" :
        try {
          this.pileIndex = Integer.parseInt(input.substring(1));
          this.sourcePile = PileType.CASCADE;
          inputCounter++;
          break;
        } catch (NumberFormatException e) {
          makeMessage("Invalid source pile index, try again!\n");
          break;
        }
      case "o" :
        try {
          this.pileIndex = Integer.parseInt(input.substring(1));
          this.sourcePile = PileType.OPEN;
          inputCounter++;
          break;
        } catch (NumberFormatException e) {
          makeMessage("Invalid source pile index, try again!\n");
          break;
        }
      case "f" :
        try {
          this.pileIndex = Integer.parseInt(input.substring(1));
          this.sourcePile = PileType.FOUNDATION;
          inputCounter++;
          break;
        } catch (NumberFormatException e) {
          makeMessage("Invalid source pile index, try again!\n");
          break;
        }
      default :
        makeMessage("Invalid source pile, try again!\n");
    }
  }

  /**
   * Checks user input to see if there is a valid card index within
   * their input and saves the corresponding card index from
   * their input. If there is no valid input, prompts the user that
   * they inputted an invalid value and lets them know to try again.
   * @param input the input to be checked
   */
  private void cardIndexInput(String input) {
    try {
      this.cardIndex = Integer.parseInt(input);
      inputCounter++;
    } catch (IllegalArgumentException e) {
      makeMessage("Invalid card index, try again!\n");
    }
  }

  /**
   * Checks user input to see if there is a valid destination pile within
   * their input and saves the corresponding destination pile and index from
   * their input. If there is no valid input, prompts the user that
   * they inputted an invalid value and lets them know to try again.
   * @param input the input to be checked
   */
  private void destPileInput(String input) {
    switch (input.substring(0, 1)) {
      case "c" :
        try {
          this.destPileIndex = Integer.parseInt(input.substring(1));
          this.destPile = PileType.CASCADE;
          inputCounter++;
          break;
        } catch (NumberFormatException e) {
          makeMessage("Invalid destination pile index, try again!\n");
          break;
        }
      case "o" :
        try {
          this.destPileIndex = Integer.parseInt(input.substring(1));
          this.destPile = PileType.OPEN;
          inputCounter++;
          break;
        } catch (NumberFormatException e) {
          makeMessage("Invalid destination pile index, try again!\n");
          break;
        }
      case "f" :
        try {
          this.destPileIndex = Integer.parseInt(input.substring(1));
          this.destPile = PileType.FOUNDATION;
          inputCounter++;
          break;
        } catch (NumberFormatException e) {
          makeMessage("Invalid destination pile index, try again!\n");
          break;
        }
      default :
        makeMessage("Invalid destination pile, try again!\n");
    }
  }

  /**
   * Connects with the model to make the move inputted into
   * the readable. Sends an invalid move message with
   * potential reasons why the move failed if calling
   * the move method returns an error.
   *
   * @param numCascades the number of cascade piles
   * @param numOpens the number of open piles
   */
  private void makeMove(int numCascades, int numOpens) {
    try {
      model.move(this.sourcePile, this.pileIndex - 1,
              this.cardIndex - 1, this.destPile, this.destPileIndex - 1);
      updateBoard();
    } catch (IllegalArgumentException e) {
      makeMessage("Invalid Move. Try again.");
      makeMessage(helpMessage(numCascades, numOpens) + "\n");
    }
  }

  /**
   * Gives a string saying a potential reason why the user's
   * attempt to move failed. Returns an empty string if the
   * case is not caught within the cases on hand.
   *
   * @param numCascades the number of cascade piles
   * @param numOpens the number of open piles
   * @return a string telling the user why their move failed
   */
  private String helpMessage(int numCascades, int numOpens) {
    if (this.pileIndex < 1 || this.cardIndex < 1 || this.destPileIndex < 1) {
      return " Cannot have an index less than 1";
    } else if (this.sourcePile.equals(PileType.FOUNDATION)) {
      return " Cannot move from foundation pile.";
    } else if ((this.pileIndex > numCascades && sourcePile.equals(PileType.CASCADE))
            || (this.pileIndex > numOpens & sourcePile.equals(PileType.OPEN))) {
      return " Your source pile index is invalid.";
    } else if ((this.destPileIndex > numCascades && destPile.equals(PileType.CASCADE))
            || this.destPileIndex > numOpens && destPile.equals(PileType.OPEN)) {
      return " Your destination pile index is invalid.";
    } else if (this.destPile.equals(PileType.FOUNDATION) && this.destPileIndex > 4) {
      return "Your destination pile index is invalid";
    }
    else if (destPile.equals(PileType.CASCADE) || destPile.equals(PileType.FOUNDATION)) {
      return " Card values do not align for such a move.";
    }
    return "";
  }
}
