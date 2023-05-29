package cs3500.freecell.model.hw02;

/**
 * A Card in a freecell solitaire game each with 1 of 13 values (Ace through King) and one of four
 * suites (Heart, Diamond, Spade, Club).
 */
public class Card implements ICard {

  private final CardSuit suit;
  private final CardValue value;

  /**
   * Constructs a card object.
   *
   * @param suit the suit of the card
   * @param value the number value of the card
   * @throws IllegalArgumentException if suit or value are null
   */
  public Card(CardValue value, CardSuit suit) throws IllegalArgumentException {
    if (value == null || suit == null) {
      throw new IllegalArgumentException("Cannot pass in null value");
    }
    this.value = value;
    this.suit = suit;
  }

  @Override
  public CardValue getValue() {
    return this.value;
  }

  @Override
  public CardSuit getSuit() {
    return this.suit;
  }

  @Override
  public String toString() {
    StringBuilder cardString = new StringBuilder();
    cardString.append(this.value.toString());
    cardString.append(this.suit.getSuit());
    return cardString.toString();
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof Card)) {
      return false;
    }
    if (o == this) {
      return true;
    }
    Card c = (Card) o;
    return this.value.equals(c.getValue()) && this.suit.equals(c.getSuit());
  }

  @Override
  public int hashCode() {
    int result = 11;
    if (this.suit != null) {
      result = 37 * result + this.value.hashCode();
    }
    if (this.value != null) {
      result = 27 * result + this.suit.hashCode();
    }
    return result;
  }
}
