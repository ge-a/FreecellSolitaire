package cs3500.freecell.model.hw02;

import java.util.ArrayList;

/**
 * The open pile of cards in a game of freecell solitaire. This class
 * contains all of the logic surrounding moving to and retrieving cards from
 * an open pile in a game of freecell.
 */
public class OpenPile implements IPile<ICard> {

  private final ArrayList<ArrayList<ICard>> openPile;

  /**
   * Constructs a new open pile.
   *
   * @param openPile the list of list of cards which forms the open pile
   */
  public OpenPile(ArrayList<ArrayList<ICard>> openPile) {
    this.openPile = openPile;
  }

  @Override
  public boolean possibleMove(int destPileNumber, ICard sourceCard, ICard destCard) {
    return this.openPile.get(destPileNumber).size() == 0;
  }

  @Override
  public ICard getCardAtIndex(int pileIndex, int cardIndex) {
    if (pileIndex >= this.openPile.size() || pileIndex < 0) {
      throw new IllegalArgumentException("Invalid pile index");
    }
    if (this.openPile.get(pileIndex).size() == 0) {
      throw new IllegalArgumentException("Cannot get card from empty pile");
    }
    if (cardIndex >= this.openPile.get(pileIndex).size() || cardIndex < 0) {
      throw new IllegalArgumentException("invalid card index");
    }
    return this.openPile.get(pileIndex).get(cardIndex);
  }

  @Override
  public ICard getLastCard(int pileIndex) {
    if (pileIndex >= this.openPile.size() || pileIndex < 0) {
      throw new IllegalArgumentException("Invalid pile index");
    }
    if (this.openPile.get(pileIndex).size() == 0) {
      throw new IllegalArgumentException("Cannot get card from empty pile");
    }
    return this.openPile.get(pileIndex).get(this.openPile.get(pileIndex).size() - 1);
  }

  @Override
  public boolean emptyPile(int pileIndex, ICard sourceCard) {
    if (pileIndex >= this.openPile.size() || pileIndex < 0) {
      throw new IllegalArgumentException("Invalid pile index");
    }
    return this.openPile.get(pileIndex).size() == 0;
  }
}
