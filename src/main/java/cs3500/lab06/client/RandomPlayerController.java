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

  /**
   * Constructs a random player with a nonsense previous guess and the initial bounds at 0 and 100.
   */
  public RandomPlayerController() {
    this.previousGuess = -1;
    this.lowBound = 0;
    this.highBound = 100;
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
    Random random = new Random();
    return random.nextInt(low, high);
  }
}
