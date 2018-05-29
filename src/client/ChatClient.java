import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

class ChatClient {
    public static void main(String[] args) {
        ChatClient client = new ChatClient();
        client.showHelp();
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
            if (beforeLogin(args[0])) {
                System.out.println("まずはログインしてください。\nlogin IPアドレス:ポート username");
                continue;
            }

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
                    showList();
                    break;

                case "send":
                    if (validateSendChat(args[1])) {
                        sendChat(args[1]);
                    } else {
                        optionHelp();
                    }
                    break;

                case "logout":
                    if (!socket.isClosed()) {
                        logout();
                    }
                    break;

                case "help":
                    showHelp();
                    break;

                case "exit":
                    if (!socket.isClosed()) {
                        logout();
                    }
                    break WHILE;

                case "fire":
                    sendFire();
                    break;

                default:
                    System.out.println("そのコマンドはありません。(→'help')");
                    break;
            }

        }
        scanner.close();
    }

    private boolean beforeLogin(String command) {
        return !command.equals("login") && !command.equals("help") && !command.equals("exit") &&
                (socket == null || socket.isClosed());
    }

    private boolean validateSendChat(String message) {
        for (String s : message.split("\\s")) {
            if (s.startsWith("-")) {
                if (!s.equals("-to") && !s.equals("-repeat")) return false;
            }
        }
        return true;
    }

    private void sendChat(String message) {
        sender.sendMessage(socket, String.format("send %s", message));
    }

    private void sendFire() {
        sender.sendMessage(socket, "fire");
    }

    private void login(String host, int port, String userName) {
        try {
            connectServer(host, port);
            thread = new Thread(() -> receiver.waiteForMessage(socket));
            thread.start();
        } catch (IOException e) {
            System.out.println(Colors.RED + "ログインに失敗しました。" + Colors.DEFAULT);
            e.printStackTrace();
        }

        sender.sendMessage(socket, String.format("login %s", userName));
    }

    private void connectServer(String host, int port) throws IOException {
        socket = new Socket(host, port);
    }

    private void showList() {
        sender.sendMessage(socket, "list");
    }

    private void logout() {
        sender.sendMessage(socket, "logout");
        try {
            // Sleep current thread to close socket after reading logout message from server.
            // This implementation should be changed.
            Thread.sleep(1000);
            socket.close();
        } catch (IOException | InterruptedException e) {
            System.out.println(Colors.RED + "ログアウト後に通信エラーが発生しました。" + Colors.DEFAULT);
            e.printStackTrace();
        }
    }

    private void showHelp() {
        System.out.println("使用できるコマンド:");
        System.out.println("\tlogin - ログインする。(login IPアドレス:ポート username)");
        System.out.println("\tlist - ログインしている人を表示する。");
        System.out.println("\tsend - メッセージを送る。(send [-to username] [-repeat n] メッセージ)");
        System.out.println("\tlogout - ログアウトする。");
        System.out.println("\texit - プログラムを終了する。");
        System.out.println("\tfire - 🔥");
    }

    private void optionHelp() {
        System.out.println("有効なオプションは、 [-to username] または [-repeat n] です。\nもう一度送信してください。");
    }
}
