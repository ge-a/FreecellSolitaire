import cs3500.freecell.model.FreecellModel;
import cs3500.freecell.model.FreecellModelCreator;
import cs3500.freecell.model.hw02.ICard;
import cs3500.freecell.model.hw02.SimpleFreecellModel;
import cs3500.freecell.model.hw04.SimpleMultiMoveFreecellModel;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/** Tests for the FreecellModelCreator class. */
public class FreecellModelCreatorTest {


  FreecellModel<ICard> simpleModel;
  FreecellModel<ICard> multiMoveModel;

  @Before
  public void init() {
    this.simpleModel = FreecellModelCreator.create(FreecellModelCreator.GameType.SINGLEMOVE);
    this.multiMoveModel = FreecellModelCreator.create(FreecellModelCreator.GameType.MULTIMOVE);
  }

  @Test
  public void testCreateSingleMoveGame() {
    assertEquals(SimpleFreecellModel.class, this.simpleModel.getClass());
  }

  @Test
  public void testCreateMultiMoveGame() {
    assertEquals(SimpleMultiMoveFreecellModel.class, this.multiMoveModel.getClass());
  }
}
