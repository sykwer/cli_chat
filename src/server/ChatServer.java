public class ChatServer {

  private static ChatServer app;

  public static ChatServer getApp() {
    if (this.app == null) {
      this.app = new ChatServer();
      return this.app;
    }
    return this.app;
  }

  private void ChatServer();

  public void start() {

  }

  public static void main(String[] args) {
    ChatServer.getApp().start();
  }
}
