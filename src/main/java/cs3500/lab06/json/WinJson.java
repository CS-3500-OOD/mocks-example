package cs3500.lab06.json;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * JSON format of this record:
 * <p>
 * <code>
 * {
 * "win": True
 * }
 * </code>
 * </p>
 *
 */
public record WinJson(
    @JsonProperty("win") boolean win) {

  /**
   * @return true if they won
   */
  public boolean isWinner() {
    return win;
  }
}
