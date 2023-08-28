package web.socket.test2;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;

public class WebSocketClientExample extends WebSocketClient {

    public WebSocketClientExample(URI serverUri) {
        super(serverUri);
    }

    @Override
    public void onOpen(ServerHandshake handshake) {
        System.out.println("Connected to server");
        // Send a test message to the server
        send("connect");
    }

    @Override
    public void onMessage(String message) {
        System.out.println("Received message from server: " + message);
        // Send a test message to the server
        send("Hello from client");
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        System.out.println("Connection closed");
    }

    @Override
    public void onError(Exception ex) {
        System.out.println("Error occurred: " + ex.getMessage());
    }

    public static void main(String[] args) {
        try {
            URI serverUri = new URI("ws://localhost:5000/ws"); // Update with your server address
            WebSocketClientExample client = new WebSocketClientExample(serverUri);
            client.connect();
        } catch (URISyntaxException e) {
            System.out.println("Invalid server URI: " + e.getMessage());
        }
    }
}
