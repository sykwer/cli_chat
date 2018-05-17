import java.io.IOException;
import java.net.Socket;

class ChatReceiver {
    private static ChatReceiver singleton = new ChatReceiver();

    private ChatReceiver() {
    }

    static ChatReceiver getReceiver() {
        return singleton;
    }

    // TODO: 2018/05/13 実装
    void waiteForMessage(Socket socket) throws IOException {
    }
}
