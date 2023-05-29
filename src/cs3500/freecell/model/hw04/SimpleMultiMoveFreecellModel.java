package cs3500.freecell.model.hw04;

import cs3500.freecell.model.FreecellModel;
import cs3500.freecell.model.PileType;
import cs3500.freecell.model.hw02.CascadePile;
import cs3500.freecell.model.hw02.ICard;
import cs3500.freecell.model.hw02.IPile;
import cs3500.freecell.model.hw02.SimpleFreecellModel;

import java.util.ArrayList;
import java.util.List;

/*
 * Changes made :
 *  - In CascadePile, changed constructor to add a boolean field to see when you
 *    can get a card that is not the last in a pile.
 *  - In SimpleFreecellModel, added a field and constructor for when multiple cards
 *    are allowed to be moved from a cascade pile. Changed the getPiles class to accommodate
 *    the new CascadePile constructor, taking in the new field.
 *  - These changes were made to allow a card to be taken from a cascade pile that
 *    is not the last card in the pile during only a multimove in a multimove game.
 */

/**
 * Represents the model of the freecell game where multiple legal moves at a time are allowed. This
 * model uses a {@code ICard } representation only allowing card moves.
 */
public class SimpleMultiMoveFreecellModel implements FreecellModel<ICard> {

  private final FreecellModel<ICard> multiMoveDelegate;

  /**
   * Constructs a new SimpleMultiMoveFreecellModel object.
   */
  public SimpleMultiMoveFreecellModel() {
    this.multiMoveDelegate = new SimpleFreecellModel(true);
  }

  @Override
  public List<ICard> getDeck() {
    return this.multiMoveDelegate.getDeck();
  }

  @Override
  public void startGame(List<ICard> deck, int numCascadePiles, int numOpenPiles, boolean shuffle)
      throws IllegalArgumentException {
    this.multiMoveDelegate.startGame(deck, numCascadePiles, numOpenPiles, shuffle);
  }

  @Override
  public void move(
          PileType source, int pileNumber, int cardIndex, PileType destination, int destPileNumber)
          throws IllegalArgumentException, IllegalStateException {
    multiMove(source, pileNumber, cardIndex, destination, destPileNumber);
  }


  /**
   * Moves a card, or multiple cards from a source to destination pile if the move is valid. A move
   * of multiple cards is only possible if they are from cascade to cascade piles and if the card
   * values still line up and there are enough empty intermediate piles.
   *
   * @param source the type of pile to be moved from
   * @param pileNumber the index of the pile to be moved from
   * @param cardIndex the index of the card, or first card to be moved
   * @param destination the type of the pile to be moved to
   * @param destPileNumber the index of the pile to be moved to
   * @throws IllegalArgumentException if the move is invalid or not possible for any reason
   * @throws IllegalStateException if the game has not yet started
   */
  private void multiMove(
      PileType source, int pileNumber, int cardIndex, PileType destination, int destPileNumber)
      throws IllegalArgumentException, IllegalStateException {
    if (isMultiMove(pileNumber, cardIndex, source, destination)) {
      if (isValidMultiMove(pileNumber, cardIndex)
              && !samePile(source, pileNumber, destination, destPileNumber)) {
        int cardsToMove = getNumCardsInCascadePile(pileNumber) - cardIndex;
        for (int i = 0; i < cardsToMove; i++) {
          this.multiMoveDelegate.move(source, pileNumber, cardIndex, destination, destPileNumber);
        }
      } else {
        throw new IllegalArgumentException("Invalid multicard move!");
      }
    } else {
      if (source.equals(PileType.CASCADE)
          && cardIndex != getNumCardsInCascadePile(pileNumber) - 1) {
        throw new IllegalArgumentException("Invalid card index!");
      }
      this.multiMoveDelegate.move(source, pileNumber, cardIndex, destination, destPileNumber);
    }
  }

  /**
   * Checks to see based on a given card index if moving this card would require a multimove.
   *
   * @param cardIndex the index of the card to be moved
   * @return whether or not a move would be a multimove
   */
  private boolean isMultiMove(
      int pileIndex, int cardIndex, PileType sourceType, PileType destPile) {
    return cardIndex < getNumCardsInCascadePile(pileIndex) - 1
        && sourceType.equals(PileType.CASCADE)
        && destPile.equals(PileType.CASCADE);
  }

  /**
   * Checks if a move of multiple cards between cascade piles is possible.
   *
   * @param pileIndex the index of the pile of cards to be moved from
   * @param cardIndex the index of the first card to be moved
   * @return if a move of multiple cards between cascade piles is possible.
   */
  private boolean isValidMultiMove(int pileIndex, int cardIndex) {
    return validCardOrder(pileIndex, cardIndex) && enoughEmptyPiles(pileIndex, cardIndex);
  }

