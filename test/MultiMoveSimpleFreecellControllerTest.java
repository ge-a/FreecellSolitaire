import cs3500.freecell.controller.FreecellController;
import cs3500.freecell.controller.SimpleFreecellController;
import cs3500.freecell.model.FreecellModel;
import cs3500.freecell.model.hw02.ICard;
import cs3500.freecell.model.hw04.SimpleMultiMoveFreecellModel;
import org.junit.Before;
import org.junit.Test;

import java.io.StringReader;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Tests for the SimpleFreecellController class that pertain to the multimove functionality.
 */
public class MultiMoveSimpleFreecellControllerTest {

  Readable rd;
  Appendable ap;
  FreecellModel<ICard> model;
  FreecellModel<ICard> mockModel;
  FreecellController<ICard> controller;
  FreecellController<ICard> mockController;
  List<ICard> deck;

  @Before
  public void init() {
    this.rd = new StringReader("q");
    this.ap = new StringBuilder();
    this.model = new SimpleMultiMoveFreecellModel();
    this.mockModel = new MockFreecellModel(this.ap);
    this.controller = new SimpleFreecellController(this.model, this.rd, this.ap);
    this.mockController = new SimpleFreecellController(this.mockModel, this.rd, this.ap);
    this.deck = model.getDeck();
  }

  @Test
  public void testValidMultiMove() {
    this.rd = new StringReader("c7 3 c15 c13 2 c15 c15 2 c9 q");
    this.controller = new SimpleFreecellController(this.model, this.rd, this.ap);
    this.controller.playGame(this.deck, 20, 4, false);
    assertEquals(
        "F1:\n"
            + "F2:\n"
            + "F3:\n"
            + "F4:\n"
            + "O1:\n"
            + "O2:\n"
            + "O3:\n"
            + "O4:\n"
            + "C1: A♣, 8♦, 2♠\n"
            + "C2: 2♣, 9♦, 3♠\n"
            + "C3: 3♣, 10♦, 4♠\n"
            + "C4: 4♣, J♦, 5♠\n"
            + "C5: 5♣, Q♦, 6♠\n"
            + "C6: 6♣, K♦, 7♠\n"
            + "C7: 7♣, A♥, 8♠\n"
            + "C8: 8♣, 2♥, 9♠\n"
            + "C9: 9♣, 3♥, 10♠\n"
            + "C10: 10♣, 4♥, J♠\n"
            + "C11: J♣, 5♥, Q♠\n"
            + "C12: Q♣, 6♥, K♠\n"
            + "C13: K♣, 7♥\n"
            + "C14: A♦, 8♥\n"
            + "C15: 2♦, 9♥\n"
            + "C16: 3♦, 10♥\n"
            + "C17: 4♦, J♥\n"
            + "C18: 5♦, Q♥\n"
            + "C19: 6♦, K♥\n"
            + "C20: 7♦, A♠\n"
            + "F1:\n"
            + "F2:\n"
            + "F3:\n"
            + "F4:\n"
            + "O1:\n"
            + "O2:\n"
            + "O3:\n"
            + "O4:\n"
            + "C1: A♣, 8♦, 2♠\n"
            + "C2: 2♣, 9♦, 3♠\n"
            + "C3: 3♣, 10♦, 4♠\n"
            + "C4: 4♣, J♦, 5♠\n"
            + "C5: 5♣, Q♦, 6♠\n"
            + "C6: 6♣, K♦, 7♠\n"
            + "C7: 7♣, A♥\n"
            + "C8: 8♣, 2♥, 9♠\n"
            + "C9: 9♣, 3♥, 10♠\n"
            + "C10: 10♣, 4♥, J♠\n"
            + "C11: J♣, 5♥, Q♠\n"
            + "C12: Q♣, 6♥, K♠\n"
            + "C13: K♣, 7♥\n"
            + "C14: A♦, 8♥\n"
            + "C15: 2♦, 9♥, 8♠\n"
            + "C16: 3♦, 10♥\n"
            + "C17: 4♦, J♥\n"
            + "C18: 5♦, Q♥\n"
            + "C19: 6♦, K♥\n"
            + "C20: 7♦, A♠\n"
            + "F1:\n"
            + "F2:\n"
            + "F3:\n"
            + "F4:\n"
            + "O1:\n"
            + "O2:\n"
            + "O3:\n"
            + "O4:\n"
            + "C1: A♣, 8♦, 2♠\n"
            + "C2: 2♣, 9♦, 3♠\n"
            + "C3: 3♣, 10♦, 4♠\n"
            + "C4: 4♣, J♦, 5♠\n"
            + "C5: 5♣, Q♦, 6♠\n"
            + "C6: 6♣, K♦, 7♠\n"
            + "C7: 7♣, A♥\n"
            + "C8: 8♣, 2♥, 9♠\n"
            + "C9: 9♣, 3♥, 10♠\n"
            + "C10: 10♣, 4♥, J♠\n"
            + "C11: J♣, 5♥, Q♠\n"
            + "C12: Q♣, 6♥, K♠\n"
            + "C13: K♣\n"
            + "C14: A♦, 8♥\n"
            + "C15: 2♦, 9♥, 8♠, 7♥\n"
            + "C16: 3♦, 10♥\n"
            + "C17: 4♦, J♥\n"
            + "C18: 5♦, Q♥\n"
            + "C19: 6♦, K♥\n"
            + "C20: 7♦, A♠\n"
            + "F1:\n"
            + "F2:\n"
            + "F3:\n"
            + "F4:\n"
            + "O1:\n"
            + "O2:\n"
            + "O3:\n"
            + "O4:\n"
            + "C1: A♣, 8♦, 2♠\n"
            + "C2: 2♣, 9♦, 3♠\n"
            + "C3: 3♣, 10♦, 4♠\n"
            + "C4: 4♣, J♦, 5♠\n"
            + "C5: 5♣, Q♦, 6♠\n"
            + "C6: 6♣, K♦, 7♠\n"
            + "C7: 7♣, A♥\n"
            + "C8: 8♣, 2♥, 9♠\n"
            + "C9: 9♣, 3♥, 10♠, 9♥, 8♠, 7♥\n"
            + "C10: 10♣, 4♥, J♠\n"
            + "C11: J♣, 5♥, Q♠\n"
            + "C12: Q♣, 6♥, K♠\n"
            + "C13: K♣\n"
            + "C14: A♦, 8♥\n"
            + "C15: 2♦\n"
            + "C16: 3♦, 10♥\n"
            + "C17: 4♦, J♥\n"
            + "C18: 5♦, Q♥\n"
            + "C19: 6♦, K♥\n"
            + "C20: 7♦, A♠\n"
            + "Game quit prematurely.",
        this.ap.toString());
  }

