package org.project;

import java.util.ArrayList;
import java.util.Hashtable;

public class ChatUtil {
    private static Hashtable<String, ArrayList<String>> rooms = new Hashtable<>();
    private static Hashtable<String, String> userIps = new Hashtable<>();
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
        }
        else
        {
            ArrayList<String> list = new ArrayList<>();
            list.add(userId);
            rooms.put(roomId, list);
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
}
