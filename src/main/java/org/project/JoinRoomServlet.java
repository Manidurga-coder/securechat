package org.project;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;

import jakarta.servlet.http.*;

public class JoinRoomServlet extends HttpServlet {

    @Override
    public void init() throws ServletException {
        super.init();
        ServerSocket.start();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String roomId = req.getParameter("room");

        if(!ChatUtil.isRoomExist(roomId))
        {
            resp.sendRedirect("NotExist.html");
        }
        InetAddress ip = InetAddress.getLocalHost();
        ChatUtil.addIpofUser(username, ip.getHostAddress());
        ChatUtil.setUserFromRoomId(roomId, username);
    }
}

