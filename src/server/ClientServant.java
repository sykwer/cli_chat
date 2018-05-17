import java.io.IOException;
import java.net.Socket;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;

class ClientServant extends Thread {

  private Socket clientSocket;
  private String username;
  private ChatServer server = ChatServer.getApp();

  public ClientServant(Socket socket) {
    this.clientSocket = socket;
  }

  public void start() {
    try {
      InputStream is = this.clientSocket.getInputStream();
      BufferedReader reader = new BufferedReader(new InputStreamReader(is));

      String line;
      while (!this.clientSocket.isClosed() && (line = reader.readLine()) != null) {
        String[] msg = line.split(" ", 2);
        handleCommand(msg[0], msg[1]);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void handleCommand(String cmd, String value) {
    System.out.println("cmd : "+cmd);
    System.out.println("value : "+value);
  }

}
