import java.io.*;
import java.net.Socket;

class ChatReceiver {
    private static ChatReceiver singleton = new ChatReceiver();

    private ChatReceiver() {
    }

    static ChatReceiver getReceiver() {
        return singleton;
    }

    // TODO: 2018/05/13 実装
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
            e.printStackTrace();
        }
    }
}
