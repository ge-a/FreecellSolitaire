import cs3500.freecell.controller.FreecellController;
import cs3500.freecell.controller.SimpleFreecellController;
import cs3500.freecell.model.FreecellModel;
import cs3500.freecell.model.hw02.ICard;
import cs3500.freecell.model.hw02.SimpleFreecellModel;
import cs3500.freecell.model.hw04.SimpleMultiMoveFreecellModel;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Tests for the SimpleFreecellController class.
 */
public abstract class AbstractSimpleFreecellControllerTest {

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
    this.model = constructModel();
    this.mockModel = new MockFreecellModel(this.ap);
    this.controller = new SimpleFreecellController(this.model, this.rd, this.ap);
    this.mockController = new SimpleFreecellController(this.mockModel, this.rd, this.ap);
    this.deck = model.getDeck();
  }

  /**
   * Makes a model of the FreecellModel interface.
   * @return a model that extends the FreecellModel interface.
   */
  protected abstract FreecellModel<ICard> constructModel();

  /**
   * Tests for the SimpleFreecellController class with a SimpleFreecellModel.
   */
  public static class SimpleFreecellControllerSimpleFreecellModelTest
          extends AbstractSimpleFreecellControllerTest {

    @Override
    protected FreecellModel<ICard> constructModel() {
      return new SimpleFreecellModel();
    }
  }

  /**
   * Tests for the SimpleFreecellController class with a SimpleMultiMoveFreecellModel.
   */
  public static class SimpleFreecellConstrollerSimpleMultiMoveFreecellModelTest
          extends AbstractSimpleFreecellControllerTest {

    @Override
    protected FreecellModel<ICard> constructModel() {
      return new SimpleMultiMoveFreecellModel();
    }
  }


  @Test(expected = IllegalArgumentException.class)
  public void testNullReadableConstructor() {
    this.controller = new SimpleFreecellController(this.model, null, this.ap);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullAppendableConstructor() {
    this.controller = new SimpleFreecellController(this.model, this.rd, null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullModelConstructor() {
    this.controller = new SimpleFreecellController(null, this.rd, this.ap);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testPlayGameInvalidDeck() {
    this.controller.playGame(null, 4, 1, false);
  }

  @Test
  public void testPlayGameInvalidCascadeValues() {
    this.controller = new SimpleFreecellController(this.model, this.rd, this.ap);
    this.controller.playGame(this.deck, -1, 1, false);
    assertEquals("Could not start game.", this.ap.toString());
  }

  @Test
  public void testMockPlayGameStartGameInvalidCascadeInput() {
    this.mockController.playGame(this.deck, 1, 1, false);
    assertEquals(deck.toString() + " 1 1 false\nGame quit prematurely.", this.ap.toString());
  }

  @Test
  public void testPlayGameInvalidOpenValues() {
    this.rd = new StringReader("bad input");
    this.controller = new SimpleFreecellController(this.model, this.rd, this.ap);
    this.controller.playGame(this.deck, 4, 0, false);
    assertEquals("Could not start game.", this.ap.toString());
  }

  @Test
  public void testMockPlayGameStartGameInvalidOpenInput() {
    this.mockController.playGame(this.deck, 4, -1, false);
    assertEquals(deck.toString() + " 4 -1 false\nGame quit prematurely.", this.ap.toString());
  }

  @Test(expected = IllegalStateException.class)
  public void testCallPlayGameMultipleTimes() {
    this.rd = new StringReader("c1 13 q o1 q");
    this.controller = new SimpleFreecellController(this.model, this.rd, this.ap);
    this.controller.playGame(this.deck, 4, 1, false);
    this.rd = new StringReader("o1 1 q f1 q");
    this.controller.playGame(this.deck, 4, 1, false);
    assertEquals("Could not start game.", this.ap.toString());
  }

  @Test
  public void testPlayGameQuitEnd() {
    this.rd = new StringReader("c1 43 o2 o2 o2 o2 43 q");
    this.controller = new SimpleFreecellController(this.model, this.rd, this.ap);
    this.controller.playGame(this.deck, 4, 1, false);
    assertEquals(
        "F1:\n"
            + "F2:\n"
            + "F3:\n"
            + "F4:\n"
            + "O1:\n"
            + "C1: A♣, 5♣, 9♣, K♣, 4♦, 8♦, Q♦, 3♥, 7♥, J♥, 2♠, 6♠, 10♠\n"
            + "C2: 2♣, 6♣, 10♣, A♦, 5♦, 9♦, K♦, 4♥, 8♥, Q♥, 3♠, 7♠, J♠\n"
            + "C3: 3♣, 7♣, J♣, 2♦, 6♦, 10♦, A♥, 5♥, 9♥, K♥, 4♠, 8♠, Q♠\n"
            + "C4: 4♣, 8♣, Q♣, 3♦, 7♦, J♦, 2♥, 6♥, 10♥, A♠, 5♠, 9♠, K♠\n"
            + "Invalid Move. Try again. Your destination pile index is invalid.\n"
            + "Invalid card index, try again!\n"
            + "Invalid card index, try again!\n"
            + "Game quit prematurely.",
        this.ap.toString());
  }

  @Test
  public void testPlayGameQuitSourceInput() {
    this.rd = new StringReader("c1 23 o1 q asd c1 ");
    this.controller = new SimpleFreecellController(this.model, this.rd, this.ap);
    this.controller.playGame(this.deck, 4, 1, false);
    assertEquals(
        "F1:\n"
            + "F2:\n"
            + "F3:\n"
            + "F4:\n"
            + "O1:\n"
            + "C1: A♣, 5♣, 9♣, K♣, 4♦, 8♦, Q♦, 3♥, 7♥, J♥, 2♠, 6♠, 10♠\n"
            + "C2: 2♣, 6♣, 10♣, A♦, 5♦, 9♦, K♦, 4♥, 8♥, Q♥, 3♠, 7♠, J♠\n"
            + "C3: 3♣, 7♣, J♣, 2♦, 6♦, 10♦, A♥, 5♥, 9♥, K♥, 4♠, 8♠, Q♠\n"
            + "C4: 4♣, 8♣, Q♣, 3♦, 7♦, J♦, 2♥, 6♥, 10♥, A♠, 5♠, 9♠, K♠\n"
            + "Invalid Move. Try again.\n"
            + "Game quit prematurely.",
        this.ap.toString());
  }

  @Test
  public void testPlayGameQuitCardInput() {
    this.rd = new StringReader("c1 23 o1 o1 q c1 ");
    this.controller = new SimpleFreecellController(this.model, this.rd, this.ap);
    this.controller.playGame(this.deck, 4, 1, false);
    assertEquals(
        "F1:\n"
            + "F2:\n"
            + "F3:\n"
            + "F4:\n"
            + "O1:\n"
            + "C1: A♣, 5♣, 9♣, K♣, 4♦, 8♦, Q♦, 3♥, 7♥, J♥, 2♠, 6♠, 10♠\n"
            + "C2: 2♣, 6♣, 10♣, A♦, 5♦, 9♦, K♦, 4♥, 8♥, Q♥, 3♠, 7♠, J♠\n"
            + "C3: 3♣, 7♣, J♣, 2♦, 6♦, 10♦, A♥, 5♥, 9♥, K♥, 4♠, 8♠, Q♠\n"
            + "C4: 4♣, 8♣, Q♣, 3♦, 7♦, J♦, 2♥, 6♥, 10♥, A♠, 5♠, 9♠, K♠\n"
            + "Invalid Move. Try again.\n"
            + "Game quit prematurely.",
        this.ap.toString());
  }

  @Test
  public void testPlayGameQuitDestInput() {
    this.rd = new StringReader("c1 23 o1 o1 12 q ");
    this.controller = new SimpleFreecellController(this.model, this.rd, this.ap);
    this.controller.playGame(this.deck, 4, 1, false);
    assertEquals(
        "F1:\n"
            + "F2:\n"
            + "F3:\n"
            + "F4:\n"
            + "O1:\n"
            + "C1: A♣, 5♣, 9♣, K♣, 4♦, 8♦, Q♦, 3♥, 7♥, J♥, 2♠, 6♠, 10♠\n"
            + "C2: 2♣, 6♣, 10♣, A♦, 5♦, 9♦, K♦, 4♥, 8♥, Q♥, 3♠, 7♠, J♠\n"
            + "C3: 3♣, 7♣, J♣, 2♦, 6♦, 10♦, A♥, 5♥, 9♥, K♥, 4♠, 8♠, Q♠\n"
            + "C4: 4♣, 8♣, Q♣, 3♦, 7♦, J♦, 2♥, 6♥, 10♥, A♠, 5♠, 9♠, K♠\n"
            + "Invalid Move. Try again.\n"
            + "Game quit prematurely.",
        this.ap.toString());
  }

  @Test(expected = IllegalStateException.class)
  public void testRunOutOfReadablesNonAttachedQ() {
    this.rd = new StringReader("c1 23 o1 asd c1 2 qz");
    this.controller = new SimpleFreecellController(this.model, this.rd, this.ap);
    this.controller.playGame(this.deck, 4, 1, false);
  }

  @Test(expected = IllegalStateException.class)
  public void testRunOutOfReadablesValidMovesNoQ() {
    this.rd = new StringReader("c1 23 o1 asd c1 2 cz f1 1 3 2 oo");
    this.controller = new SimpleFreecellController(this.model, this.rd, this.ap);
    this.controller.playGame(this.deck, 4, 1, false);
  }

  @Test(expected = IllegalStateException.class)
  public void testRunOutOfReadablesEmptyString() {
    this.rd = new StringReader("");
    this.controller = new SimpleFreecellController(this.model, this.rd, this.ap);
    this.controller.playGame(this.deck, 4, 1, false);
  }

  @Test(expected = IllegalStateException.class)
  public void testRunOutOfReadablesNoCompletedMove() {
    this.rd = new StringReader("f 32 c1 2 gg df cr");
    this.controller = new SimpleFreecellController(this.model, this.rd, this.ap);
    this.controller.playGame(this.deck, 4, 1, false);
  }

  @Test
  public void testValidInputsBogusMove() {
    this.rd = new StringReader("o1 12 f1 q");
    this.controller = new SimpleFreecellController(this.model, this.rd, this.ap);
    this.controller.playGame(this.deck, 4, 1, false);
    assertEquals(
        "F1:\n"
            + "F2:\n"
            + "F3:\n"
            + "F4:\n"
            + "O1:\n"
            + "C1: A♣, 5♣, 9♣, K♣, 4♦, 8♦, Q♦, 3♥, 7♥, J♥, 2♠, 6♠, 10♠\n"
            + "C2: 2♣, 6♣, 10♣, A♦, 5♦, 9♦, K♦, 4♥, 8♥, Q♥, 3♠, 7♠, J♠\n"
            + "C3: 3♣, 7♣, J♣, 2♦, 6♦, 10♦, A♥, 5♥, 9♥, K♥, 4♠, 8♠, Q♠\n"
            + "C4: 4♣, 8♣, Q♣, 3♦, 7♦, J♦, 2♥, 6♥, 10♥, A♠, 5♠, 9♠, K♠\n"
            + "Invalid Move. Try again. Card values do not align for such a move.\n"
            + "Game quit prematurely.",
        this.ap.toString());
  }

  @Test
  public void testMockValidInputsBogusMove() {
    this.rd = new StringReader("o1 12 f1 q");
    this.mockController = new SimpleFreecellController(this.mockModel, this.rd, this.ap);
    this.mockController.playGame(this.deck, 4, 1, false);
    assertEquals(
        this.deck.toString()
            + " 4 1 false\n"
            + "game not over game not over O0 11 F0\n"
            + "game not over Game quit prematurely.",
        this.ap.toString());
  }

  @Test
  public void testPlayGameValidMoveCorrectedInvalidCardIndex() {
    this.rd = new StringReader("c1 1E c1 13 o1 q");
    this.controller = new SimpleFreecellController(this.model, this.rd, this.ap);
    this.controller.playGame(this.deck, 4, 1, false);
    assertEquals(
        "F1:\nF2:\nF3:\nF4:\nO1:\n"
            + "C1: A♣, 5♣, 9♣, K♣, 4♦, 8♦, Q♦, 3♥, 7♥, J♥, 2♠, 6♠, 10♠\n"
            + "C2: 2♣, 6♣, 10♣, A♦, 5♦, 9♦, K♦, 4♥, 8♥, Q♥, 3♠, 7♠, J♠\n"
            + "C3: 3♣, 7♣, J♣, 2♦, 6♦, 10♦, A♥, 5♥, 9♥, K♥, 4♠, 8♠, Q♠\n"
            + "C4: 4♣, 8♣, Q♣, 3♦, 7♦, J♦, 2♥, 6♥, 10♥, A♠, 5♠, 9♠, K♠\n"
            + "Invalid card index, try again!\nInvalid card index, try again!\n"
            + "F1:\nF2:\nF3:\nF4:\nO1: 10♠\n"
            + "C1: A♣, 5♣, 9♣, K♣, 4♦, 8♦, Q♦, 3♥, 7♥, J♥, 2♠, 6♠\n"
            + "C2: 2♣, 6♣, 10♣, A♦, 5♦, 9♦, K♦, 4♥, 8♥, Q♥, 3♠, 7♠, J♠\n"
            + "C3: 3♣, 7♣, J♣, 2♦, 6♦, 10♦, A♥, 5♥, 9♥, K♥, 4♠, 8♠, Q♠\n"
            + "C4: 4♣, 8♣, Q♣, 3♦, 7♦, J♦, 2♥, 6♥, 10♥, A♠, 5♠, 9♠, K♠\n"
            + "Game quit prematurely.",
        this.ap.toString());
  }

  @Test
  public void testMockPlayGameValidMoveCorrectedInvalidCardIndex() {
    this.rd = new StringReader("c1 1E c1 233 f1 q");
    this.mockController = new SimpleFreecellController(this.mockModel, this.rd, this.ap);
    this.mockController.playGame(this.deck, 4, 1, false);
    assertEquals(
        this.deck.toString()
            + " 4 1 false\n"
            + "game not over Invalid card index, try again!\n"
            + "game not over Invalid card index, try again!\n"
            + "game not over game not over C0 232 F0\n"
            + "game not over Game quit prematurely.",
        this.ap.toString());
  }

  @Test
  public void testPlayGameCorrectedInvalidSourcePiles() {
    this.rd = new StringReader("cd 14 o1 13 f1 q");
    this.controller = new SimpleFreecellController(this.model, this.rd, this.ap);
    this.controller.playGame(this.deck, 4, 1, false);
    assertEquals(
        "F1:\nF2:\nF3:\nF4:\nO1:\n"
            + "C1: A♣, 5♣, 9♣, K♣, 4♦, 8♦, Q♦, 3♥, 7♥, J♥, 2♠, 6♠, 10♠\n"
            + "C2: 2♣, 6♣, 10♣, A♦, 5♦, 9♦, K♦, 4♥, 8♥, Q♥, 3♠, 7♠, J♠\n"
            + "C3: 3♣, 7♣, J♣, 2♦, 6♦, 10♦, A♥, 5♥, 9♥, K♥, 4♠, 8♠, Q♠\n"
            + "C4: 4♣, 8♣, Q♣, 3♦, 7♦, J♦, 2♥, 6♥, 10♥, A♠, 5♠, 9♠, K♠\n"
            + "Invalid source pile index, try again!\n"
            + "Invalid source pile, try again!\n"
            + "Invalid Move. Try again. Card values do not align for such a move.\n"
            + "Game quit prematurely.",
        this.ap.toString());
  }

  @Test
  public void testMockPlayGameCorrectedInvalidSourcePiles() {
    this.rd = new StringReader("cd 14 c12 -1 f1 q");
    this.mockController = new SimpleFreecellController(this.mockModel, this.rd, this.ap);
    this.mockController.playGame(this.deck, 4, 1, false);
    assertEquals(
        deck.toString()
            + " 4 1 false\n"
            + "Invalid source pile index, try again!\n"
            + "game not over Invalid source pile, try again!\n"
            + "game not over game not over game not over C11 -2 F0\n"
            + "game not over Game quit prematurely.",
        this.ap.toString());
  }

  @Test
  public void testPlayGameCorrectedInvalidDestinationPiles() {
    this.rd = new StringReader("o1 13 13 fq f1 q");
    this.controller = new SimpleFreecellController(this.model, this.rd, this.ap);
    this.controller.playGame(this.deck, 4, 1, false);
    assertEquals(
        "F1:\nF2:\nF3:\nF4:\nO1:\n"
            + "C1: A♣, 5♣, 9♣, K♣, 4♦, 8♦, Q♦, 3♥, 7♥, J♥, 2♠, 6♠, 10♠\n"
            + "C2: 2♣, 6♣, 10♣, A♦, 5♦, 9♦, K♦, 4♥, 8♥, Q♥, 3♠, 7♠, J♠\n"
            + "C3: 3♣, 7♣, J♣, 2♦, 6♦, 10♦, A♥, 5♥, 9♥, K♥, 4♠, 8♠, Q♠\n"
            + "C4: 4♣, 8♣, Q♣, 3♦, 7♦, J♦, 2♥, 6♥, 10♥, A♠, 5♠, 9♠, K♠\n"
            + "Invalid destination pile, try again!\n"
            + "Invalid destination pile index, try again!\n"
            + "Invalid Move. Try again. Card values do not align for such a move.\n"
            + "Game quit prematurely.",
        this.ap.toString());
  }

  @Test
  public void testMockPlayGameCorrectedInvalidDestinationPiles() {
    this.rd = new StringReader("o1 15 13 fq f11 q");
    this.mockController = new SimpleFreecellController(this.mockModel, this.rd, this.ap);
    this.mockController.playGame(this.deck, 4, 1, false);
    assertEquals(
        deck.toString()
            + " 4 1 false\n"
            + "game not over game not over Invalid destination pile, try again!\n"
            + "game not over Invalid destination pile index, try again!\n"
            + "game not over O0 14 F10\n"
            + "game not over Game quit prematurely.",
        this.ap.toString());
  }

  @Test
  public void testPlayGameCorrectedInvalidSourcePileAndCardIndex() {
    this.rd = new StringReader("cd c1 c1 14 o1 q");
    this.controller = new SimpleFreecellController(this.model, this.rd, this.ap);
    this.controller.playGame(this.deck, 4, 1, false);
    assertEquals(
        "F1:\nF2:\nF3:\nF4:\nO1:\n"
            + "C1: A♣, 5♣, 9♣, K♣, 4♦, 8♦, Q♦, 3♥, 7♥, J♥, 2♠, 6♠, 10♠\n"
            + "C2: 2♣, 6♣, 10♣, A♦, 5♦, 9♦, K♦, 4♥, 8♥, Q♥, 3♠, 7♠, J♠\n"
            + "C3: 3♣, 7♣, J♣, 2♦, 6♦, 10♦, A♥, 5♥, 9♥, K♥, 4♠, 8♠, Q♠\n"
            + "C4: 4♣, 8♣, Q♣, 3♦, 7♦, J♦, 2♥, 6♥, 10♥, A♠, 5♠, 9♠, K♠\n"
            + "Invalid source pile index, try again!\n"
            + "Invalid card index, try again!\n"
            + "Invalid Move. Try again.\n"
            + "Game quit prematurely.",
        this.ap.toString());
  }

  @Test
  public void testMockPlayGameCorrectedInvalidSourcePileAndCardIndex() {
    this.rd = new StringReader("cd c4 c1 -2 o1 q");
    this.mockController = new SimpleFreecellController(this.mockModel, this.rd, this.ap);
    this.mockController.playGame(this.deck, 4, 1, false);
    assertEquals(
        deck.toString()
            + " 4 1 false\n"
            + "Invalid source pile index, try again!\n"
            + "game not over game not over Invalid card index, try again!\n"
            + "game not over game not over C3 -3 O0\n"
            + "game not over Game quit prematurely.",
        this.ap.toString());
  }

  @Test
  public void testPlayGameCorrectedInvalidSourceAndDestPiles() {
    this.rd = new StringReader("cd c1 14 14 f1 q");
    this.controller = new SimpleFreecellController(this.model, this.rd, this.ap);
    this.controller.playGame(this.deck, 4, 1, false);
    assertEquals(
        "F1:\nF2:\nF3:\nF4:\nO1:\n"
            + "C1: A♣, 5♣, 9♣, K♣, 4♦, 8♦, Q♦, 3♥, 7♥, J♥, 2♠, 6♠, 10♠\n"
            + "C2: 2♣, 6♣, 10♣, A♦, 5♦, 9♦, K♦, 4♥, 8♥, Q♥, 3♠, 7♠, J♠\n"
            + "C3: 3♣, 7♣, J♣, 2♦, 6♦, 10♦, A♥, 5♥, 9♥, K♥, 4♠, 8♠, Q♠\n"
            + "C4: 4♣, 8♣, Q♣, 3♦, 7♦, J♦, 2♥, 6♥, 10♥, A♠, 5♠, 9♠, K♠\n"
            + "Invalid source pile index, try again!\n"
            + "Invalid destination pile, try again!\n"
            + "Invalid Move. Try again. Card values do not align for such a move.\n"
            + "Game quit prematurely.",
        this.ap.toString());
  }

  @Test
  public void testMockPlayGameCorrectedInvalidSourceAndDestPiles() {
    this.rd = new StringReader("cd c1 14 14 f1 q");
    this.mockController = new SimpleFreecellController(this.mockModel, this.rd, this.ap);
    this.mockController.playGame(this.deck, 4, 1, false);
    assertEquals(
        deck.toString()
            + " 4 1 false\n"
            + "Invalid source pile index, try again!\n"
            + "game not over game not over game not over Invalid destination pile, try again!\n"
            + "game not over C0 13 F0\n"
            + "game not over Game quit prematurely.",
        this.ap.toString());
  }

  @Test
  public void testPlayGameCorrectedInvalidDestPileAndCardIndex() {
    this.rd = new StringReader("c4 c1 13 13 f1 q");
    this.controller = new SimpleFreecellController(this.model, this.rd, this.ap);
    this.controller.playGame(this.deck, 4, 1, false);
    assertEquals(
        "F1:\nF2:\nF3:\nF4:\nO1:\n"
            + "C1: A♣, 5♣, 9♣, K♣, 4♦, 8♦, Q♦, 3♥, 7♥, J♥, 2♠, 6♠, 10♠\n"
            + "C2: 2♣, 6♣, 10♣, A♦, 5♦, 9♦, K♦, 4♥, 8♥, Q♥, 3♠, 7♠, J♠\n"
            + "C3: 3♣, 7♣, J♣, 2♦, 6♦, 10♦, A♥, 5♥, 9♥, K♥, 4♠, 8♠, Q♠\n"
            + "C4: 4♣, 8♣, Q♣, 3♦, 7♦, J♦, 2♥, 6♥, 10♥, A♠, 5♠, 9♠, K♠\n"
            + "Invalid card index, try again!\n"
            + "Invalid destination pile, try again!\n"
            + "Invalid Move. Try again. Card values do not align for such a move.\n"
            + "Game quit prematurely.",
        this.ap.toString());
  }

  @Test
  public void testMockPlayGameCorrectedInvalidDestPileAndCardIndex() {
    this.rd = new StringReader("c4 cadf32 13 f1d f1 q");
    this.mockController = new SimpleFreecellController(this.mockModel, this.rd, this.ap);
    this.mockController.playGame(this.deck, 4, 1, false);
    assertEquals(
        deck.toString()
            + " 4 1 false\n"
            + "game not over Invalid card index, try again!\n"
            + "game not over game not over Invalid destination pile index, try again!\n"
            + "game not over C3 12 F0\n"
            + "game not over Game quit prematurely.",
        this.ap.toString());
  }

  @Test
  public void testPlayGameCorrectedInvalidSourceAndDestPileAndCardIndex() {
    this.rd = new StringReader("cz c4 c1 13 13 f1 q");
    this.controller = new SimpleFreecellController(this.model, this.rd, this.ap);
    this.controller.playGame(this.deck, 4, 1, false);
    assertEquals(
        "F1:\nF2:\nF3:\nF4:\nO1:\n"
            + "C1: A♣, 5♣, 9♣, K♣, 4♦, 8♦, Q♦, 3♥, 7♥, J♥, 2♠, 6♠, 10♠\n"
            + "C2: 2♣, 6♣, 10♣, A♦, 5♦, 9♦, K♦, 4♥, 8♥, Q♥, 3♠, 7♠, J♠\n"
            + "C3: 3♣, 7♣, J♣, 2♦, 6♦, 10♦, A♥, 5♥, 9♥, K♥, 4♠, 8♠, Q♠\n"
            + "C4: 4♣, 8♣, Q♣, 3♦, 7♦, J♦, 2♥, 6♥, 10♥, A♠, 5♠, 9♠, K♠\n"
            + "Invalid source pile index, try again!\n"
            + "Invalid card index, try again!\n"
            + "Invalid destination pile, try again!\n"
            + "Invalid Move. Try again. Card values do not align for such a move.\n"
            + "Game quit prematurely.",
        this.ap.toString());
  }

  @Test
  public void testMockPlayGameCorrectedInvalidSourceAndDestPileAndCardIndex() {
    this.rd = new StringReader("cz12 c4 1c2 13 13 f-1 q");
    this.mockController = new SimpleFreecellController(this.mockModel, this.rd, this.ap);
    this.mockController.playGame(this.deck, 4, 1, false);
    assertEquals(
        deck.toString()
            + " 4 1 false\n"
            + "Invalid source pile index, try again!\n"
            + "game not over game not over Invalid card index, try again!\n"
            + "game not over game not over Invalid destination pile, try again!\n"
            + "game not over C3 12 F-2\n"
            + "game not over Game quit prematurely.",
        this.ap.toString());
  }

  @Test
  public void testPlayGameMixedIntegerAndString() {
    this.rd = new StringReader("c12z5 q");
    this.controller = new SimpleFreecellController(this.model, this.rd, this.ap);
    this.controller.playGame(this.deck, 4, 1, false);
    assertEquals(
        "F1:\nF2:\nF3:\nF4:\nO1:\n"
            + "C1: A♣, 5♣, 9♣, K♣, 4♦, 8♦, Q♦, 3♥, 7♥, J♥, 2♠, 6♠, 10♠\n"
            + "C2: 2♣, 6♣, 10♣, A♦, 5♦, 9♦, K♦, 4♥, 8♥, Q♥, 3♠, 7♠, J♠\n"
            + "C3: 3♣, 7♣, J♣, 2♦, 6♦, 10♦, A♥, 5♥, 9♥, K♥, 4♠, 8♠, Q♠\n"
            + "C4: 4♣, 8♣, Q♣, 3♦, 7♦, J♦, 2♥, 6♥, 10♥, A♠, 5♠, 9♠, K♠\n"
            + "Invalid source pile index, try again!\n"
            + "Game quit prematurely.",
        this.ap.toString());
  }

  @Test
  public void testMockPlayGameMixedIntegerAndString() {
    this.rd = new StringReader("c12z5 c1 1z4 14 f3o3 f1 q");
    this.mockController = new SimpleFreecellController(this.mockModel, this.rd, this.ap);
    this.mockController.playGame(this.deck, 4, 1, false);
    assertEquals(
        deck.toString()
            + " 4 1 false\n"
            + "Invalid source pile index, try again!\n"
            + "game not over game not over Invalid card index, try again!\n"
            + "game not over game not over Invalid destination pile index, try again!\n"
            + "game not over C0 13 F0\n"
            + "game not over Game quit prematurely.",
        this.ap.toString());
  }

  @Test
  public void testPlayGameGameOver() {
    StringBuilder gameCommand = new StringBuilder();
    for (int i = 1; i < 14; i++) {
      gameCommand.append("C").append(i).append(" 1");
      gameCommand.append(" F").append("1 ");
    }
    for (int i = 14; i < 27; i++) {
      gameCommand.append("C").append(i).append(" 1");
      gameCommand.append(" F").append("2 ");
    }
    for (int i = 27; i < 40; i++) {
      gameCommand.append("C").append(i).append(" 1");
      gameCommand.append(" F").append("3 ");
    }
    for (int i = 40; i < 53; i++) {
      gameCommand.append("C").append(i).append(" 1");
      gameCommand.append(" F").append("4 ");
    }
    this.rd = new StringReader(gameCommand.toString());
    this.controller = new SimpleFreecellController(this.model, this.rd, this.ap);
    this.controller.playGame(this.deck, 52, 1, false);
    assertEquals("Game over.", this.ap.toString().substring(this.ap.toString().length() - 10));
  }

  @Test
  public void testPlayGameGameOverWithInvalidInputs() {
    StringBuilder gameCommand = new StringBuilder();
    for (int i = 1; i < 14; i++) {
      gameCommand.append("C").append(i).append(" 1");
      gameCommand.append(" F").append("1 3sdf ");
    }
    for (int i = 14; i < 27; i++) {
      gameCommand.append("C").append(i).append(" 1");
      gameCommand.append(" F").append("2 ");
    }
    for (int i = 27; i < 40; i++) {
      gameCommand.append("C").append(i).append(" 1");
      gameCommand.append(" F").append("3 ");
    }
    for (int i = 40; i < 53; i++) {
      gameCommand.append("C").append(i).append(" 1");
      gameCommand.append(" F").append("4 ");
    }
    this.rd = new StringReader(gameCommand.toString());
    this.controller = new SimpleFreecellController(this.model, this.rd, this.ap);
    this.controller.playGame(this.deck, 52, 1, false);
    assertEquals("Game over.", this.ap.toString().substring(this.ap.toString().length() - 10));
  }

  @Test
  public void testNegativeOutOfBounds() {
    this.rd = new StringReader("c-1 -343 f0 q");
    this.controller = new SimpleFreecellController(this.model, this.rd, this.ap);
    this.controller.playGame(this.deck, 4, 1, false);
    assertEquals(
        "F1:\nF2:\nF3:\nF4:\nO1:\n"
            + "C1: A♣, 5♣, 9♣, K♣, 4♦, 8♦, Q♦, 3♥, 7♥, J♥, 2♠, 6♠, 10♠\n"
            + "C2: 2♣, 6♣, 10♣, A♦, 5♦, 9♦, K♦, 4♥, 8♥, Q♥, 3♠, 7♠, J♠\n"
            + "C3: 3♣, 7♣, J♣, 2♦, 6♦, 10♦, A♥, 5♥, 9♥, K♥, 4♠, 8♠, Q♠\n"
            + "C4: 4♣, 8♣, Q♣, 3♦, 7♦, J♦, 2♥, 6♥, 10♥, A♠, 5♠, 9♠, K♠\n"
            + "Invalid Move. Try again. Cannot have an index less than 1\n"
            + "Game quit prematurely.",
        this.ap.toString());
  }

  @Test
  public void testTooLargeOutOfBounds() {
    this.rd = new StringReader("c5 14 f7000 q");
    this.controller = new SimpleFreecellController(this.model, this.rd, this.ap);
    this.controller.playGame(this.deck, 4, 1, false);
    assertEquals(
        "F1:\nF2:\nF3:\nF4:\nO1:\n"
            + "C1: A♣, 5♣, 9♣, K♣, 4♦, 8♦, Q♦, 3♥, 7♥, J♥, 2♠, 6♠, 10♠\n"
            + "C2: 2♣, 6♣, 10♣, A♦, 5♦, 9♦, K♦, 4♥, 8♥, Q♥, 3♠, 7♠, J♠\n"
            + "C3: 3♣, 7♣, J♣, 2♦, 6♦, 10♦, A♥, 5♥, 9♥, K♥, 4♠, 8♠, Q♠\n"
            + "C4: 4♣, 8♣, Q♣, 3♦, 7♦, J♦, 2♥, 6♥, 10♥, A♠, 5♠, 9♠, K♠\n"
            + "Invalid Move. Try again. Your source pile index is invalid.\n"
            + "Game quit prematurely.",
        this.ap.toString());
  }

  /**
   * Main method to run and play the Freecell game with an inputstream.
   *
   * @param args Array of String arguments
   */
  public static void main(String[] args) {
    FreecellModel<ICard> m = new SimpleFreecellModel();
    FreecellModel<ICard> m2 = new SimpleMultiMoveFreecellModel();

    FreecellController<ICard> c =
        new SimpleFreecellController(m2, new InputStreamReader(System.in), System.out);

    c.playGame(m.getDeck(), 20, 10, false);
  }
}
