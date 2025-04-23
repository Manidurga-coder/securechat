package org.project;

import org.java_websocket.WebSocket;


public class MyServerHandler implements ConnectionHandler{

    @Override
    public void onClose(WebSocket socket) {
        System.out.println("Client disconnected: " + socket.getRemoteSocketAddress());
    }

    @Override
    public void onConnect(WebSocket socket) {
        System.out.println("Client connected: " + socket.getRemoteSocketAddress());
    }

    @Override
    public void onMessage(WebSocket socket, String message) {
        // You can also write a response:
        try {
            System.out.println("Server Received: " + message + " from " + socket.getRemoteSocketAddress());
            String[] msg = message.split(ChatUtil.ROOM);
            String[] msgAndUserName = msg[1].split(ChatUtil.USER);
            ChatUtil.broadCastMsgToAll(msg[0].trim(), msgAndUserName[0], msgAndUserName[1], socket.getRemoteSocketAddress().getAddress().getHostAddress());
        } catch (Exception e) {
            System.err.println("Server Error: " + e);
        }
    }
}
