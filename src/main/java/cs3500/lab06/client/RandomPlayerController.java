package cs3500.lab06.client;

import java.util.Random;

/**
 * A simple implementation of the PLayer interface that randomly chooses a guess between bounds
 * that are reduced with each hint.
 */
public class RandomPlayerController implements PlayerController {

  private int previousGuess;
  private int lowBound;
  private int highBound;
  private final Random random;

  /**
   * Constructs a random player with a nonsense previous guess and the initial bounds at 0 and 100.
   * @param random Random to use for generating outputs.
   */
  public RandomPlayerController(Random random) {
    this.random = random;
    this.previousGuess = -1;
    this.lowBound = 0;
    this.highBound = 100;
  }

  /**
   * Constructs a random player with a nonsense previous guess and the initial bounds at 0 and 100.
   */
  public RandomPlayerController() {
    this(new Random());
  }

  @Override
  public int guess() {
    int newGuess = guessWithinBounds(this.lowBound, this.highBound);
    this.previousGuess = newGuess;
    return newGuess;
  }

  @Override
  public int guess(boolean shouldGuessLower) {
    if (shouldGuessLower) {
      this.highBound = this.previousGuess;
    } else {
      this.lowBound = this.previousGuess;
    }
    return guess();
  }

  @Override
  public void win(boolean isWinner) {
    if (isWinner) {
      System.out.println("I won!");
    } else {
      System.out.println("I lost!");
    }
  }

  /**
   * Randomly picks an integer between the given bounds.
   *
   * @param low  the low bound (inclusive)
   * @param high the high bound (exclusive)
   * @return the random guess
   */
  private int guessWithinBounds(int low, int high) {
    return this.random.nextInt(low, high);
  }
}
