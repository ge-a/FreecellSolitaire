import cs3500.freecell.model.FreecellModel;
import cs3500.freecell.model.PileType;
import cs3500.freecell.model.hw02.ICard;

import java.io.IOException;
import java.util.List;

/**
 * A Mock freecell model for testing the inputs to the controller.
 */
public class MockFreecellModel implements FreecellModel<ICard> {

  private final Appendable out;

  /**
   * Constructs a mock model.
   *
   * @param out the output the mock model is testing
   */
  public MockFreecellModel(Appendable out) {
    if (out == null) {
      throw new IllegalArgumentException("Cannot have a null output");
    }
    this.out = out;
  }

  @Override
  public List<ICard> getDeck() {
    return null;
  }

  @Override
  public void startGame(List<ICard> deck, int numCascadePiles, int numOpenPiles, boolean shuffle)
      throws IllegalArgumentException {
    try {
      StringBuilder s = new StringBuilder();
      s.append(deck.toString());
      s.append(" ").append(numCascadePiles).append(" ").append(numOpenPiles);
      s.append(" ").append(shuffle);
      out.append(s.toString());
    } catch (IOException e) {
      throw new IllegalStateException("Could not write to appendable");
    }
  }

  @Override
  public void move(
          PileType source, int pileNumber, int cardIndex, PileType destination, int destPileNumber)
      throws IllegalArgumentException, IllegalStateException {
    try {
      StringBuilder s = new StringBuilder();
      s.append(pileTypeToString(source)).append(pileNumber);
      s.append(" ").append(cardIndex);
      s.append(" ").append(pileTypeToString(destination)).append(destPileNumber);
      out.append(s.toString());
    } catch (IOException e) {
      throw new IllegalStateException("Could not write to appendable");
    }
  }

  /**
   * To find the corresponding string to a pile type.
   * @param type the type of the pile
   * @return a string letter corresponding to the type
   * @throws IllegalArgumentException if there is an invalid piletype
   */
  private String pileTypeToString(PileType type) throws IllegalArgumentException {
    switch (type) {
      case OPEN:
        return "O";
      case CASCADE:
        return "C";
      case FOUNDATION:
        return "F";
      default:
        throw new IllegalArgumentException("Cannot have any other pile type");
    }
  }

  @Override
  public boolean isGameOver() {
    try {
      out.append("game not over ");
    } catch (IOException e) {
      throw new IllegalStateException("Could not write to appendable");
    }
    return false;
  }

  @Override
  public int getNumCardsInFoundationPile(int index)
      throws IllegalArgumentException, IllegalStateException {
    return 0;
  }

  @Override
  public int getNumCascadePiles() {
    return 0;
  }

  @Override
  public int getNumCardsInCascadePile(int index)
      throws IllegalArgumentException, IllegalStateException {
    return 0;
  }

  @Override
  public int getNumCardsInOpenPile(int index)
      throws IllegalArgumentException, IllegalStateException {
    return 0;
  }

  @Override
  public int getNumOpenPiles() {
    return 0;
  }

  @Override
  public ICard getFoundationCardAt(int pileIndex, int cardIndex)
      throws IllegalArgumentException, IllegalStateException {
    return null;
  }

  @Override
  public ICard getCascadeCardAt(int pileIndex, int cardIndex)
      throws IllegalArgumentException, IllegalStateException {
    return null;
  }

  @Override
  public ICard getOpenCardAt(int pileIndex)
      throws IllegalArgumentException, IllegalStateException {
    return null;
  }
}
