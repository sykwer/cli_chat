import java.io.IOException;
import java.io.PrintWriter;
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
    		PrintWriter writer = new PrintWriter(socket.getOutputStream());
    		System.out.println("送信メッセージ: " + message);
    		writer.println(message);
    		writer.close();
    }
}
