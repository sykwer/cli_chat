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

    private enum Command {
        LOGIN,
        LIST,
        SEND,
        LOGOUT,
        HELP,
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
                case LOGIN:
                    if (socket != null && socket.isConnected()) {
                        System.out.println("すでにログインしています。");
                    } else {
                        String[] destination = args[1].split(":", 2);
                        login(destination[0], Integer.parseInt(destination[1]), args[2]);
                    }
                    break;

                case LIST:
                	    sender.sendMessage(socket, "list");
                	    break;

                case SEND:
                    sendChat(args[1]);
                    break;

                case LOGOUT:
                    logout();
                    close();
                    break;

                case HELP:
            	        help();
            	        break;

                case EXIT:
                    break WHILE;

                default:
                    System.out.println("そのコマンドはありません。(→'help')");
                    break;
            }

        }
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
            System.out.println("\u001b[31m"+"ログインに失敗しました。"+"\u001b[30m");
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

    private void help() {
        System.out.println("使用できるコマンド:");
	    System.out.println("\tlogin - ログインする。(login IPアドレス:ポート username)");
	    System.out.println("\tlist - ログインしている人を表示する。");
	    System.out.println("\tsend - メッセージを送る。(send [-to username] メッセージ)");
	    System.out.println("\tlogout - ログアウトする。");
	    System.out.println("\texit - プログラムを終了する。");
    }
}
