package cs3500.freecell.model.hw02;

/**
 * The IPile interface represents the functions a pile of cards should be able to perform
 * in determining whether a valid move can be made to a certain type of pile and for
 * retrieving cards from different kinds of piles.
 *
 * @param <T> the card
 */
public interface IPile<T> {

  /**
   * Checks to see if a move of a card to another pile is possible or not.
   *
   * @param destPileNumber the number of the pile being moved to
   * @param sourceCard the card being moved
   * @param destCard the card at the destination
   * @return whether or not the move is possible
   * @throws IllegalArgumentException if the move is not possible
   */
  boolean possibleMove(int destPileNumber, T sourceCard, T destCard)
          throws IllegalArgumentException;

  /**
   * Observes the card in the given pile and index of a card.
   *
   * @param pileIndex the index of the pile
   * @param cardIndex the index of the card
   * @return the card at the pile and card index
   * @throws IllegalArgumentException if the pile being retrieved from is empty or if the indices
   *     are invalid
   */
  T getCardAtIndex(int pileIndex, int cardIndex) throws IllegalArgumentException;

  /**
   * Observes the last card in a pile.
   *
   * @param pileIndex the index of the pile
   * @return the last card of the pile index
   * @throws IllegalArgumentException if the pile being retrieved from is empty or if the indices
   *     are invalid
   */
  T getLastCard(int pileIndex) throws IllegalArgumentException;

  /**
   * Checks if the pile is empty and performs the appropriate move logic based on the type of pile.
   *
   * @param pileIndex the index of the pile being checked
   * @param sourceCard the card being moved
   * @return whether or not a card can be moved to the pile
   */
  boolean emptyPile(int pileIndex, ICard sourceCard);
}
