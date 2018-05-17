import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

class ChatClient {
    public static void main(String[] args) {
        ChatClient client = new ChatClient();
        client.waitForCommands(args);
    }

    private Socket socket;
    private ChatSender sender = ChatSender.getSender();

    private enum Command {
        CHATLOGIN,
        SEND,
        LOGOUT,
        EXIT
    }

    private ChatClient() {
    }

    private void waitForCommands(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // todo
        try {
            connectServer(Integer.parseInt(args[0]));
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            sender.sendMessage(socket, args[1]);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void connectServer(int port) throws IOException {
        socket = new Socket("localhost", port);
    }

    private void close() {
    }
}
