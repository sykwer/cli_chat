import java.net.*;
import java.util.*;
import java.io.*;

public class ChatServer {

    private static ChatServer app;

    public static ChatServer getApp() {
        if (app == null) {
            app = new ChatServer();
            return app;
        }
        return app;
    }


    private ServerSocket serverSocket;
    public ArrayList<ClientServant> clientServants = new ArrayList<ClientServant>();

    private ChatServer() {
    }

    public void start(int port) {
        try {
            this.serverSocket = new ServerSocket(port);

            while (!this.serverSocket.isClosed()) {
                Socket clientSocket = this.serverSocket.accept();

                ClientServant cs = new ClientServant(clientSocket);
                this.clientServants.add(cs);

                cs.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        int port = Integer.parseInt(args[0]);
        System.out.println("My Address: " + InetAddress.getLocalHost().getHostAddress() + ":" + port);
        ChatServer.getApp().start(port);
    }

    public void removeClient(ClientServant cs) {
        clientServants.remove(cs);
    }
}
