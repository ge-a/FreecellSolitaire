package cs3500.freecell.view;

import cs3500.freecell.model.FreecellModel;
import java.io.IOException;


/**
 * The text view to visualize the freecell game in text form.
 */
public class FreecellTextView implements FreecellView {

  private final FreecellModel<?> model;
  private final Appendable ap;

  /**
   * Constructs a FreecellTextView to visualize the freecell game
   * that takes in only a model.
   *
   * @param model the model of the freecell game
   * @throws IllegalArgumentException if the model is null
   */
  public FreecellTextView(FreecellModel<?> model) throws IllegalArgumentException {
    if (model == null) {
      throw new IllegalArgumentException("model cannot be null");
    }
    this.model = model;
    ap = new StringBuilder();
  }

  /**
   * Constructs a FreecellTextView to visualize the freecell game
   * taking in a model of the game and appendable to output messages
   * and the board of the game in conjunction with the controller.
   *
   * @param model the model of the freecell game
   * @param ap the appendable object to output messages and the board
   * @throws IllegalArgumentException if the model or appendable are null
   */
  public FreecellTextView(FreecellModel<?> model, Appendable ap) throws IllegalArgumentException {
    if (model == null) {
      throw new IllegalArgumentException("model cannot be null");
    }
    if (ap == null) {
      throw new IllegalArgumentException("output cannot be null");
    }
    this.model = model;
    this.ap = ap;
  }

  @Override
  public String toString() {
    StringBuilder gameString = new StringBuilder();
    gameString.append(foundationString());
    gameString.append(openString());
    gameString.append(cascadeString());
    return gameString.toString();
  }

  /**
   * Return the present state of the foundation piles.
   *
   * @return the formatted string of the foundation piles
   */
  private String foundationString() {
    StringBuilder fString = new StringBuilder();
    if (this.model.getNumOpenPiles() < 1 || this.model.getNumCascadePiles() < 4) {
      return "";
    }
    for (int i = 1; i <= 4; i++) {
      fString.append("F");
      fString.append(i);
      fString.append(":");
      for (int j = 0; j < this.model.getNumCardsInFoundationPile(i - 1); j++) {
        fString.append(" ");
        fString.append(this.model.getFoundationCardAt(i - 1, j).toString());
        if (j != this.model.getNumCardsInFoundationPile(i - 1) - 1) {
          fString.append(",");
        }
      }
      fString.append("\n");
    }
    return fString.toString();
  }

  /**
   * Return the present state of the open piles.
   *
   * @return the formatted string of the open piles
   */
  private String openString() {
    StringBuilder oString = new StringBuilder();
    if (this.model.getNumOpenPiles() < 1 || this.model.getNumCascadePiles() < 4) {
      return "";
    }
    for (int i = 1; i <= this.model.getNumOpenPiles(); i++) {
      oString.append("O");
      oString.append(i);
      oString.append(":");
      if (this.model.getNumCardsInOpenPile(i - 1) == 1) {
        oString.append(" ");
        oString.append(this.model.getOpenCardAt(i - 1).toString());
      }
      oString.append("\n");
    }
    return oString.toString();
  }

  /**
   * Return the present state of the cascade piles.
   *
   * @return the formatted string of the cascade piles
   */
  private String cascadeString() {
    StringBuilder cString = new StringBuilder();
    if (this.model.getNumOpenPiles() < 1 || this.model.getNumCascadePiles() < 4) {
      return "";
    }
    for (int i = 1; i <= this.model.getNumCascadePiles(); i++) {
      cString.append("C");
      cString.append(i);
      cString.append(":");
      for (int j = 0; j < this.model.getNumCardsInCascadePile(i - 1); j++) {
        cString.append(" ");
        cString.append(this.model.getCascadeCardAt(i - 1, j).toString());
        if (j != this.model.getNumCardsInCascadePile(i - 1) - 1) {
          cString.append(",");
        }
      }
      if (i != this.model.getNumCascadePiles()) {
        cString.append("\n");
      }
    }
    return cString.toString();
  }

  @Override
  public void renderBoard() throws IOException {
    try {
      this.ap.append(toString());
      this.ap.append("\n");
    } catch (IOException e) {
      throw new IOException("transmission of the board to the provided data destination fails");
    }
  }

  @Override
  public void renderMessage(String message) throws IOException {
    try {
      this.ap.append(message);
    } catch (IOException e) {
      throw new IOException("transmission of the board to the provided data destination fails");
    }
  }
}
