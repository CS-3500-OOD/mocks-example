package cs3500.lab06.client;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import cs3500.lab06.json.GuessJson;
import cs3500.lab06.json.HintJson;
import cs3500.lab06.json.JsonUtils;
import cs3500.lab06.json.MessageJson;
import cs3500.lab06.json.WinJson;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.Socket;

/**
 * This class uses the Proxy Pattern to talk to the Server and dispatch methods to the Player.
 */
public class ProxyDealer {

  private final Socket server;
  private final InputStream in;
  private final PrintStream out;
  private final PlayerController player;
  private final ObjectMapper mapper = new ObjectMapper();

  private static final JsonNode VOID_RESPONSE =
      new ObjectMapper().getNodeFactory().textNode("void");

  /**
   * Construct an instance of a ProxyPlayer.
   *
   * @param server the socket connection to the server
   * @param player the instance of the player
   * @throws IOException if
   */
  public ProxyDealer(Socket server, PlayerController player) throws IOException {
    this.server = server;
    this.in = server.getInputStream();
    this.out = new PrintStream(server.getOutputStream());
    this.player = player;
  }


  /**
   * Listens for messages from the server as JSON in the format of a MessageJSON. When a complete
   * message is sent by the server, the message is parsed and then delegated to the corresponding
   * helper method for each message. This method stops when the connection to the server is closed
   * or an IOException is thrown from parsing malformed JSON.
   */
  public void run() {
    try {
      JsonParser parser = this.mapper.getFactory().createParser(this.in);

      while (!this.server.isClosed()) {
        MessageJson message = parser.readValueAs(MessageJson.class);
        delegateMessage(message);
      }
    } catch (IOException e) {
      // Disconnected from server or parsing exception
    }
  }


  /**
   * Determines the type of request the server has sent ("guess" or "win") and delegates to the
   * corresponding helper method with the message arguments.
   *
   * @param message the MessageJSON used to determine what the server has sent
   */
  private void delegateMessage(MessageJson message) {
    String name = message.messageName();
    JsonNode arguments = message.arguments();

    if ("hint".equals(name)) {
      handleHint(arguments);
    } else if ("win".equals(name)) {
      handleWin(arguments);
    } else {
      throw new IllegalStateException("Invalid message name");
    }
  }


  /**
   * Parses the given message arguments as a GuessJSON type, asks the player to take a guess given
   * the hint from the server, and then serializes the player's new guess to Json and sends the
   * response to the server.
   *
   * @param arguments the Json representation of a GuessJSON
   */
  private void handleHint(JsonNode arguments) {
    HintJson guessArgs = this.mapper.convertValue(arguments, HintJson.class);

    int guess = getPlayerGuess(guessArgs);

    GuessJson response = new GuessJson(guess);
    JsonNode jsonResponse = JsonUtils.serializeRecord(response);
    this.out.println(jsonResponse);
  }

  /**
   * Given a GuessJSON, gets a guess from the player given the hint provided by the server. If a
   * hint is not provided, then ask the player to guess with no hint.
   *
   * @param guessArgs the guess hint
   * @return the player's next guess
   */
  private int getPlayerGuess(HintJson guessArgs) {
    if (guessArgs.hasHint()) {
      return this.player.guess(guessArgs.shouldGuessLower());
    }
    return this.player.guess();
  }


  /**
   * Parses the given arguments as a WinJSON, notifies the player whether they won, and provides a
   * void response to the server.
   *
   * @param arguments the Json representation of a WinJSON
   */
  private void handleWin(JsonNode arguments) {
    WinJson winJson = this.mapper.convertValue(arguments, WinJson.class);

    this.player.win(winJson.isWin());

    this.out.println(VOID_RESPONSE);
  }

}
