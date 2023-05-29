import cs3500.freecell.model.hw02.CardValue;
import cs3500.freecell.model.hw02.ICard;
import cs3500.freecell.model.hw02.IPile;
import cs3500.freecell.model.hw02.Card;
import cs3500.freecell.model.hw02.CardSuit;
import cs3500.freecell.model.hw02.FoundationPile;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

/**
 * Tests for the FoundationPile class.
 */
public class FoundationPileTest {

  ArrayList<ICard> mtFoundPile;
  ArrayList<ICard> spadeList;
  ArrayList<ICard> heartList;
  ArrayList<ICard> clubList;
  ArrayList<ICard> diamondList;
  IPile<ICard> mtPile;
  IPile<ICard> fPile;
  IPile<ICard> fPile2;
  IPile<ICard> fPile3;

  @Before
  public void init() {
    this.mtFoundPile = new ArrayList<>();
    this.spadeList =
        new ArrayList<>(
            Arrays.asList(
                new Card(CardValue.ACE, CardSuit.SPADE),
                new Card(CardValue.TWO, CardSuit.SPADE),
                new Card(CardValue.THREE, CardSuit.SPADE)));
    this.heartList = new ArrayList<>(Arrays.asList(new Card(CardValue.ACE, CardSuit.HEART)));
    this.clubList =
        new ArrayList<>(
            Arrays.asList(
                new Card(CardValue.ACE, CardSuit.CLUB), new Card(CardValue.TWO, CardSuit.CLUB)));
    this.diamondList =
        new ArrayList<>(
            Arrays.asList(
                new Card(CardValue.ACE, CardSuit.DIAMOND),
                new Card(CardValue.TWO, CardSuit.DIAMOND),
                new Card(CardValue.THREE, CardSuit.DIAMOND),
                new Card(CardValue.FOUR, CardSuit.DIAMOND),
                new Card(CardValue.FIVE, CardSuit.DIAMOND),
                new Card(CardValue.SIX, CardSuit.DIAMOND),
                new Card(CardValue.SEVEN, CardSuit.DIAMOND)));
    this.mtPile =
        new FoundationPile(
            new ArrayList<>(
                Arrays.asList(
                    this.mtFoundPile, this.mtFoundPile, this.mtFoundPile, this.mtFoundPile)));
    this.fPile =
        new FoundationPile(
            new ArrayList<>(
                Arrays.asList(this.mtFoundPile, this.clubList, this.mtFoundPile, this.heartList)));
    this.fPile2 =
        new FoundationPile(
            new ArrayList<>(
                Arrays.asList(this.clubList, this.heartList, this.diamondList, this.mtFoundPile)));
    this.fPile3 =
        new FoundationPile(
            new ArrayList<>(
                Arrays.asList(this.heartList, this.diamondList, this.clubList, this.spadeList)));
  }

  @Test
  public void testPossibleMoveRightSuitWrongValue() {
    assertFalse(
        fPile3.possibleMove(
            1,
            new Card(CardValue.NINE, CardSuit.DIAMOND),
            new Card(CardValue.SEVEN, CardSuit.DIAMOND)));
  }

  @Test
  public void testPossibleMoveWrongSuitRightValue() {
    assertFalse(
        this.fPile2.possibleMove(
            0,
            new Card(CardValue.THREE, CardSuit.DIAMOND),
            new Card(CardValue.TWO, CardSuit.CLUB)));
  }

  @Test
  public void testPossibleMoveRightSuitRightValueHeart() {
    assertTrue(
        this.fPile.possibleMove(
            3, new Card(CardValue.TWO, CardSuit.HEART),
                new Card(CardValue.ACE, CardSuit.HEART)));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetCardAtIndexEmptyPile() {
    this.mtPile.getCardAtIndex(0, 5);
  }

  @Test
  public void testGetCardAtIndexFirstCard() {
    assertEquals(new Card(CardValue.ACE, CardSuit.DIAMOND),
            this.fPile2.getCardAtIndex(2, 0));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetCardAtNegativeCardIndex() {
    this.fPile3.getCardAtIndex(0, -1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetCardAtTooLargeCardIndex() {
    this.fPile.getCardAtIndex(0, 10);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetCardAtNegativePileIndex() {
    this.fPile3.getCardAtIndex(-1, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetCardAtTooLargePileIndex() {
    this.fPile.getCardAtIndex(5, 0);
  }

  @Test
  public void testGetCardAtIndexSecondCard() {
    assertEquals(new Card(CardValue.TWO, CardSuit.CLUB),
            this.fPile3.getCardAtIndex(2, 1));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetLastCardEmptyPile() {
    this.mtPile.getLastCard(2);
  }

  @Test
  public void testGetLastCardDiamondPile() {
    assertEquals(new Card(CardValue.SEVEN, CardSuit.DIAMOND), this.fPile2.getLastCard(2));
  }

  @Test
  public void testGetLastCardClubPile() {
    assertEquals(new Card(CardValue.TWO, CardSuit.CLUB), this.fPile.getLastCard(1));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetLastCardNegativePileIndex() {
    this.fPile2.getLastCard(-1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetLastCardTooLargePileIndex() {
    this.fPile3.getLastCard(12);
  }

  @Test
  public void testAddAceToEmptyPile() {
    assertTrue(mtPile.emptyPile(3, new Card(CardValue.ACE, CardSuit.SPADE)));
  }

  @Test
  public void testAddNonAceToEmptyPile() {
    assertFalse(mtPile.emptyPile(2, new Card(CardValue.TWO, CardSuit.HEART)));
  }

  @Test
  public void testNotEmptyPile() {
    assertFalse(fPile3.emptyPile(3, new Card(CardValue.FOUR, CardSuit.SPADE)));
  }
}
