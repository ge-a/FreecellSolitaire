import cs3500.freecell.model.FreecellModel;
import cs3500.freecell.model.PileType;
import cs3500.freecell.model.hw02.ICard;
import cs3500.freecell.model.hw02.SimpleFreecellModel;
import cs3500.freecell.view.FreecellTextView;
import cs3500.freecell.view.FreecellView;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;

/** Tests for the FreecellTextView class. */
public class FreecellTextViewTest {

  FreecellModel<ICard> model;
  FreecellTextView view;
  FreecellView newView;
  List<ICard> deck;
  Appendable ap;

  @Before
  public void init() {
    this.model = new SimpleFreecellModel();
    this.ap = new StringBuilder();
    this.view = new FreecellTextView(this.model);
    this.newView = new FreecellTextView(this.model, this.ap);
    this.deck = this.model.getDeck();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testFreecellTextViewNullModelConstructorException() {
    new FreecellTextView(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testFreecellTextViewNullModelNewConstructorException() {
    new FreecellTextView(null, this.ap);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testFreecellTextViewNullAppendableNewConstructorException() {
    new FreecellTextView(this.model, null);
  }

  @Test
  public void testToStringFourCascadePilesUnShuffledGame() {
    this.model.startGame(this.deck, 4, 2, false);
    assertEquals(
        "F1:\n"
            + "F2:\n"
            + "F3:\n"
            + "F4:\n"
            + "O1:\n"
            + "O2:\n"
            + "C1: A♣, 5♣, 9♣, K♣, 4♦, 8♦, Q♦, 3♥, 7♥, J♥, 2♠, 6♠, 10♠\n"
            + "C2: 2♣, 6♣, 10♣, A♦, 5♦, 9♦, K♦, 4♥, 8♥, Q♥, 3♠, 7♠, J♠\n"
            + "C3: 3♣, 7♣, J♣, 2♦, 6♦, 10♦, A♥, 5♥, 9♥, K♥, 4♠, 8♠, Q♠\n"
            + "C4: 4♣, 8♣, Q♣, 3♦, 7♦, J♦, 2♥, 6♥, 10♥, A♠, 5♠, 9♠, K♠",
        this.view.toString());
  }

  @Test
  public void testToStringFullOpenPiles() {
    this.model.startGame(this.deck, 4, 2, false);
    this.model.move(PileType.CASCADE, 0, 12, PileType.OPEN, 0);
    this.model.move(PileType.CASCADE, 0, 11, PileType.OPEN, 1);
    assertEquals(
        "F1:\n"
            + "F2:\n"
            + "F3:\n"
            + "F4:\n"
            + "O1: 10♠\n"
            + "O2: 6♠\n"
            + "C1: A♣, 5♣, 9♣, K♣, 4♦, 8♦, Q♦, 3♥, 7♥, J♥, 2♠\n"
            + "C2: 2♣, 6♣, 10♣, A♦, 5♦, 9♦, K♦, 4♥, 8♥, Q♥, 3♠, 7♠, J♠\n"
            + "C3: 3♣, 7♣, J♣, 2♦, 6♦, 10♦, A♥, 5♥, 9♥, K♥, 4♠, 8♠, Q♠\n"
            + "C4: 4♣, 8♣, Q♣, 3♦, 7♦, J♦, 2♥, 6♥, 10♥, A♠, 5♠, 9♠, K♠",
        this.view.toString());
  }

  @Test
  public void testToStringMoveToFoundationPiles() {
    this.model.startGame(this.deck, 4, 5, false);
    this.model.move(PileType.CASCADE, 0, 12, PileType.OPEN, 0);
    this.model.move(PileType.CASCADE, 0, 11, PileType.OPEN, 1);
    this.model.move(PileType.CASCADE, 3, 12, PileType.OPEN, 2);
    this.model.move(PileType.CASCADE, 3, 11, PileType.OPEN, 3);
    this.model.move(PileType.CASCADE, 3, 10, PileType.OPEN, 4);
    this.model.move(PileType.CASCADE, 3, 9, PileType.FOUNDATION, 0);
    this.model.move(PileType.CASCADE, 0, 10, PileType.FOUNDATION, 0);
    assertEquals(
        "F1: A♠, 2♠\n"
            + "F2:\n"
            + "F3:\n"
            + "F4:\n"
            + "O1: 10♠\n"
            + "O2: 6♠\n"
            + "O3: K♠\n"
            + "O4: 9♠\n"
            + "O5: 5♠\n"
            + "C1: A♣, 5♣, 9♣, K♣, 4♦, 8♦, Q♦, 3♥, 7♥, J♥\n"
            + "C2: 2♣, 6♣, 10♣, A♦, 5♦, 9♦, K♦, 4♥, 8♥, Q♥, 3♠, 7♠, J♠\n"
            + "C3: 3♣, 7♣, J♣, 2♦, 6♦, 10♦, A♥, 5♥, 9♥, K♥, 4♠, 8♠, Q♠\n"
            + "C4: 4♣, 8♣, Q♣, 3♦, 7♦, J♦, 2♥, 6♥, 10♥",
        this.view.toString());
  }

  @Test
  public void testToStringFiveCascadePilesNoShuffle() {
    this.model.startGame(this.deck, 5, 2, false);
    assertEquals(
        "F1:\n"
            + "F2:\n"
            + "F3:\n"
            + "F4:\n"
            + "O1:\n"
            + "O2:\n"
            + "C1: A♣, 6♣, J♣, 3♦, 8♦, K♦, 5♥, 10♥, 2♠, 7♠, Q♠\n"
            + "C2: 2♣, 7♣, Q♣, 4♦, 9♦, A♥, 6♥, J♥, 3♠, 8♠, K♠\n"
            + "C3: 3♣, 8♣, K♣, 5♦, 10♦, 2♥, 7♥, Q♥, 4♠, 9♠\n"
            + "C4: 4♣, 9♣, A♦, 6♦, J♦, 3♥, 8♥, K♥, 5♠, 10♠\n"
            + "C5: 5♣, 10♣, 2♦, 7♦, Q♦, 4♥, 9♥, A♠, 6♠, J♠",
        this.view.toString());
  }

  @Test
  public void testToStringGameNotStarted() {
    assertEquals("", this.view.toString());
  }

  @Test
  public void testRenderBoardFourCascadeOneOpen() {
    this.model.startGame(this.deck, 4, 1, false);
    try {
      this.newView.renderBoard();
    } catch (IOException e) {
      // don't do anything here
    }
    assertEquals(
        "F1:\n"
            + "F2:\n"
            + "F3:\n"
            + "F4:\n"
            + "O1:\n"
            + "C1: A♣, 5♣, 9♣, K♣, 4♦, 8♦, Q♦, 3♥, 7♥, J♥, 2♠, 6♠, 10♠\n"
            + "C2: 2♣, 6♣, 10♣, A♦, 5♦, 9♦, K♦, 4♥, 8♥, Q♥, 3♠, 7♠, J♠\n"
            + "C3: 3♣, 7♣, J♣, 2♦, 6♦, 10♦, A♥, 5♥, 9♥, K♥, 4♠, 8♠, Q♠\n"
            + "C4: 4♣, 8♣, Q♣, 3♦, 7♦, J♦, 2♥, 6♥, 10♥, A♠, 5♠, 9♠, K♠\n",
        this.ap.toString());
  }

  @Test
  public void testRenderBoardFiveCascadeTwoOpen() {
    this.model.startGame(this.deck, 5, 2, false);
    try {
      this.newView.renderBoard();
    } catch (IOException e) {
      // don't do anything here
    }
    assertEquals(
        "F1:\n"
            + "F2:\n"
            + "F3:\n"
            + "F4:\n"
            + "O1:\n"
            + "O2:\n"
            + "C1: A♣, 6♣, J♣, 3♦, 8♦, K♦, 5♥, 10♥, 2♠, 7♠, Q♠\n"
            + "C2: 2♣, 7♣, Q♣, 4♦, 9♦, A♥, 6♥, J♥, 3♠, 8♠, K♠\n"
            + "C3: 3♣, 8♣, K♣, 5♦, 10♦, 2♥, 7♥, Q♥, 4♠, 9♠\n"
            + "C4: 4♣, 9♣, A♦, 6♦, J♦, 3♥, 8♥, K♥, 5♠, 10♠\n"
            + "C5: 5♣, 10♣, 2♦, 7♦, Q♦, 4♥, 9♥, A♠, 6♠, J♠\n",
        this.ap.toString());
  }

  @Test
  public void testRenderBoardGameNotStarted() {
    try {
      this.newView.renderBoard();
    } catch (IOException e) {
      // don't do anything here
    }
    assertEquals("\n", this.ap.toString());
  }

  @Test
  public void testRenderInvalidMoveMessage() {
    this.model.startGame(this.deck, 5, 2, false);
    try {
      this.newView.renderMessage("Invalid Message");
    } catch (IOException e) {
      // don't do anything here
    }
    assertEquals("Invalid Message", this.ap.toString());
  }

  @Test
  public void testRenderNullMessage() {
    this.model.startGame(this.deck, 5, 2, false);
    try {
      this.newView.renderMessage(null);
    } catch (IOException e) {
      // don't do anything here
    }
    assertEquals("null", this.ap.toString());
  }
}
