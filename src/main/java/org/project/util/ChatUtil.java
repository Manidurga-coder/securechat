package org.project.util;

import org.java_websocket.WebSocket;
import org.project.Handler.MyServerHandler;
import org.project.MyServerSocket;

import java.util.*;

public class ChatUtil {
    private static Hashtable<String, ArrayList<String>> rooms = new Hashtable<>();//roomid vs users
    private static Hashtable<String, String> userIps = new Hashtable<>(); //user vs ip
    private static Hashtable<String , HashSet<WebSocket>> clientsConn = new Hashtable<>();//roomId vs user's connection

    public static final String USER = ":@user@:";
    public static final String ROOM = ":@room@:";


    public static Hashtable<String, ArrayList<String>> getRooms() {
        return rooms;
    }

    public static ArrayList<String> getUserFromRoomId(String roomId)
    {
        return rooms.get(roomId);
    }

    public static void setUserFromRoomId(String roomId, String userId)
    {
        if (rooms.containsKey(roomId))
        {
            rooms.get(roomId).add(userId);
            System.out.println("Debug: Room " + userId + " has been added to the list ::"+roomId);
        }
        else
        {
            ArrayList<String> list = new ArrayList<>();
            list.add(userId);
            rooms.put(roomId, list);
            System.out.println("Debug: Room " + roomId + " new user added to the list ::"+userId);
        }
    }
    public static boolean delUserFromRoomId(String roomId, String userId)
    {
        if (rooms.containsKey(roomId))
        {
            return rooms.get(roomId).remove(userId);
        }
        else
        {
            System.out.println("Room not found");
            return false;
        }
    }

    public static ArrayList<String> delRoom(String roomId)
    {
        return rooms.remove(roomId);
    }

    public static void addIpofUser(String usrId, String ip)
    {
        userIps.put(usrId,ip);
    }

    public static void removeIpofUser(String usrId)
    {
        userIps.remove(usrId);
    }

    public static boolean isRoomExist(String roomId)
    {
        System.out.println(rooms);
        return rooms.containsKey(roomId);
    }

    public static void startServerSocket()
    {
        if(!MyServerSocket.isServerSocketStarted()) {
            new Thread(() -> MyServerSocket.start(new MyServerHandler())).start();
        }
    }

    public static void broadCastMsgToAll(String roomId, String msg, String username, String senderIp)
    {
        System.out.println("BroadCastMsgToAll: roomId:: "+roomId+ " Message " + msg + " from " + senderIp);
        System.out.println(":::"+clientsConn);
        try {
            if(clientsConn.containsKey(roomId))
            {
                HashSet<WebSocket> clients = clientsConn.get(roomId);
                if(clients.size()>1) {
                //if(!clients.isEmpty()) {
                    for (WebSocket client : clients) {
                        if (client.isOpen() && !((client.getRemoteSocketAddress().getAddress()).equals(senderIp))) {
                            client.send(msg+ChatUtil.USER+username);
                            System.out.println(msg+" sent");
                        }
                    }
                }
            }
            else
            {
                System.out.println("Room Not Found");
            }
        }
        catch (Exception e) {
            System.out.println("Server Error: Error Occurred in broadCastMsgToAll." + e);
        }
    }


    public static void addClientConnection(String roomId, WebSocket socket)
    {
        boolean isExist = false;
        try
        {
            if (clientsConn.containsKey(roomId))
            {
                for (WebSocket client : clientsConn.get(roomId)) {
                    if(((client.getRemoteSocketAddress().getAddress()).equals(socket.getRemoteSocketAddress().getAddress()))) {
                        isExist = true;
                        break;
                    }
                }
                if(!isExist) {
                    clientsConn.get(roomId).add(socket);
                }
            }
            else
            {
                HashSet<WebSocket> clients = new HashSet<>();
                clients.add(socket);
                clientsConn.put(roomId, clients);
            }
            System.out.println(clientsConn);
        } catch (Exception e) {
            System.err.println("Server Error: Error Occurred in addClientConnection." + e);
        }
    }

}
