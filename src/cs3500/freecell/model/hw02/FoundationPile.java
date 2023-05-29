package cs3500.freecell.model.hw02;

import java.util.ArrayList;

/**
 * A foundation pile of cards in a game of freecell solitaire. This class
 * contains all of the logic surrounding moving to and retrieving cards from
 * a foundation pile in a game of freecell.
 */
public class FoundationPile implements IPile<ICard> {

  private final ArrayList<ArrayList<ICard>> foundPile;

  /**
   * Constructs a new foundation pile.
   *
   * @param foundPile the list of list of cards which forms the foundation pile
   */
  public FoundationPile(ArrayList<ArrayList<ICard>> foundPile) {
    this.foundPile = foundPile;
  }

  @Override
  public boolean possibleMove(int destPileNumber, ICard sourceCard, ICard destCard) {
    switch (destCard.getSuit()) {
      case SPADE:
        return (sourceCard.getSuit().equals(CardSuit.SPADE)
            && sourceCard.getValue().getValue() == destCard.getValue().getValue() + 1);
      case CLUB:
        return (sourceCard.getSuit().equals(CardSuit.CLUB)
            && sourceCard.getValue().getValue() == destCard.getValue().getValue() + 1);
      case HEART:
        return (sourceCard.getSuit().equals(CardSuit.HEART)
            && sourceCard.getValue().getValue() == destCard.getValue().getValue() + 1);
      case DIAMOND:
        return (sourceCard.getSuit().equals(CardSuit.DIAMOND)
            && sourceCard.getValue().getValue() == destCard.getValue().getValue() + 1);
      default:
        throw new IllegalArgumentException("there cannot be more foundation piles");
    }
  }

  @Override
  public ICard getCardAtIndex(int pileIndex, int cardIndex) {
    if (pileIndex >= this.foundPile.size() || pileIndex < 0) {
      throw new IllegalArgumentException("Invalid pile index");
    }
    if (this.foundPile.get(pileIndex).size() == 0) {
      throw new IllegalArgumentException("Cannot get card from empty pile");
    }
    if (cardIndex >= this.foundPile.get(pileIndex).size() || cardIndex < 0) {
      throw new IllegalArgumentException("invalid card index");
    }
    return this.foundPile.get(pileIndex).get(cardIndex);
  }

  @Override
  public ICard getLastCard(int pileIndex) {
    if (pileIndex >= this.foundPile.size() || pileIndex < 0) {
      throw new IllegalArgumentException("Invalid pile index");
    }
    if (this.foundPile.get(pileIndex).size() == 0) {
      throw new IllegalArgumentException("Cannot get card from empty pile");
    }
    return this.foundPile.get(pileIndex).get(this.foundPile.get(pileIndex).size() - 1);
  }

  @Override
  public boolean emptyPile(int pileIndex, ICard sourceCard) {
    if (pileIndex >= this.foundPile.size()) {
      throw new IllegalArgumentException("Invalid pile index");
    }
    if (this.foundPile.get(pileIndex).size() == 0) {
      return sourceCard.getValue().getValue() == 1;
    }
    return false;
  }
}