  @Test
  public void testValidMultiMoveNotEnoughOpenPiles() {
    this.rd = new StringReader("c7 3 c15 c13 2 c15 c15 2 c9 q");
    this.controller = new SimpleFreecellController(this.model, this.rd, this.ap);
    this.controller.playGame(this.deck, 20, 1, false);
    assertEquals(
        "F1:\n"
            + "F2:\n"
            + "F3:\n"
            + "F4:\n"
            + "O1:\n"
            + "C1: A♣, 8♦, 2♠\n"
            + "C2: 2♣, 9♦, 3♠\n"
            + "C3: 3♣, 10♦, 4♠\n"
            + "C4: 4♣, J♦, 5♠\n"
            + "C5: 5♣, Q♦, 6♠\n"
            + "C6: 6♣, K♦, 7♠\n"
            + "C7: 7♣, A♥, 8♠\n"
            + "C8: 8♣, 2♥, 9♠\n"
            + "C9: 9♣, 3♥, 10♠\n"
            + "C10: 10♣, 4♥, J♠\n"
            + "C11: J♣, 5♥, Q♠\n"
            + "C12: Q♣, 6♥, K♠\n"
            + "C13: K♣, 7♥\n"
            + "C14: A♦, 8♥\n"
            + "C15: 2♦, 9♥\n"
            + "C16: 3♦, 10♥\n"
            + "C17: 4♦, J♥\n"
            + "C18: 5♦, Q♥\n"
            + "C19: 6♦, K♥\n"
            + "C20: 7♦, A♠\n"
            + "F1:\n"
            + "F2:\n"
            + "F3:\n"
            + "F4:\n"
            + "O1:\n"
            + "C1: A♣, 8♦, 2♠\n"
            + "C2: 2♣, 9♦, 3♠\n"
            + "C3: 3♣, 10♦, 4♠\n"
            + "C4: 4♣, J♦, 5♠\n"
            + "C5: 5♣, Q♦, 6♠\n"
            + "C6: 6♣, K♦, 7♠\n"
            + "C7: 7♣, A♥\n"
            + "C8: 8♣, 2♥, 9♠\n"
            + "C9: 9♣, 3♥, 10♠\n"
            + "C10: 10♣, 4♥, J♠\n"
            + "C11: J♣, 5♥, Q♠\n"
            + "C12: Q♣, 6♥, K♠\n"
            + "C13: K♣, 7♥\n"
            + "C14: A♦, 8♥\n"
            + "C15: 2♦, 9♥, 8♠\n"
            + "C16: 3♦, 10♥\n"
            + "C17: 4♦, J♥\n"
            + "C18: 5♦, Q♥\n"
            + "C19: 6♦, K♥\n"
            + "C20: 7♦, A♠\n"
            + "F1:\n"
            + "F2:\n"
            + "F3:\n"
            + "F4:\n"
            + "O1:\n"
            + "C1: A♣, 8♦, 2♠\n"
            + "C2: 2♣, 9♦, 3♠\n"
            + "C3: 3♣, 10♦, 4♠\n"
            + "C4: 4♣, J♦, 5♠\n"
            + "C5: 5♣, Q♦, 6♠\n"
            + "C6: 6♣, K♦, 7♠\n"
            + "C7: 7♣, A♥\n"
            + "C8: 8♣, 2♥, 9♠\n"
            + "C9: 9♣, 3♥, 10♠\n"
            + "C10: 10♣, 4♥, J♠\n"
            + "C11: J♣, 5♥, Q♠\n"
            + "C12: Q♣, 6♥, K♠\n"
            + "C13: K♣\n"
            + "C14: A♦, 8♥\n"
            + "C15: 2♦, 9♥, 8♠, 7♥\n"
            + "C16: 3♦, 10♥\n"
            + "C17: 4♦, J♥\n"
            + "C18: 5♦, Q♥\n"
            + "C19: 6♦, K♥\n"
            + "C20: 7♦, A♠\n"
            + "Invalid Move. Try again. Card values do not align for such a move.\n"
            + "Game quit prematurely.",
        this.ap.toString());
  }

