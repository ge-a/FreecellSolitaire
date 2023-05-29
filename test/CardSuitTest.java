import cs3500.freecell.model.hw02.CardSuit;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tests for the CardSuit enumeration.
 */
public class CardSuitTest {

  @Test
  public void testGetClubSuit() {
    Assert.assertEquals("♣", CardSuit.CLUB.getSuit());
  }

  @Test
  public void testGetHeartSuit() {
    assertEquals("♥", CardSuit.HEART.getSuit());
  }

  @Test
  public void testGetSpadeSuit() {
    assertEquals("♠", CardSuit.SPADE.getSuit());
  }

  @Test
  public void testGetDiamondSuit() {
    assertEquals("♦", CardSuit.DIAMOND.getSuit());
  }

  @Test
  public void testGetClubToString() {
    assertEquals("♣", CardSuit.CLUB.toString());
  }

  @Test
  public void testGetHeartToString() {
    assertEquals("♥", CardSuit.HEART.toString());
  }

  @Test
  public void testGetSpadeToString() {
    assertEquals("♠", CardSuit.SPADE.toString());
  }

  @Test
  public void testGetDiamondToString() {
    assertEquals("♦", CardSuit.DIAMOND.toString());
  }
}
