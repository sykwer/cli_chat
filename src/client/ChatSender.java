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
			System.out.println("\u001b[31m"+"メッセージの送信に失敗しました。"+"\u001b[30m");
			e.printStackTrace();
		} finally {
			if (writer != null) {
				writer.flush();
			}
		}
	}
}
