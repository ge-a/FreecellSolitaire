package cs3500.freecell.model.hw02;

import cs3500.freecell.model.FreecellModel;
import cs3500.freecell.model.PileType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Represents the model of the freecell game where only one card move at a time is allowed.
 * This model uses a {@code ICard } representation only allowing card moves.
 */
public class SimpleFreecellModel implements FreecellModel<ICard> {

  private ArrayList<ArrayList<ICard>> cascadePile;
  private ArrayList<ArrayList<ICard>> foundationPile;
  private ArrayList<ArrayList<ICard>> openPile;
  private boolean gameStarted;
  private final boolean allowMultipleMoves;

  /**
   * Constructs a SimpleFreecellModel object.
   */
  public SimpleFreecellModel() {
    this.gameStarted = false;
    this.allowMultipleMoves = false;
  }

  /**
   * Constructs a SimpleFreeccellModel object.
   *
   * @param allowMultipleMoves whether or not multiple cascade pile cards
   *                           should be allowed to move at once
   */
  public SimpleFreecellModel(boolean allowMultipleMoves) {
    this.gameStarted = false;
    this.allowMultipleMoves = allowMultipleMoves;
  }


  @Override
  public List<ICard> getDeck() {
    List<ICard> deck = new ArrayList<>();
    for (CardSuit suit : CardSuit.values()) {
      for (CardValue val : CardValue.values()) {
        deck.add(new Card(val, suit));
      }
    }
    return deck;
  }

  @Override
  public void startGame(List<ICard> deck, int numCascadePiles, int numOpenPiles, boolean shuffle)
      throws IllegalArgumentException {
    if (!validDeck(deck)) {
      throw new IllegalArgumentException("this deck is invalid");
    }
    gameStarted = true;
    if (shuffle) {
      Collections.shuffle(deck);
    }
    resetGame();
    initPiles(numOpenPiles, numCascadePiles, deck);
  }

  /**
   * Checks if a deck is valid meaning if it has 52 cards, has no duplicate cards, and has no
   * invalid cards.
   *
   * @param deck the list of cards in the startGame function
   * @return whether or not the deck of cards is valid
   */
  private boolean validDeck(List<ICard> deck) {
    return fullDeck(deck) && validCards(deck) && duplicateCards(deck);
  }

  /**
   * Checks to see if a deck has 52 cards.
   *
   * @param deck the deck of cards being checked
   * @return whether or not a deck has 52 cards
   */
  private boolean fullDeck(List<ICard> deck) {
    return deck.size() == 52;
  }

  /**
   * Checks to see if a deck has no cards with an invalid suit or value.
   *
   * @param deck the deck of cards being checked
   * @return whether or not all the cards in the deck are valid
   */
  private boolean validCards(List<ICard> deck) {
    int validCounter = 0;
    List<CardValue> valueList = Arrays.asList(CardValue.values());
    List<CardSuit> suiteList = Arrays.asList(CardSuit.values());
    for (int i = 0; i < 52; i++) {
      if (valueList.contains(deck.get(i).getValue()) && suiteList.contains(deck.get(i).getSuit())) {
        validCounter++;
      }
    }
    return validCounter == 52;
  }

  /**
   * Checks to see if a deck does not have the same card twice.
   *
   * @param deck the deck of cards being checked
   * @return whether or not the deck has any of the same card twice
   */
  private boolean duplicateCards(List<ICard> deck) {
    for (int i = 0; i < 52; i++) {
      for (int j = i + 1; j < 52; j++) {
        if (deck.get(i).getSuit().equals(deck.get(j).getSuit())
            && deck.get(i).getValue().equals(deck.get(j).getValue())) {
          return false;
        }
      }
    }
    return true;
  }

  /** Resets all the different piles of cards back to being empty. */
  private void resetGame() {
    cascadePile = new ArrayList<>();
    foundationPile = new ArrayList<>();
    openPile = new ArrayList<>();
  }

  /**
   * Initializes all the different piles to have the correct number of piles and deals the cards
   * into the cascade pile.
   *
   * @param numOpenPiles the number of open piles
   * @param numCascadePiles the number of cascade piles
   * @param deck the deck of cards
   */
  private void initPiles(int numOpenPiles, int numCascadePiles, List<ICard> deck) {
    initOpenPile(numOpenPiles);
    initFoundationPile();
    initCascadePile(numCascadePiles, deck);
  }

