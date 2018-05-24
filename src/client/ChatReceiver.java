import java.io.*;
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
            BufferedReader  bufferedReader = new BufferedReader(inputStreamReader);
            while(!socket.isClosed()){
                String line = bufferedReader.readLine();
                if(line != null){
                    System.out.println(line);
                }
            }
        } catch (IOException e) {
            System.out.println("メッセージの受け取りに失敗しました。");
            e.printStackTrace();
        }
    }
}
