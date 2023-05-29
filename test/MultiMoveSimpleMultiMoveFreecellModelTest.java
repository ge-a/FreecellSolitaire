import cs3500.freecell.model.FreecellModel;
import cs3500.freecell.model.PileType;
import cs3500.freecell.model.hw02.Card;
import cs3500.freecell.model.hw02.CardSuit;
import cs3500.freecell.model.hw02.CardValue;
import cs3500.freecell.model.hw02.ICard;
import cs3500.freecell.model.hw02.SimpleFreecellModel;
import cs3500.freecell.model.hw04.SimpleMultiMoveFreecellModel;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Tests for the multimove feature of the SimpleMultiMoveFreecellModel class.
 *
 * <p>To find tests for the overall SimpleMultiMoveFreecellModel class go to the
 * AbstractFreecellModelTest class
 */
public class MultiMoveSimpleMultiMoveFreecellModelTest {

  FreecellModel<ICard> model;
  List<ICard> deck;

  @Before
  public void init() {
    this.model = new SimpleMultiMoveFreecellModel();
    this.deck = this.model.getDeck();
  }

  @Test
  public void testValidThreeCardMultiMoveEnoughOPenEmptyPiles() {
    this.model.startGame(this.deck, 20, 2, false);
    this.model.move(PileType.CASCADE, 6, 2, PileType.CASCADE, 14);
    this.model.move(PileType.CASCADE, 12, 1, PileType.CASCADE, 14);
    this.model.move(PileType.CASCADE, 14, 1, PileType.CASCADE, 8);
    assertEquals(1, this.model.getNumCardsInCascadePile(14));
    assertEquals(6, this.model.getNumCardsInCascadePile(8));
    assertEquals(new Card(CardValue.SEVEN, CardSuit.HEART), this.model.getCascadeCardAt(8, 5));
    assertEquals(new Card(CardValue.EIGHT, CardSuit.SPADE), this.model.getCascadeCardAt(8, 4));
    assertEquals(new Card(CardValue.NINE, CardSuit.HEART), this.model.getCascadeCardAt(8, 3));
  }

