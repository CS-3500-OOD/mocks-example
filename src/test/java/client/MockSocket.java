package client;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import org.mockito.Mock;

/**
 * Simulate behaviors of the Socket class to test ProxyController
 */
public class MockSocket extends Socket {

  StringBuilder log;
  MockInStream inStream;
  ByteArrayOutputStream outStream = new ByteArrayOutputStream(2048);

  /**
   * @param log where to write the received and sent messages to this socket.
   */
  public MockSocket(StringBuilder log) {
    this.log = log;
    this.inStream = new MockInStream(this.log);
  }

  /**
   * Simulate a Socket passing the following input.
   *
   * @param input input to send into the stream
   */
  public void tryInput(String input) {
    this.inStream.insertString(input);
  }

  @Override
  public InputStream getInputStream() throws IOException {
    return this.inStream;
  }

  @Override
  public OutputStream getOutputStream() throws IOException {
    return this.outStream;
  }

  /**
   * Simulates an InputStream such that we can insert any string into the stream for testing.
   */
  private static class MockInStream extends InputStream {
    InputStream inner = new ByteArrayInputStream(new byte[5]);
    StringBuilder log;

    MockInStream(StringBuilder log) {
      this.log = log;
    }

    @Override
    public int read() throws IOException {
      int data = this.inner.read();
      this.log.append(data); // writes as bytes right now
      return data;
    }

    void insertString(String s) {
      System.out.println("inserted" + " " + s);
      this.inner = new ByteArrayInputStream(s.getBytes());
    }
  }

  private static class MockOutStream extends OutputStream {

    OutputStream inner = new ByteArrayOutputStream(2048);
    StringBuilder log;

    @Override
    public void write(int b) throws IOException {
      log.append(b);
      inner.write(b);
    }
  }
}
