package cs3500.lab06.client;

import java.util.Scanner;

/**
 * Represents a Controller that asks a player for input on which moves to make.
 */
public class ManualPlayerController implements PlayerController {
  Scanner sc;
  int lastGuess = -1;

  /**
   * @param in Readable for how to read the input
   */
  public ManualPlayerController(Readable in) {
    this.sc = new Scanner(in);
    System.out.println("Welcome to Guess the Number!");
    System.out.print("What number do you think is right? Enter your guess: ");
  }

  /**
   * The player must make a guess from 0 to 100 and is provided no hints.
   *
   * @return the player's guess
   */
  @Override
  public int guess() {
    if (sc.hasNextInt()) {
      lastGuess = sc.nextInt();
    }
    return lastGuess;
  }

  /**
   * The player must make a guess from 0 to 100. The player is also given a hint as to if their
   * last guess was too high.
   *
   * @param lastGuessWasTooHigh true if the previous guess was too high
   * @return the player's guess
   */
  @Override
  public int guess(boolean lastGuessWasTooHigh) {
    String hint = lastGuessWasTooHigh ? "high" : "low";
    System.out.print(lastGuess + " is a bit too " + hint + ". Guess again: ");

    if (sc.hasNextInt()) {
      lastGuess = sc.nextInt();
    }
    return lastGuess;
  }

  /**
   * The player is told whether it won the game or lost the game.
   *
   * @param isWinner if the player won
   */
  @Override
  public void win(boolean isWinner) {
    if (isWinner) {
      System.out.println("Congratulations! You guessed the number: " + lastGuess + "!");
      return;
    }
    System.out.println("Oh no! You did not guess the right number.");
  }
}
