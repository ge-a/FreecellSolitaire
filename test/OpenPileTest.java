import cs3500.freecell.model.hw02.CardValue;
import cs3500.freecell.model.hw02.ICard;
import cs3500.freecell.model.hw02.IPile;
import cs3500.freecell.model.hw02.Card;
import cs3500.freecell.model.hw02.OpenPile;
import cs3500.freecell.model.hw02.CardSuit;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

/**
 * Tests for the OpenPile class.
 * */
public class OpenPileTest {

  ArrayList<ICard> mtOpenList;
  ArrayList<ICard> openList1;
  ArrayList<ICard> openList2;
  ArrayList<ICard> openList3;
  ArrayList<ICard> openList4;
  IPile<ICard> mtPile;
  IPile<ICard> oPile;
  IPile<ICard> oPile2;
  IPile<ICard> oPile3;

  @Before
  public void init() {
    this.mtOpenList = new ArrayList<>();
    this.openList1 = new ArrayList<>(Arrays.asList(new Card(CardValue.ACE, CardSuit.SPADE)));
    this.openList2 = new ArrayList<>(Arrays.asList(new Card(CardValue.ACE, CardSuit.HEART)));
    this.openList3 = new ArrayList<>(Arrays.asList(new Card(CardValue.TWO, CardSuit.SPADE)));
    this.openList4 = new ArrayList<>(Arrays.asList(new Card(CardValue.THREE, CardSuit.HEART)));
    this.mtPile = new OpenPile(new ArrayList<>(Arrays.asList(this.mtOpenList)));
    this.oPile = new OpenPile(new ArrayList<>(Arrays.asList(this.mtOpenList, this.openList1)));
    this.oPile2 =
        new OpenPile(
            new ArrayList<>(Arrays.asList(this.openList2, this.openList1, this.openList4)));
    this.oPile3 =
        new OpenPile(
            new ArrayList<>(
                Arrays.asList(this.mtOpenList, this.mtOpenList, this.openList3, this.openList1)));
  }

  @Test
  public void testPossibleMoveToFullPile() {
    assertFalse(
        oPile3.possibleMove(
            3, new Card(CardValue.FOUR, CardSuit.HEART), new Card(CardValue.ACE, CardSuit.SPADE)));
  }

  @Test
  public void testPossibleMoveToEmptyPile() {
    assertTrue(
        mtPile.possibleMove(
            0, new Card(CardValue.FOUR, CardSuit.HEART), new Card(CardValue.ACE, CardSuit.SPADE)));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetCardAtIndexEmptyPile() {
    this.mtPile.getCardAtIndex(0, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetCardAtIndexNegativePileIndex() {
    this.oPile3.getCardAtIndex(-1, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetCardAtIndexTooLargePileIndex() {
    this.oPile3.getCardAtIndex(5, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetCardAtIndexNegativeCardIndex() {
    this.oPile2.getCardAtIndex(0, -1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetCardAtIndexTooLargeCardIndex() {
    this.oPile2.getCardAtIndex(2, 5);
  }

  @Test
  public void testGetCardAtIndexSecondPile() {
    assertEquals(new Card(CardValue.ACE, CardSuit.SPADE), this.oPile.getCardAtIndex(1, 0));
  }

  @Test
  public void testGetCardAtIndexFirstPile() {
    assertEquals(new Card(CardValue.ACE, CardSuit.HEART), this.oPile2.getCardAtIndex(0, 0));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetLastCardEmptyPile() {
    this.mtPile.getLastCard(0);
  }

  @Test
  public void testGetLastCardPileFour() {
    assertEquals(new Card(CardValue.ACE, CardSuit.SPADE), this.oPile3.getLastCard(3));
  }

  @Test
  public void testGetLastCardPileThree() {
    assertEquals(new Card(CardValue.THREE, CardSuit.HEART), this.oPile2.getLastCard(2));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetLastCardNegativePileIndex() {
    this.oPile2.getLastCard(-1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetLastCardTooLargePileIndex() {
    this.oPile3.getLastCard(5);
  }

  @Test
  public void testActuallyEmptyPile() {
    assertTrue(mtPile.emptyPile(0, new Card(CardValue.SEVEN, CardSuit.DIAMOND)));
  }

  @Test
  public void testFullPile() {
    assertFalse(oPile.emptyPile(1, new Card(CardValue.NINE, CardSuit.HEART)));
  }
}