  @Test
  public void testValidThreeCardMultiMoveEnoughOpenEmptyPilesFilledOpenPile() {
    this.model.startGame(this.deck, 20, 3, false);
    this.model.move(PileType.CASCADE, 1, 2, PileType.OPEN, 0);
    this.model.move(PileType.CASCADE, 6, 2, PileType.CASCADE, 14);
    this.model.move(PileType.CASCADE, 12, 1, PileType.CASCADE, 14);
    this.model.move(PileType.CASCADE, 14, 1, PileType.CASCADE, 8);
    assertEquals(1, this.model.getNumCardsInCascadePile(14));
    assertEquals(6, this.model.getNumCardsInCascadePile(8));
    assertEquals(new Card(CardValue.SEVEN, CardSuit.HEART), this.model.getCascadeCardAt(8, 5));
    assertEquals(new Card(CardValue.EIGHT, CardSuit.SPADE), this.model.getCascadeCardAt(8, 4));
    assertEquals(new Card(CardValue.NINE, CardSuit.HEART), this.model.getCascadeCardAt(8, 3));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testValidThreeCardMultiMoveNotEnoughOpenEmptyPiles() {
    this.model.startGame(this.deck, 20, 1, false);
    this.model.move(PileType.CASCADE, 6, 2, PileType.CASCADE, 14);
    this.model.move(PileType.CASCADE, 12, 1, PileType.CASCADE, 14);
    this.model.move(PileType.CASCADE, 14, 1, PileType.CASCADE, 8);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testValidThreeCardMultiMoveNotEnoughOpenEmptyPilesFilledOpenPile() {
    this.model.startGame(this.deck, 20, 2, false);
    this.model.move(PileType.CASCADE, 1, 2, PileType.OPEN, 0);
    this.model.move(PileType.CASCADE, 6, 2, PileType.CASCADE, 14);
    this.model.move(PileType.CASCADE, 12, 1, PileType.CASCADE, 14);
    this.model.move(PileType.CASCADE, 14, 1, PileType.CASCADE, 8);
  }

  @Test
  public void testValidTwoCardMultiMoveEnoughCascadeEmptyPiles() {
    this.model.startGame(this.deck, 20, 3, false);
    this.model.move(PileType.CASCADE, 1, 2, PileType.OPEN, 0);
    this.model.move(PileType.CASCADE, 1, 1, PileType.OPEN, 1);
    this.model.move(PileType.CASCADE, 1, 0, PileType.OPEN, 2);
    this.model.move(PileType.CASCADE, 6, 2, PileType.CASCADE, 14);
    this.model.move(PileType.CASCADE, 14, 1, PileType.CASCADE, 8);
    assertEquals(1, this.model.getNumCardsInCascadePile(14));
    assertEquals(5, this.model.getNumCardsInCascadePile(8));
    assertEquals(new Card(CardValue.EIGHT, CardSuit.SPADE), this.model.getCascadeCardAt(8, 4));
    assertEquals(new Card(CardValue.NINE, CardSuit.HEART), this.model.getCascadeCardAt(8, 3));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testValidThreeCardMultiMoveNotEnoughCascadeEmptyPiles() {
    this.model.startGame(this.deck, 20, 3, false);
    this.model.move(PileType.CASCADE, 1, 2, PileType.OPEN, 0);
    this.model.move(PileType.CASCADE, 1, 1, PileType.OPEN, 1);
    this.model.move(PileType.CASCADE, 1, 0, PileType.OPEN, 2);
    this.model.move(PileType.CASCADE, 6, 2, PileType.CASCADE, 14);
    this.model.move(PileType.CASCADE, 12, 1, PileType.CASCADE, 14);
    this.model.move(PileType.CASCADE, 14, 1, PileType.CASCADE, 8);
  }

  @Test
  public void testValidFourCardMultiMoveEnoughCascadeAndOpenEmptyPiles() {
    this.model.startGame(this.deck, 20, 4, false);
    this.model.move(PileType.CASCADE, 1, 2, PileType.OPEN, 0);
    this.model.move(PileType.CASCADE, 1, 1, PileType.OPEN, 1);
    this.model.move(PileType.CASCADE, 1, 0, PileType.OPEN, 2);
    this.model.move(PileType.CASCADE, 6, 2, PileType.CASCADE, 14);
    this.model.move(PileType.CASCADE, 12, 1, PileType.CASCADE, 14);
    this.model.move(PileType.CASCADE, 4, 2, PileType.CASCADE, 14);
    this.model.move(PileType.CASCADE, 14, 1, PileType.CASCADE, 8);
    assertEquals(1, this.model.getNumCardsInCascadePile(14));
    assertEquals(7, this.model.getNumCardsInCascadePile(8));
    assertEquals(new Card(CardValue.SIX, CardSuit.SPADE), this.model.getCascadeCardAt(8, 6));
    assertEquals(new Card(CardValue.SEVEN, CardSuit.HEART), this.model.getCascadeCardAt(8, 5));
    assertEquals(new Card(CardValue.EIGHT, CardSuit.SPADE), this.model.getCascadeCardAt(8, 4));
    assertEquals(new Card(CardValue.NINE, CardSuit.HEART), this.model.getCascadeCardAt(8, 3));
  }

  @Test
  public void testValidFiveCardMultiMoveEnoughEmptyCascadeAndOpenEmptyPiles() {
    this.model.startGame(this.deck, 20, 4, false);
    this.model.move(PileType.CASCADE, 10, 2, PileType.OPEN, 0);
    this.model.move(PileType.CASCADE, 6, 2, PileType.CASCADE, 14);
    this.model.move(PileType.CASCADE, 12, 1, PileType.CASCADE, 14);
    this.model.move(PileType.CASCADE, 4, 2, PileType.CASCADE, 14);
    this.model.move(PileType.CASCADE, 10, 1, PileType.CASCADE, 14);
    this.model.move(PileType.CASCADE, 10, 0, PileType.OPEN, 1);
    this.model.move(PileType.CASCADE, 14, 1, PileType.CASCADE, 8);
    assertEquals(1, this.model.getNumCardsInCascadePile(14));
    assertEquals(8, this.model.getNumCardsInCascadePile(8));
    assertEquals(new Card(CardValue.FIVE, CardSuit.HEART), this.model.getCascadeCardAt(8, 7));
    assertEquals(new Card(CardValue.SIX, CardSuit.SPADE), this.model.getCascadeCardAt(8, 6));
    assertEquals(new Card(CardValue.SEVEN, CardSuit.HEART), this.model.getCascadeCardAt(8, 5));
    assertEquals(new Card(CardValue.EIGHT, CardSuit.SPADE), this.model.getCascadeCardAt(8, 4));
    assertEquals(new Card(CardValue.NINE, CardSuit.HEART), this.model.getCascadeCardAt(8, 3));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testValidFiveCardMultiMoveNotEnoughEmptyCascadeAndOpenEmptyPiles() {
    this.model.startGame(this.deck, 20, 3, false);
    this.model.move(PileType.CASCADE, 10, 2, PileType.OPEN, 0);
    this.model.move(PileType.CASCADE, 6, 2, PileType.CASCADE, 14);
    this.model.move(PileType.CASCADE, 12, 1, PileType.CASCADE, 14);
    this.model.move(PileType.CASCADE, 4, 2, PileType.CASCADE, 14);
    this.model.move(PileType.CASCADE, 10, 1, PileType.CASCADE, 14);
    this.model.move(PileType.CASCADE, 10, 0, PileType.OPEN, 1);
    this.model.move(PileType.CASCADE, 14, 1, PileType.CASCADE, 8);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidTwoCardMoveWrongCardOrderEnoughOpenPiles() {
    this.model.startGame(this.deck, 15, 2, false);
    this.model.move(PileType.CASCADE, 0, 2, PileType.CASCADE, 14);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidTwoCardMoveWrongCardOrderAndWrongBaseCardEnoughCascadePiles() {
    this.model.startGame(this.deck, 15, 3, false);
    this.model.move(PileType.CASCADE, 10, 2, PileType.OPEN, 0);
    this.model.move(PileType.CASCADE, 10, 1, PileType.OPEN, 1);
    this.model.move(PileType.CASCADE, 10, 0, PileType.OPEN, 2);
    this.model.move(PileType.CASCADE, 8, 1, PileType.CASCADE, 3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidFiveCardMultiMoveWrongBaseCardEnoughEmptyCascadeAndOpenPiles() {
    this.model.startGame(this.deck, 20, 4, false);
    this.model.move(PileType.CASCADE, 10, 2, PileType.OPEN, 0);
    this.model.move(PileType.CASCADE, 6, 2, PileType.CASCADE, 14);
    this.model.move(PileType.CASCADE, 12, 1, PileType.CASCADE, 14);
    this.model.move(PileType.CASCADE, 4, 2, PileType.CASCADE, 14);
    this.model.move(PileType.CASCADE, 10, 1, PileType.CASCADE, 14);
    this.model.move(PileType.CASCADE, 10, 0, PileType.OPEN, 1);
    this.model.move(PileType.CASCADE, 14, 1, PileType.CASCADE, 7);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidThreeCardMoveWrongCardOrderAndWrongBaseCardNotEnoughEmptyOpenPiles() {
    this.model.startGame(this.deck, 10, 1, false);
    this.model.move(PileType.CASCADE, 0, 4, PileType.CASCADE, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidFiveCardMoveWrongBaseCardNotEnoughEmptyCascadeAndOpenPiles() {
    this.model.startGame(this.deck, 20, 3, false);
    this.model.move(PileType.CASCADE, 0, 2, PileType.OPEN, 0);
    this.model.move(PileType.CASCADE, 0, 1, PileType.OPEN, 1);
    this.model.move(PileType.CASCADE, 0, 0, PileType.OPEN, 2);
    this.model.move(PileType.CASCADE, 6, 2, PileType.CASCADE, 14);
    this.model.move(PileType.CASCADE, 12, 1, PileType.CASCADE, 14);
    this.model.move(PileType.CASCADE, 4, 2, PileType.CASCADE, 14);
    this.model.move(PileType.CASCADE, 14, 0, PileType.CASCADE, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidFiveCardMoveWrongBaseCardEnoughEmptyPiles() {
    this.model.startGame(this.deck, 20, 5, false);
    this.model.move(PileType.CASCADE, 0, 2, PileType.OPEN, 0);
    this.model.move(PileType.CASCADE, 0, 1, PileType.OPEN, 1);
    this.model.move(PileType.CASCADE, 0, 0, PileType.OPEN, 2);
    this.model.move(PileType.CASCADE, 6, 2, PileType.CASCADE, 14);
    this.model.move(PileType.CASCADE, 12, 1, PileType.CASCADE, 14);
    this.model.move(PileType.CASCADE, 4, 2, PileType.CASCADE, 14);
    this.model.move(PileType.CASCADE, 14, 0, PileType.CASCADE, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidThreeCardMoveWrongCardOrderNotEnoughEmptyPiles() {
    this.model.startGame(this.deck, 15, 3, false);
    this.model.move(PileType.CASCADE, 14, 2, PileType.OPEN, 0);
    this.model.move(PileType.CASCADE, 14, 1, PileType.OPEN, 1);
    this.model.move(PileType.CASCADE, 14, 0, PileType.OPEN, 2);
    this.model.move(PileType.CASCADE, 0, 1, PileType.CASCADE, 14);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMultiMoveToFoundationPile() {
    this.model.startGame(this.deck, 20, 2, false);
    this.model.move(PileType.CASCADE, 7, 1, PileType.FOUNDATION, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMultiMoveToOpenPile() {
    this.model.startGame(this.deck, 4, 2, false);
    this.model.move(PileType.CASCADE, 3, 10, PileType.OPEN, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMultiMoveFromFoundationPile() {
    this.model.startGame(this.deck, 52, 2, false);
    this.model.move(PileType.CASCADE, 0, 0, PileType.FOUNDATION, 0);
    this.model.move(PileType.CASCADE, 1, 0, PileType.FOUNDATION, 0);
    this.model.move(PileType.FOUNDATION, 0, 0, PileType.CASCADE, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testValidMoveFromCascadeToSameCascadePile() {
    this.model.startGame(this.deck, 15, 2, false);
    this.model.move(PileType.CASCADE, 0, 3, PileType.OPEN, 0);
    this.model.move(PileType.CASCADE, 12, 2, PileType.CASCADE, 0);
    this.model.move(PileType.CASCADE, 0, 1, PileType.CASCADE, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSimpleFreecellModelMultiMove() {
    this.model = new SimpleFreecellModel();
    this.model.startGame(this.deck, 20, 4, false);
    this.model.move(PileType.CASCADE, 1, 2, PileType.OPEN, 0);
    this.model.move(PileType.CASCADE, 1, 1, PileType.OPEN, 1);
    this.model.move(PileType.CASCADE, 1, 0, PileType.OPEN, 2);
    this.model.move(PileType.CASCADE, 6, 2, PileType.CASCADE, 14);
    this.model.move(PileType.CASCADE, 12, 1, PileType.CASCADE, 14);
    this.model.move(PileType.CASCADE, 4, 2, PileType.CASCADE, 14);
    this.model.move(PileType.CASCADE, 14, 1, PileType.CASCADE, 8);
  }
}
