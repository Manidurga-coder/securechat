package org.project.Handler;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.project.util.ChatUtil;

import java.net.URI;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class MyClientHandler extends WebSocketClient {
    public static final List<String> incomingMessages = new CopyOnWriteArrayList<>();

    public MyClientHandler(URI serverUri) {
        super(serverUri);
    }

    @Override
    public void onOpen(ServerHandshake serverHandshake) {
        System.out.println("Client connected :: " + serverHandshake.toString());
    }

    @Override
    public void onMessage(String s) {
        System.out.println("Client received :: " + s);
        String[] msgAndUsername = s.split(ChatUtil.USER);
        String username = msgAndUsername[1];
        String message = msgAndUsername[0];
        incomingMessages.add(username + " : " + message);
    }

    @Override
    public void onClose(int i, String s, boolean b) {
        System.out.println("Client disconnected :: " + s +" :: I ::"+ i +" :: boolean ::"+b);
    }

    @Override
    public void onError(Exception e) {
        System.err.println("Client error :: " + e);
    }
}