  /**
   * Initializes the open pile of cards to have the correct number of piles.
   *
   * @param numOpenPiles the number of open piles of cards
   * @throws IllegalArgumentException if the number of open piles is less than 1
   */
  private void initOpenPile(int numOpenPiles) throws IllegalArgumentException {
    if (numOpenPiles >= 1) {
      for (int j = 0; j < numOpenPiles; j++) {
        openPile.add(new ArrayList<>());
      }
    } else {
      throw new IllegalArgumentException("Impossible number of open piles");
    }
  }

  /** Initializes there to be 4 foundation piles. */
  private void initFoundationPile() {
    for (int i = 0; i < 4; i++) {
      foundationPile.add(new ArrayList<>());
    }
  }

  /**
   * Initializes the cascade pile to have the inputted number of piles and deals the deck into the
   * cascade piles.
   *
   * @param numCascadePiles the number of cascade piles
   * @param deck the deck to be dealt
   * @throws IllegalArgumentException if there are less than 4 piles
   */
  private void initCascadePile(int numCascadePiles, List<ICard> deck)
      throws IllegalArgumentException {
    if (numCascadePiles >= 4) {
      for (int i = 0; i < numCascadePiles; i++) {
        cascadePile.add(new ArrayList<>());
        int cardIdx = i;
        for (int j = 0; j < 52 / numCascadePiles + 1; j++) {
          if (cardIdx < 52) {
            cascadePile.get(i).add(deck.get(cardIdx));
            cardIdx += numCascadePiles;
          }
        }
      }
    } else {
      throw new IllegalArgumentException("impossible number of cascade piles");
    }
  }

  @Override
  public void move(
      PileType source, int pileNumber, int cardIndex, PileType destination, int destPileNumber)
      throws IllegalArgumentException, IllegalStateException {
    hasGameStarted();
    IPile<ICard> sourcePile = getPile(source);
    IPile<ICard> destPile = getPile(destination);
    if ((destPile.emptyPile(destPileNumber, sourcePile.getCardAtIndex(pileNumber, cardIndex))
            || destPile.possibleMove(
                destPileNumber,
                sourcePile.getCardAtIndex(pileNumber, cardIndex),
                destPile.getLastCard(destPileNumber)))
        && !source.equals(PileType.FOUNDATION)) {
      moveCards(source, pileNumber, cardIndex, destination, destPileNumber);
    } else {
      throw new IllegalArgumentException("this move is not possible");
    }
  }

  /**
   * Throws an illegal argument exception if the game has not yet started.
   *
   * @throws IllegalArgumentException if the game has not started
   */
  private void hasGameStarted() throws IllegalStateException {
    if (!gameStarted) {
      throw new IllegalStateException("game has not started");
    }
  }

  /**
   * Gets the correct pile from the lists of cascade, foundation, and open piles.
   *
   * @param source the type of pile to be found
   * @return the pile matching the source and pile number from the lists of cascade, foundation, and
   *     open piles
   * @throws IllegalArgumentException if there is an invalid pile type
   */
  private IPile<ICard> getPile(PileType source) throws IllegalArgumentException {
    switch (source) {
      case CASCADE:
        return new CascadePile(this.cascadePile, this.allowMultipleMoves);
      case FOUNDATION:
        return new FoundationPile(this.foundationPile);
      case OPEN:
        return new OpenPile(this.openPile);
      default:
        throw new IllegalArgumentException("cannot have any other pile type");
    }
  }

  /**
   * Moves a card from one pile to another.
   *
   * @param source the type of pile to have the card moved from
   * @param pileNumber the index of the pile to have the card moved from in the list of piles
   * @param cardIndex the index of the card to be moved in the pile
   * @param destination the type of pile to have to card moved to
   * @param destPileNumber the index of the pile to have the card moved to in the list of piles
   * @throws IllegalArgumentException if there is an invalid pile type
   */
  private void moveCards(
      PileType source, int pileNumber, int cardIndex, PileType destination, int destPileNumber)
      throws IllegalArgumentException {
    switch (destination) {
      case CASCADE:
        this.cascadePile.get(destPileNumber).add(removeCard(source, pileNumber, cardIndex));
        break;
      case FOUNDATION:
        this.foundationPile.get(destPileNumber).add(removeCard(source, pileNumber, cardIndex));
        break;
      case OPEN:
        this.openPile.get(destPileNumber).add(removeCard(source, pileNumber, cardIndex));
        break;
      default:
        throw new IllegalArgumentException("cannot have any other pile type");
    }
  }

