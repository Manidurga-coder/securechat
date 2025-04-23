package org.project;

import org.java_websocket.server.WebSocketServer;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.project.Handler.ConnectionHandler;
import org.project.util.ChatUtil;

import java.net.InetSocketAddress;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class MyServerSocket {

    private static boolean isStarted = false;

    private static Set<WebSocket> clients = Collections.synchronizedSet(new HashSet<>());

    public static void start(ConnectionHandler handler)
    {
        try{
            WebSocketServer serverSocket = new WebSocketServer(new InetSocketAddress(12335)) {
                @Override
                public void onOpen(WebSocket conn, ClientHandshake clientHandshake) {
                    clients.add(conn);
                    handler.onConnect(conn);
                }

                @Override
                public void onClose(WebSocket conn, int i, String s, boolean b) {
                    clients.remove(conn);
                    handler.onClose(conn);
                }

                @Override
                public void onMessage(WebSocket conn, String message) {
                    ChatUtil.addClientConnection(message.split(ChatUtil.ROOM)[0].trim(), conn);
                    handler.onMessage(conn, message);
                }

                @Override
                public void onError(WebSocket webSocket, Exception e) {
                    System.err.println("WebSocket error: " + e.getMessage());
                }

                @Override
                public void onStart() {
                    System.out.println("Server:: On Start Triggered");
                }
            };
            System.out.println("Server started on port 12335");

//            while (true) {
//                isStarted = true;
//                Socket socket = serverSocket.accept();
//                handler.onConnect(socket);
//
//                new Thread(() -> handleClient(socket, handler)).start();
//            }
            serverSocket.start();
            isStarted = true;
            System.out.println("WebSocket server started on port 12335");
        }
        catch (Exception e) {
            System.err.println("WebSocket error: " + e.getMessage());
        }
    }

//    private static void handleClient(WebSocket socket, ConnectionHandler handler) {
//        BufferedReader in = null;
//        try {
//            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//            String line;
//            while ((line = in.readLine()) != null) {
//                handler.onMessage(socket, line);
//            }
//        } catch (IOException e) {
//            System.err.println("Error occurred in handleClient: " + e);
//        } finally {
//            try {
//                if (in != null) in.close(); // Only close the reader
//                if (!socket.isClosed()) socket.close(); // Explicitly close the socket
//            } catch (IOException ex) {
//                System.err.println("Error closing socket: " + ex.getMessage());
//            }
//            handler.onClose(socket);
//        }
//    }

    public static boolean isServerSocketStarted() {
        return isStarted;
    }
}
