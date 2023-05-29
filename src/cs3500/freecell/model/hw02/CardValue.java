package cs3500.freecell.model.hw02;

/**
 * An enumeration representing the different values of a card, with the 13
 * different options being Ace through King, with integers 1 through 13
 * representing each value.
 */
public enum CardValue {
  ACE(1),
  TWO(2),
  THREE(3),
  FOUR(4),
  FIVE(5),
  SIX(6),
  SEVEN(7),
  EIGHT(8),
  NINE(9),
  TEN(10),
  JACK(11),
  QUEEN(12),
  KING(13);

  private final int value;

  /**
   * Constructs a new card value.
   *
   * @param value the integer value of the card
   */
  CardValue(int value) {
    this.value = value;
  }

  /**
   * Observes the integer value of the CardValue.
   *
   * @return the int value of the CardValue
   */
  public int getValue() {
    return this.value;
  }

  @Override
  public String toString() {
    switch (this.value) {
      case 1 :
        return "A";
      case 11 :
        return "J";
      case 12 :
        return "Q";
      case 13 :
        return "K";
      default :
        return Integer.toString(this.value);
    }
  }
}
