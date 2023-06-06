package client;

import static org.junit.jupiter.api.Assertions.*;

import com.fasterxml.jackson.databind.JsonNode;
import cs3500.lab06.client.PlayerController;
import cs3500.lab06.client.ProxyDealer;
import cs3500.lab06.client.RandomPlayerController;
import cs3500.lab06.json.HintJson;
import cs3500.lab06.json.JsonUtils;
import cs3500.lab06.json.MessageJson;
import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProxyDealerTest {

  private MockSocket socket;
  private ProxyDealer dealer;
  private StringBuilder log;
  private PlayerController player;

  @BeforeEach
  public void setup() {
    this.log = new StringBuilder();
    this.socket = new MockSocket(this.log);
    this.player = new RandomPlayerController();
    try {
      this.dealer = new ProxyDealer(this.socket, this.player);
    } catch (IOException e) {
      fail();
    }
  }

  @Test
  public void testRun() {
    HintJson hintJson = new HintJson("higher");
    MessageJson messageJson = new MessageJson("hint", JsonUtils.serializeRecord(hintJson));
    JsonNode jsonNode = JsonUtils.serializeRecord(messageJson);

    this.dealer.run();
    this.socket.tryInput(jsonNode.toString());

    hintJson = new HintJson("lower");
    messageJson = new MessageJson("hint", JsonUtils.serializeRecord(hintJson));
    jsonNode = JsonUtils.serializeRecord(messageJson);
    this.socket.tryInput(jsonNode.toString());

    assertEquals(this.log.toString(), "");
    // GOAL: They can assert the log compared to what they expect the socket to be sending and receiving
  }

}