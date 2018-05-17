import java.io.IOException;
import java.net.Socket;

class ChatSender {
    private static ChatSender singleton = new ChatSender();

    private ChatSender() {
    }

    static ChatSender getSender() {
        return singleton;
    }

    // TODO: 2018/05/13 実装
    void sendMessage(Socket socket, String message) throws IOException {
    }
}
