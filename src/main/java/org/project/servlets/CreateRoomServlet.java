package org.project.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.*;
import org.project.util.ChatUtil;


import java.io.IOException;
import java.util.UUID;

public class CreateRoomServlet extends HttpServlet {

    @Override
    public void init() throws ServletException {
        super.init();
        ChatUtil.startServerSocket();
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException,IOException {
        String username = req.getParameter("username");

        String roomId = UUID.randomUUID().toString().substring(0, 6); // Short 6-char room code
        System.out.println(roomId);
        HttpSession session = req.getSession();
        session.setAttribute("username", username);
        session.setAttribute("roomId", roomId);
        session.setAttribute("myIP", req.getRemoteAddr());
        ChatUtil.setUserFromRoomId(roomId, username);
        ChatUtil.addIpofUser(username, req.getRemoteAddr());


        resp.sendRedirect("Chat.jsp");
    }
}


