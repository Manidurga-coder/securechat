package org.project;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.UUID;

@WebServlet("/createroom")

public class CreateRoomServlet extends HttpServlet {
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException,IOException {
        String username = req.getParameter("username");
        String roomId = UUID.randomUUID().toString().substring(0, 6); // Short 6-char room code

        HttpSession session = req.getSession();
        session.setAttribute("username", username);
        session.setAttribute("roomId", roomId);

        getServletContext().setAttribute(roomId, username); // temporary room storage

        resp.sendRedirect("chat.jsp?room=" + roomId);
    }
}


