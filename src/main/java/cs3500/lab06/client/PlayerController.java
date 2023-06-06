package cs3500.lab06.client;

/**
 * This interface defines the functionality of a Player.
 *
 * <p>
 * A player must be able to provide a guess (given a hint or not)
 * and accept winning or losing.
 * </p>
 */
public interface PlayerController {

  /**
   * The player must make a guess from 0 to 100 and is provided no hints.
   *
   * @return the player's guess
   */
  int guess();

  /**
   * The player must make a guess from 0 to 100. The player is also given a hint as to if their
   * last guess was too high.
   *
   * @param lastGuessWasTooHigh true if the previous guess was too high
   * @return the player's guess
   */
  int guess(boolean lastGuessWasTooHigh);

  /**
   * The player is told whether it won the game or lost the game.
   *
   * @param isWinner if the player won
   */
  void win(boolean isWinner);

}
