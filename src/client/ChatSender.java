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

    void sendMessage(Socket socket, String message) {
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(socket.getOutputStream());
            writer.println(message);
        } catch (IOException e) {
            System.out.println(Colors.RED.getCode() + "メッセージの送信に失敗しました。" + Colors.DEFAULT.getCode());
            e.printStackTrace();
        } finally {
            if (writer != null) {
                writer.flush();
            }
        }
    }
}
