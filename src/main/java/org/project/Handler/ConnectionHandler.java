package org.project.Handler;

import org.java_websocket.WebSocket;

public interface ConnectionHandler {
    void onConnect(WebSocket socket);
    void onMessage(WebSocket socket, String message);
    void onClose(WebSocket socket);
}