  /**
   * Removes a card from a pile and returns the card it removes.
   *
   * @param source the type of pile to be removed from
   * @param pileNumber the index of the pile in the list of piles
   * @param cardIndex the index of the card to be removed in the pile
   * @return the card being removed from the specified pile
   * @throws IllegalArgumentException if there is any invalid pile type
   */
  private ICard removeCard(PileType source, int pileNumber, int cardIndex)
      throws IllegalArgumentException {
    ICard removedCard;
    switch (source) {
      case CASCADE:
        removedCard = this.cascadePile.get(pileNumber).get(cardIndex);
        this.cascadePile.get(pileNumber).remove(getCascadeCardAt(pileNumber, cardIndex));
        return removedCard;
      case FOUNDATION:
        removedCard = this.foundationPile.get(pileNumber).get(cardIndex);
        this.foundationPile.get(pileNumber).remove(getFoundationCardAt(pileNumber, cardIndex));
        return removedCard;
      case OPEN:
        removedCard = this.openPile.get(pileNumber).get(cardIndex);
        this.openPile.get(pileNumber).remove(getOpenCardAt(pileNumber));
        return removedCard;
      default:
        throw new IllegalArgumentException("cannot have any other pile type");
    }
  }

  @Override
  public boolean isGameOver() {
    int fullCount = 0;
    if (!gameStarted) {
      return false;
    }
    for (int i = 0; i < 4; i++) {
      if (this.foundationPile.get(i).size() == 13) {
        fullCount++;
      }
    }
    return fullCount == 4;
  }

  @Override
  public int getNumCardsInFoundationPile(int index)
      throws IllegalArgumentException, IllegalStateException {
    hasGameStarted();
    if (index < 0 || index >= 4) {
      throw new IllegalArgumentException("this index is invalid");
    }
    return this.foundationPile.get(index).size();
  }

  @Override
  public int getNumCascadePiles() {
    if (!gameStarted) {
      return -1;
    } else {
      return this.cascadePile.size();
    }
  }

  @Override
  public int getNumCardsInCascadePile(int index)
      throws IllegalArgumentException, IllegalStateException {
    hasGameStarted();
    if (index < 0 || index >= getNumCascadePiles()) {
      throw new IllegalArgumentException("this index is invalid");
    }
    return this.cascadePile.get(index).size();
  }

  @Override
  public int getNumCardsInOpenPile(int index)
      throws IllegalArgumentException, IllegalStateException {
    hasGameStarted();
    if (index < 0 || index >= getNumOpenPiles()) {
      throw new IllegalArgumentException("this index is invalid");
    }
    return this.openPile.get(index).size();
  }

  @Override
  public int getNumOpenPiles() {
    if (!gameStarted) {
      return -1;
    }
    return this.openPile.size();
  }

  @Override
  public ICard getFoundationCardAt(int pileIndex, int cardIndex)
      throws IllegalArgumentException, IllegalStateException {
    hasGameStarted();
    if (pileIndex < 0 || pileIndex >= 4) {
      throw new IllegalArgumentException("invalid pile index");
    }
    if (cardIndex < 0 || cardIndex >= getNumCardsInFoundationPile(pileIndex)) {
      throw new IllegalArgumentException("invalid card index");
    }
    return this.foundationPile.get(pileIndex).get(cardIndex);
  }

  @Override
  public ICard getCascadeCardAt(int pileIndex, int cardIndex)
      throws IllegalArgumentException, IllegalStateException {
    hasGameStarted();
    if (pileIndex < 0 || pileIndex >= getNumCascadePiles()) {
      throw new IllegalArgumentException("invalid pile index");
    }
    if (cardIndex < 0 || cardIndex >= getNumCardsInCascadePile(pileIndex)) {
      throw new IllegalArgumentException("invalid card index");
    }
    return this.cascadePile.get(pileIndex).get(cardIndex);
  }

  @Override
  public ICard getOpenCardAt(int pileIndex) throws IllegalArgumentException, IllegalStateException {
    hasGameStarted();
    if (pileIndex < 0 || pileIndex >= getNumOpenPiles()) {
      throw new IllegalArgumentException("invalid pile index");
    }
    if (this.openPile.get(pileIndex).size() == 0) {
      return null;
    }
    return this.openPile.get(pileIndex).get(0);
  }
}
