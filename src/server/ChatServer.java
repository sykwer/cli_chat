public class ChatServer {

  private static ChatServer app;

  public static ChatServer getApp() {
    if (app == null) {
      app = new ChatServer();
      return app;
    }
    return app;
  }

  private void ChatServer() {};

  public void start() {

  }

  public static void main(String[] args) {
    ChatServer.getApp().start();
  }
}
