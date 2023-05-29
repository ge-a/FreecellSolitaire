import cs3500.freecell.model.hw02.Card;
import cs3500.freecell.model.hw02.CardSuit;
import cs3500.freecell.model.hw02.CardValue;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertEquals;

/**
 * Tests for the Card class.
 */
public class CardTest {

  Card aceOfClubs;
  Card kingOfHearts;
  Card tenOfSpades;

  @Before
  public void init() {
    this.aceOfClubs = new Card(CardValue.ACE, CardSuit.CLUB);
    this.kingOfHearts = new Card(CardValue.KING, CardSuit.HEART);
    this.tenOfSpades = new Card(CardValue.TEN, CardSuit.SPADE);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullCardConstructor() {
    new Card(null, null);
  }

  @Test
  public void testGetAceValue() {
    assertEquals(CardValue.ACE, this.aceOfClubs.getValue());
  }

  @Test
  public void testGetKingValue() {
    assertEquals(CardValue.KING, this.kingOfHearts.getValue());
  }

  @Test
  public void testGet10Value() {
    assertEquals(CardValue.TEN, this.tenOfSpades.getValue());
  }

  @Test
  public void testGetClubSuit() {
    assertEquals(CardSuit.CLUB, this.aceOfClubs.getSuit());
  }

  @Test
  public void testGetHeartSuit() {
    assertEquals(CardSuit.HEART, this.kingOfHearts.getSuit());
  }

  @Test
  public void testGetSpadeSuit() {
    assertEquals(CardSuit.SPADE, this.tenOfSpades.getSuit());
  }

  @Test
  public void testAceClubToString() {
    assertEquals("A♣", this.aceOfClubs.toString());
  }

  @Test
  public void testKingHeartToString() {
    assertEquals("K♥", this.kingOfHearts.toString());
  }

  @Test
  public void testTenSpadeToString() {
    assertEquals("10♠", this.tenOfSpades.toString());
  }

  @Test
  public void testAceOfClubsEqual() {
    assertEquals(this.aceOfClubs, new Card(CardValue.ACE, CardSuit.CLUB));
  }

  @Test
  public void testAceOfClubsNotEqual() {
    assertNotEquals(this.aceOfClubs, new Card(CardValue.ACE, CardSuit.HEART));
  }

  @Test
  public void testKingOfHeartsEqual() {
    assertEquals(this.kingOfHearts, new Card(CardValue.KING, CardSuit.HEART));
  }

  @Test
  public void testKingOfHeartsNotEqual() {
    assertNotEquals(this.kingOfHearts, new Card(CardValue.QUEEN, CardSuit.HEART));
  }

  @Test
  public void testAceOfClubsHashCode() {
    assertEquals(new Card(CardValue.ACE, CardSuit.CLUB).hashCode(),
            this.aceOfClubs.hashCode());
  }

  @Test
  public void testKingOfHeartsHashCode() {
    assertEquals(new Card(CardValue.KING, CardSuit.HEART).hashCode(),
            this.kingOfHearts.hashCode());
  }

  @Test
  public void testTenOfSpadesHashCode() {
    assertEquals(new Card(CardValue.TEN, CardSuit.SPADE).hashCode(),
            this.tenOfSpades.hashCode());
  }
}
