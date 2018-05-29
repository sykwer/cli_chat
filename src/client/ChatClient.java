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
    private ChatReceiver receiver = ChatReceiver.getReceiver();
    private Thread thread;

    private ChatClient() {
    }

    private void waitForCommands() {
        Scanner scanner = new Scanner(System.in);

        WHILE:
        while (true) {
            String[] args = scanner.nextLine().trim().split("\\s", 2);
            switch (args[0]) {
                case "login":
                    if (socket != null && !socket.isClosed()) {
                        System.out.println("すでにログインしています。");
                    } else {
                        // nextLine example: "login 10.213.19.210:1111 Ken"
                        String[] s = args[1].split("\\s", 2);
                        String[] destination = s[0].split(":", 2);
                        login(destination[0], Integer.parseInt(destination[1]), s[1]);
                    }
                    break;

                case "list":
                    sender.sendMessage(socket, "list");
                    break;

                case "send":
                    sendChat(args[1]);
                    break;

                case "logout":
                    if (!socket.isClosed()) {
                        logout();
                    }
                    break;

                case "help":
                    help();
                    break;

                case "exit":
                    if (!socket.isClosed()) {
                        logout();
                    }
                    break WHILE;

                case "fire":
                    sender.sendMessage(socket, "fire");
                    break;

                default:
                    System.out.println("そのコマンドはありません。(→'help')");
                    break;
            }

        }
        scanner.close();
    }

    private void sendChat(String message) {
        sender.sendMessage(socket, String.format("send %s", message));
    }

    private void login(String host, int port, String userName) {
        try {
            connectServer(host, port);
            thread = new Thread(() -> receiver.waiteForMessage(socket));
            thread.start();
        } catch (IOException e) {
            System.out.println("\u001b[31m" + "ログインに失敗しました。" + "\u001b[30m");
            e.printStackTrace();
        }

        sender.sendMessage(socket, String.format("login %s", userName));
    }

    private void connectServer(String host, int port) throws IOException {
        socket = new Socket(host, port);
    }

    private void logout() {
        sender.sendMessage(socket, "logout");
        try {
            // Sleep current thread to close socket after reading logout message from server.
            // This implementation should be changed.
            Thread.sleep(1000);
            socket.close();
        } catch (IOException | InterruptedException e) {
            System.out.println("\u001b[31m" + "ログアウト後に通信エラーが発生しました。" + "\u001b[30m");
            e.printStackTrace();
        }
    }

    private void help() {
        System.out.println("使用できるコマンド:");
        System.out.println("\tlogin - ログインする。(login IPアドレス:ポート username)");
        System.out.println("\tlist - ログインしている人を表示する。");
        System.out.println("\tsend - メッセージを送る。(send [-to username] メッセージ)");
        System.out.println("\tlogout - ログアウトする。");
        System.out.println("\texit - プログラムを終了する。");
    }
}
