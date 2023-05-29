package cs3500.freecell.model;

import cs3500.freecell.model.hw02.ICard;
import cs3500.freecell.model.hw02.SimpleFreecellModel;
import cs3500.freecell.model.hw04.SimpleMultiMoveFreecellModel;

/**
 * Factory class to allow users to decide whether or not they want to play
 * a game of Freecell where they are only allowed to move one card at a time
 * or where they are allowed to move multiple cards at a time from between
 * cascade piles so long as the moves are legal.
 */
public class FreecellModelCreator {

  /**
   * Constructs a new FreecellModelCreator object.
   */
  public FreecellModelCreator() {
    // does nothing as there are no fields to declare.
  }

  /**
   * Enumeration representing the two different types of the freecell game
   * SINGLEMOVE, where only one move is allowed at a time, and MULTIMOVE,
   * where multiple legal moves between cascade piles are allowed.
   */
  public enum GameType {
    SINGLEMOVE,
    MULTIMOVE;
  }

  /**
   * Provides an object of the model corresponding to the type of freecell
   * game inputted as a paramter.
   *
   * @param type the type of game
   * @return the model object of the corresponding freecell type
   */
  public static FreecellModel<ICard> create(GameType type) {
    switch (type) {
      case SINGLEMOVE:
        return new SimpleFreecellModel();
      case MULTIMOVE:
        return new SimpleMultiMoveFreecellModel();
      default:
        throw new IllegalArgumentException("Cannot have any other gametype");
    }
  }
}
