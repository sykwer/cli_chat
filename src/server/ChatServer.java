import java.util.*;
import java.net.*;
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

    // FIXME: Following method inspects broadest ip address that can be seen from host.
    // To certainly find IP address that connects to LAN, you may have to check routing table...
    private static String inspectLanIpAddress() throws SocketException, IOException {
        Long minLong = null;
        String minIpAddress = null;

        Enumeration<NetworkInterface> netItfs = NetworkInterface.getNetworkInterfaces();

        if (netItfs == null) {
            return null;
        }

        while (netItfs.hasMoreElements()) {
            NetworkInterface ni = netItfs.nextElement();

            if (ni.isLoopback() || !ni.isUp() || ni.isPointToPoint() || ni.isVirtual()) {
                continue;
            }

            Enumeration<InetAddress> addrs = ni.getInetAddresses();

            while (addrs.hasMoreElements()) {
                InetAddress addr = addrs.nextElement();

                if (addr instanceof Inet6Address || !addr.isReachable(1000)) {
                    continue;
                }

                long ipLong = ipToLong(addr);
                if (minLong == null || ipLong < minLong) {
                    minLong = ipLong;
                    minIpAddress = addr.getHostAddress();
                }
            }
        }

        return minIpAddress;
    }

    private static long ipToLong(InetAddress ip) {
        byte[] octets = ip.getAddress();
        long result = 0;

        for (byte octet : octets) {
            result <<= 8;
            result |= octet & 0xff;
        }

        return result;
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            throw new IllegalArgumentException("You need to type in port number for argument");
        }

        int port = Integer.parseInt(args[0]);

        try {
            System.out.println("My Address: " + inspectLanIpAddress() + ":" + port);
            ChatServer.getApp().start(port);
        } catch (IOException e) {
            System.out.println("Error: " + e);
        }
    }

    public void removeClient(ClientServant cs) {
        clientServants.remove(cs);
    }

    public ArrayList<ClientServant> getClientServants() {
      return clientServants;
    }
}
