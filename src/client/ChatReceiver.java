import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

class ChatReceiver {
    private static ChatReceiver singleton = new ChatReceiver();

    private ChatReceiver() {
    }

    static ChatReceiver getReceiver() {
        return singleton;
    }

    void waiteForMessage(Socket socket) {
        try {
            InputStream input = socket.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(input);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            while (!socket.isClosed()) {
                String line = bufferedReader.readLine();
                if (line != null) {
                    System.out.println(Colors.GREEN + line + Colors.DEFAULT);
                }
            }
        } catch (IOException e) {
            System.out.println(Colors.RED + "メッセージの受け取りに失敗しました。" + Colors.DEFAULT);
            e.printStackTrace();
        }
    }
}
