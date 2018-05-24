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
    private Thread thread;

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

        WHILE:
        while (true) {
            String[] args = scanner.nextLine().trim().split("\\s");
            Command command = Command.valueOf(args[0].toUpperCase());
            switch (command) {
                case CHATLOGIN:
                    if (socket != null && socket.isConnected()) {
                        System.out.println("すでにログインしています。");
                    } else {
                        login(args[2].split(":")[0], Integer.parseInt(args[2].split(":")[1]), args[4]);
                    }
                    break;

                case SEND:
                    sendChat(args[1]);
                    break;

                case LOGOUT:
                    logout();
                    close();
                    break;

                case EXIT:
                    break WHILE;
            }

        }
    }

    private void sendChat(String message) {
        sender.sendMessage(socket, String.format("send %s", message));
    }

    private void login(String host, int port, String userName) {
        try {
            connectServer(host, port);
            thread = new Thread(
                    // todo: implement
            );
            thread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

        sender.sendMessage(socket, String.format("login %s", userName));
    }

    private void connectServer(String host, int port) throws IOException {
        socket = new Socket(host, port);
    }

    private void logout() {
        sender.sendMessage(socket, "logout");
    }

    private void close() {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
