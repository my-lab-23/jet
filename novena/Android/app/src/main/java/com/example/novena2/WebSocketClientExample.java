package com.example.novena2;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.util.Objects;

public class WebSocketClientExample extends WebSocketClient {

    private final String apiKey;
    private final String serial = Aux.INSTANCE.getSerial();

    public WebSocketClientExample(URI serverUri, String apiKey) {
        super(serverUri);
        this.apiKey = apiKey;
    }

    @Override
    public void onOpen(ServerHandshake handshake) {
        System.out.println("Connected to server");
        // Send a test message to the server
        send("Linux client " + serial);
    }

    @Override
    public void onMessage(String message) {
        System.out.println("Received message from server: " + message);
        if (message.contains(" ha scritto!")) {
            // Verifica se message è diverso da serial + " ha scritto!"
            if (!Objects.equals(message, serial + " ha scritto!")) {
                Synchro.update();
            }
        }
        // Send a test message to the server
        // send("Hello from client");
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        System.out.println("Connection closed");
    }

    @Override
    public void onError(Exception ex) {
        System.out.println("Error occurred: " + ex.getMessage());
    }

    @Override
    public void connect() {
        // Before connecting, set the X-Api-Key header with the API key
        addHeader("X-Api-Key", apiKey);
        super.connect();
    }

    public void sendMsg(String msg) {
        try {
            send(msg);
        } catch (Exception e) {
            System.out.println("Error occurred while messaging: " + e.getMessage());
        }
    }
}