  /**
   * Checks if the suits and values of the cards to be moved are allow for a valid move between
   * cascade piles.
   *
   * @param pileIndex the index of the pile to move cards from
   * @param cardIndex the index of the first card to be moved
   * @return if the order of the multiple cascade cards is valid for a move
   */
  private boolean validCardOrder(int pileIndex, int cardIndex) {
    int validCount = 0;
    IPile<ICard> c = new CascadePile(new ArrayList<>(), false);
    for (int i = 1; i < getNumCardsInCascadePile(pileIndex) - cardIndex; i++) {
      if (c.possibleMove(
          0,
          getCascadeCardAt(pileIndex, cardIndex + i),
          getCascadeCardAt(pileIndex, cardIndex + i - 1))) {
        validCount++;
      }
    }
    return validCount == getNumCardsInCascadePile(pileIndex) - cardIndex - 1;
  }

  /**
   * Checks whether or not there are enough empty cascade and open piles to make a move with
   * multiple cards from a cascade pile.
   *
   * @param pileIndex the index of the pile to be moved from
   * @param cardIndex the index of the first card to be moved
   * @return if a move with multiple cascade cards is possible given the number of empty open and
   *     cascade piles
   */
  private boolean enoughEmptyPiles(int pileIndex, int cardIndex) {
    return getNumCardsInCascadePile(pileIndex) - cardIndex
        <= (getNumEmptyOpenPiles() + 1) * Math.pow(2, getNumEmptyCascadePiles());
  }

  /**
   * Finds the number of open piles that have no cards in them.
   *
   * @return the number of open piles that are empty
   */
  private int getNumEmptyOpenPiles() {
    int numEmptyPiles = 0;
    for (int i = 0; i < getNumOpenPiles(); i++) {
      if (getNumCardsInOpenPile(i) == 0) {
        numEmptyPiles++;
      }
    }
    return numEmptyPiles;
  }

  /**
   * Finds the number of cascade piles that have no cards in them.
   *
   * @return the number of cascade piles that are empty
   */
  private int getNumEmptyCascadePiles() {
    int numEmptyPiles = 0;
    for (int i = 0; i < getNumCascadePiles(); i++) {
      if (getNumCardsInCascadePile(i) == 0) {
        numEmptyPiles++;
      }
    }
    return numEmptyPiles;
  }

  /**
   * To return whether or not the source and destination piles are the same pile.
   *
   * @param source the source pile type
   * @param pileNumber the source pile number
   * @param destination the destination pile type
   * @param destPileNumber the destination pile number
   * @return whether or not the source and destination piles are the same pile
   */
  private boolean samePile(
          PileType source, int pileNumber, PileType destination, int destPileNumber) {
    return source.equals(destination) && pileNumber == destPileNumber;
  }

  @Override
  public boolean isGameOver() {
    return this.multiMoveDelegate.isGameOver();
  }

  @Override
  public int getNumCardsInFoundationPile(int index)
      throws IllegalArgumentException, IllegalStateException {
    return this.multiMoveDelegate.getNumCardsInFoundationPile(index);
  }

  @Override
  public int getNumCascadePiles() {
    return this.multiMoveDelegate.getNumCascadePiles();
  }

  @Override
  public int getNumCardsInCascadePile(int index)
      throws IllegalArgumentException, IllegalStateException {
    return this.multiMoveDelegate.getNumCardsInCascadePile(index);
  }

  @Override
  public int getNumCardsInOpenPile(int index)
      throws IllegalArgumentException, IllegalStateException {
    return this.multiMoveDelegate.getNumCardsInOpenPile(index);
  }

  @Override
  public int getNumOpenPiles() {
    return this.multiMoveDelegate.getNumOpenPiles();
  }

  @Override
  public ICard getFoundationCardAt(int pileIndex, int cardIndex)
      throws IllegalArgumentException, IllegalStateException {
    return this.multiMoveDelegate.getFoundationCardAt(pileIndex, cardIndex);
  }

  @Override
  public ICard getCascadeCardAt(int pileIndex, int cardIndex)
      throws IllegalArgumentException, IllegalStateException {
    return this.multiMoveDelegate.getCascadeCardAt(pileIndex, cardIndex);
  }

  @Override
  public ICard getOpenCardAt(int pileIndex) throws IllegalArgumentException, IllegalStateException {
    return this.multiMoveDelegate.getOpenCardAt(pileIndex);
  }
}
