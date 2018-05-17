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
            Command command = Command.valueOf(args[0]);
            switch (command) {
                case CHATLOGIN:
                    if (socket != null && socket.isConnected()) {
                        System.out.println("すでにログインしています。");
                    } else {
                        // todo
                        try {
                            connectServer(args[2].split(":")[0], Integer.parseInt(args[2].split(":")[1]));
                            thread = new Thread(
                                    // todo: implement
                            );
                            thread.start();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        sender.sendMessage(socket, String.format("login %s", args[4]));
                    }
                    break;

                case SEND:
                    sender.sendMessage(socket, args[1]);
                    break;

                case LOGOUT:
                    sender.sendMessage(socket, "logout");
                    close();
                    break;

                case EXIT:
                    break WHILE;
            }

        }
    }

    private void connectServer(String host, int port) throws IOException {
        socket = new Socket(host, port);
    }

    private void close() {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
