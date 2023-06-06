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
import java.util.Random;
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
    WinJson winJson = new WinJson(true);
    MessageJson messageJson = new MessageJson("win", JsonUtils.serializeRecord(winJson));
    JsonNode jsonNode = JsonUtils.serializeRecord(messageJson);

    Mocket socket = new Mocket(this.testLog, List.of(jsonNode.toString()));

    try {
      this.dealer = new ProxyDealer(socket, new RandomPlayerController());
    } catch (IOException e) {
      fail();
    }

    this.dealer.run();

    assertEquals("\"void\"\n", logToString());
  }

  /**
   * Check that the server returns a guess when given a hint.
   */
  @Test
  public void testGuessForHint() {
    HintJson hintJson = new HintJson("higher");
    MessageJson messageJson = new MessageJson("hint", JsonUtils.serializeRecord(hintJson));
    JsonNode jsonNode = JsonUtils.serializeRecord(messageJson);

    Mocket socket = new Mocket(this.testLog, List.of(jsonNode.toString()));
    int seed = 0;
    Random playerRandom = new Random(seed);
    Random assertRandom = new Random(seed);
    try {

      this.dealer = new ProxyDealer(socket, new RandomPlayerController(playerRandom));
    } catch (IOException e) {
      fail();
    }

    this.dealer.run();

    responseToClass(GuessJson.class);
  }

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
}
