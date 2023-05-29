import cs3500.freecell.model.FreecellModel;
import cs3500.freecell.model.PileType;
import cs3500.freecell.model.hw02.CardValue;
import cs3500.freecell.model.hw02.ICard;
import cs3500.freecell.model.hw02.Card;
import cs3500.freecell.model.hw02.CardSuit;
import cs3500.freecell.model.hw02.SimpleFreecellModel;
import cs3500.freecell.model.hw04.SimpleMultiMoveFreecellModel;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

/**
 * Abstract test class for tests pertaining to the FreecellModel interface.
 */
public abstract class AbstractFreecellModelTest {

  FreecellModel<ICard> model;
  List<ICard> deck;

  @Before
  public void init() {
    this.model = constructModel();
    this.deck = this.model.getDeck();
  }

  /**
   * Makes a model of the FreecellModel interface.
   * @return a model that extends the FreecellModel interface.
   */
  protected abstract FreecellModel<ICard> constructModel();

  /**
   * Tests for the SimpleFreecellModel class.
   */
  public static class SimpleFreecellModelTest extends AbstractFreecellModelTest {

    @Override
    protected FreecellModel<ICard> constructModel() {
      return new SimpleFreecellModel();
    }
  }

  /**
   * Tests for the SimpleMultiMoveFreecellModel class that do not include tests for the
   * new functionality, multiple card moves between cascade piles. To find tests that
   * pertain to the new functionality of moving multiple cards between cascade piles
   * go to the MultiMoveSimpleMultiMoveFreecellModelTest class.
   */
  public static class SimpleMultiMoveFreecellModelTest extends AbstractFreecellModelTest {

    @Override
    protected FreecellModel<ICard> constructModel() {
      return new SimpleMultiMoveFreecellModel();
    }
  }


  @Test
  public void testDeckSize() {
    assertEquals(this.model.getDeck().size(), 52);
  }

  @Test
  public void testDeckCards() {
    assertEquals(
            this.deck.toString(),
            "[A♣, 2♣, 3♣, 4♣, 5♣, 6♣, 7♣, 8♣, 9♣, 10♣, J♣, Q♣, K♣, A♦, 2♦, 3♦, "
                    + "4♦, 5♦, 6♦, 7♦, 8♦, 9♦, 10♦, J♦, Q♦, K♦, A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, "
                    + "9♥, 10♥, J♥, Q♥, K♥, A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠, K♠]");
  }

  @Test(expected = IllegalArgumentException.class)
  public void startGameNon52Cards() {
    this.deck.remove(0);
    this.model.startGame(this.deck, 4, 2, false);
  }

  @Test(expected = IllegalArgumentException.class)
  public void startGameDuplicateCards() {
    this.deck.remove(0);
    this.deck.add(0, new Card(CardValue.TWO, CardSuit.CLUB));
    this.model.startGame(this.deck, 4, 2, true);
  }

  @Test
  public void testStartGameResetPileSizes() {
    this.model.startGame(this.deck, 8, 1, false);
    this.model.startGame(this.deck, 4, 2, false);
    assertEquals(4, this.model.getNumCascadePiles());
    assertEquals(2, this.model.getNumOpenPiles());
    assertEquals(13, this.model.getNumCardsInCascadePile(0));
  }

  @Test(expected = IllegalArgumentException.class)
  public void startGameInvalidNumCascadePiles() {
    this.model.startGame(this.deck, 3, 1, false);
  }

  @Test(expected = IllegalArgumentException.class)
  public void startGameInvalidNumOpenPiles() {
    this.model.startGame(this.deck, 7, 0, false);
  }

  @Test
  public void testStartGameCreatesPiles() {
    this.model.startGame(this.deck, 6, 3, true);
    assertEquals(6, this.model.getNumCascadePiles());
    assertEquals(3, this.model.getNumOpenPiles());
  }

  @Test
  public void testStartGameDealCards() {
    this.model.startGame(this.deck, 6, 3, false);
    assertEquals(9, this.model.getNumCardsInCascadePile(0));
    assertEquals(9, this.model.getNumCardsInCascadePile(1));
    assertEquals(9, this.model.getNumCardsInCascadePile(2));
    assertEquals(9, this.model.getNumCardsInCascadePile(3));
    assertEquals(8, this.model.getNumCardsInCascadePile(4));
    assertEquals(8, this.model.getNumCardsInCascadePile(5));
  }

