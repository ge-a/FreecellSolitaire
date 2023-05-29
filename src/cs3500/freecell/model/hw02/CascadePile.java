package cs3500.freecell.model.hw02;

import java.util.ArrayList;

/**
 * The cascading pile of cards in a game of freecell solitaire. This class contains all of the logic
 * surrounding moving to and retrieving cards from a cascading pile in a game of freecell.
 */
public class CascadePile implements IPile<ICard> {

  private final ArrayList<ArrayList<ICard>> cascPile;
  private boolean allowMultiMove;

  /**
   * Constructs a new cascade pile.
   *
   * @param cascPile the list of list of cards which forms the cascade pile
   */
  public CascadePile(ArrayList<ArrayList<ICard>> cascPile, boolean allowMultiMove) {
    this.cascPile = cascPile;
    this.allowMultiMove = allowMultiMove;
  }

  @Override
  public boolean possibleMove(int destPileNumber, ICard sourceCard, ICard destCard) {
    if (sourceCard.getSuit().equals(CardSuit.SPADE) || sourceCard.getSuit().equals(CardSuit.CLUB)) {
      return (destCard.getSuit().equals(CardSuit.DIAMOND)
              || destCard.getSuit().equals(CardSuit.HEART))
          && sourceCard.getValue().getValue() == destCard.getValue().getValue() - 1;
    } else if (sourceCard.getSuit().equals(CardSuit.HEART)
        || sourceCard.getSuit().equals(CardSuit.DIAMOND)) {
      return (destCard.getSuit().equals(CardSuit.SPADE) || destCard.getSuit().equals(CardSuit.CLUB))
          && sourceCard.getValue().getValue() == destCard.getValue().getValue() - 1;
    }
    return false;
  }

  @Override
  public ICard getCardAtIndex(int pileIndex, int cardIndex) {
    if (pileIndex >= this.cascPile.size() || pileIndex < 0) {
      throw new IllegalArgumentException("Invalid pile index");
    }
    if (this.cascPile.get(pileIndex).size() == 0) {
      throw new IllegalArgumentException("Cannot get card from empty pile");
    }
    if (allowMultiMove && (cardIndex < 0 || cardIndex >= this.cascPile.get(pileIndex).size())) {
      throw new IllegalArgumentException("invalid card index");
    }
    if (!allowMultiMove && cardIndex != this.cascPile.get(pileIndex).size() - 1) {
      throw new IllegalArgumentException("invalid card index");
    }
    return this.cascPile.get(pileIndex).get(cardIndex);
  }

  @Override
  public ICard getLastCard(int pileIndex) {
    if (pileIndex >= this.cascPile.size() || pileIndex < 0) {
      throw new IllegalArgumentException("Invalid pile index");
    }
    if (this.cascPile.get(pileIndex).size() == 0) {
      throw new IllegalArgumentException("Cannot get card from empty pile");
    }
    return this.cascPile.get(pileIndex).get(this.cascPile.get(pileIndex).size() - 1);
  }

  @Override
  public boolean emptyPile(int pileIndex, ICard sourceCard) {
    if (pileIndex >= this.cascPile.size() || pileIndex < 0) {
      throw new IllegalArgumentException("Invalid pile index");
    }
    return cascPile.get(pileIndex).size() == 0;
  }
}
