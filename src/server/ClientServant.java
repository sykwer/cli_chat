import java.net.Socket;

class ClientServant extends Thread {

  private Socket clientSocket;
  private String username;
  private ChatServer server = ChatServer.getApp();

  public ClientServant(Socket socket) {
    this.clientSocket = socket;
  }

}
