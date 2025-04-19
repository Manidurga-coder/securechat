package org.project;
import java.net.*;

public class ServerSocket {
    public static boolean start()
    {
        try(java.net.ServerSocket serverSocket = new java.net.ServerSocket(12335))
        {
            System.out.println("Server started on port 12335");
            Socket socket= serverSocket.accept();
            return true;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
