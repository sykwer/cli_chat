import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

class ChatClient {
    public static void main(String[] args) {
        ChatClient client = new ChatClient();
        client.waitForCommands();
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

    private void waitForCommands() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String[] args = scanner.nextLine().trim().split("\\s");

            // todo
            try {
                connectServer(args[2].split(":")[0], Integer.parseInt(args[2].split(":")[1]));
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                sender.sendMessage(socket, args[4]);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void connectServer(String host, int port) throws IOException {
        socket = new Socket(host, port);
    }

    private void close() {
    }
}
