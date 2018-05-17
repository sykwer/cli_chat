import java.util.ArrayList;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatServer {

  private static ChatServer app;

  public static ChatServer getApp() {
    if (app == null) {
      app = new ChatServer();
      return app;
    }
    return app;
  }


  private ServerSocket serverSocket;
  private ArrayList<ClientServant> clientServants;

  private ChatServer() {};

  public void start(int port) {
    try {
      this.serverSocket = new ServerSocket(port);

      while (!this.serverSocket.isClosed()) {
        Socket clientSocket = this.serverSocket.accept();

        ClientServant cs = new ClientServant(clientSocket);
        this.clientServants.add(cs);

        cs.start();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static void main(String[] args) {
    int port = Integer.parseInt(args[0]);
    ChatServer.getApp().start(port);
  }
}
