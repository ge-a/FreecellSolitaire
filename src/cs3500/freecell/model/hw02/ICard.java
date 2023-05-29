package cs3500.freecell.model.hw02;

/**
 * The ICard interface represents the operations a card in a
 * game of freecell should be able to perform.
 */
public interface ICard {

  /**
   * Observes the card value of the card.
   *
   * @return the card value of the card
   */
  CardValue getValue();

  /**
   * Observes the suit of the card.
   *
   * @return the suit of the card
   */
  CardSuit getSuit();

  /**
   * Formats the card as a string.
   *
   * @return the information of the card as a string
   */
  String toString();

  /**
   * Returns true if a card has the same suite and value as another card.
   *
   * @param o the other card
   * @return whether or not a card has the same suite and value as another card
   */
  boolean equals(Object o);

  /**
   * Return a unique hashCode for each Card object.
   *
   * @return a unique integer for each Card object
   */
  int hashCode();
}
