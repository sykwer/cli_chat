import java.util.ArrayList;
import java.io.IOException;
import java.net.Socket;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.OutputStream;
import java.io.PrintWriter;

class ClientServant extends Thread {

    private Socket clientSocket;
    private Boolean isLoggedIn;
    private String username;
    private ChatServer server = ChatServer.getApp();

    public ClientServant(Socket socket) {
        this.clientSocket = socket;
    }

    public void run() {
        try {
            InputStream is = this.clientSocket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));

            String line;
            while (!this.clientSocket.isClosed() && (line = reader.readLine()) != null) {
                String[] cmd = line.split(" ");
                handleCommand(cmd);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleCommand(String[] cmd) {
        switch (cmd[0]) {
            case "login":
                login(cmd[1]);
                break;
            case "logout":
                logout();
                break;
            case "send":
                String msg = cmd[cmd.length - 1];
                Integer repeatsNum = null;
                String destUsername = null;

                for (int i = 1; i < cmd.length; i++) {
                    if (cmd[i] == "-repeat") {
                        repeatsNum = Integer.parseInt(cmd[i+1]);
                    }

                    if (cmd[i] == "-to") {
                        destUsername = cmd[i+1];
                    }
                }

                if (repeatsNum != null) {
                    broadcastRepeatedMsg(msg, repeatsNum);
                } else if (destUsername != null) {
                    directMsg(msg, destUsername);
                } else {
                    broadcastMsg(msg);
                }

                break;
            case "list":
                listClients();
                break;
            default:

        }
    }

    private void login(String username) {
        sendStringToAllClients(String.format("%s has entered this chat room.", username));
        isLoggedIn = true;
        this.username = username;
    }

    private void logout() {
        try {
            sendStringToAllClients(String.format("%s left.", username));
            server.removeClient(this);
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void broadcastMsg(String msg) {
        sendStringToAllClients(String.format("%s: %s", username, msg));
    }

    private void broadcastRepeatedMsg(String msg, int times) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < times; i++) {
            sb.append(msg + "\n");
        }

        sendStringToAllClients(sb.toString());
    }

    private void directMsg(String msg, String username) {
        ClientServant dest = null;

        for (ClientServant cs : server.getClientServants()) {
            if (cs.getUsername() == username) {
                dest = cs;
            }
        }

        if (dest == null) {
            sendString(username + "is not logged in");
        }

        dest.sendString(String.format("(DM)%s -> %s: %s", getUsername(), dest.getUsername(), msg));
    }

    private void listClients() {
        StringBuilder sb = new StringBuilder();
        sb.append("List of clients:\n");
        ArrayList<ClientServant> clients = server.getClientServants();
        for (ClientServant client : clients) {
            sb.append(String.format("\t%s\n", client.getUsername()));
        }
        this.sendString(sb.toString());
    }

    private void sendStringToAllClients(String str) {
        ArrayList<ClientServant> clients = server.getClientServants();
        for (ClientServant client : clients) {
            client.sendString(str);
        }
    }

    private void sendString(String str) {
        try {
            OutputStream os = clientSocket.getOutputStream();
            PrintWriter writer = new PrintWriter(os);
            writer.println(str);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getUsername() {
        return username;
    }

}
