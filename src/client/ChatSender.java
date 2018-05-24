import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

class ChatSender {
	private static ChatSender singleton = new ChatSender();

	private ChatSender() {
	}

	static ChatSender getSender() {
		return singleton;
	}

	void sendMessage(Socket socket, String message) {
		PrintWriter writer = null;
		try {
			writer = new PrintWriter(socket.getOutputStream());
			writer.println(message);
		} catch (IOException e) {
			System.out.println("通信エラーです。");
			e.printStackTrace();
		} finally {
			if (writer != null) {
				writer.flush();
			}
		}
	}
}
