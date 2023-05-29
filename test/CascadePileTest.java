import cs3500.freecell.model.hw02.CardValue;
import cs3500.freecell.model.hw02.ICard;
import cs3500.freecell.model.hw02.IPile;
import cs3500.freecell.model.hw02.Card;
import cs3500.freecell.model.hw02.CardSuit;
import cs3500.freecell.model.hw02.CascadePile;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

/** Tests for the CascadePile class. */
public class CascadePileTest {

  ArrayList<ICard> mtCascList;
  ArrayList<ICard> cascList1;
  ArrayList<ICard> cascList2;
  ArrayList<ICard> cascList3;
  ArrayList<ICard> cascList4;
  IPile<ICard> mtPile;
  IPile<ICard> cPile;
  IPile<ICard> cPile2;
  IPile<ICard> cPile3;
  IPile<ICard> cPile4;

  @Before
  public void init() {
    this.mtCascList = new ArrayList<>();
    this.cascList1 =
        new ArrayList<>(
            Arrays.asList(
                new Card(CardValue.ACE, CardSuit.SPADE),
                new Card(CardValue.TEN, CardSuit.SPADE),
                new Card(CardValue.KING, CardSuit.DIAMOND)));
    this.cascList2 =
        new ArrayList<>(
            Arrays.asList(
                new Card(CardValue.ACE, CardSuit.HEART),
                new Card(CardValue.SEVEN, CardSuit.SPADE)));
    this.cascList3 =
        new ArrayList<>(
            Arrays.asList(
                new Card(CardValue.TWO, CardSuit.SPADE),
                new Card(CardValue.JACK, CardSuit.DIAMOND),
                new Card(CardValue.QUEEN, CardSuit.SPADE)));
    this.cascList4 =
        new ArrayList<>(
            Arrays.asList(
                new Card(CardValue.THREE, CardSuit.HEART),
                new Card(CardValue.QUEEN, CardSuit.HEART),
                new Card(CardValue.EIGHT, CardSuit.HEART)));
    this.mtPile = new CascadePile(new ArrayList<>(Arrays.asList(this.mtCascList)), false);
    this.cPile =
        new CascadePile(new ArrayList<>(Arrays.asList(this.mtCascList, this.cascList1)), false);
    this.cPile2 =
        new CascadePile(
            new ArrayList<>(
                Arrays.asList(this.cascList1, this.cascList2, this.cascList3, this.mtCascList)),
            false);
    this.cPile3 =
        new CascadePile(
            new ArrayList<>(
                Arrays.asList(this.cascList1, this.cascList2, this.cascList3, this.cascList4)),
            false);
    this.cPile4 =
        new CascadePile(
            new ArrayList<>(
                Arrays.asList(this.cascList1, this.cascList2, this.cascList3, this.cascList4)),
            true);
  }

  @Test
  public void testPossibleMoveQueenOfSpadesToKingOfDiamonds() {
    assertTrue(
        this.cPile3.possibleMove(
            0,
            new Card(CardValue.QUEEN, CardSuit.SPADE),
            new Card(CardValue.KING, CardSuit.DIAMOND)));
  }

  @Test
  public void testPossibleMoveSevenOfSpadesToEightOfHearts() {
    assertTrue(
        this.cPile3.possibleMove(
            3,
            new Card(CardValue.SEVEN, CardSuit.SPADE),
            new Card(CardValue.EIGHT, CardSuit.HEART)));
  }

  @Test
  public void testPossibleMoveSameSuitColor() {
    assertFalse(
        this.cPile2.possibleMove(
            0,
            new Card(CardValue.QUEEN, CardSuit.HEART),
            new Card(CardValue.KING, CardSuit.DIAMOND)));
  }

  @Test
  public void testPossibleMoveInvalidValues() {
    assertFalse(
        this.cPile3.possibleMove(
            1,
            new Card(CardValue.EIGHT, CardSuit.DIAMOND),
            new Card(CardValue.SEVEN, CardSuit.SPADE)));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetCardAtIndexEmptyPile() {
    this.mtPile.getCardAtIndex(0, 5);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetCardAtIndexFirstCard() {
    this.cPile3.getCardAtIndex(3, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetCardAtIndexNotLastCard() {
    this.cPile3.getCardAtIndex(3, 1);
  }

  @Test
  public void testGetCardAtIndexNotLastCardAllowed() {
    assertEquals(new Card(CardValue.QUEEN, CardSuit.HEART), this.cPile4.getCardAtIndex(3, 1));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetCardAtNegativePileIndexMultiMove() {
    this.cPile4.getCardAtIndex(-1, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetCardAtTooLargePileIndexMultiMove() {
    this.cPile4.getCardAtIndex(4, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetCardAtNegativeCardIndexMultiMove() {
    this.cPile4.getCardAtIndex(3, -1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetCardAtTooLargeCardIndexMultiMove() {
    this.cPile4.getCardAtIndex(3, 4);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetCardAtNegativePileIndex() {
    this.cPile2.getCardAtIndex(-1, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetCardAtTooLargePileIndex() {
    this.cPile2.getCardAtIndex(10, 1);
  }

  @Test
  public void testGetCardAtIndexLastCard() {
    assertEquals(new Card(CardValue.EIGHT, CardSuit.HEART), this.cPile3.getCardAtIndex(3, 2));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetLastCardEmptyPile() {
    this.mtPile.getLastCard(0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetLastCardNegativePileIndex() {
    this.cPile.getLastCard(-1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetLastCardTooLargePileIndex() {
    this.cPile3.getLastCard(12);
  }

  @Test
  public void testGetLastCardPileFour() {
    assertEquals(new Card(CardValue.EIGHT, CardSuit.HEART), this.cPile3.getLastCard(3));
  }

  @Test
  public void testGetLastCardPileTwo() {
    assertEquals(new Card(CardValue.SEVEN, CardSuit.SPADE), this.cPile2.getLastCard(1));
  }

  @Test
  public void testActuallyEmptyPile() {
    assertTrue(this.mtPile.emptyPile(0, new Card(CardValue.KING, CardSuit.DIAMOND)));
  }

  @Test
  public void testFullPile() {
    assertFalse(cPile.emptyPile(1, new Card(CardValue.KING, CardSuit.DIAMOND)));
  }

  @Test
  public void testActuallyEmptyPileMultipleLists() {
    assertTrue(cPile2.emptyPile(3, new Card(CardValue.KING, CardSuit.DIAMOND)));
  }
}
