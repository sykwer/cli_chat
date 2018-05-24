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
    void waiteForMessage(Socket socket, String message){
        InputStream input = socket.getInputStream();
        InputStreamReader inputStreamReader = new InputStreamReader(input);
        BufferedReader  bufferedReader = new BufferedReader(inputStreamReader);
        while(!socket.isClosed()){
            String line = bufferedReader.readLine();
            System.out.println(line);
        }
        


        /*
        * やりたいこと
        * 1. socketを用いて、入力を受けとる
        * 2. 入力を受け取ったら、それを標準出力で表示する
        * */
    }
}
