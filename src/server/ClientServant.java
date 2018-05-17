import java.util.ArrayList;
import java.io.IOException;
import java.net.Socket;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.OutputStream;
import java.io.PrintWriter;

class ClientServant extends Thread {

  private Socket clientSocket;
  private Boolean login;
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
    //System.out.println("cmd : "+cmd);
    //System.out.println("value : "+value);
    switch (cmd) {
      case "login":

        break;
      case "logout":

        break;
      case "send":

        break;
      default:

    }
  }

  private void login(String username) {

  }

  private void logout() {

  }

  private void broadcastMsg(String username, String msg) {

  }

  private void sendStringToAllMembers(String str) {
    ArrayList<Socket> sockets = server.getAllSockets();
    for (Socket s: sockets) {
      sendString(s, str);
    }
  }

  private void sendString(Socket socket, String str) {
    try {
      OutputStream os = socket.getOutputStream();
      PrintWriter writer = new PrintWriter(os);
      writer.println(str);
      writer.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public Socket getSocket() {
    return clientSocket;
  }

}
