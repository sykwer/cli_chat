class ChatClient {
    public static void main(String[] args) {
        ChatClient client = new ChatClient();
        client.waitForCommands();
    }
    
    private enum Command {
        CHATLOGIN,
        SEND,
        LOGOUT,
        EXIT
    }

    private ChatClient() {
    }

    private void waitForCommands() {
    }

    private void connectServer() {
    }

    private void close() {
    }
}
