package cs3500.lab06.json;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * JSON format of this record:
 * <p>
 * <code>
 * {
 *   "hint": "lower/higher"
 * }
 * </code>
 * </p>
 *
 * @param hint if the player should guess higher or lower
 */
public record HintJson(
    @JsonProperty("hint") String hint) {

  /**
   * Determines if the hint string is not empty.
   *
   * @return if the hint string is not empty
   */
  public boolean hasHint() {
    return !this.hint.isEmpty();
  }

  /**
   * Determines if the string suggests to guess lower.
   *
   * @return if the hint is equal to "lower"
   */
  public boolean shouldGuessLower() {
    return this.hint.equals("lower");
  }
}