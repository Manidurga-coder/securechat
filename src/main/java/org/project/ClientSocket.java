package org.project;

import org.java_websocket.client.WebSocketClient;
import org.project.Handler.MyClientHandler;

import java.net.URI;

public class ClientSocket {
    WebSocketClient webSocketClient = null;
    public void connectToServer() {
        System.out.println("Connecting to server...");
        try {
            webSocketClient = new MyClientHandler(new URI("ws://localhost:12335"));
            System.out.println("Connected to serverrrrrrrrrrrrrrrrrrrrr");

            webSocketClient.connectBlocking();

        } catch (Exception e) {
            throw new RuntimeException("Failed to connect to server", e);
        }
    }


    public void sendMessage(String message) {
        try {
            webSocketClient.send(message);
        } catch (Exception e) {
            System.err.println("Failed to send message: " + e.getMessage());
        }
    }
}
