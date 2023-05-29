import cs3500.freecell.model.hw02.CardValue;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tests for the CardValue enumeration.
 */
public class CardValueTest {

  @Test
  public void testAceGetValue() {
    Assert.assertEquals(1, CardValue.ACE.getValue());
  }

  @Test
  public void testKingGetValue() {
    assertEquals(13, CardValue.KING.getValue());
  }

  @Test
  public void testSevenGetValue() {
    assertEquals(7, CardValue.SEVEN.getValue());
  }

  @Test
  public void testAceToString() {
    assertEquals("A", CardValue.ACE.toString());
  }

  @Test
  public void testKingToString() {
    assertEquals("K", CardValue.KING.toString());
  }

  @Test
  public void testSevenToString() {
    assertEquals("7", CardValue.SEVEN.toString());
  }
}
