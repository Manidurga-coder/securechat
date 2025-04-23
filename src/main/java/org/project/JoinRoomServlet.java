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
        ChatUtil.startServerSocket();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String roomId = req.getParameter("room");
        System.out.println("Debug: Room " + roomId + " user added to the list ::"+username);

        if(!ChatUtil.isRoomExist(roomId))
        {
            resp.sendRedirect("NotExist.html");
            return;
        }
        ChatUtil.addIpofUser(username, req.getRemoteAddr());
        ChatUtil.setUserFromRoomId(roomId, username);
        HttpSession session = req.getSession();
        session.setAttribute("username", username);
        session.setAttribute("roomId", roomId);
        session.setAttribute("myIP", req.getRemoteAddr());
        System.out.println("JoinRoomServlet: username " + username + " room " + roomId);
        resp.sendRedirect("Chat.jsp");
    }
}

