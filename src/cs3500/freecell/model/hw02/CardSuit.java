package cs3500.freecell.model.hw02;

/**
 * An enumeration representing the different possible suits of a card
 * with the four possibilities being a club, diamond, heart, or spade.
 */
public enum CardSuit {
  CLUB("♣"),
  DIAMOND("♦"),
  HEART("♥"),
  SPADE("♠");

  private final String suit;

  /**
   * Constructs a new card suit.
   *
   * @param suit the string value of the suit of the card
   */
  CardSuit(String suit) {
    this.suit = suit;
  }

  /**
   * Observes the string suit of the CardSuit.
   *
   * @return the string of the CardSuit
   */
  public String getSuit() {
    return this.suit;
  }

  @Override
  public String toString() {
    return this.suit;
  }
}