  @Test
  public void testStartGameShuffleDealCards() {
    this.model.startGame(this.deck, 5, 3, true);
    assertEquals(11, this.model.getNumCardsInCascadePile(0));
    assertEquals(11, this.model.getNumCardsInCascadePile(1));
    assertEquals(10, this.model.getNumCardsInCascadePile(2));
    assertEquals(10, this.model.getNumCardsInCascadePile(3));
    assertEquals(10, this.model.getNumCardsInCascadePile(4));
  }

  @Test
  public void testStartGameShuffleDeck() {
    this.model.startGame(this.deck,4, 2, true);
    assertNotEquals(this.deck, this.model.getDeck());
  }

  @Test(expected = IllegalStateException.class)
  public void testMoveGameNotStarted() {
    this.model.move(PileType.CASCADE, 0, 5, PileType.FOUNDATION, 0);
  }

  @Test
  public void testMoveAceIntoEmptyFoundation() {
    this.model.startGame(this.deck, 52, 2, false);
    this.model.move(PileType.CASCADE, 0, 0, PileType.FOUNDATION, 0);
    assertEquals(1, this.model.getNumCardsInFoundationPile(0));
    assertEquals(0, this.model.getNumCardsInCascadePile(0));
    assertEquals(new Card(CardValue.ACE, CardSuit.CLUB), this.model.getFoundationCardAt(0, 0));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoveNonAceToEmptyFoundation() {
    this.model.startGame(this.deck, 4, 2, false);
    this.model.move(PileType.CASCADE, 0, 12, PileType.FOUNDATION, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoveCardFromFoundation() {
    this.model.startGame(this.deck, 52, 2, false);
    this.model.move(PileType.CASCADE, 0, 0, PileType.FOUNDATION, 0);
    this.model.move(PileType.FOUNDATION, 0, 0, PileType.OPEN, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoveInvalidValueToNonEmptyFoundation() {
    this.model.startGame(this.deck, 52, 2, false);
    for (int i = 0; i < 6; i++) {
      this.model.move(PileType.CASCADE, i, 0, PileType.FOUNDATION, 0);
    }
    this.model.move(PileType.CASCADE, 8, 0, PileType.FOUNDATION, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoveInvalidSuiteToNonEmptyFoundation() {
    this.model.startGame(this.deck, 52, 2, false);
    this.model.move(PileType.CASCADE, 0, 0, PileType.FOUNDATION, 0);
    this.model.move(PileType.CASCADE, 14, 0, PileType.FOUNDATION, 0);
  }

  @Test
  public void testMoveFromOpenToFoundation() {
    this.model.startGame(this.deck, 52, 1, false);
    for (int i = 0; i < 12; i++) {
      this.model.move(PileType.CASCADE, i, 0, PileType.FOUNDATION, 0);
    }
    this.model.move(PileType.CASCADE, 12, 0, PileType.OPEN, 0);
    assertEquals(12, this.model.getNumCardsInFoundationPile(0));
    this.model.move(PileType.OPEN, 0, 0, PileType.FOUNDATION, 0);
    assertEquals(13, this.model.getNumCardsInFoundationPile(0));
    assertEquals(0, this.model.getNumCardsInCascadePile(12));
    assertEquals(new Card(CardValue.KING, CardSuit.CLUB), this.model.getFoundationCardAt(0, 12));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoveToFullFoundationPile() {
    this.model.startGame(this.deck, 52, 1, false);
    for (int i = 0; i < 13; i++) {
      this.model.move(PileType.CASCADE, i, 0, PileType.FOUNDATION, 0);
    }
    this.model.move(PileType.CASCADE, 17, 0, PileType.FOUNDATION, 0);
  }

  @Test
  public void testMoveFromCascadeToEmptyOpen() {
    this.model.startGame(this.deck, 5, 2, false);
    this.model.move(PileType.CASCADE, 1, 10, PileType.OPEN, 0);
    assertEquals(1, this.model.getNumCardsInOpenPile(0));
    assertEquals(10, this.model.getNumCardsInCascadePile(1));
    assertEquals(new Card(CardValue.KING, CardSuit.SPADE), this.model.getOpenCardAt(0));
  }

  @Test
  public void testMoveFromOpenToCascade() {
    this.model.startGame(this.deck, 7, 2, false);
    this.model.move(PileType.CASCADE, 3, 6, PileType.OPEN, 0);
    this.model.move(PileType.CASCADE, 1, 7, PileType.OPEN, 1);
    assertEquals(6, this.model.getNumCardsInCascadePile(3));
    this.model.move(PileType.OPEN, 1, 0, PileType.CASCADE, 3);
    assertEquals(0, this.model.getNumCardsInOpenPile(1));
    assertEquals(7, this.model.getNumCardsInCascadePile(3));
    assertEquals(new Card(CardValue.QUEEN, CardSuit.SPADE), this.model.getCascadeCardAt(3, 6));
  }

  @Test
  public void testValidMoveFromCascadeToCascade() {
    this.model.startGame(this.deck, 52, 2, false);
    this.model.move(PileType.CASCADE, 1, 0, PileType.CASCADE, 15);
    assertEquals(0, this.model.getNumCardsInCascadePile(1));
    assertEquals(2, this.model.getNumCardsInCascadePile(15));
    assertEquals(new Card(CardValue.TWO, CardSuit.CLUB), this.model.getCascadeCardAt(15, 1));
  }

  @Test
  public void testValidMoveFromCascadeToEmptyCascade() {
    this.model.startGame(this.deck, 52, 2, false);
    this.model.move(PileType.CASCADE, 1, 0, PileType.CASCADE, 15);
    assertEquals(0, this.model.getNumCardsInCascadePile(1));
    this.model.move(PileType.CASCADE, 2, 0, PileType.CASCADE, 1);
    assertEquals(1, this.model.getNumCardsInCascadePile(1));
    assertEquals(0, this.model.getNumCardsInCascadePile(2));
    assertEquals(new Card(CardValue.THREE, CardSuit.CLUB), this.model.getCascadeCardAt(1, 0));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testWrongSuitMoveFromCascadeToCascade() {
    this.model.startGame(this.deck, 7, 2, false);
    this.model.move(PileType.CASCADE, 1, 7, PileType.CASCADE, 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSameValueMoveFromCascadeToCascade() {
    this.model.startGame(this.deck, 14, 2, false);
    this.model.move(PileType.CASCADE, 9, 3, PileType.CASCADE, 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testOneHigherValueMoveFromCascadeToCascade() {
    this.model.startGame(this.deck, 15, 2, false);
    this.model.move(PileType.CASCADE, 8, 2, PileType.CASCADE, 5);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoveFromEmptyOpenPile() {
    this.model.startGame(this.deck, 4, 2, true);
    this.model.move(PileType.OPEN, 0, 0, PileType.FOUNDATION, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoveFromEmptyCascadePile() {
    this.model.startGame(this.deck, 52, 2, false);
    this.model.move(PileType.CASCADE, 0, 0, PileType.FOUNDATION, 0);
    this.model.move(PileType.CASCADE, 0, 0, PileType.OPEN, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoveToFullOpenPile() {
    this.model.startGame(this.deck, 4, 2, false);
    this.model.move(PileType.CASCADE, 0, 12, PileType.OPEN, 0);
    this.model.move(PileType.CASCADE, 0, 11, PileType.OPEN, 0);
  }

  @Test
  public void testValidMoveFromOpenToOpen() {
    this.model.startGame(this.deck, 4, 2, false);
    this.model.move(PileType.CASCADE, 0, 12, PileType.OPEN, 0);
    assertEquals(1, model.getNumCardsInOpenPile(0));
    this.model.move(PileType.OPEN, 0, 0, PileType.OPEN, 1);
    assertEquals(0, model.getNumCardsInOpenPile(0));
    assertEquals(1, model.getNumCardsInOpenPile(1));
    assertEquals(new Card(CardValue.TEN, CardSuit.SPADE), this.model.getOpenCardAt(1));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoveNegativeCardIndex() {
    this.model.startGame(this.deck, 4, 2, false);
    this.model.move(PileType.CASCADE, 0, -1, PileType.OPEN, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoveNegativeSourcePileIndex() {
    this.model.startGame(this.deck, 4, 2, false);
    this.model.move(PileType.CASCADE, -1, 13, PileType.OPEN, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoveNegativeDestPileIndex() {
    this.model.startGame(this.deck, 4, 2, false);
    this.model.move(PileType.CASCADE, 0, 13, PileType.OPEN, -1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoveTooLargeCardIndex() {
    this.model.startGame(this.deck, 4, 2, false);
    this.model.move(PileType.CASCADE, 0, 13, PileType.OPEN, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoveTooLargeSourcePileIndex() {
    this.model.startGame(this.deck, 4, 2, false);
    this.model.move(PileType.CASCADE, 4, 13, PileType.OPEN, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoveTooLargeDestPileIndex() {
    this.model.startGame(this.deck, 4, 2, false);
    this.model.move(PileType.CASCADE, 0, 13, PileType.OPEN, 2);
  }

  @Test
  public void testGameActuallyOver() {
    this.model.startGame(this.deck, 52, 1, false);
    int foundIndex = -1;
    for (int i = 0; i < 52; i++) {
      if (i % 13 == 0) {
        foundIndex++;
      }
      this.model.move(PileType.CASCADE, i, 0, PileType.FOUNDATION, foundIndex);
    }
    assertTrue(this.model.isGameOver());
  }

  @Test
  public void testGameHalfOver() {
    this.model.startGame(this.deck, 52, 1, false);
    int foundIndex = -1;
    for (int i = 0; i < 26; i++) {
      if (i % 13 == 0) {
        foundIndex++;
      }
      this.model.move(PileType.CASCADE, i, 0, PileType.FOUNDATION, foundIndex);
    }
    assertFalse(this.model.isGameOver());
  }

  @Test
  public void testGameNotOver() {
    this.model.startGame(this.deck, 4, 1, false);
    assertFalse(this.model.isGameOver());
  }

  @Test
  public void testGameOverGameNotStarted() {
    assertFalse(this.model.isGameOver());
  }

  @Test(expected = IllegalStateException.class)
  public void testGetNumCardsInFoundationPileGameNotStarted() {
    this.model.getNumCardsInFoundationPile(3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetNumCardsInFoundationPileIllegalLargeIndex() {
    this.model.startGame(this.deck, 4, 1, false);
    this.model.getNumCardsInFoundationPile(4);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetNumCardsInFoundationPileIllegalSmallIndex() {
    this.model.startGame(this.deck, 4, 1, false);
    this.model.getNumCardsInFoundationPile(-1);
  }

  @Test
  public void testGetNumCardsInEmptyFoundationPile() {
    this.model.startGame(this.deck, 4, 1, false);
    assertEquals(0, this.model.getNumCardsInFoundationPile(1));
  }

  @Test
  public void testGetNumCardsInNonEmptyFoundationPile() {
    this.model.startGame(this.deck, 52, 3, false);
    this.model.move(PileType.CASCADE, 0, 0, PileType.FOUNDATION, 0);
    this.model.move(PileType.CASCADE, 1, 0, PileType.FOUNDATION, 0);
    assertEquals(2, this.model.getNumCardsInFoundationPile(0));
  }

  @Test
  public void testGetNumCascadePilesGameNotStarted() {
    assertEquals(-1, this.model.getNumCascadePiles());
  }

  @Test
  public void testGetNumSevenCascadePiles() {
    this.model.startGame(this.deck, 7, 2, true);
    assertEquals(7, this.model.getNumCascadePiles());
  }

  @Test
  public void testGetNumFourCascadePiles() {
    this.model.startGame(this.deck, 4, 2, true);
    assertEquals(4, this.model.getNumCascadePiles());
  }

  @Test(expected = IllegalStateException.class)
  public void testGetNumCardsInCascadePileGameNotStarted() {
    this.model.getNumCardsInCascadePile(3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetNumCardsInCascadePileIllegalSmallIndex() {
    this.model.startGame(this.deck, 10, 3, false);
    this.model.getNumCardsInCascadePile(-2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetNumCardsInCascadePileIllegalLargeIndex() {
    this.model.startGame(this.deck, 5, 3, false);
    this.model.getNumCardsInCascadePile(5);
  }

  @Test
  public void testGetNumCardsInFirstOfFourCascadePiles() {
    this.model.startGame(this.deck, 4, 2, true);
    assertEquals(13, this.model.getNumCardsInCascadePile(0));
  }

  @Test
  public void testGetNumCardsInSecondOfFiveCascadePiles() {
    this.model.startGame(this.deck, 5, 2, false);
    assertEquals(11, this.model.getNumCardsInCascadePile(1));
  }

  @Test
  public void testGetNumCardsInSeventhOfSevenCascadePiles() {
    this.model.startGame(this.deck, 7, 1, true);
    assertEquals(7, this.model.getNumCardsInCascadePile(6));
  }

  @Test(expected = IllegalStateException.class)
  public void testGetNumCardsInOpenPileGameNotStarted() {
    this.model.getNumCardsInOpenPile(3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetNumCardsInOpenPileInvalidLargeIndex() {
    this.model.startGame(this.deck, 4, 2, false);
    this.model.getNumCardsInOpenPile(2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetNumCardsInOpenPileInvalidSmallIndex() {
    this.model.startGame(this.deck, 4, 2, false);
    this.model.getNumCardsInOpenPile(-1);
  }

  @Test
  public void testGetNumCardsInEmptyOpenPile() {
    this.model.startGame(this.deck, 7, 3, false);
    assertEquals(0, this.model.getNumCardsInOpenPile(2));
  }

  @Test
  public void testGetNumCardsInFullOpenPile() {
    this.model.startGame(this.deck, 4, 2, true);
    this.model.move(PileType.CASCADE, 0, 12, PileType.OPEN, 1);
    assertEquals(1, this.model.getNumCardsInOpenPile(1));
  }

  @Test
  public void testGetNumOpenPilesGameNotStarted() {
    assertEquals(-1, this.model.getNumOpenPiles());
  }

  @Test
  public void testGetNumTwoOpenPiles() {
    this.model.startGame(this.deck, 4, 2, false);
    assertEquals(2, this.model.getNumOpenPiles());
  }

  @Test
  public void testGetNumFiveOpenPiles() {
    this.model.startGame(this.deck, 4, 5, false);
    assertEquals(5, this.model.getNumOpenPiles());
  }

  @Test(expected = IllegalStateException.class)
  public void testGetFoundationCardAtGameNotStarted() {
    this.model.getFoundationCardAt(2, 4);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetFoundationCardAtInvalidSmallPileIndex() {
    this.model.startGame(this.deck, 6, 2, false);
    this.model.getFoundationCardAt(-1, 3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetFoundationCardAtInvalidLargePileIndex() {
    this.model.startGame(this.deck, 4, 2, false);
    this.model.getFoundationCardAt(4, 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetFoundationCardAtInvalidSmallCardIndex() {
    this.model.startGame(this.deck, 5, 2, false);
    this.model.getFoundationCardAt(3, -1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetFoundationCardAtInvalidLargeCardIndex() {
    this.model.startGame(this.deck, 7, 3, false);
    this.model.getFoundationCardAt(3, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetFoundationCardAtEmptyPile() {
    this.model.startGame(this.deck, 7, 3, false);
    this.model.getFoundationCardAt(3, 0);
  }

  @Test
  public void testGetFoundationCardAtValidIndex() {
    this.model.startGame(this.deck, 52, 3, false);
    this.model.move(PileType.CASCADE, 0, 0, PileType.FOUNDATION, 0);
    assertEquals(new Card(CardValue.ACE, CardSuit.CLUB), this.model.getFoundationCardAt(0, 0));
  }

  @Test(expected = IllegalStateException.class)
  public void testGetCascadeCardAtGameNotStarted() {
    this.model.getCascadeCardAt(2, 3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetCascadeCardAtInvalidSmallPileIndex() {
    this.model.startGame(this.deck, 7, 2, false);
    this.model.getCascadeCardAt(-1, 4);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetCascadeCardAtInvalidLargePileIndex() {
    model.startGame(this.deck, 4, 3, true);
    model.getCascadeCardAt(4, 4);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetCascadeCardAtInvalidSmallCardIndex() {
    this.model.startGame(deck, 8, 2, false);
    this.model.getCascadeCardAt(4, -1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetCascadeCardAtInvalidLargeCardIndex() {
    this.model.startGame(this.deck, 4, 2, true);
    this.model.getCascadeCardAt(3, 13);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetCascadeCardAtEmptyPile() {
    this.model.startGame(this.deck, 52, 2, true);
    this.model.move(PileType.CASCADE, 0, 0, PileType.OPEN, 0);
    this.model.getCascadeCardAt(0, 0);
  }

  @Test
  public void testGetCascadeCardAtValidIndex() {
    this.model.startGame(this.deck, 4, 2, false);
    assertEquals(new Card(CardValue.TEN, CardSuit.SPADE), this.model.getCascadeCardAt(0, 12));
  }

  @Test(expected = IllegalStateException.class)
  public void testGetOpenCardAtGameNotStarted() {
    this.model.getOpenCardAt(0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetOpenCardAtInvalidSmallPileIndex() {
    model.startGame(deck, 4, 2, true);
    model.getOpenCardAt(-1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetOpenCardAtInvalidLargePileIndex() {
    this.model.startGame(this.deck, 4, 2, false);
    this.model.getOpenCardAt(2);
  }

  @Test
  public void testGetOpenCardAtEmptyPile() {
    this.model.startGame(this.deck, 4, 2, false);
    assertNull(this.model.getOpenCardAt(0));
  }

  @Test
  public void testGetOpenCardAtValidIndex() {
    this.model.startGame(this.deck, 4, 2, false);
    this.model.move(PileType.CASCADE, 0, 12, PileType.OPEN, 0);
    assertEquals(new Card(CardValue.TEN, CardSuit.SPADE), this.model.getOpenCardAt(0));
  }
}
