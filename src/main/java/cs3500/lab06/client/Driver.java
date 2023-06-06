package cs3500.lab06.client;


import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * The Driver is responsible for connecting to the server
 * and then running an entire game with a player.
 */
public class Driver {
  /**
   * This method connects to the server at the given host and port, builds a proxy referee
   * to handle communication with the server, and sets up a client player.
   *
   * @param host the server host
   * @param port the server port
   * @throws IOException if there is a communication issue with the server
   */
  private static void runClient(Readable in, String host, int port)
      throws IOException, IllegalStateException {
    Socket server = new Socket(host, port);

    // uncomment line 26 to use RandomPlayerController instead of ManualPlayerController
    // ProxyReferee proxyReferee = new ProxyReferee(server, new RandomPlayerController());
    ProxyDealer proxyDealer = new ProxyDealer(server, new ManualPlayerController(in));
    proxyDealer.run();
  }

  /**
   * The main entrypoint into the code as the Client. Given a host and port as parameters, the
   * client is run. If there is an issue with the client or connecting,
   * an error message will be printed.
   *
   * @param args The expected parameters are the server's host and port
   */
  public static void main(String[] args) {
    Readable in = new InputStreamReader(System.in);

    try {
      if (args.length == 2) {
        runClient(in, args[0], Integer.parseInt(args[1]));
      } else {
        System.out.println("Expected two arguments: `[host] [port]`.");
      }
    } catch (IOException | IllegalStateException e) {
      System.out.println("Unable to connect to the server.");
    } catch (NumberFormatException e) {
      System.out.println("Second argument should be an integer. Format: `[host] [part]`.");
    }
  }
}