  @Test
  public void testInvalidMultiMoveWrongCardOrderAndWrongBaseCard() {
    this.rd = new StringReader("c4 11 c3 q");
    this.controller = new SimpleFreecellController(this.model, this.rd, this.ap);
    this.controller.playGame(this.deck, 4, 2, false);
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
            + "C4: 4♣, 8♣, Q♣, 3♦, 7♦, J♦, 2♥, 6♥, 10♥, A♠, 5♠, 9♠, K♠\n"
            + "Invalid Move. Try again. Card values do not align for such a move.\n"
            + "Game quit prematurely.",
        this.ap.toString());
  }

  @Test
  public void testInvalidMultiMoveWrongBaseCard() {
    this.rd = new StringReader("c7 3 c15 c13 2 c15 c15 1 c9 q");
    this.controller = new SimpleFreecellController(this.model, this.rd, this.ap);
    this.controller.playGame(this.deck, 20, 4, false);
    assertEquals(
        "F1:\n"
            + "F2:\n"
            + "F3:\n"
            + "F4:\n"
            + "O1:\n"
            + "O2:\n"
            + "O3:\n"
            + "O4:\n"
            + "C1: A♣, 8♦, 2♠\n"
            + "C2: 2♣, 9♦, 3♠\n"
            + "C3: 3♣, 10♦, 4♠\n"
            + "C4: 4♣, J♦, 5♠\n"
            + "C5: 5♣, Q♦, 6♠\n"
            + "C6: 6♣, K♦, 7♠\n"
            + "C7: 7♣, A♥, 8♠\n"
            + "C8: 8♣, 2♥, 9♠\n"
            + "C9: 9♣, 3♥, 10♠\n"
            + "C10: 10♣, 4♥, J♠\n"
            + "C11: J♣, 5♥, Q♠\n"
            + "C12: Q♣, 6♥, K♠\n"
            + "C13: K♣, 7♥\n"
            + "C14: A♦, 8♥\n"
            + "C15: 2♦, 9♥\n"
            + "C16: 3♦, 10♥\n"
            + "C17: 4♦, J♥\n"
            + "C18: 5♦, Q♥\n"
            + "C19: 6♦, K♥\n"
            + "C20: 7♦, A♠\n"
            + "F1:\n"
            + "F2:\n"
            + "F3:\n"
            + "F4:\n"
            + "O1:\n"
            + "O2:\n"
            + "O3:\n"
            + "O4:\n"
            + "C1: A♣, 8♦, 2♠\n"
            + "C2: 2♣, 9♦, 3♠\n"
            + "C3: 3♣, 10♦, 4♠\n"
            + "C4: 4♣, J♦, 5♠\n"
            + "C5: 5♣, Q♦, 6♠\n"
            + "C6: 6♣, K♦, 7♠\n"
            + "C7: 7♣, A♥\n"
            + "C8: 8♣, 2♥, 9♠\n"
            + "C9: 9♣, 3♥, 10♠\n"
            + "C10: 10♣, 4♥, J♠\n"
            + "C11: J♣, 5♥, Q♠\n"
            + "C12: Q♣, 6♥, K♠\n"
            + "C13: K♣, 7♥\n"
            + "C14: A♦, 8♥\n"
            + "C15: 2♦, 9♥, 8♠\n"
            + "C16: 3♦, 10♥\n"
            + "C17: 4♦, J♥\n"
            + "C18: 5♦, Q♥\n"
            + "C19: 6♦, K♥\n"
            + "C20: 7♦, A♠\n"
            + "F1:\n"
            + "F2:\n"
            + "F3:\n"
            + "F4:\n"
            + "O1:\n"
            + "O2:\n"
            + "O3:\n"
            + "O4:\n"
            + "C1: A♣, 8♦, 2♠\n"
            + "C2: 2♣, 9♦, 3♠\n"
            + "C3: 3♣, 10♦, 4♠\n"
            + "C4: 4♣, J♦, 5♠\n"
            + "C5: 5♣, Q♦, 6♠\n"
            + "C6: 6♣, K♦, 7♠\n"
            + "C7: 7♣, A♥\n"
            + "C8: 8♣, 2♥, 9♠\n"
            + "C9: 9♣, 3♥, 10♠\n"
            + "C10: 10♣, 4♥, J♠\n"
            + "C11: J♣, 5♥, Q♠\n"
            + "C12: Q♣, 6♥, K♠\n"
            + "C13: K♣\n"
            + "C14: A♦, 8♥\n"
            + "C15: 2♦, 9♥, 8♠, 7♥\n"
            + "C16: 3♦, 10♥\n"
            + "C17: 4♦, J♥\n"
            + "C18: 5♦, Q♥\n"
            + "C19: 6♦, K♥\n"
            + "C20: 7♦, A♠\n"
            + "Invalid Move. Try again. Card values do not align for such a move.\n"
            + "Game quit prematurely.",
        this.ap.toString());
  }

  @Test
  public void testInvalidMultiMoveWrongCardOrder() {
    this.rd = new StringReader("C1 4 c5 q");
    this.controller = new SimpleFreecellController(this.model, this.rd, this.ap);
    this.controller.playGame(this.deck, 10, 4, false);
    assertEquals(
        "F1:\n"
            + "F2:\n"
            + "F3:\n"
            + "F4:\n"
            + "O1:\n"
            + "O2:\n"
            + "O3:\n"
            + "O4:\n"
            + "C1: A♣, J♣, 8♦, 5♥, 2♠, Q♠\n"
            + "C2: 2♣, Q♣, 9♦, 6♥, 3♠, K♠\n"
            + "C3: 3♣, K♣, 10♦, 7♥, 4♠\n"
            + "C4: 4♣, A♦, J♦, 8♥, 5♠\n"
            + "C5: 5♣, 2♦, Q♦, 9♥, 6♠\n"
            + "C6: 6♣, 3♦, K♦, 10♥, 7♠\n"
            + "C7: 7♣, 4♦, A♥, J♥, 8♠\n"
            + "C8: 8♣, 5♦, 2♥, Q♥, 9♠\n"
            + "C9: 9♣, 6♦, 3♥, K♥, 10♠\n"
            + "C10: 10♣, 7♦, 4♥, A♠, J♠\n"
            + "Invalid Move. Try again. Card values do not align for such a move.\n"
            + "Game quit prematurely.",
        this.ap.toString());
  }
}
