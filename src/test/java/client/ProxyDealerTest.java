package client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import cs3500.lab06.client.ProxyDealer;
import cs3500.lab06.client.RandomPlayerController;
import cs3500.lab06.json.GuessJson;
import cs3500.lab06.json.HintJson;
import cs3500.lab06.json.JsonUtils;
import cs3500.lab06.json.MessageJson;
import cs3500.lab06.json.WinJson;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test correct responses for different requests from the socket using a Mock Socket (mocket)
 */
public class ProxyDealerTest {

  private ByteArrayOutputStream testLog;
  private ProxyDealer dealer;


  /**
   * Reset the test log before each test is run.
   */
  @BeforeEach
  public void setup() {
    this.testLog = new ByteArrayOutputStream(2048);
    assertEquals("", logToString());
  }

  /**
   * Check that the server returns a guess when given a hint.
   */
  @Test
  public void testVoidForWin() {
    // Prepare sample message
    WinJson winJson = new WinJson(true);
    JsonNode sampleMessage = createSampleMessage("win", winJson);

    // Create the client with all necessary messages
    Mocket socket = new Mocket(this.testLog, List.of(sampleMessage.toString()));

    // Create a Dealer
    try {
      this.dealer = new ProxyDealer(socket, new RandomPlayerController());
    } catch (IOException e) {
      fail(); // fail if the dealer can't be created
    }

    // run the dealer and verify the response
    this.dealer.run();

    String expected = "\"void\"\n";
    assertEquals(expected, logToString());
  }

  /**
   * Check that the server returns a guess when given a hint.
   */
  @Test
  public void testGuessForHint() {
    // Create sample hint
    HintJson hintJson = new HintJson(HintJson.HIGHER);
    JsonNode jsonNode = createSampleMessage("hint", hintJson);

    // Create socket with sample input
    Mocket socket = new Mocket(this.testLog, List.of(jsonNode.toString()));

    // Create a dealer
    try {
      this.dealer = new ProxyDealer(socket, new RandomPlayerController());
    } catch (IOException e) {
      fail(); // fail if the dealer can't be created
    }

    // Run dealer and verify response.
    this.dealer.run();
    responseToClass(GuessJson.class);
  }

  /**
   * Converts the ByteArrayOutputStream log to a string in UTF_8 format
   * @return String representing the current log buffer
   */
  private String logToString() {
    return testLog.toString(StandardCharsets.UTF_8);
  }

  /**
   * Try converting the current test log to a string of a certain class.
   *
   * @param classRef Type to try converting the current test stream to.
   * @param <T>      Type to try converting the current test stream to.
   */
  private <T> void responseToClass(@SuppressWarnings("SameParameterValue") Class<T> classRef) {
    try {
      JsonParser jsonParser = new ObjectMapper().createParser(logToString());
      jsonParser.readValueAs(classRef);
      // No error thrown when parsing to a GuessJson, test passes!
    } catch (IOException e) {
      // Could not read
      // -> exception thrown
      // -> test fails since it must have been the wrong type of response.
      fail();
    }
  }

  /**
   * Create a MessageJson for some name and arguments.
   * @param messageName name of the type of message; "hint" or "win"
   * @param messageObject object to embed in a message json
   * @return a MessageJson for the object
   */
  private JsonNode createSampleMessage(String messageName, Record messageObject) {
    MessageJson messageJson = new MessageJson(messageName, JsonUtils.serializeRecord(messageObject));
    return JsonUtils.serializeRecord(messageJson);
  }
}
